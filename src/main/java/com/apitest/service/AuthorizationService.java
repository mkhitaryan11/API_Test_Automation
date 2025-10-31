package com.apitest.service;

import com.apitest.client.AuthApiClient;
import com.apitest.models.response.auth.VerifyResponse;
import com.apitest.models.response.auth.AuthResponse;
import com.apitest.utils.ResponseValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service class for handling 2-step authorization operations
 * Refactored to use AuthApiClient for better separation of concerns
 */
public class AuthorizationService {
    
    private static final Logger logger = LogManager.getLogger(AuthorizationService.class);

    /**
     * Step 1: Initiate authorization by sending email
     * @param email User's email address
     * @return true if initiation was successful
     */
    public static boolean initiateAuthorization(String email) {
        logger.info("Initiating authorization for email: {}", email);
        
        try {
            // Use AuthApiClient for the API call
            Response response = AuthApiClient.initiateAuth(email);
            
            // Validate response
            ResponseValidator.validateStatusCode(response, 200);
            ResponseValidator.validateContentType(response, "application/json");
            ResponseValidator.validateResponseTime(response, AuthApiClient.getAuthResponseTimeLimit());
            
            logger.info("Authorization initiation successful for email: {}", email);
            return true;
            
        } catch (Exception e) {
            logger.error("Authorization initiation failed for email: {} - {}", email, e.getMessage());
            return false;
        }
    }

    /**
     * Step 2: Verify authorization with email and code
     * @param email User's email address
     * @param code Verification code
     * @return Authorization token
     */
    public static VerifyResponse verifyAuthorization(String email, String code) {
        logger.info("Verifying authorization for email: {}", email);
        
        try {
            // Use AuthApiClient for the API call
            Response response = AuthApiClient.verifyAuth(email, code);
            
            // Validate response
            ResponseValidator.validateStatusCode(response, 200);
            ResponseValidator.validateContentType(response, "application/json");
            ResponseValidator.validateResponseBodyNotEmpty(response);
            ResponseValidator.validateResponseTime(response, AuthApiClient.getAuthResponseTimeLimit());
            
            // Parse response
            AuthResponse authResponse = response.as(AuthResponse.class);
            
            // Check for errors
            if (authResponse.hasError()) {
                logger.error("Authorization verification failed with error: {} - {}", 
                    authResponse.getError(), authResponse.getMessage());
                throw new RuntimeException("Authorization failed: " + authResponse.getError());
            }
            
            // Validate token
            if (!authResponse.hasValidToken()) {
                logger.error("Authorization response does not contain valid access token");
                throw new RuntimeException("No valid access token received");
            }
            
            // Create token object with default 1 hour expiration
            VerifyResponse verifyResponse = new VerifyResponse(
                authResponse.getAccessToken(),
                authResponse.getRefreshToken(),
                authResponse.getUserInfo(),
                3600 // 1 hour default expiration
            );
            
            logger.info("Authorization verification successful for email: {}", email);
            return verifyResponse;
            
        } catch (Exception e) {
            logger.error("Authorization verification failed for email: {} - {}", email, e.getMessage());
            throw new RuntimeException("Authorization verification failed: " + e.getMessage(), e);
        }
    }

    /**
     * Complete 2-step authorization flow
     * Automatically stores the token in TokenManager
     * @param email User's email address
     * @param code Verification code
     * @return Authorization token (also stored in TokenManager)
     */
    public static VerifyResponse completeAuthorization(String email, String code) {
        logger.info("Starting complete authorization flow for email: {}", email);
        
        try {
            // Use AuthApiClient's complete flow method
            AuthResponse authResponse = AuthApiClient.completeAuthFlow(email, code);
            
            // Create token object
            VerifyResponse verifyResponse = new VerifyResponse(
                authResponse.getAccessToken(),
                authResponse.getRefreshToken(),
                authResponse.getUserInfo(),
                3600 // 1 hour default expiration
            );
            
            // Store token in TokenManager automatically
            TokenManager.getInstance().setToken(verifyResponse);
            
            logger.info("Complete authorization flow successful for email: {} - Token stored", email);
            return verifyResponse;
            
        } catch (Exception e) {
            logger.error("Complete authorization flow failed for email: {} - {}", email, e.getMessage());
            throw new RuntimeException("Complete authorization flow failed: " + e.getMessage(), e);
        }
    }

    /**
     * Refresh authorization token
     * @param refreshToken Refresh token
     * @return New authorization token
     */
    public static VerifyResponse refreshToken(String refreshToken) {
        logger.info("Attempting to refresh authorization token");
        
        try {
            // Use AuthApiClient for the API call
            Response response = AuthApiClient.refreshToken(refreshToken);
            
            // Validate response
            ResponseValidator.validateStatusCode(response, 200);
            ResponseValidator.validateContentType(response, "application/json");
            ResponseValidator.validateResponseTime(response, AuthApiClient.getAuthResponseTimeLimit());
            
            // Parse response
            AuthResponse authResponse = response.as(AuthResponse.class);
            
            // Check for errors
            if (authResponse.hasError()) {
                logger.error("Token refresh failed with error: {} - {}", 
                    authResponse.getError(), authResponse.getMessage());
                throw new RuntimeException("Token refresh failed: " + authResponse.getError());
            }
            
            // Validate token
            if (!authResponse.hasValidToken()) {
                logger.error("Token refresh response does not contain valid access token");
                throw new RuntimeException("No valid access token received from refresh");
            }
            
            // Create new token object
            VerifyResponse verifyResponse = new VerifyResponse(
                authResponse.getAccessToken(),
                authResponse.getRefreshToken(),
                authResponse.getUserInfo(),
                3600 // 1 hour default expiration
            );
            
            logger.info("Token refresh successful");
            return verifyResponse;
            
        } catch (Exception e) {
            logger.error("Token refresh failed: {}", e.getMessage());
            throw new RuntimeException("Token refresh failed: " + e.getMessage(), e);
        }
    }

    /**
     * Logout and invalidate token
     * @param verifyResponse Token to invalidate
     * @return true if logout successful
     */
    public static boolean logout(VerifyResponse verifyResponse) {
        logger.info("Attempting to logout");
        
        try {
            // Use AuthApiClient for the API call
            Response response = AuthApiClient.logout();
            
            // Validate response (logout typically returns 200 or 204)
            if (response.getStatusCode() == 200 || response.getStatusCode() == 204) {
                // Invalidate token
                if (verifyResponse != null) {
                    verifyResponse.invalidate();
                }
                
                logger.info("Logout successful");
                return true;
            } else {
                logger.error("Logout failed with status code: {}", response.getStatusCode());
                // Even if logout request fails, invalidate the local token
                if (verifyResponse != null) {
                    verifyResponse.invalidate();
                }
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Logout failed: {}", e.getMessage());
            // Even if logout request fails, invalidate the local token
            if (verifyResponse != null) {
                verifyResponse.invalidate();
            }
            return false;
        }
    }

    /**
     * Validate if a token is still valid
     * @param verifyResponse Token to validate
     * @return true if token is valid
     */
    public static boolean validateToken(VerifyResponse verifyResponse) {
        if (verifyResponse == null) {
            return false;
        }
        
        // Check if token is valid and not expired
        boolean isValid = verifyResponse.isTokenValid();
        
        if (!isValid) {
            logger.info("Token validation failed - token is invalid or expired");
        } else {
            logger.debug("Token validation successful");
        }
        
        return isValid;
    }

    /**
     * Get auth response time limit from AuthApiClient
     * @return Response time limit in milliseconds
     */
    public static long getAuthResponseTimeLimit() {
        return AuthApiClient.getAuthResponseTimeLimit();
    }

    /**
     * Get all available auth endpoints
     * @return Array of auth endpoint strings
     */
    public static String[] getAuthEndpoints() {
        return AuthApiClient.getAuthEndpoints();
    }
}
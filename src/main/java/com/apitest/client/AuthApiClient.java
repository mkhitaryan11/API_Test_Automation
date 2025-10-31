package com.apitest.client;

import com.apitest.models.response.auth.AuthResponse;
import com.apitest.models.request.auth.InitiateRequest;
import com.apitest.models.request.auth.VerifyRequest;
import com.apitest.utils.ResponseValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * API Client for authentication endpoints
 * Provides dedicated methods for auth-related API calls following best practices
 */
public class AuthApiClient {
    
    private static final Logger logger = LogManager.getLogger(AuthApiClient.class);
    
    // Auth endpoints
    private static final String INITIATE_ENDPOINT = "/auth/initiate";
    private static final String VERIFY_ENDPOINT = "/auth/verify";
    private static final String REFRESH_ENDPOINT = "/auth/refresh";
    private static final String LOGOUT_ENDPOINT = "/auth/logout";
    
    // Default response time limit for auth operations
    private static final long AUTH_RESPONSE_TIME_LIMIT = 15000; // 15 seconds

    /**
     * Initiate authentication process by sending email
     * @param email User's email address
     * @return Response from initiate endpoint
     */
    public static Response initiateAuth(String email) {
        logger.info("Initiating authentication for email: {}", email);
        
        InitiateRequest initiateRequest = new InitiateRequest(email);
        Response response = ApiClient.post(INITIATE_ENDPOINT, initiateRequest);
        
        // Log request details
        logger.info("Initiate auth request sent to: {} with email: {}", INITIATE_ENDPOINT, email);
        logger.info("Initiate auth response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Initiate authentication process using InitiateRequest object
     * @param initiateRequest InitiateRequest object containing email
     * @return Response from initiate endpoint
     */
    public static Response initiateAuth(InitiateRequest initiateRequest) {
        logger.info("Initiating authentication with request object for email: {}", 
                   initiateRequest.getEmail());
        
        Response response = ApiClient.post(INITIATE_ENDPOINT, initiateRequest);
        
        // Log request details
        logger.info("Initiate auth request sent to: {}", INITIATE_ENDPOINT);
        logger.info("Initiate auth response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Verify authentication with email and verification code
     * @param email User's email address
     * @param code Verification code
     * @return Response from verify endpoint
     */
    public static Response verifyAuth(String email, String code) {
        logger.info("Verifying authentication for email: {} with code: {}", email, code);
        
        VerifyRequest verifyRequest = new VerifyRequest(email, code);
        Response response = ApiClient.post(VERIFY_ENDPOINT, verifyRequest);
        
        // Log request details
        logger.info("Verify auth request sent to: {}", VERIFY_ENDPOINT);
        logger.info("Verify auth response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Verify authentication using VerifyRequest object
     * @param verifyRequest VerifyRequest object containing email and code
     * @return Response from verify endpoint
     */
    public static Response verifyAuth(VerifyRequest verifyRequest) {
        logger.info("Verifying authentication with request object for email: {}", 
                   verifyRequest.getEmail());
        
        Response response = ApiClient.post(VERIFY_ENDPOINT, verifyRequest);
        
        // Log request details
        logger.info("Verify auth request sent to: {}", VERIFY_ENDPOINT);
        logger.info("Verify auth response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Refresh authentication token using refresh token
     * @param refreshToken The refresh token
     * @return Response from refresh endpoint
     */
    public static Response refreshToken(String refreshToken) {
        logger.info("Refreshing authentication token");
        
        String requestBody = String.format("{\"refresh\":\"%s\"}", refreshToken);
        Response response = ApiClient.post(REFRESH_ENDPOINT, requestBody);
        
        // Log request details
        logger.info("Refresh token request sent to: {}", REFRESH_ENDPOINT);
        logger.info("Refresh token response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Refresh authentication token using refresh token object
     * @param refreshTokenRequest Object containing refresh token
     * @return Response from refresh endpoint
     */
    public static Response refreshToken(Object refreshTokenRequest) {
        logger.info("Refreshing authentication token with request object");
        
        Response response = ApiClient.post(REFRESH_ENDPOINT, refreshTokenRequest);
        
        // Log request details
        logger.info("Refresh token request sent to: {}", REFRESH_ENDPOINT);
        logger.info("Refresh token response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Logout and invalidate current session
     * @return Response from logout endpoint
     */
    public static Response logout() {
        logger.info("Performing logout");
        
        Response response = ApiClient.post(LOGOUT_ENDPOINT, "{}");
        
        // Log request details
        logger.info("Logout request sent to: {}", LOGOUT_ENDPOINT);
        logger.info("Logout response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Complete 2-step authentication flow (initiate + verify)
     * @param email User's email address
     * @param code Verification code
     * @return AuthResponse containing tokens and user info
     */
    public static AuthResponse completeAuthFlow(String email, String code) {
        logger.info("Starting complete authentication flow for email: {}", email);
        
        try {
            // Step 1: Initiate authentication
            logger.info("Step 1: Initiating authentication");
            Response initiateResponse = initiateAuth(email);
            
            // Validate initiate response
            ResponseValidator.validateStatusCode(initiateResponse, 200);
            ResponseValidator.validateContentType(initiateResponse, "application/json");
            ResponseValidator.validateResponseTime(initiateResponse, AUTH_RESPONSE_TIME_LIMIT);
            
            logger.info("Step 1 completed successfully - Authentication initiated");
            
            // Step 2: Verify authentication
            logger.info("Step 2: Verifying authentication");
            Response verifyResponse = verifyAuth(email, code);
            
            // Validate verify response
            ResponseValidator.validateStatusCode(verifyResponse, 200);
            ResponseValidator.validateContentType(verifyResponse, "application/json");
            ResponseValidator.validateResponseBodyNotEmpty(verifyResponse);
            ResponseValidator.validateResponseTime(verifyResponse, AUTH_RESPONSE_TIME_LIMIT);
            
            // Parse and return auth response
            AuthResponse authResponse = verifyResponse.as(AuthResponse.class);
            
            // Validate auth response content
            if (authResponse == null) {
                throw new RuntimeException("Failed to parse authentication response");
            }
            
            if (authResponse.hasError()) {
                throw new RuntimeException("Authentication failed: " + authResponse.getError());
            }
            
            if (!authResponse.hasValidToken()) {
                throw new RuntimeException("Authentication response does not contain valid access token");
            }
            
            logger.info("Step 2 completed successfully - Authentication verified and tokens received");
            logger.info("Complete authentication flow successful for email: {}", email);
            
            return authResponse;
            
        } catch (Exception e) {
            logger.error("Complete authentication flow failed for email: {} - {}", email, e.getMessage());
            throw new RuntimeException("Authentication flow failed: " + e.getMessage(), e);
        }
    }

    /**
     * Validate authentication response
     * @param response HTTP response from auth endpoint
     * @return true if response is valid, false otherwise
     */
    public static boolean isValidAuthResponse(Response response) {
        if (response == null) {
            return false;
        }
        
        // Check status code
        if (response.getStatusCode() != 200) {
            return false;
        }
        
        // Check content type
        String contentType = response.getContentType();
        if (contentType == null || !contentType.contains("application/json")) {
            return false;
        }
        
        // Check response time
        if (response.getTime() > AUTH_RESPONSE_TIME_LIMIT) {
            return false;
        }
        
        // Try to parse as AuthResponse
        try {
            AuthResponse authResponse = response.as(AuthResponse.class);
            return authResponse != null && !authResponse.hasError() && authResponse.hasValidToken();
        } catch (Exception e) {
            logger.warn("Failed to parse auth response: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get response time limit for auth operations
     * @return Response time limit in milliseconds
     */
    public static long getAuthResponseTimeLimit() {
        return AUTH_RESPONSE_TIME_LIMIT;
    }

    /**
     * Get all auth endpoints
     * @return Array of auth endpoint strings
     */
    public static String[] getAuthEndpoints() {
        return new String[]{
            INITIATE_ENDPOINT,
            VERIFY_ENDPOINT,
            REFRESH_ENDPOINT,
            LOGOUT_ENDPOINT
        };
    }
}

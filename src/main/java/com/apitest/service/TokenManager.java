package com.apitest.service;

import com.apitest.models.response.auth.VerifyResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton service for managing a single authorization token
 * Simple and clean - stores only one token at a time
 * When you call authorizeUserWithCredentials, it replaces the current token
 */
public class TokenManager {
    
    private static final Logger logger = LogManager.getLogger(TokenManager.class);
    private static volatile TokenManager instance;
    
    // Single token storage
    private VerifyResponse currentToken;

    private TokenManager() {
        logger.debug("TokenManager initialized");
    }

    /**
     * Get singleton instance of TokenManager
     * @return TokenManager instance
     */
    public static TokenManager getInstance() {
        if (instance == null) {
            synchronized (TokenManager.class) {
                if (instance == null) {
                    instance = new TokenManager();
                }
            }
        }
        return instance;
    }

    /**
     * Store the current token (replaces any existing token)
     * @param verifyResponse Authorization token from verify response
     */
    public void setToken(VerifyResponse verifyResponse) {
        logger.info("Setting new token");
        this.currentToken = verifyResponse;
    }

    /**
     * Get the current token
     * @return Current authorization token or null if not set
     */
    public VerifyResponse getToken() {
        if (currentToken != null) {
            logger.debug("Retrieved current token");
        } else {
            logger.warn("No token currently set");
        }
        return currentToken;
    }

    /**
     * Get the authorization header value for the current token
     * @return Authorization header value (e.g., "Bearer eyJhbGc...") or null if no token
     */
    public String getAuthorizationHeader() {
        if (currentToken != null && currentToken.getAccessToken() != null) {
            return "Bearer " + currentToken.getAccessToken();
        }
        logger.warn("No valid token available for authorization header");
        return null;
    }

    /**
     * Check if a valid token is currently set
     * @return true if valid token exists
     */
    public boolean hasValidToken() {
        return currentToken != null && 
               currentToken.getAccessToken() != null && 
               !currentToken.getAccessToken().isEmpty();
    }

    /**
     * Clear the current token
     */
    public void clearToken() {
        logger.info("Clearing current token");
        if (currentToken != null) {
            currentToken.invalidate();
            currentToken = null;
        }
    }

    /**
     * Get the current user info from the token
     * @return User info object or null if no token
     */
    public Object getUserInfo() {
        return currentToken != null ? currentToken.getUserInfo() : null;
    }

    /**
     * Get the refresh token
     * @return Refresh token or null if no token
     */
    public String getRefreshToken() {
        return currentToken != null ? currentToken.getRefreshToken() : null;
    }

    /**
     * Check if token has a refresh token available
     * @return true if refresh token exists
     */
    public boolean hasRefreshToken() {
        return currentToken != null && 
               currentToken.getRefreshToken() != null && 
               !currentToken.getRefreshToken().isEmpty();
    }
}

package com.apitest.client;

import com.apitest.models.entity.user.User;
import com.apitest.models.request.user.UserUpdateRequest;
import com.apitest.utils.ResponseValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * API Client for User endpoints
 * Provides dedicated methods for user-related API calls
 */
public class UserApiClient {
    
    private static final Logger logger = LogManager.getLogger(UserApiClient.class);
    
    // User endpoints
    private static final String ME_ENDPOINT = "/users/me";
    private static final String USER_BY_ID_ENDPOINT = "/users/{userId}";
    private static final String HOTEL_ADMIN_ENDPOINT = "/users/me/hotel-admin";
    private static final String AVATAR_ENDPOINT = "/users/me/avatar";
    private static final String MY_STAYS_ENDPOINT = "/users/me/stays";
    
    // Default response time limit for user operations
    private static final long USER_RESPONSE_TIME_LIMIT = 10000; // 10 seconds

    /**
     * Get current user profile (me)
     * @return Response from GET /users/me endpoint
     */
    public static Response getMe() {
        logger.info("Getting current user profile");
        
        Response response = ApiClient.get(ME_ENDPOINT);
        
        logger.info("Get me request sent to: {}", ME_ENDPOINT);
        logger.info("Get me response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Update current user profile
     * @param updateRequest UserUpdateRequest object
     * @return Response from PATCH /users/me endpoint
     */
    public static Response updateMe(UserUpdateRequest updateRequest) {
        logger.info("Updating current user profile");
        
        Response response = ApiClient.patch(ME_ENDPOINT, updateRequest);
        
        logger.info("Update me request sent to: {}", ME_ENDPOINT);
        logger.info("Update me response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get user by ID
     * @param userId User ID
     * @return Response from GET /users/{userId} endpoint
     */
    public static Response getUserById(String userId) {
        logger.info("Getting user by ID: {}", userId);
        
        String endpoint = USER_BY_ID_ENDPOINT.replace("{userId}", userId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get user by ID request sent to: {}", endpoint);
        logger.info("Get user by ID response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get hotel admin information for current user
     * @return Response from GET /users/me/hotel-admin endpoint
     */
    public static Response getHotelAdmin() {
        logger.info("Getting hotel admin information for current user");
        
        Response response = ApiClient.get(HOTEL_ADMIN_ENDPOINT);
        
        logger.info("Get hotel admin request sent to: {}", HOTEL_ADMIN_ENDPOINT);
        logger.info("Get hotel admin response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Upload avatar for current user
     * @param filePath Path to the image file
     * @return Response from POST /users/me/avatar endpoint
     */
    public static Response uploadAvatar(String filePath) {
        logger.info("Uploading avatar for current user");
        
        Response response = ApiClient.postMultipart(AVATAR_ENDPOINT, filePath);
        
        logger.info("Upload avatar request sent to: {}", AVATAR_ENDPOINT);
        logger.info("Upload avatar response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Delete avatar for current user
     * @return Response from DELETE /users/me/avatar endpoint
     */
    public static Response deleteAvatar() {
        logger.info("Deleting avatar for current user");
        
        Response response = ApiClient.delete(AVATAR_ENDPOINT);
        
        logger.info("Delete avatar request sent to: {}", AVATAR_ENDPOINT);
        logger.info("Delete avatar response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get current user's active stays
     * @return Response from GET /users/me/stays endpoint
     */
    public static Response getMyStays() {
        logger.info("Getting current user's active stays");
        
        Response response = ApiClient.get(MY_STAYS_ENDPOINT);
        
        logger.info("Get my stays request sent to: {}", MY_STAYS_ENDPOINT);
        logger.info("Get my stays response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get current user profile with validation
     * @return User object if successful, null otherwise
     */
    public static User getMeValidated() {
        logger.info("Getting current user profile with validation");
        
        try {
            Response response = getMe();
            
            // Validate response
            ResponseValidator.validateStatusCode(response, 200);
            ResponseValidator.validateContentType(response, "application/json");
            ResponseValidator.validateResponseBodyNotEmpty(response);
            ResponseValidator.validateResponseTime(response, USER_RESPONSE_TIME_LIMIT);
            
            // Parse and return user
            User user = response.as(User.class);
            logger.info("Successfully retrieved current user profile");
            return user;
            
        } catch (Exception e) {
            logger.error("Failed to get current user profile: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Update current user profile with validation
     * @param updateRequest UserUpdateRequest object
     * @return User object if successful, null otherwise
     */
    public static User updateMeValidated(UserUpdateRequest updateRequest) {
        logger.info("Updating current user profile with validation");
        
        try {
            Response response = updateMe(updateRequest);
            
            // Validate response
            ResponseValidator.validateStatusCode(response, 200);
            ResponseValidator.validateContentType(response, "application/json");
            ResponseValidator.validateResponseBodyNotEmpty(response);
            ResponseValidator.validateResponseTime(response, USER_RESPONSE_TIME_LIMIT);
            
            // Parse and return updated user
            User user = response.as(User.class);
            logger.info("Successfully updated current user profile");
            return user;
            
        } catch (Exception e) {
            logger.error("Failed to update current user profile: {}", e.getMessage());
            return null;
        }
    }

//    /**
//     * Get hotel admin information with validation
//     * @return HotelAdmin object if successful, null otherwise
//     */
//    public static HotelAdmin getHotelAdminValidated() {
//        logger.info("Getting hotel admin information with validation");
//
//        try {
//            Response response = getHotelAdmin();
//
//            // Validate response
//            ResponseValidator.validateStatusCode(response, 200);
//            ResponseValidator.validateContentType(response, "application/json");
//            ResponseValidator.validateResponseBodyNotEmpty(response);
//            ResponseValidator.validateResponseTime(response, USER_RESPONSE_TIME_LIMIT);
//
//            // Parse and return hotel admin
//            HotelAdmin hotelAdmin = response.as(HotelAdmin.class);
//            logger.info("Successfully retrieved hotel admin information");
//            return hotelAdmin;
//
//        } catch (Exception e) {
//            logger.error("Failed to get hotel admin information: {}", e.getMessage());
//            return null;
//        }
//    }

    /**
     * Get response time limit for user operations
     * @return Response time limit in milliseconds
     */
    public static long getUserResponseTimeLimit() {
        return USER_RESPONSE_TIME_LIMIT;
    }

    /**
     * Get all user endpoints
     * @return Array of user endpoint strings
     */
    public static String[] getUserEndpoints() {
        return new String[]{
            ME_ENDPOINT,
            USER_BY_ID_ENDPOINT,
            HOTEL_ADMIN_ENDPOINT,
            AVATAR_ENDPOINT
        };
    }
}

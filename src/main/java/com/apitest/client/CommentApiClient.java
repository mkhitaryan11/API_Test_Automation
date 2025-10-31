package com.apitest.client;

//import com.apitest.models.comment.Comment;
import com.apitest.models.request.comment.CommentCreateRequest;
import com.apitest.utils.ResponseValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * API Client for Comment endpoints
 * Provides dedicated methods for comment-related API calls
 */
public class CommentApiClient {
    
    private static final Logger logger = LogManager.getLogger(CommentApiClient.class);
    
    // Comment endpoints
    private static final String COMMENTS_ENDPOINT = "/events/{eventId}/comments";
    private static final String COMMENT_BY_ID_ENDPOINT = "/events/{eventId}/comments/{commentId}";
    
    // Default response time limit for comment operations
    private static final long COMMENT_RESPONSE_TIME_LIMIT = 10000; // 10 seconds

    /**
     * Get comments for an event
     * @param eventId Event ID
     * @param authorId Author ID filter (optional)
     * @param limit Number of comments to return (optional)
     * @return Response from GET /events/{eventId}/comments endpoint
     */
    public static Response getComments(String eventId, String authorId, Integer limit) {
        logger.info("Getting comments for event: {}", eventId);
        
        String endpoint = COMMENTS_ENDPOINT.replace("{eventId}", eventId);
        StringBuilder queryParams = new StringBuilder();
        
        if (authorId != null || limit != null) {
            queryParams.append("?");
            boolean first = true;
            
            if (authorId != null) {
                queryParams.append("author_id=").append(authorId);
                first = false;
            }
            
            if (limit != null) {
                if (!first) queryParams.append("&");
                queryParams.append("limit=").append(limit);
            }
        }
        
        String fullEndpoint = endpoint + queryParams.toString();
        Response response = ApiClient.get(fullEndpoint);
        
        logger.info("Get comments request sent to: {}", fullEndpoint);
        logger.info("Get comments response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get comments for an event (default parameters)
     * @param eventId Event ID
     * @return Response from GET /events/{eventId}/comments endpoint
     */
    public static Response getComments(String eventId) {
        return getComments(eventId, null, 20);
    }

    /**
     * Create a new comment
     * @param eventId Event ID
     * @param createRequest CommentCreateRequest object
     * @return Response from POST /events/{eventId}/comments endpoint
     */
    public static Response createComment(String eventId, CommentCreateRequest createRequest) {
        logger.info("Creating comment for event: {}", eventId);
        
        String endpoint = COMMENTS_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.post(endpoint, createRequest);
        
        logger.info("Create comment request sent to: {}", endpoint);
        logger.info("Create comment response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Create a new comment with direct body text
     * @param eventId Event ID
     * @param body Comment body text
     * @return Response from POST /events/{eventId}/comments endpoint
     */
    public static Response createComment(String eventId, String body) {
        logger.info("Creating comment for event: {} with body: {}", eventId, body);
        
        CommentCreateRequest createRequest = CommentCreateRequest.builder()
                .body(body)
                .build();
        
        return createComment(eventId, createRequest);
    }

    /**
     * Delete a comment
     * @param eventId Event ID
     * @param commentId Comment ID
     * @return Response from DELETE /events/{eventId}/comments/{commentId} endpoint
     */
    public static Response deleteComment(String eventId, String commentId) {
        logger.info("Deleting comment: {} for event: {}", commentId, eventId);
        
        String endpoint = COMMENT_BY_ID_ENDPOINT.replace("{eventId}", eventId).replace("{commentId}", commentId);
        Response response = ApiClient.delete(endpoint);
        
        logger.info("Delete comment request sent to: {}", endpoint);
        logger.info("Delete comment response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== VALIDATED METHODS ====================

//    /**
//     * Get comments with validation
//     * @param eventId Event ID
//     * @return List of Comment objects if successful, null otherwise
//     */
//    public static List<Comment> getCommentsValidated(String eventId) {
//        logger.info("Getting comments with validation for event: {}", eventId);
//
//        try {
//            Response response = getComments(eventId);
//
//            // Validate response
//            ResponseValidator.validateStatusCode(response, 200);
//            ResponseValidator.validateContentType(response, "application/json");
//            ResponseValidator.validateResponseBodyNotEmpty(response);
//            ResponseValidator.validateResponseTime(response, COMMENT_RESPONSE_TIME_LIMIT);
//
//            // Parse and return comments
//            List<Comment> comments = response.jsonPath().getList("", Comment.class);
//            logger.info("Successfully retrieved {} comments for event: {}", comments.size(), eventId);
//            return comments;
//
//        } catch (Exception e) {
//            logger.error("Failed to get comments for event: {} - {}", eventId, e.getMessage());
//            return null;
//        }
//    }
//
//    /**
//     * Create comment with validation
//     * @param eventId Event ID
//     * @param createRequest CommentCreateRequest object
//     * @return Comment object if successful, null otherwise
//     */
//    public static Comment createCommentValidated(String eventId, CommentCreateRequest createRequest) {
//        logger.info("Creating comment with validation for event: {}", eventId);
//
//        try {
//            Response response = createComment(eventId, createRequest);
//
//            // Validate response
//            ResponseValidator.validateStatusCode(response, 200);
//            ResponseValidator.validateContentType(response, "application/json");
//            ResponseValidator.validateResponseBodyNotEmpty(response);
//            ResponseValidator.validateResponseTime(response, COMMENT_RESPONSE_TIME_LIMIT);
//
//            // Parse and return comment
//            Comment comment = response.as(Comment.class);
//            logger.info("Successfully created comment for event: {}", eventId);
//            return comment;
//
//        } catch (Exception e) {
//            logger.error("Failed to create comment for event: {} - {}", eventId, e.getMessage());
//            return null;
//        }
//    }
//
//    /**
//     * Create comment with validation using direct body text
//     * @param eventId Event ID
//     * @param body Comment body text
//     * @return Comment object if successful, null otherwise
//     */
//    public static Comment createCommentValidated(String eventId, String body) {
//        logger.info("Creating comment with validation for event: {} with body: {}", eventId, body);
//
//        CommentCreateRequest createRequest = CommentCreateRequest.builder()
//                .body(body)
//                .build();
//
//        return createCommentValidated(eventId, createRequest);
//    }

    /**
     * Get response time limit for comment operations
     * @return Response time limit in milliseconds
     */
    public static long getCommentResponseTimeLimit() {
        return COMMENT_RESPONSE_TIME_LIMIT;
    }

    /**
     * Get all comment endpoints
     * @return Array of comment endpoint strings
     */
    public static String[] getCommentEndpoints() {
        return new String[]{
            COMMENTS_ENDPOINT,
            COMMENT_BY_ID_ENDPOINT
        };
    }
}

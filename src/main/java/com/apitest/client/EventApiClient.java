package com.apitest.client;

//import com.apitest.models.event.*;
import com.apitest.models.request.event.EventCreateRequest;
import com.apitest.utils.ResponseValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * API Client for Event endpoints
 * Provides dedicated methods for event-related API calls
 */
public class EventApiClient {
    
    private static final Logger logger = LogManager.getLogger(EventApiClient.class);
    
    // Event endpoints
    private static final String EVENTS_ENDPOINT = "/events";
    private static final String EVENT_BY_ID_ENDPOINT = "/events/{eventId}";
    private static final String ATTEND_EVENT_ENDPOINT = "/events/{eventId}/attend";
    private static final String LEAVE_EVENT_ENDPOINT = "/events/{eventId}/attend";
    private static final String CANCEL_EVENT_ENDPOINT = "/events/{eventId}/cancel";
    private static final String ARCHIVE_EVENT_ENDPOINT = "/events/{eventId}/archive";
    private static final String LIKE_EVENT_ENDPOINT = "/events/{eventId}/like";
    private static final String LIKES_COUNT_ENDPOINT = "/events/{eventId}/likes/count";
    private static final String EVENT_IMAGES_ENDPOINT = "/events/{eventId}/images";
    private static final String EVENT_IMAGE_BY_ID_ENDPOINT = "/events/{eventId}/images/{imageId}";
    
    // Recurring event endpoints
    private static final String RECURRING_EVENTS_ENDPOINT = "/events/recurring";
    private static final String RECURRING_EVENT_BY_ID_ENDPOINT = "/events/recurring/{recurringEventId}";
    private static final String RECURRING_INSTANCES_ENDPOINT = "/events/recurring/{recurringEventId}/instances";
    private static final String ALL_RECURRING_INSTANCES_ENDPOINT = "/events/recurring/instances";
    private static final String RECURRING_EXCEPTIONS_ENDPOINT = "/events/recurring/{recurringEventId}/exceptions";
    private static final String RRULE_EXAMPLES_ENDPOINT = "/events/recurring/rrule-examples";
    
    // Default response time limit for event operations
    private static final long EVENT_RESPONSE_TIME_LIMIT = 10000; // 10 seconds

    // ==================== EVENT ENDPOINTS ====================

    /**
     * Create a new event
     * @param createRequest EventCreateRequest object
     * @return Response from POST /events endpoint
     */
    public static Response createEvent(EventCreateRequest createRequest) {
        logger.info("Creating new event: {}", createRequest.getName());
        
        Response response = ApiClient.post(EVENTS_ENDPOINT, createRequest);
        
        logger.info("Create event request sent to: {}", EVENTS_ENDPOINT);
        logger.info("Create event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get events with filtering
     * @param hotelId Hotel ID filter
     * @param creatorUserId Creator user ID filter
     * @param startDate Start date filter
     * @param endDate End date filter
     * @param limit Number of events to return
     * @param offset Number of events to skip
     * @return Response from GET /events endpoint
     */
    public static Response getEvents(String hotelId, String creatorUserId, String startDate, 
                                   String endDate, Integer limit, Integer offset) {
        logger.info("Getting events with filters");
        
        StringBuilder endpoint = new StringBuilder(EVENTS_ENDPOINT + "?");
        boolean first = true;
        
        if (hotelId != null) {
            endpoint.append(first ? "" : "&").append("hotel_id=").append(hotelId);
            first = false;
        }
        if (creatorUserId != null) {
            endpoint.append(first ? "" : "&").append("creator_user_id=").append(creatorUserId);
            first = false;
        }
        if (startDate != null) {
            endpoint.append(first ? "" : "&").append("start_date=").append(startDate);
            first = false;
        }
        if (endDate != null) {
            endpoint.append(first ? "" : "&").append("end_date=").append(endDate);
            first = false;
        }
        if (limit != null) {
            endpoint.append(first ? "" : "&").append("limit=").append(limit);
            first = false;
        }
        if (offset != null) {
            endpoint.append(first ? "" : "&").append("offset=").append(offset);
        }
        
        Response response = ApiClient.get(endpoint.toString());
        
        logger.info("Get events request sent to: {}", endpoint);
        logger.info("Get events response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get events with hotel filter only
     * @param hotelId Hotel ID filter
     * @return Response from GET /events endpoint
     */
    public static Response getEvents(String hotelId) {
        return getEvents(hotelId, null, null, null, 20, 0);
    }

    /**
     * Get event by ID
     * @param eventId Event ID
     * @return Response from GET /events/{eventId} endpoint
     */
    public static Response getEventById(String eventId) {
        logger.info("Getting event by ID: {}", eventId);
        
        String endpoint = EVENT_BY_ID_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get event by ID request sent to: {}", endpoint);
        logger.info("Get event by ID response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Update event
     * @param eventId Event ID
     * @param updateRequest EventCreateRequest object (used for updates too)
     * @return Response from PATCH /events/{eventId} endpoint
     */
    public static Response updateEvent(String eventId, EventCreateRequest updateRequest) {
        logger.info("Updating event: {}", eventId);
        
        String endpoint = EVENT_BY_ID_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.patch(endpoint, updateRequest);
        
        logger.info("Update event request sent to: {}", endpoint);
        logger.info("Update event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Attend event
     * @param eventId Event ID
     * @return Response from POST /events/{eventId}/attend endpoint
     */
    public static Response attendEvent(String eventId) {
        logger.info("Attending event: {}", eventId);
        
        String endpoint = ATTEND_EVENT_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.post(endpoint, "");
        
        logger.info("Attend event request sent to: {}", endpoint);
        logger.info("Attend event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Leave event
     * @param eventId Event ID
     * @return Response from DELETE /events/{eventId}/attend endpoint
     */
    public static Response leaveEvent(String eventId) {
        logger.info("Leaving event: {}", eventId);
        
        String endpoint = LEAVE_EVENT_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.delete(endpoint);
        
        logger.info("Leave event request sent to: {}", endpoint);
        logger.info("Leave event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Cancel event
     * @param eventId Event ID
     * @return Response from POST /events/{eventId}/cancel endpoint
     */
    public static Response cancelEvent(String eventId) {
        logger.info("Cancelling event: {}", eventId);
        
        String endpoint = CANCEL_EVENT_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.post(endpoint, "");
        
        logger.info("Cancel event request sent to: {}", endpoint);
        logger.info("Cancel event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Archive event
     * @param eventId Event ID
     * @return Response from POST /events/{eventId}/archive endpoint
     */
    public static Response archiveEvent(String eventId) {
        logger.info("Archiving event: {}", eventId);
        
        String endpoint = ARCHIVE_EVENT_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.post(endpoint, "");
        
        logger.info("Archive event request sent to: {}", endpoint);
        logger.info("Archive event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Like event
     * @param eventId Event ID
     * @return Response from POST /events/{eventId}/like endpoint
     */
    public static Response likeEvent(String eventId) {
        logger.info("Liking event: {}", eventId);
        
        String endpoint = LIKE_EVENT_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.post(endpoint, "");
        
        logger.info("Like event request sent to: {}", endpoint);
        logger.info("Like event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get likes count for event
     * @param eventId Event ID
     * @return Response from GET /events/{eventId}/likes/count endpoint
     */
    public static Response getLikesCount(String eventId) {
        logger.info("Getting likes count for event: {}", eventId);
        
        String endpoint = LIKES_COUNT_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get likes count request sent to: {}", endpoint);
        logger.info("Get likes count response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== EVENT IMAGE ENDPOINTS ====================

    /**
     * Upload image for event
     * @param eventId Event ID
     * @param filePath Path to the image file
     * @param isCover Whether this is a cover image
     * @return Response from POST /events/{eventId}/images endpoint
     */
    public static Response uploadEventImage(String eventId, String filePath, Boolean isCover) {
        logger.info("Uploading image for event: {}", eventId);
        
        String endpoint = EVENT_IMAGES_ENDPOINT.replace("{eventId}", eventId);
        if (isCover != null) {
            endpoint += "?is_cover=" + isCover;
        }
        
        Response response = ApiClient.postMultipart(endpoint, filePath);
        
        logger.info("Upload event image request sent to: {}", endpoint);
        logger.info("Upload event image response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get event images
     * @param eventId Event ID
     * @return Response from GET /events/{eventId}/images endpoint
     */
    public static Response getEventImages(String eventId) {
        logger.info("Getting images for event: {}", eventId);
        
        String endpoint = EVENT_IMAGES_ENDPOINT.replace("{eventId}", eventId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get event images request sent to: {}", endpoint);
        logger.info("Get event images response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Delete event image
     * @param eventId Event ID
     * @param imageId Image ID
     * @return Response from DELETE /events/{eventId}/images/{imageId} endpoint
     */
    public static Response deleteEventImage(String eventId, String imageId) {
        logger.info("Deleting image: {} for event: {}", imageId, eventId);
        
        String endpoint = EVENT_IMAGE_BY_ID_ENDPOINT.replace("{eventId}", eventId).replace("{imageId}", imageId);
        Response response = ApiClient.delete(endpoint);
        
        logger.info("Delete event image request sent to: {}", endpoint);
        logger.info("Delete event image response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== RECURRING EVENT ENDPOINTS ====================

//    /**
//     * Create recurring event
//     * @param createRequest RecurringEventCreateRequest object
//     * @return Response from POST /events/recurring endpoint
//     */
//    public static Response createRecurringEvent(RecurringEventCreateRequest createRequest) {
//        logger.info("Creating recurring event");
//
//        Response response = ApiClient.post(RECURRING_EVENTS_ENDPOINT, createRequest);
//
//        logger.info("Create recurring event request sent to: {}", RECURRING_EVENTS_ENDPOINT);
//        logger.info("Create recurring event response - Status: {}, Time: {}ms",
//                   response.getStatusCode(), response.getTime());
//
//        return response;
//    }

    /**
     * Get recurring event instances
     * @param recurringEventId Recurring event ID
     * @return Response from GET /events/recurring/{recurringEventId}/instances endpoint
     */
    public static Response getRecurringInstances(String recurringEventId) {
        logger.info("Getting recurring instances for: {}", recurringEventId);
        
        String endpoint = RECURRING_INSTANCES_ENDPOINT.replace("{recurringEventId}", recurringEventId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get recurring instances request sent to: {}", endpoint);
        logger.info("Get recurring instances response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get all recurring instances
     * @return Response from GET /events/recurring/instances endpoint
     */
    public static Response getAllRecurringInstances() {
        logger.info("Getting all recurring instances");
        
        Response response = ApiClient.get(ALL_RECURRING_INSTANCES_ENDPOINT);
        
        logger.info("Get all recurring instances request sent to: {}", ALL_RECURRING_INSTANCES_ENDPOINT);
        logger.info("Get all recurring instances response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

//    /**
//     * Add recurring exception
//     * @param recurringEventId Recurring event ID
//     * @param createRequest EventExceptionCreateRequest object
//     * @return Response from POST /events/recurring/{recurringEventId}/exceptions endpoint
//     */
//    public static Response addRecurringException(String recurringEventId, EventExceptionCreateRequest createRequest) {
//        logger.info("Adding recurring exception for: {}", recurringEventId);
//
//        String endpoint = RECURRING_EXCEPTIONS_ENDPOINT.replace("{recurringEventId}", recurringEventId);
//        Response response = ApiClient.post(endpoint, createRequest);
//
//        logger.info("Add recurring exception request sent to: {}", endpoint);
//        logger.info("Add recurring exception response - Status: {}, Time: {}ms",
//                   response.getStatusCode(), response.getTime());
//
//        return response;
//    }

    /**
     * Deactivate recurring event
     * @param recurringEventId Recurring event ID
     * @return Response from DELETE /events/recurring/{recurringEventId} endpoint
     */
    public static Response deactivateRecurringEvent(String recurringEventId) {
        logger.info("Deactivating recurring event: {}", recurringEventId);
        
        String endpoint = RECURRING_EVENT_BY_ID_ENDPOINT.replace("{recurringEventId}", recurringEventId);
        Response response = ApiClient.delete(endpoint);
        
        logger.info("Deactivate recurring event request sent to: {}", endpoint);
        logger.info("Deactivate recurring event response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get RRULE examples
     * @return Response from GET /events/recurring/rrule-examples endpoint
     */
    public static Response getRruleExamples() {
        logger.info("Getting RRULE examples");
        
        Response response = ApiClient.get(RRULE_EXAMPLES_ENDPOINT);
        
        logger.info("Get RRULE examples request sent to: {}", RRULE_EXAMPLES_ENDPOINT);
        logger.info("Get RRULE examples response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== VALIDATED METHODS ====================
//
//    /**
//     * Get event by ID with validation
//     * @param eventId Event ID
//     * @return Event object if successful, null otherwise
//     */
//    public static Event getEventByIdValidated(String eventId) {
//        logger.info("Getting event by ID with validation: {}", eventId);
//
//        try {
//            Response response = getEventById(eventId);
//
//            // Validate response
//            ResponseValidator.validateStatusCode(response, 200);
//            ResponseValidator.validateContentType(response, "application/json");
//            ResponseValidator.validateResponseBodyNotEmpty(response);
//            ResponseValidator.validateResponseTime(response, EVENT_RESPONSE_TIME_LIMIT);
//
//            // Parse and return event
//            Event event = response.as(Event.class);
//            logger.info("Successfully retrieved event: {}", eventId);
//            return event;
//
//        } catch (Exception e) {
//            logger.error("Failed to get event by ID: {} - {}", eventId, e.getMessage());
//            return null;
//        }
//    }
//
//    /**
//     * Get events with validation
//     * @param hotelId Hotel ID filter
//     * @return List of Event objects if successful, null otherwise
//     */
//    public static List<Event> getEventsValidated(String hotelId) {
//        logger.info("Getting events with validation for hotel: {}", hotelId);
//
//        try {
//            Response response = getEvents(hotelId);
//
//            // Validate response
//            ResponseValidator.validateStatusCode(response, 200);
//            ResponseValidator.validateContentType(response, "application/json");
//            ResponseValidator.validateResponseBodyNotEmpty(response);
//            ResponseValidator.validateResponseTime(response, EVENT_RESPONSE_TIME_LIMIT);
//
//            // Parse and return events
//            List<Event> events = response.jsonPath().getList("", Event.class);
//            logger.info("Successfully retrieved {} events for hotel: {}", events.size(), hotelId);
//            return events;
//
//        } catch (Exception e) {
//            logger.error("Failed to get events for hotel: {} - {}", hotelId, e.getMessage());
//            return null;
//        }
//    }

    /**
     * Get response time limit for event operations
     * @return Response time limit in milliseconds
     */
    public static long getEventResponseTimeLimit() {
        return EVENT_RESPONSE_TIME_LIMIT;
    }

    /**
     * Get all event endpoints
     * @return Array of event endpoint strings
     */
    public static String[] getEventEndpoints() {
        return new String[]{
            EVENTS_ENDPOINT,
            EVENT_BY_ID_ENDPOINT,
            ATTEND_EVENT_ENDPOINT,
            LEAVE_EVENT_ENDPOINT,
            CANCEL_EVENT_ENDPOINT,
            ARCHIVE_EVENT_ENDPOINT,
            LIKE_EVENT_ENDPOINT,
            LIKES_COUNT_ENDPOINT,
            EVENT_IMAGES_ENDPOINT,
            EVENT_IMAGE_BY_ID_ENDPOINT,
            RECURRING_EVENTS_ENDPOINT,
            RECURRING_EVENT_BY_ID_ENDPOINT,
            RECURRING_INSTANCES_ENDPOINT,
            ALL_RECURRING_INSTANCES_ENDPOINT,
            RECURRING_EXCEPTIONS_ENDPOINT,
            RRULE_EXAMPLES_ENDPOINT
        };
    }
}

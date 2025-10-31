package com.apitest.client;

import com.apitest.models.request.hotel.HotelCreateRequest;
import com.apitest.models.request.hotel.HotelMemberCreateRequest;
import com.apitest.models.request.hotel.LocationCreateRequest;
import com.apitest.models.request.hotel.StayCreateRequest;
import com.apitest.models.response.hotel.Hotel;
import com.apitest.models.response.hotel.Location;
import com.apitest.utils.ResponseValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * API Client for Hotel endpoints
 * Provides dedicated methods for hotel-related API calls
 */
public class HotelApiClient {
    
    private static final Logger logger = LogManager.getLogger(HotelApiClient.class);
    
    // Hotel endpoints
    private static final String HOTELS_ENDPOINT = "/hotels";
    private static final String HOTEL_BY_ID_ENDPOINT = "/hotels/{hotelId}";
    private static final String HOTEL_LOCATIONS_ENDPOINT = "/hotels/{hotelId}/locations";
    private static final String LOCATION_BY_ID_ENDPOINT = "/hotels/locations/{locationId}";
    private static final String HOTEL_MEMBERS_ENDPOINT = "/hotels/{hotelId}/members";
    private static final String HOTEL_MEMBER_BY_ID_ENDPOINT = "/hotels/{hotelId}/members/{userId}";
    private static final String HOTEL_STAYS_ENDPOINT = "/hotels/{hotelId}/stays";
    private static final String HOTEL_STAY_BY_ID_ENDPOINT = "/hotels/{hotelId}/stays/{stayId}";
    
    // Default response time limit for hotel operations
    private static final long HOTEL_RESPONSE_TIME_LIMIT = 10000; // 10 seconds

    // ==================== HOTEL ENDPOINTS ====================

    /**
     * Create a new hotel
     * @param createRequest HotelCreateRequest object
     * @return Response from POST /hotels endpoint
     */
    public static Response createHotel(HotelCreateRequest createRequest) {
        logger.info("Creating new hotel: {}", createRequest.getName());
        
        Response response = ApiClient.post(HOTELS_ENDPOINT, createRequest);
        
        logger.info("Create hotel request sent to: {}", HOTELS_ENDPOINT);
        logger.info("Create hotel response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get all hotels with pagination
     * @param limit Number of hotels to return
     * @param offset Number of hotels to skip
     * @return Response from GET /hotels endpoint
     */
    public static Response getHotels(Integer limit, Integer offset) {
        logger.info("Getting hotels with limit: {}, offset: {}", limit, offset);
        
        String endpoint = HOTELS_ENDPOINT;
        if (limit != null || offset != null) {
            endpoint += "?";
            if (limit != null) endpoint += "limit=" + limit;
            if (limit != null && offset != null) endpoint += "&";
            if (offset != null) endpoint += "offset=" + offset;
        }
        
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get hotels request sent to: {}", endpoint);
        logger.info("Get hotels response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get all hotels (default pagination)
     * @return Response from GET /hotels endpoint
     */
    public static Response getHotels() {
        return getHotels(20, 0);
    }

    /**
     * Get hotel by ID
     * @param hotelId Hotel ID
     * @return Response from GET /hotels/{hotelId} endpoint
     */
    public static Response getHotelById(String hotelId) {
        logger.info("Getting hotel by ID: {}", hotelId);
        
        String endpoint = HOTEL_BY_ID_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get hotel by ID request sent to: {}", endpoint);
        logger.info("Get hotel by ID response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Update hotel
     * @param hotelId Hotel ID
     * @param updateRequest HotelCreateRequest object (used for updates too)
     * @return Response from PATCH /hotels/{hotelId} endpoint
     */
    public static Response updateHotel(String hotelId, HotelCreateRequest updateRequest) {
        logger.info("Updating hotel: {}", hotelId);
        
        String endpoint = HOTEL_BY_ID_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.patch(endpoint, updateRequest);
        
        logger.info("Update hotel request sent to: {}", endpoint);
        logger.info("Update hotel response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== LOCATION ENDPOINTS ====================

    /**
     * Get hotel locations
     * @param hotelId Hotel ID
     * @return Response from GET /hotels/{hotelId}/locations endpoint
     */
    public static Response getHotelLocations(String hotelId) {
        logger.info("Getting locations for hotel: {}", hotelId);
        
        String endpoint = HOTEL_LOCATIONS_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get hotel locations request sent to: {}", endpoint);
        logger.info("Get hotel locations response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Create hotel location
     * @param hotelId Hotel ID
     * @param createRequest LocationCreateRequest object
     * @return Response from POST /hotels/{hotelId}/locations endpoint
     */
    public static Response createHotelLocation(String hotelId, LocationCreateRequest createRequest) {
        logger.info("Creating location for hotel: {}", hotelId);
        
        String endpoint = HOTEL_LOCATIONS_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.post(endpoint, createRequest);
        
        logger.info("Create hotel location request sent to: {}", endpoint);
        logger.info("Create hotel location response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Update hotel location
     * @param locationId Location ID
     * @param updateRequest LocationCreateRequest object (used for updates too)
     * @return Response from PATCH /hotels/locations/{locationId} endpoint
     */
    public static Response updateLocation(String locationId, LocationCreateRequest updateRequest) {
        logger.info("Updating location: {}", locationId);
        
        String endpoint = LOCATION_BY_ID_ENDPOINT.replace("{locationId}", locationId);
        Response response = ApiClient.patch(endpoint, updateRequest);
        
        logger.info("Update location request sent to: {}", endpoint);
        logger.info("Update location response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Delete hotel location
     * @param locationId Location ID
     * @return Response from DELETE /hotels/locations/{locationId} endpoint
     */
    public static Response deleteLocation(String locationId) {
        logger.info("Deleting location: {}", locationId);
        
        String endpoint = LOCATION_BY_ID_ENDPOINT.replace("{locationId}", locationId);
        Response response = ApiClient.delete(endpoint);
        
        logger.info("Delete location request sent to: {}", endpoint);
        logger.info("Delete location response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== MEMBER ENDPOINTS ====================

    /**
     * Get hotel members
     * @param hotelId Hotel ID
     * @return Response from GET /hotels/{hotelId}/members endpoint
     */
    public static Response getHotelMembers(String hotelId) {
        logger.info("Getting members for hotel: {}", hotelId);
        
        String endpoint = HOTEL_MEMBERS_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get hotel members request sent to: {}", endpoint);
        logger.info("Get hotel members response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Add hotel member
     * @param hotelId Hotel ID
     * @param createRequest HotelMemberCreateRequest object
     * @return Response from POST /hotels/{hotelId}/members endpoint
     */
    public static Response addHotelMember(String hotelId, HotelMemberCreateRequest createRequest) {
        logger.info("Adding member to hotel: {}", hotelId);
        
        String endpoint = HOTEL_MEMBERS_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.post(endpoint, createRequest);
        
        logger.info("Add hotel member request sent to: {}", endpoint);
        logger.info("Add hotel member response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Remove hotel member
     * @param hotelId Hotel ID
     * @param userId User ID
     * @return Response from DELETE /hotels/{hotelId}/members/{userId} endpoint
     */
    public static Response removeHotelMember(String hotelId, String userId) {
        logger.info("Removing member from hotel: {}, user: {}", hotelId, userId);
        
        String endpoint = HOTEL_MEMBER_BY_ID_ENDPOINT.replace("{hotelId}", hotelId).replace("{userId}", userId);
        Response response = ApiClient.delete(endpoint);
        
        logger.info("Remove hotel member request sent to: {}", endpoint);
        logger.info("Remove hotel member response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== STAY ENDPOINTS ====================

    /**
     * Get hotel stays
     * @param hotelId Hotel ID
     * @return Response from GET /hotels/{hotelId}/stays endpoint
     */
    public static Response getHotelStays(String hotelId) {
        logger.info("Getting stays for hotel: {}", hotelId);
        
        String endpoint = HOTEL_STAYS_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.get(endpoint);
        
        logger.info("Get hotel stays request sent to: {}", endpoint);
        logger.info("Get hotel stays response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Get hotel stay by ID
     * @param hotelId Hotel ID
     * @param stayId Stay ID
     * @return Response from GET /hotels/{hotelId}/stays endpoint
     */
    public static Response getHotelStayById(String hotelId, String stayId) {
        logger.info("Getting stays for hotel: {}", hotelId);

        String endpoint = HOTEL_STAYS_ENDPOINT.replace("{hotelId}", hotelId);
        StringBuilder queryParams = new StringBuilder();
        queryParams.append("?stayId=").append(stayId);

        String fullEndpoint = endpoint + queryParams;

        Response response = ApiClient.get(fullEndpoint);

        logger.info("Get hotel stays request sent to: {}", fullEndpoint);
        logger.info("Get hotel stays response - Status: {}, Time: {}ms",
                   response.getStatusCode(), response.getTime());

        return response;
    }

    /**
     * Create hotel stay
     * @param hotelId Hotel ID
     * @param createRequest StayCreateRequest object
     * @return Response from POST /hotels/{hotelId}/stays endpoint
     */
    public static Response createHotelStay(String hotelId, StayCreateRequest createRequest) {
        logger.info("Creating stay for hotel: {}", hotelId);
        
        String endpoint = HOTEL_STAYS_ENDPOINT.replace("{hotelId}", hotelId);
        Response response = ApiClient.post(endpoint, createRequest);
        
        logger.info("Create hotel stay request sent to: {}", endpoint);
        logger.info("Create hotel stay response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Update hotel stay
     * @param hotelId Hotel ID
     * @param stayId Stay ID
     * @param updateRequest StayCreateRequest object (used for updates too)
     * @return Response from PATCH /hotels/{hotelId}/stays/{stayId} endpoint
     */
    public static Response updateHotelStay(String hotelId, String stayId, StayCreateRequest updateRequest) {
        logger.info("Updating stay: {} for hotel: {}", stayId, hotelId);
        
        String endpoint = HOTEL_STAY_BY_ID_ENDPOINT.replace("{hotelId}", hotelId).replace("{stayId}", stayId);
        Response response = ApiClient.patch(endpoint, updateRequest);
        
        logger.info("Update hotel stay request sent to: {}", endpoint);
        logger.info("Update hotel stay response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    /**
     * Delete hotel stay
     * @param hotelId Hotel ID
     * @param stayId Stay ID
     * @return Response from DELETE /hotels/{hotelId}/stays/{stayId} endpoint
     */
    public static Response deleteHotelStay(String hotelId, String stayId) {
        logger.info("Deleting stay: {} for hotel: {}", stayId, hotelId);
        
        String endpoint = HOTEL_STAY_BY_ID_ENDPOINT.replace("{hotelId}", hotelId).replace("{stayId}", stayId);
        Response response = ApiClient.delete(endpoint);
        
        logger.info("Delete hotel stay request sent to: {}", endpoint);
        logger.info("Delete hotel stay response - Status: {}, Time: {}ms", 
                   response.getStatusCode(), response.getTime());
        
        return response;
    }

    // ==================== VALIDATED METHODS ====================

    /**
     * Get hotel by ID with validation
     * @param hotelId Hotel ID
     * @return Hotel object if successful, null otherwise
     */
    public static Hotel getHotelByIdValidated(String hotelId) {
        logger.info("Getting hotel by ID with validation: {}", hotelId);
        
        try {
            Response response = getHotelById(hotelId);
            
            // Validate response
            ResponseValidator.validateStatusCode(response, 200);
            ResponseValidator.validateContentType(response, "application/json");
            ResponseValidator.validateResponseBodyNotEmpty(response);
            ResponseValidator.validateResponseTime(response, HOTEL_RESPONSE_TIME_LIMIT);
            
            // Parse and return hotel
            Hotel hotel = response.as(Hotel.class);
            logger.info("Successfully retrieved hotel: {}", hotelId);
            return hotel;
            
        } catch (Exception e) {
            logger.error("Failed to get hotel by ID: {} - {}", hotelId, e.getMessage());
            return null;
        }
    }

    /**
     * Get hotel locations with validation
     * @param hotelId Hotel ID
     * @return List of Location objects if successful, null otherwise
     */
    public static List<Location> getHotelLocationsValidated(String hotelId) {
        logger.info("Getting hotel locations with validation: {}", hotelId);
        
        try {
            Response response = getHotelLocations(hotelId);
            
            // Validate response
            ResponseValidator.validateStatusCode(response, 200);
            ResponseValidator.validateContentType(response, "application/json");
            ResponseValidator.validateResponseBodyNotEmpty(response);
            ResponseValidator.validateResponseTime(response, HOTEL_RESPONSE_TIME_LIMIT);
            
            // Parse and return locations
            List<Location> locations = response.jsonPath().getList("", Location.class);
            logger.info("Successfully retrieved {} locations for hotel: {}", locations.size(), hotelId);
            return locations;
            
        } catch (Exception e) {
            logger.error("Failed to get hotel locations: {} - {}", hotelId, e.getMessage());
            return null;
        }
    }

    /**
     * Get response time limit for hotel operations
     * @return Response time limit in milliseconds
     */
    public static long getHotelResponseTimeLimit() {
        return HOTEL_RESPONSE_TIME_LIMIT;
    }

    /**
     * Get all hotel endpoints
     * @return Array of hotel endpoint strings
     */
    public static String[] getHotelEndpoints() {
        return new String[]{
            HOTELS_ENDPOINT,
            HOTEL_BY_ID_ENDPOINT,
            HOTEL_LOCATIONS_ENDPOINT,
            LOCATION_BY_ID_ENDPOINT,
            HOTEL_MEMBERS_ENDPOINT,
            HOTEL_MEMBER_BY_ID_ENDPOINT,
            HOTEL_STAYS_ENDPOINT,
            HOTEL_STAY_BY_ID_ENDPOINT
        };
    }
}

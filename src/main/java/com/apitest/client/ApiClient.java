package com.apitest.client;

import com.apitest.service.TokenManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * API Client for making HTTP requests using RestAssured
 */
public class ApiClient {
    
    private static final Logger logger = LogManager.getLogger(ApiClient.class);
    
    // Base URL for all API requests - update this to point to your API endpoint
    private static final String BASE_URL = "https://treveler-api-470986740614.europe-west1.run.app";
    
    static {
        // Configure RestAssured
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * Get base request specification with common configurations and automatic token injection
     * @return RequestSpecification
     */
    private static RequestSpecification getBaseRequestSpec() {
        RequestSpecification spec = RestAssured.given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
        
        // Automatically inject Bearer token if available
        String authHeader = TokenManager.getInstance().getAuthorizationHeader();
        if (authHeader != null) {
            spec.header("Authorization", authHeader);
            logger.debug("Automatically injected Bearer token");
        }
        
        return spec;
    }


    /**
     * Perform GET request
     * @param endpoint API endpoint
     * @return Response
     */
    public static Response get(String endpoint) {
        logger.info("Performing GET request to: {}", endpoint);
        Response response = getBaseRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("GET request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform GET request with path parameters
     * @param endpoint API endpoint
     * @param pathParams Path parameters
     * @return Response
     */
    public static Response get(String endpoint, Map<String, Object> pathParams) {
        logger.info("Performing GET request to: {} with path params: {}", endpoint, pathParams);
        Response response = getBaseRequestSpec()
                .pathParams(pathParams)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("GET request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform GET request with query parameters
     * @param endpoint API endpoint
     * @param queryParams Query parameters
     * @return Response
     */
    public static Response getWithQueryParams(String endpoint, Map<String, Object> queryParams) {
        logger.info("Performing GET request to: {} with query params: {}", endpoint, queryParams);
        Response response = getBaseRequestSpec()
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("GET request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform POST request with JSON body
     * @param endpoint API endpoint
     * @param requestBody Request body object
     * @return Response
     */
    public static Response post(String endpoint, Object requestBody) {
        logger.info("Performing POST request to: {}", endpoint);
        Response response = getBaseRequestSpec()
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("POST request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform POST request with JSON body and path parameters
     * @param endpoint API endpoint
     * @param requestBody Request body object
     * @param pathParams Path parameters
     * @return Response
     */
    public static Response post(String endpoint, Object requestBody, Map<String, Object> pathParams) {
        logger.info("Performing POST request to: {} with path params: {}", endpoint, pathParams);
        Response response = getBaseRequestSpec()
                .pathParams(pathParams)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("POST request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform PUT request with JSON body
     * @param endpoint API endpoint
     * @param requestBody Request body object
     * @return Response
     */
    public static Response put(String endpoint, Object requestBody) {
        logger.info("Performing PUT request to: {}", endpoint);
        Response response = getBaseRequestSpec()
                .body(requestBody)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("PUT request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform PUT request with JSON body and path parameters
     * @param endpoint API endpoint
     * @param requestBody Request body object
     * @param pathParams Path parameters
     * @return Response
     */
    public static Response put(String endpoint, Object requestBody, Map<String, Object> pathParams) {
        logger.info("Performing PUT request to: {} with path params: {}", endpoint, pathParams);
        Response response = getBaseRequestSpec()
                .pathParams(pathParams)
                .body(requestBody)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("PUT request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform PATCH request with JSON body
     * @param endpoint API endpoint
     * @param requestBody Request body object
     * @return Response
     */
    public static Response patch(String endpoint, Object requestBody) {
        logger.info("Performing PATCH request to: {}", endpoint);
        Response response = getBaseRequestSpec()
                .body(requestBody)
                .when()
                .patch(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("PATCH request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform PATCH request with JSON body and path parameters
     * @param endpoint API endpoint
     * @param requestBody Request body object
     * @param pathParams Path parameters
     * @return Response
     */
    public static Response patch(String endpoint, Object requestBody, Map<String, Object> pathParams) {
        logger.info("Performing PATCH request to: {} with path params: {}", endpoint, pathParams);
        Response response = getBaseRequestSpec()
                .pathParams(pathParams)
                .body(requestBody)
                .when()
                .patch(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("PATCH request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform DELETE request
     * @param endpoint API endpoint
     * @return Response
     */
    public static Response delete(String endpoint) {
        logger.info("Performing DELETE request to: {}", endpoint);
        Response response = getBaseRequestSpec()
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("DELETE request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Perform DELETE request with path parameters
     * @param endpoint API endpoint
     * @param pathParams Path parameters
     * @return Response
     */
    public static Response delete(String endpoint, Map<String, Object> pathParams) {
        logger.info("Performing DELETE request to: {} with path params: {}", endpoint, pathParams);
        Response response = getBaseRequestSpec()
                .pathParams(pathParams)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("DELETE request completed. Status code: {}", response.getStatusCode());
        return response;
    }

    /**
     * Set base URI for API requests
     * @param baseUri Base URI
     */
    public static void setBaseUri(String baseUri) {
        logger.info("Setting base URI to: {}", baseUri);
        RestAssured.baseURI = baseUri;
    }

    /**
     * Get current base URI
     * @return Current base URI
     */
    public static String getBaseUri() {
        return RestAssured.baseURI;
    }

    /**
     * POST multipart/form-data request (for file uploads)
     * @param endpoint API endpoint
     * @param filePath Path to the file to upload
     * @return Response object
     */
    public static Response postMultipart(String endpoint, String filePath) {
        logger.info("Performing POST multipart request to: {}", endpoint);
        
        Response response = getBaseRequestSpec()
                .contentType("multipart/form-data")
                .multiPart("file", new java.io.File(filePath))
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
        
        logger.info("POST multipart request completed. Status code: {}", response.getStatusCode());
        return response;
    }
}

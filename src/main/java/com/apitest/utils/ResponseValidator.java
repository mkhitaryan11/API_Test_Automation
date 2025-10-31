package com.apitest.utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for response validation
 */
public class ResponseValidator {
    
    private static final Logger logger = LogManager.getLogger(ResponseValidator.class);

    /**
     * Validate response
     * @param response RestAssured response
     * @param expectedStatusCode Expected status code
     * @param expectedContentType Expected content type
     * @param maxResponseTime Maximum acceptable response time in milliseconds
     */
    public static void validateResponse(Response response, int expectedStatusCode, String expectedContentType, long maxResponseTime, boolean isBodyEmpty) {
        int actualStatusCode = response.getStatusCode();
        logger.info("Validating status code. Expected: {}, Actual: {}", expectedStatusCode, actualStatusCode);
        if (actualStatusCode != expectedStatusCode) {
            throw new AssertionError("Status code mismatch. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
        }
        String actualContentType = response.getContentType();
        logger.info("Validating content type. Expected: {}, Actual: {}", expectedContentType, actualContentType);
        if (!actualContentType.contains(expectedContentType)) {
            throw new AssertionError("Content type mismatch. Expected: " + expectedContentType + ", Actual: " + actualContentType);
        }
        long actualResponseTime = response.getTime();
        logger.info("Validating response time. Expected max: {}ms, Actual: {}ms", maxResponseTime, actualResponseTime);
        if (actualResponseTime > maxResponseTime) {
            throw new AssertionError("Response time exceeds maximum. Expected max: " + maxResponseTime + "ms, Actual: " + actualResponseTime + "ms");
        }
        String responseBody = response.getBody().asString();
        logger.info("Validating response body is not empty");
        if(!isBodyEmpty){
            if (responseBody == null) {
                throw new AssertionError("Response body should not be null");
            }
            if (responseBody.isEmpty()) {
                throw new AssertionError("Response body should not be empty");
            }
        } else {
            if (!responseBody.isEmpty()) {
                throw new AssertionError("Response body should be empty");
            }
        }
    }

    /**
     * Validate response status code
     * @param response RestAssured response
     * @param expectedStatusCode Expected status code
     */
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        logger.info("Validating status code. Expected: {}, Actual: {}", expectedStatusCode, actualStatusCode);
        if (actualStatusCode != expectedStatusCode) {
            throw new AssertionError("Status code mismatch. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
        }
    }

    /**
     * Validate response content type
     * @param response RestAssured response
     * @param expectedContentType Expected content type
     */
    public static void validateContentType(Response response, String expectedContentType) {
        String actualContentType = response.getContentType();
        logger.info("Validating content type. Expected: {}, Actual: {}", expectedContentType, actualContentType);
        if (!actualContentType.contains(expectedContentType)) {
            throw new AssertionError("Content type mismatch. Expected: " + expectedContentType + ", Actual: " + actualContentType);
        }
    }

    /**
     * Validate response header
     * @param response RestAssured response
     * @param headerName Header name
     * @param expectedValue Expected header value
     */
    public static void validateHeader(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        logger.info("Validating header {}. Expected: {}, Actual: {}", headerName, expectedValue, actualValue);
        if (!expectedValue.equals(actualValue)) {
            throw new AssertionError("Header " + headerName + " mismatch. Expected: " + expectedValue + ", Actual: " + actualValue);
        }
    }

    /**
     * Validate response body is not empty
     * @param response RestAssured response
     */
    public static void validateResponseBodyNotEmpty(Response response) {
        String responseBody = response.getBody().asString();
        logger.info("Validating response body is not empty");
        if (responseBody == null) {
            throw new AssertionError("Response body should not be null");
        }
        if (responseBody.isEmpty()) {
            throw new AssertionError("Response body should not be empty");
        }
    }

    /**
     * Validate response time is within acceptable range
     * @param response RestAssured response
     * @param maxResponseTime Maximum acceptable response time in milliseconds
     */
    public static void validateResponseTime(Response response, long maxResponseTime) {
        long actualResponseTime = response.getTime();
        logger.info("Validating response time. Expected max: {}ms, Actual: {}ms", maxResponseTime, actualResponseTime);
        if (actualResponseTime > maxResponseTime) {
            throw new AssertionError("Response time exceeds maximum. Expected max: " + maxResponseTime + "ms, Actual: " + actualResponseTime + "ms");
        }
    }

    /**
     * Validate JSON path value
     * @param response RestAssured response
     * @param jsonPath JSON path expression
     * @param expectedValue Expected value
     */
    public static void validateJsonPathValue(Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        logger.info("Validating JSON path {}. Expected: {}, Actual: {}", jsonPath, expectedValue, actualValue);
        if (!expectedValue.equals(actualValue)) {
            throw new AssertionError("JSON path " + jsonPath + " mismatch. Expected: " + expectedValue + ", Actual: " + actualValue);
        }
    }

    /**
     * Validate JSON path exists
     * @param response RestAssured response
     * @param jsonPath JSON path expression
     */
    public static void validateJsonPathExists(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        logger.info("Validating JSON path exists: {}", jsonPath);
        if (value == null) {
            throw new AssertionError("JSON path " + jsonPath + " should exist");
        }
    }

    /**
     * Validate JSON path is not null
     * @param response RestAssured response
     * @param jsonPath JSON path expression
     */
    public static void validateJsonPathNotNull(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        logger.info("Validating JSON path is not null: {}", jsonPath);
        if (value == null) {
            throw new AssertionError("JSON path " + jsonPath + " should not be null");
        }
    }

    /**
     * Validate JSON path is not empty
     * @param response RestAssured response
     * @param jsonPath JSON path expression
     */
    public static void validateJsonPathNotEmpty(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        logger.info("Validating JSON path is not empty: {}", jsonPath);
        if (value == null) {
            throw new AssertionError("JSON path " + jsonPath + " should not be null");
        }
        if (value instanceof String && ((String) value).isEmpty()) {
            throw new AssertionError("JSON path " + jsonPath + " should not be empty");
        }
    }

    /**
     * Validate response contains specific text
     * @param response RestAssured response
     * @param expectedText Expected text to be present
     */
    public static void validateResponseContainsText(Response response, String expectedText) {
        String responseBody = response.getBody().asString();
        logger.info("Validating response contains text: {}", expectedText);
        if (!responseBody.contains(expectedText)) {
            throw new AssertionError("Response should contain text: " + expectedText);
        }
    }

    /**
     * Validate response size (for arrays)
     * @param response RestAssured response
     * @param jsonPath JSON path to array
     * @param expectedSize Expected array size
     */
    public static void validateArraySize(Response response, String jsonPath, int expectedSize) {
        int actualSize = response.jsonPath().getList(jsonPath).size();
        logger.info("Validating array size for path {}. Expected: {}, Actual: {}", jsonPath, expectedSize, actualSize);
        if (actualSize != expectedSize) {
            throw new AssertionError("Array size mismatch for path " + jsonPath + ". Expected: " + expectedSize + ", Actual: " + actualSize);
        }
    }
}

package com.apitest.tests;

import com.apitest.client.ApiClient;
import com.apitest.models.response.auth.VerifyResponse;
import com.apitest.models.request.auth.VerifyRequest;
import com.apitest.service.AuthorizationService;
import com.apitest.service.TokenManager;
import com.apitest.utils.TestDataLoader;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

/**
 * Base test class providing common setup, teardown, and utility methods
 * for all test classes in the framework
 */
public abstract class BaseTest {
    
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    SoftAssert softAssert = new SoftAssert();
    
    // Test configuration constants
    protected static final long DEFAULT_RESPONSE_TIME_LIMIT = 5000; // 5 seconds
    protected static final String DEFAULT_CONTENT_TYPE = "application/json";
    protected final String HOTEL_ADMIN = "hotel_admin@example.com";
    protected final String HOTEL_SUPER_ADMIN = "hotel_super_admin@example.com";
    protected final String PLATFORM_SUPER_ADMIN = "platform_super_admin@example.com";

    @BeforeClass
    public void setUpClass() {
        logger.info("Setting up test class: {}", this.getClass().getSimpleName());
        
        // Log test class information
        Allure.addAttachment("Test Class", "text/plain", 
            "Test Class: " + this.getClass().getSimpleName() + "\n" +
            "Base URL: " + ApiClient.getBaseUri() + "\n" +
            "Response Time Limit: " + DEFAULT_RESPONSE_TIME_LIMIT + "ms"
        );
        
        logger.info("Test class setup completed for: {}", this.getClass().getSimpleName());
    }
    
    @AfterClass
    public void tearDownClass() {
        logger.info("Tearing down test class: {}", this.getClass().getSimpleName());
        
        // Clear current authorization token
        TokenManager.getInstance().clearToken();
        logger.info("Cleared authorization token");
        
        // Add any cleanup logic here if needed
        // For example: clearing test data, closing connections, etc.
        
        logger.info("Test class teardown completed for: {}", this.getClass().getSimpleName());
    }
    
    @BeforeMethod
    public void setUpMethod() {
        String methodName = getCurrentTestMethodName();
        logger.info("Setting up test method: {}", methodName);
        
        // Add method-level setup logic here if needed
        // For example: resetting test data, clearing caches, etc.
        
        logger.info("Test method setup completed for: {}", methodName);
    }
    
    @AfterMethod
    public void tearDownMethod() {
        String methodName = getCurrentTestMethodName();
        logger.info("Tearing down test method: {}", methodName);
        
        // Add method-level cleanup logic here if needed
        // For example: capturing screenshots on failure, logging test results, etc.
        
        logger.info("Test method teardown completed for: {}", methodName);
    }
    
    /**
     * Get the name of the currently executing test method
     * @return Test method name
     */
    protected String getCurrentTestMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }
    
    /**
     * Add test step to Allure report
     * @param stepName Name of the test step
     * @param stepDescription Description of the test step
     */
    @Step("{stepName}")
    protected void addTestStepWithNameDescription(String stepName, String stepDescription) {
        logger.info("Test Step: {} - {}", stepName, stepDescription);
        Allure.addAttachment("Step Details", "text/plain", 
            "Step: " + stepName + "\nDescription: " + stepDescription
        );
    }

    /**
     * Add test step to Allure report
     * @param stepName Name of the test step
     */
    @Step("{stepName}")
    protected void addTestStep(String stepName) {
        logger.info("Test Step: {}", stepName);
        Allure.addAttachment("Step Details", "text/plain",
            "Step: " + stepName);
    }
    
    /**
     * Add request details to Allure report
     * @param requestType HTTP method (GET, POST, PUT, etc.)
     * @param endpoint API endpoint
     * @param requestBody Request body (if any)
     */
    protected void addRequestDetails(String requestType, String endpoint, String requestBody) {
        String requestDetails = "Request Type: " + requestType + "\n" +
                               "Endpoint: " + endpoint + "\n" +
                               "Request Body: " + (requestBody != null ? requestBody : "N/A");
        
        Allure.addAttachment("Request Details", "text/plain", requestDetails);
        logger.info("Request Details - Type: {}, Endpoint: {}", requestType, endpoint);
    }
    
    /**
     * Add response details to Allure report
     * @param statusCode HTTP status code
     * @param responseBody Response body
     * @param responseTime Response time in milliseconds
     */
    protected void addResponseDetails(int statusCode, String responseBody, long responseTime) {
        String responseDetails = "Status Code: " + statusCode + "\n" +
                                "Response Time: " + responseTime + "ms\n" +
                                "Response Body: " + (responseBody != null ? responseBody : "N/A");
        
        Allure.addAttachment("Response Details", "text/plain", responseDetails);
        logger.info("Response Details - Status: {}, Time: {}ms", statusCode, responseTime);
    }
    
    /**
     * Add test data to Allure report
     * @param testDataName Name of the test data
     * @param testData Test data content
     */
    protected void addTestData(String testDataName, String testData) {
        Allure.addAttachment(testDataName, "application/json", testData);
        logger.info("Test data added to report: {}", testDataName);
    }
    
    /**
     * Add error details to Allure report
     * @param errorMessage Error message
     * @param stackTrace Stack trace (if available)
     */
    protected void addErrorDetails(String errorMessage, String stackTrace) {
        String errorDetails = "Error Message: " + errorMessage + "\n" +
                             "Stack Trace: " + (stackTrace != null ? stackTrace : "N/A");
        
        Allure.addAttachment("Error Details", "text/plain", errorDetails);
        logger.error("Error Details - Message: {}", errorMessage);
    }
    
    /**
     * Sleep for specified milliseconds
     * @param milliseconds Time to sleep in milliseconds
     */
    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
            logger.debug("Slept for {} milliseconds", milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Sleep interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Get current timestamp as string
     * @return Current timestamp
     */
    protected String getCurrentTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
    
    /**
     * Generate unique test identifier
     * @return Unique test identifier
     */
    protected String generateTestId() {
        return "TEST_" + getCurrentTimestamp() + "_" + Thread.currentThread().getId();
    }
    
    /**
     * Log test execution summary
     * @param testName Test name
     * @param status Test status (PASSED, FAILED, SKIPPED)
     * @param executionTime Execution time in milliseconds
     */
    protected void logTestSummary(String testName, String status, long executionTime) {
        String summary = String.format("Test Summary - Name: %s, Status: %s, Execution Time: %dms", 
                                      testName, status, executionTime);
        
        Allure.addAttachment("Test Summary", "text/plain", summary);
        logger.info(summary);
    }
    
    /**
     * Validate that a string is not null or empty
     * @param value String to validate
     * @param fieldName Name of the field for error message
     * @throws IllegalArgumentException if string is null or empty
     */
    protected void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            String errorMessage = fieldName + " cannot be null or empty";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
    
    /**
     * Validate that an object is not null
     * @param value Object to validate
     * @param fieldName Name of the field for error message
     * @throws IllegalArgumentException if object is null
     */
    protected void validateObject(Object value, String fieldName) {
        if (value == null) {
            String errorMessage = fieldName + " cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Authorize user with 2-step flow using test data
     * @param verifyDataFileName Verify request data file name
     * @return Authorization token
     */
    protected VerifyResponse authorizeUser(String verifyDataFileName) {
        logger.info("Authorizing user with data from: {}", verifyDataFileName);
        
        VerifyRequest verifyRequest = TestDataLoader.loadData(verifyDataFileName, VerifyRequest.class);
        VerifyResponse verifyResponse = AuthorizationService.completeAuthorization(verifyRequest.getEmail(), verifyRequest.getCode());
        
        // Token is automatically stored in TokenManager by AuthorizationService
        addTestStepWithNameDescription("User Authorization", "Successfully authorized user: " + verifyRequest.getEmail());
        
        logger.info("User authorization completed for: {}", verifyRequest.getEmail());
        return verifyResponse;
    }

    /**
     * Authorize user with 2-step flow using direct credentials
     * Token is automatically stored in TokenManager
     * Calling this again will replace the current token with the new one
     * @param email User's email address
     * @param code Verification code
     * @return Authorization token
     */
    protected VerifyResponse authorizeUserWithCredentials(String email, String code) {
        logger.info("Authorizing user: {}", email);
        
        // completeAuthorization automatically stores token in TokenManager
        VerifyResponse verifyResponse = AuthorizationService.completeAuthorization(email, code);
        
        addTestStepWithNameDescription("User Authorization", "Successfully authorized user: " + email);
        logger.info("User authorization completed for: {}", email);
        
        return verifyResponse;
    }

    /**
     * Get current authorization token
     * @return Authorization token or null if not authorized
     */
    protected VerifyResponse getAuthToken() {
        return TokenManager.getInstance().getToken();
    }

    /**
     * Check if user is currently authorized
     * @return true if valid token exists
     */
    protected boolean isAuthorized() {
        return TokenManager.getInstance().hasValidToken();
    }

    /**
     * Logout current user and clear token
     */
    protected void logout() {
        VerifyResponse verifyResponse = getAuthToken();
        if (verifyResponse != null) {
            AuthorizationService.logout(verifyResponse);
            TokenManager.getInstance().clearToken();
            addTestStepWithNameDescription("User Logout", "Successfully logged out user");
            logger.info("User logout completed");
        }
    }

    /**
     * Clear the current authorization token
     */
    protected void clearToken() {
        TokenManager.getInstance().clearToken();
        addTestStepWithNameDescription("Clear Token", "Cleared authorization token");
        logger.info("Authorization token cleared");
    }
}

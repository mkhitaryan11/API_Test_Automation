package com.apitest.tests;

import com.apitest.client.ApiClient;
import com.apitest.models.response.auth.VerifyResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class demonstrating authorized API calls
 * Shows how token management works with automatic token injection
 */
@Epic("Authorization")
@Feature("Authorized API Calls")
public class AuthorizedApiTests extends BaseTest {

    @Test(description = "Test authorized API call with single user")
    @Description("Verify that authorized user can make API calls with automatic token injection")
    public void testAuthorizedApiCallWithSingleUser() {
        logger.info("Starting test: Authorized API call with single user");
        
        // Authorize user (token is automatically set in TokenManager)
        VerifyResponse verifyResponse = authorizeUser("verify_request.json");
        Assert.assertTrue(isAuthorized(), "User should be authorized");
        
        // Make API call (Bearer token automatically injected)
        addTestStepWithNameDescription("Authorized API Call", "Making API call with automatic token injection");
        
        Response response = ApiClient.get("/users/me");
        Assert.assertEquals(response.getStatusCode(), 200, "Authorized request should succeed");
        
        addRequestDetails("GET", "/users/me", null);
        addResponseDetails(response.getStatusCode(), 
                          response.getBody().asString(), 
                          response.getTime());
        
        logger.info("Test completed successfully: Authorized API call verified");
    }

    @Test(description = "Test switching between different users")
    @Description("Test to verify that calling authorizeUserWithCredentials again replaces the current token")
    public void testSwitchingBetweenUsers() {
        logger.info("Starting test: Switching between different users");
        
        // Authorize first user
        VerifyResponse verifyResponse1 = authorizeUser("verify_request.json");
        Assert.assertTrue(isAuthorized(), "First user should be authorized");
        String firstUserToken = verifyResponse1.getAccessToken();
        
        // Make API call with first user
        Response response1 = ApiClient.get("/users/me");
        Assert.assertEquals(response1.getStatusCode(), 200, "First user request should succeed");
        addTestStepWithNameDescription("First User Call", "Successfully made API call with first user");
        
        // Authorize second user (this REPLACES the first user's token)
        VerifyResponse verifyResponse2 = authorizeUser("admin_verify_request.json");
        Assert.assertTrue(isAuthorized(), "Second user should be authorized");
        String secondUserToken = verifyResponse2.getAccessToken();
        
        // Verify token was replaced
        Assert.assertNotEquals(firstUserToken, secondUserToken, "Token should be different for different users");
        
        // Make API call with second user (using the new token)
        Response response2 = ApiClient.get("/users/me");
        Assert.assertEquals(response2.getStatusCode(), 200, "Second user request should succeed");
        addTestStepWithNameDescription("Second User Call", "Successfully made API call with second user (token replaced)");
        
        logger.info("Test completed successfully: User switching verified");
    }

    @Test(description = "Test API call without authorization")
    @Description("Test to verify behavior when no token is set")
    public void testUnauthorizedApiCall() {
        logger.info("Starting test: Unauthorized API call");
        
        // Clear any existing token
        clearToken();
        Assert.assertFalse(isAuthorized(), "No user should be authorized");
        
        // Make unauthorized API call
        addTestStepWithNameDescription("Unauthorized API Call", "Making API call without authorization");
        
        Response response = ApiClient.get("/users/me");
        
        addRequestDetails("GET", "/users/me", null);
        addResponseDetails(response.getStatusCode(), 
                          response.getBody().asString(), 
                          response.getTime());
        
        // Verify response (should be 401 Unauthorized)
        Assert.assertEquals(response.getStatusCode(), 401, "Unauthorized request should return 401");
        
        logger.info("Test completed successfully: Unauthorized API call verified");
    }

    @Test(description = "Test token persistence across multiple API calls")
    @Description("Verify that once authorized, multiple API calls use the same token")
    public void testTokenPersistenceAcrossMultipleCalls() {
        logger.info("Starting test: Token persistence across multiple calls");
        
        // Authorize user once
        authorizeUserWithCredentials("test@example.com", "123456");
        
        // Make multiple API calls
        addTestStepWithNameDescription("Multiple API Calls", "Making multiple calls with the same token");
        
        Response response1 = ApiClient.get("/users/me");
        Assert.assertEquals(response1.getStatusCode(), 200, "First call should succeed");
        
        Response response2 = ApiClient.get("/users/me");
        Assert.assertEquals(response2.getStatusCode(), 200, "Second call should succeed");
        
        Response response3 = ApiClient.get("/users/me");
        Assert.assertEquals(response3.getStatusCode(), 200, "Third call should succeed");
        
        addTestStepWithNameDescription("Token Persistence Verified",
                   "All 3 API calls succeeded with the same token");
        
        logger.info("Test completed successfully: Token persistence verified");
    }

    @Test(description = "Test that re-authorizing replaces the token")
    @Description("Verify that calling authorizeUserWithCredentials again replaces the current token")
    public void testReAuthorizingReplacesToken() {
        logger.info("Starting test: Re-authorizing replaces token");
        
        // First authorization
        VerifyResponse token1 = authorizeUserWithCredentials("test@example.com", "123456");
        String firstToken = token1.getAccessToken();
        
        // Wait a moment to ensure timestamp difference
        try { Thread.sleep(1000); } catch (InterruptedException e) { }
        
        // Second authorization (should replace the first token)
        VerifyResponse token2 = authorizeUserWithCredentials("test@example.com", "123456");
        String secondToken = token2.getAccessToken();
        
        // Tokens should be different (new token issued)
        Assert.assertNotEquals(firstToken, secondToken, "New authorization should generate a new token");
        
        // Verify only the second token is active
        Assert.assertEquals(getAuthToken().getAccessToken(), secondToken, 
                          "Current token should be the second token");
        
        addTestStepWithNameDescription("Token Replacement Verified",
                   "Re-authorization successfully replaced the old token with a new one");
        
        logger.info("Test completed successfully: Token replacement verified");
    }
}

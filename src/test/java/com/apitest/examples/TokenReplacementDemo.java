package com.apitest.examples;

import com.apitest.models.response.auth.VerifyResponse;
import com.apitest.service.AuthorizationService;
import com.apitest.service.TokenManager;
import com.apitest.tests.BaseTest;
import org.testng.annotations.Test;

/**
 * Demonstration of what happens when authorizeUserWithCredentials is called multiple times
 */
public class TokenReplacementDemo extends BaseTest {
    
    @Test
    public void demonstrateTokenReplacement() {
        System.out.println("\n========================================");
        System.out.println("Token Replacement Demonstration");
        System.out.println("========================================\n");
        
        // FIRST CALL: Authorize user1
        System.out.println("STEP 1: First call to authorizeUserWithCredentials()");
        System.out.println("Authorizing: test@example.com");
        VerifyResponse token1 = authorizeUserWithCredentials("test@example.com", "123456");
        
        System.out.println("✅ Token 1 stored in TokenManager");
        System.out.println("   Access Token: " + token1.getAccessToken().substring(0, 50) + "...");
        System.out.println("   User Email: test@example.com");
        System.out.println("   Current Token in Manager: " + TokenManager.getInstance().getToken().getAccessToken().substring(0, 50) + "...");
        System.out.println("   Has valid token: " + TokenManager.getInstance().hasValidToken());
        
        // Small delay to ensure different timestamps
        try { Thread.sleep(1000); } catch (InterruptedException e) { }
        
        System.out.println("\n========================================");
        
        // SECOND CALL: Authorize user2 (REPLACES token1)
        System.out.println("STEP 2: Second call to authorizeUserWithCredentials()");
        System.out.println("Authorizing: admin@example.com");
        VerifyResponse token2 = authorizeUserWithCredentials("admin@example.com", "654321");
        
        System.out.println("✅ Token 2 stored in TokenManager (REPLACED Token 1)");
        System.out.println("   Access Token: " + token2.getAccessToken().substring(0, 50) + "...");
        System.out.println("   User Email: admin@example.com");
        System.out.println("   Current Token in Manager: " + TokenManager.getInstance().getToken().getAccessToken().substring(0, 50) + "...");
        System.out.println("   Has valid token: " + TokenManager.getInstance().hasValidToken());
        
        System.out.println("\n========================================");
        System.out.println("COMPARISON:");
        System.out.println("========================================");
        
        System.out.println("Token 1 (test@example.com): " + token1.getAccessToken().substring(0, 50) + "...");
        System.out.println("Token 2 (admin@example.com): " + token2.getAccessToken().substring(0, 50) + "...");
        System.out.println("Current token in Manager: " + TokenManager.getInstance().getToken().getAccessToken().substring(0, 50) + "...");
        
        boolean tokensAreDifferent = !token1.getAccessToken().equals(token2.getAccessToken());
        boolean currentIsToken2 = TokenManager.getInstance().getToken().getAccessToken().equals(token2.getAccessToken());
        
        System.out.println("\n✅ Tokens are different: " + tokensAreDifferent);
        System.out.println("✅ Current token is Token 2: " + currentIsToken2);
        System.out.println("❌ Current token is NOT Token 1: " + !TokenManager.getInstance().getToken().getAccessToken().equals(token1.getAccessToken()));
        
        System.out.println("\n========================================");
        System.out.println("WHAT HAPPENED:");
        System.out.println("========================================");
        System.out.println("1. First call set Token 1 (test@example.com)");
        System.out.println("2. Second call REPLACED Token 1 with Token 2 (admin@example.com)");
        System.out.println("3. Only Token 2 is now active");
        System.out.println("4. All subsequent API calls will use Token 2");
        System.out.println("5. Token 1 is NO LONGER in TokenManager");
        
        System.out.println("\n========================================");
        System.out.println("Demonstration Complete!");
        System.out.println("========================================\n");
    }
}


package com.apitest.models.response.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing an authorization token
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyResponse {
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("user_info")
    private Object userInfo;
    
    @JsonProperty("expiry_seconds")
    private int expirySeconds;

    public boolean isExpired() {
        // For simplicity, we'll consider token valid for the duration specified in expirySeconds
        return false; // This should be implemented based on actual token expiration logic
    }

    public boolean isTokenValid() {
        return accessToken != null && !accessToken.isEmpty() && !isExpired();
    }

    public void invalidate() {
        this.accessToken = null;
        this.refreshToken = null;
        this.userInfo = null;
    }

    public String getAuthorizationHeader() {
        if (accessToken != null && !accessToken.isEmpty()) {
            return "Bearer " + accessToken;
        }
        return null;
    }
}

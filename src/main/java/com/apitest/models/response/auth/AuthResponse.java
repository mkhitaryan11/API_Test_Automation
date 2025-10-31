package com.apitest.models.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO class representing an authorization response
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {

    @JsonProperty("access")
    private String accessToken;

    @JsonProperty("refresh")
    private String refreshToken;

    @JsonProperty("user")
    private Object userInfo;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    /**
     * Check if the response contains an error
     * @return true if error is present
     */
    public boolean hasError() {
        return error != null && !error.trim().isEmpty();
    }

    /**
     * Check if the response contains a valid access token
     * @return true if access token is present and not empty
     */
    public boolean hasValidToken() {
        return accessToken != null && !accessToken.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "accessToken='" + (accessToken != null ? "[HIDDEN]" : "null") + '\'' +
                ", refreshToken='" + (refreshToken != null ? "[HIDDEN]" : "null") + '\'' +
                ", userInfo=" + userInfo +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

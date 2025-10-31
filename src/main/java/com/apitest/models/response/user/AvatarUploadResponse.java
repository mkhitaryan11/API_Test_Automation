package com.apitest.models.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvatarUploadResponse {
    @JsonProperty("avatar_id")
    private String avatarId;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("message")
    private String message;
}

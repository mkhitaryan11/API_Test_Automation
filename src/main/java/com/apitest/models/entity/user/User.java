package com.apitest.models.entity.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing a User entity
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("first_name")
    private String firstName;
    
    @JsonProperty("last_name")
    private String lastName;
    
    @JsonProperty("gender")
    private String gender;
    
    @JsonProperty("birthdate")
    private String birthdate;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("global_role")
    private String globalRole;
    
    @JsonProperty("avatar_id")
    private String avatarId;
    
    @JsonProperty("avatar_url")
    private String avatarUrl;
}

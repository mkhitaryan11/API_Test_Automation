package com.apitest.models.response.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing a Hotel Member entity
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelMember {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("hotel_id")
    private String hotelId;
    
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("role")
    private String role;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("updated_at")
    private String updatedAt;
}

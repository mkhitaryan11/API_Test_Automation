package com.apitest.models.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing a Hotel Stay entity
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Stay {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("hotel_id")
    private String hotelId;
    
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("room_number")
    private String roomNumber;
    
    @JsonProperty("start_at")
    private String startAt;
    
    @JsonProperty("end_at")
    private String endAt;

    @JsonProperty("is_active")
    private String isActive;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("updated_at")
    private String updatedAt;
}

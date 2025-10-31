package com.apitest.models.request.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing a Stay creation request
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StayCreateRequest {
    
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("room_number")
    private String roomNumber;
    
    @JsonProperty("start_at")
    private String startAt;
    
    @JsonProperty("end_at")
    private String endAt;
}

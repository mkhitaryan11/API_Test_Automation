package com.apitest.models.response.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing a Hotel Location entity
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("hotel_id")
    private String hotelId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("text")
    private String text;
    
    @JsonProperty("lat")
    private Double lat;
    
    @JsonProperty("lon")
    private Double lon;
    
    @JsonProperty("pre_moderated")
    private Boolean preModerated;
    
    @JsonProperty("is_active")
    private Boolean isActive;
    
    @JsonProperty("location_type")
    private String locationType;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("updated_at")
    private String updatedAt;
}

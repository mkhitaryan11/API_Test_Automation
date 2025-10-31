package com.apitest.models.request.hotel;

import com.apitest.enums.LocationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing a Location creation request
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationCreateRequest {
    
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
}

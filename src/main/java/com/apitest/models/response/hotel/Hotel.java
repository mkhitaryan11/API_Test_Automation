package com.apitest.models.response.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * POJO representing a Hotel entity
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hotel {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("contacts_json")
    private String contactsJson;
    
    @JsonProperty("address_text")
    private String addressText;
    
    @JsonProperty("lat")
    private Double lat;
    
    @JsonProperty("lon")
    private Double lon;
    
    @JsonProperty("is_active")
    private Boolean isActive;
    
    @JsonProperty("pre_moderated")
    private Boolean preModerated;
    
    @JsonProperty("hotel_type")
    private String hotelType;
    
    @JsonProperty("event_policy")
    private String eventPolicy;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("updated_at")
    private String updatedAt;
}

package com.apitest.models.request.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * POJO representing an Event creation request
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventCreateRequest {
    
    @JsonProperty("hotel_id")
    private String hotelId;
    
    @JsonProperty("hotel_location_id")
    private String hotelLocationId;
    
    @JsonProperty("mode")
    private String mode;
    
    @JsonProperty("activity_type_code")
    private String activityTypeCode;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("description_short")
    private String descriptionShort;
    
    @JsonProperty("description_long")
    private String descriptionLong;
    
    @JsonProperty("start_at")
    private String startAt;
    
    @JsonProperty("duration_min")
    private Integer durationMin;
    
    @JsonProperty("max_attendees")
    private Integer maxAttendees;
    
    @JsonProperty("age_restricted")
    private Boolean ageRestricted;
    
    @JsonProperty("cover_image_url")
    private String coverImageUrl;
    
    @JsonProperty("comments_enabled")
    private Boolean commentsEnabled;
    
    @JsonProperty("languages")
    private List<String> languages;
    
    @JsonProperty("price")
    private Double price;
    
    @JsonProperty("currency_code")
    private String currencyCode;
    
    @JsonProperty("current_attendees")
    private Integer currentAttendees;
    
    @JsonProperty("attendance_required")
    private Boolean attendanceRequired;

    @JsonProperty("created_by")
    private String createdBy;
}

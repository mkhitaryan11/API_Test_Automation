package com.apitest.models.response.user;

import com.apitest.models.response.hotel.Hotel;
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
public class HotelAdminResponse {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("global_role")
    private String globalRole;

    @JsonProperty("hotel")
    private Hotel hotel;

}

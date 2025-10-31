package com.apitest.tests;

import com.apitest.client.HotelApiClient;
import com.apitest.client.UserApiClient;
import com.apitest.enums.UserType;
import com.apitest.models.entity.user.User;
import com.apitest.models.hotel.Stay;
import com.apitest.models.request.hotel.StayCreateRequest;
import com.apitest.models.response.hotel.HotelMember;
import com.apitest.models.request.hotel.HotelMemberCreateRequest;
import com.apitest.models.response.hotel.Location;
import com.apitest.models.request.hotel.HotelCreateRequest;
import com.apitest.models.request.hotel.LocationCreateRequest;
import com.apitest.models.response.hotel.Hotel;
import com.apitest.utils.DataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.*;

@Epic("Hotel Management")
@Feature("Hotel API")
public class HotelApiTests extends BaseTest {

    private String email;

    @BeforeClass
    public void setUpUserTests() {
        logger.info("Setting up Hotel API tests");

        email = DataGenerator.generateEmail();

        // Load test data


        logger.info("User API tests setup completed");
    }

    @Test(priority = 1, description = "Guest User restricted operations")
    @Description("Verify that authenticated guest user can't perform restricted operations.\n" +
            "1. Authorize guest User\n" +
            "2. Create Hotel, expect 403 response, and msg Requires platform_super_admin\n" +
            "3. Update Hotel, expect 403 response, and msg Requires hotel super_admin or platform_super_admin\n")
    public void hotel001() {
        authorizeUserWithCredentials(email, "123456");

        HotelCreateRequest hotelCreateRequest = new HotelCreateRequest()
                .setName(DataGenerator.generateHotelName())
                .setDescription(DataGenerator.generateDescription("Hotel"))
                .setContactsJson(DataGenerator.generateContactsJson())
                .setAddressText(DataGenerator.generateAddressText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setIsActive(DataGenerator.generateBoolean())
                .setPreModerated(DataGenerator.generateBoolean())
                .setHotelType(DataGenerator.generateHotelType())
                .setEventPolicy(DataGenerator.generateEventPolicy());

        HotelApiClient.createHotel(hotelCreateRequest)
                .then()
                .statusCode(403)
                .body("detail", equalTo("Requires platform_super_admin"));

        Hotel hotel = HotelApiClient.getHotels().as((new TypeRef<List<Hotel>>() {})).get(0);

        HotelApiClient.updateHotel(hotel.getId(),hotelCreateRequest)
                .then()
                .statusCode(403)
                .body("detail", equalTo("Requires hotel super_admin or platform_super_admin"));
    }

    @Test(priority = 2, description = "Hotel CRU with PLATFORM_SUPER_ADMIN")
    @Description("Verify that authenticated PLATFORM_SUPER_ADMIN can CRU hotel.\n" +
            "1. Authorize platform super admin\n" +
            "2. Create Hotel, expect 200, assertions\n" +
            "3. Get Hotel by Id, expect 200, assertions\n" +
            "4. Update Hotel, expect 200, assertions\n" +
            "5. Get Hotel by Id, do assertions\n")
    public void hotel002() {
        authorizeUserWithCredentials(UserType.PLATFORM_SUPER_ADMIN.getValue(), "123456");
        HotelCreateRequest hotelCreateRequest = new HotelCreateRequest()
                .setName(DataGenerator.generateHotelName())
                .setDescription(DataGenerator.generateDescription("Hotel"))
                .setContactsJson(DataGenerator.generateContactsJson())
                .setAddressText(DataGenerator.generateAddressText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setIsActive(DataGenerator.generateBoolean())
                .setPreModerated(DataGenerator.generateBoolean())
                .setHotelType(DataGenerator.generateHotelType())
                .setEventPolicy(DataGenerator.generateEventPolicy());

        Response createResponse = HotelApiClient.createHotel(hotelCreateRequest);
        assertEquals(createResponse.statusCode(), 200, "Status code isn't correct");
        Hotel hotel = createResponse.as(Hotel.class);
        assertEquals(hotel.getName(), hotelCreateRequest.getName(), "Hotel name isn't correctly set.");
        assertEquals(hotel.getDescription(), hotelCreateRequest.getDescription(), "Hotel description isn't correctly set.");
        assertEquals(hotel.getContactsJson(), hotelCreateRequest.getContactsJson(), "Hotel contacts isn't correctly set.");
        assertEquals(hotel.getAddressText(), hotelCreateRequest.getAddressText(), "Hotel address isn't correctly set.");
        assertEquals(hotel.getLon(), hotelCreateRequest.getLon(), "Hotel lon isn't correctly set.");
        assertEquals(hotel.getLat(), hotelCreateRequest.getLat(), "Hotel lat isn't correctly set.");
        assertEquals(hotel.getIsActive(), hotelCreateRequest.getIsActive(), "Hotel isActive isn't correctly set.");
        assertEquals(hotel.getPreModerated(), hotelCreateRequest.getPreModerated(), "Hotel PreModerated isn't correctly set.");
        assertEquals(hotel.getHotelType(), hotelCreateRequest.getHotelType(), "Hotel type isn't correctly set.");
        assertEquals(hotel.getEventPolicy(), hotelCreateRequest.getEventPolicy(), "Hotel eventPolicy isn't correctly set.");
        assertNotNull(hotel.getCreatedAt(), "Hotel createdAt shouldn't be null.");
        assertNotNull(hotel.getUpdatedAt(), "Hotel updatedAt shouldn't be null.");

        Response getResponse = HotelApiClient.getHotelById(hotel.getId());
        assertEquals(getResponse.statusCode(), 200, "Status code isn't correct");
        Hotel hotelById = getResponse.as(Hotel.class);
        assertEquals(hotelById.getName(), hotelCreateRequest.getName(), "Hotel name isn't correctly set.");
        assertEquals(hotelById.getDescription(), hotelCreateRequest.getDescription(), "Hotel description isn't correctly set.");
        assertEquals(hotelById.getContactsJson(), hotelCreateRequest.getContactsJson(), "Hotel contacts isn't correctly set.");
        assertEquals(hotelById.getAddressText(), hotelCreateRequest.getAddressText(), "Hotel address isn't correctly set.");
        assertEquals(hotelById.getLon(), hotelCreateRequest.getLon(), "Hotel lon isn't correctly set.");
        assertEquals(hotelById.getLat(), hotelCreateRequest.getLat(), "Hotel lat isn't correctly set.");
        assertEquals(hotelById.getIsActive(), hotelCreateRequest.getIsActive(), "Hotel isActive isn't correctly set.");
        assertEquals(hotelById.getPreModerated(), hotelCreateRequest.getPreModerated(), "Hotel PreModerated isn't correctly set.");
        assertEquals(hotelById.getHotelType(), hotelCreateRequest.getHotelType(), "Hotel type isn't correctly set.");
        assertEquals(hotelById.getEventPolicy(), hotelCreateRequest.getEventPolicy(), "Hotel eventPolicy isn't correctly set.");
        assertNotNull(hotelById.getCreatedAt(), "Hotel createdAt shouldn't be null.");
        assertNotNull(hotelById.getUpdatedAt(), "Hotel updatedAt shouldn't be null.");

        HotelCreateRequest hotelUpdateRequest = new HotelCreateRequest()
                .setName(DataGenerator.generateHotelName())
                .setDescription(DataGenerator.generateDescription("Hotel"))
                .setContactsJson(DataGenerator.generateContactsJson())
                .setAddressText(DataGenerator.generateAddressText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setIsActive(DataGenerator.generateBoolean())
                .setPreModerated(DataGenerator.generateBoolean())
                .setHotelType(DataGenerator.generateHotelType())
                .setEventPolicy(DataGenerator.generateEventPolicy());
        Response updateResponse = HotelApiClient.updateHotel(hotel.getId(), hotelUpdateRequest);
        assertEquals(updateResponse.statusCode(), 200, "Status code isn't correct");
        Hotel updatedHotel = updateResponse.as(Hotel.class);
        assertEquals(updatedHotel.getName(), hotelUpdateRequest.getName(), "Hotel name isn't correctly updated.");
        assertEquals(updatedHotel.getDescription(), hotelUpdateRequest.getDescription(), "Hotel description isn't correctly updated.");
        assertEquals(updatedHotel.getContactsJson(), hotelUpdateRequest.getContactsJson(), "Hotel contacts isn't correctly updated.");
        assertEquals(updatedHotel.getAddressText(), hotelUpdateRequest.getAddressText(), "Hotel address isn't correctly updated.");
        assertEquals(updatedHotel.getLon(), hotelUpdateRequest.getLon(), "Hotel lon isn't correctly updated.");
        assertEquals(updatedHotel.getLat(), hotelUpdateRequest.getLat(), "Hotel lat isn't correctly updated.");
        assertEquals(updatedHotel.getIsActive(), hotelUpdateRequest.getIsActive(), "Hotel isActive isn't correctly updated.");
        assertEquals(updatedHotel.getPreModerated(), hotelUpdateRequest.getPreModerated(), "Hotel PreModerated isn't correctly updated.");
        assertEquals(updatedHotel.getHotelType(), hotelUpdateRequest.getHotelType(), "Hotel type isn't correctly updated.");
        assertEquals(updatedHotel.getEventPolicy(), hotelUpdateRequest.getEventPolicy(), "Hotel eventPolicy isn't correctly updated.");
        assertEquals(updatedHotel.getCreatedAt(), hotelById.getCreatedAt(), "Hotel createdAt shouldn't be changed after update.");
        assertNotEquals(updatedHotel.getUpdatedAt(), hotelById.getUpdatedAt(), "Hotel updatedAt should be changed.");

        Response getResponseAfterUpdate = HotelApiClient.getHotelById(hotel.getId());
        assertEquals(getResponseAfterUpdate.statusCode(), 200, "Status code isn't correct");
        Hotel hotelByIdAfterUpdate = getResponseAfterUpdate.as(Hotel.class);
        assertEquals(hotelByIdAfterUpdate.getName(), hotelUpdateRequest.getName(), "Hotel name isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getDescription(), hotelUpdateRequest.getDescription(), "Hotel description isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getContactsJson(), hotelUpdateRequest.getContactsJson(), "Hotel contacts isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getAddressText(), hotelUpdateRequest.getAddressText(), "Hotel address isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getLon(), hotelUpdateRequest.getLon(), "Hotel lon isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getLat(), hotelUpdateRequest.getLat(), "Hotel lat isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getIsActive(), hotelUpdateRequest.getIsActive(), "Hotel isActive isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getPreModerated(), hotelUpdateRequest.getPreModerated(), "Hotel PreModerated isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getHotelType(), hotelUpdateRequest.getHotelType(), "Hotel type isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getEventPolicy(), hotelUpdateRequest.getEventPolicy(), "Hotel eventPolicy isn't correctly set.");
        assertEquals(hotelByIdAfterUpdate.getCreatedAt(), hotelById.getCreatedAt(), "Hotel createdAt shouldn't be changed after update.");
        assertNotEquals(hotelByIdAfterUpdate.getUpdatedAt(), hotelById.getUpdatedAt(), "Hotel updatedAt should be changed.");
    }

    @Test(priority = 3, description = "Hotel location CRUD with PLATFORM_SUPER_ADMIN")
    @Description("Verify that authenticated PLATFORM_SUPER_ADMIN can perform CRUD for hotel location.\n" +
            "1. Authorize PLATFORM_SUPER_ADMIN\n" +
            "2. Create Hotel, expect 200, assertions\n" +
            "3. Create Location for this hotel, expect 200, assertions\n" +
            "4. Get Locations list and find by Name\n" +
            "5. Create Location with the same params, expect 409\n" +
            "6. Update Location, expect 200, do assertions\n" +
            "7. Delete Location by Id, expect 200\n" +
            "8. Get Locations list, make sure location don't found in the list\n" +
            "9. Delete Location by Id, expect 409\n")
    public void hotel003(){
        authorizeUserWithCredentials(UserType.PLATFORM_SUPER_ADMIN.getValue(), "123456");
        HotelCreateRequest hotelCreateRequest = new HotelCreateRequest()
                .setName(DataGenerator.generateHotelName())
                .setDescription(DataGenerator.generateDescription("Hotel"))
                .setContactsJson(DataGenerator.generateContactsJson())
                .setAddressText(DataGenerator.generateAddressText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setIsActive(DataGenerator.generateBoolean())
                .setPreModerated(DataGenerator.generateBoolean())
                .setHotelType(DataGenerator.generateHotelType())
                .setEventPolicy(DataGenerator.generateEventPolicy());
        Response hotelCreateResponse = HotelApiClient.createHotel(hotelCreateRequest);
        assertEquals(hotelCreateResponse.statusCode(), 200, "Status code isn't correct");
        Hotel hotel = hotelCreateResponse.as(Hotel.class);

        LocationCreateRequest locationCreateRequest = new LocationCreateRequest()
                .setName(DataGenerator.generateLocationName())
                .setText(DataGenerator.generateLocationText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setPreModerated(DataGenerator.generateBoolean())
                .setIsActive(DataGenerator.generateBoolean())
                .setLocationType(DataGenerator.generateLocationType());
        Response locationCreateResponse = HotelApiClient.createHotelLocation(hotel.getId(), locationCreateRequest);
        assertEquals(locationCreateResponse.getStatusCode(), 200, "Status code isn't correct");
        Location locationFromResponse = locationCreateResponse.as(Location.class);
        assertEquals(locationFromResponse.getName(), locationCreateRequest.getName(), "Location name isn't correctly set.");
        assertEquals(locationFromResponse.getText(), locationCreateRequest.getText(), "Location text isn't correctly set.");
        assertEquals(locationFromResponse.getLon(), locationCreateRequest.getLon(), "Location lon isn't correctly set.");
        assertEquals(locationFromResponse.getLat(), locationCreateRequest.getLat(), "Location lat isn't correctly set.");
        assertEquals(locationFromResponse.getPreModerated(), locationCreateRequest.getPreModerated(), "Location PreModerated isn't correctly set.");
        assertEquals(locationFromResponse.getIsActive(), locationCreateRequest.getIsActive(), "Hotel isActive isn't correctly set.");
        assertEquals(locationFromResponse.getLocationType(), locationCreateRequest.getLocationType(), "Location type isn't correctly set.");

        Response getHotelLocationsResponse = HotelApiClient.getHotelLocations(hotel.getId());
        assertEquals(getHotelLocationsResponse.getStatusCode(), 200, "Status code isn't correct");
        List<Location> locationList = getHotelLocationsResponse.as(new TypeRef<List<Location>>() {});
        Location location = null;
        for (Location l : locationList) {
            if(l.getName().equals(locationCreateRequest.getName())){
                location = l;
            }
            break;
        }
        assertNotNull(location, "Location can't be null");
        assertNotNull(location.getId(), "Location Id isn't correctly set.");
        assertEquals(location.getHotelId(), hotel.getId(), "Location Hotel Id isn't correctly set.");
        assertEquals(location.getName(), locationCreateRequest.getName(), "Location name isn't correctly set.");
        assertEquals(location.getText(), locationCreateRequest.getText(), "Location text isn't correctly set.");
        assertEquals(location.getLon(), locationCreateRequest.getLon(), "Location lon isn't correctly set.");
        assertEquals(location.getLat(), locationCreateRequest.getLat(), "Location lat isn't correctly set.");
        assertEquals(location.getIsActive(), locationCreateRequest.getIsActive(), "Location isActive isn't correctly set.");
        assertEquals(location.getPreModerated(), locationCreateRequest.getPreModerated(), "Location PreModerated isn't correctly set.");

        Response locationCreateResponseCopy = HotelApiClient.createHotelLocation(hotel.getId(), locationCreateRequest);
        assertEquals(locationCreateResponseCopy.getStatusCode(), 409, "Status code isn't correct");

        LocationCreateRequest locationUpdateRequest = new LocationCreateRequest()
                .setName(DataGenerator.generateLocationName())
                .setText(DataGenerator.generateLocationText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setPreModerated(DataGenerator.generateBoolean())
                .setIsActive(DataGenerator.generateBoolean())
                .setLocationType(DataGenerator.generateLocationType());
        Response updateResponse = HotelApiClient.updateLocation(location.getId(), locationUpdateRequest);
        assertEquals(updateResponse.statusCode(), 200, "Status code isn't correct");
        Location locationUpdateResponse = updateResponse.as(Location.class);

        assertEquals(locationUpdateResponse.getId(), location.getId(), "Location Id isn't correctly updated.");
        assertEquals(locationUpdateResponse.getHotelId(), location.getHotelId(), "Location Hotel Id isn't correctly updated.");
        assertEquals(locationUpdateResponse.getName(), locationUpdateRequest.getName(), "Location name isn't correctly updated.");
        assertEquals(locationUpdateResponse.getText(), locationUpdateRequest.getText(), "Location text isn't correctly updated.");
        assertEquals(locationUpdateResponse.getLon(), locationUpdateRequest.getLon(), "Location lon isn't correctly updated.");
        assertEquals(locationUpdateResponse.getLat(), locationUpdateRequest.getLat(), "Location lat isn't correctly updated.");
        assertEquals(locationUpdateResponse.getIsActive(), locationUpdateRequest.getIsActive(), "Location isActive isn't correctly updated.");
        assertEquals(locationUpdateResponse.getPreModerated(), locationUpdateRequest.getPreModerated(), "Location PreModerated isn't correctly updated.");

        Response deleteLocationResponse = HotelApiClient.deleteLocation(location.getId());
        assertEquals(deleteLocationResponse.statusCode(), 200, "Status code isn't correct");

        Response getLocationsAfterDeleteResponse = HotelApiClient.getHotelLocations(hotel.getId());
        List<Location> locationListAfterDelete = getLocationsAfterDeleteResponse.as(new TypeRef<List<Location>>() {});
        Location locationAfterDelete = null;
        for (Location l : locationListAfterDelete) {
            if(l.getName().equals(locationCreateRequest.getName())){
                locationAfterDelete = l;
            }
            break;
        }
        assertNull(locationAfterDelete);

        Response oneMoreDeleteLocationResponse = HotelApiClient.deleteLocation(location.getId());
        assertEquals(oneMoreDeleteLocationResponse.statusCode(), 404, "Status code isn't correct");
    }

    @Test(priority = 4, description = "Hotel member CRD with PLATFORM_SUPER_ADMIN\n")
    @Description("Verify that authenticated PLATFORM_SUPER_ADMIN can perform CRD for hotel member.\n" +
            "1. Authorize user\n" +
            "2. Authorize PLATFORM_SUPER_ADMIN\n" +
            "3. Create Hotel\n" +
            "4. Create Member, expect 200, do assertions\n" +
            "5. Get Hotel Members list, make sure member exists in the list\n" +
            "6. Remove Member, make sure member doesn't exist in the list\n")
    public void hotel004(){
        String email = DataGenerator.generateEmail();
        authorizeUserWithCredentials(email, "123456");
        String userId = UserApiClient.getMe().as(com.apitest.models.entity.user.User.class).getId();

        authorizeUserWithCredentials(UserType.PLATFORM_SUPER_ADMIN.getValue(), "123456");
        HotelCreateRequest hotelCreateRequest = new HotelCreateRequest()
                .setName(DataGenerator.generateHotelName())
                .setDescription(DataGenerator.generateDescription("Hotel"))
                .setContactsJson(DataGenerator.generateContactsJson())
                .setAddressText(DataGenerator.generateAddressText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setIsActive(DataGenerator.generateBoolean())
                .setPreModerated(DataGenerator.generateBoolean())
                .setHotelType(DataGenerator.generateHotelType())
                .setEventPolicy(DataGenerator.generateEventPolicy());
        Response hotelCreateResponse = HotelApiClient.createHotel(hotelCreateRequest);
        assertEquals(hotelCreateResponse.statusCode(), 200, "Status code isn't correct");
        Hotel hotel = hotelCreateResponse.as(Hotel.class);

        String role = "asd";

        HotelMemberCreateRequest memberCreateRequest = new HotelMemberCreateRequest()
                .setRole(role)
                .setUserId(userId);
        Response memberCreateResponse = HotelApiClient.addHotelMember(hotel.getId(), memberCreateRequest);
        assertEquals(memberCreateResponse.getStatusCode(), 200, "Status code isn't correct");

        HotelMember memberFromResponse = memberCreateResponse.as(HotelMember.class);
        assertEquals(memberFromResponse.getUserId(), memberCreateRequest.getUserId(), "Member Id isn't correctly set.");
        assertEquals(memberFromResponse.getRole(), role, "Location text isn't correctly set.");

        Response getMembersResponse = HotelApiClient.getHotelMembers(hotel.getId());
        assertEquals(getMembersResponse.statusCode(), 200, "Status code isn't correct");
        List<HotelMember> hotelMemberList = getMembersResponse.as(new TypeRef<>() {});
        HotelMember foundMember = null;
        for (HotelMember hm : hotelMemberList) {
            if (hm.getUserId().equals(userId)){
                foundMember = hm;
                break;
            }
        }
        assertNotNull(foundMember, "HotelMember isn't found in the list.");

        Response removeMemberResponse = HotelApiClient.removeHotelMember(hotel.getId(), userId);
        assertEquals(removeMemberResponse.getStatusCode(), 200, "Status code isn't correct");
        removeMemberResponse.then().body("removed",equalTo(true));

        Response getMembersAfterRemoveResponse = HotelApiClient.getHotelMembers(hotel.getId());
        List<HotelMember> hotelMemberListAfterRemove = getMembersAfterRemoveResponse.as(new TypeRef<>() {});
        HotelMember foundMemberAfterRemove = null;
        for (HotelMember hm : hotelMemberListAfterRemove) {
            if (hm.getUserId().equals(userId)){
                foundMemberAfterRemove = hm;
                break;
            }
        }
        assertNull(foundMemberAfterRemove, "HotelMember isn't removed from the list.");
    }

    @Test(priority = 5, description = "Hotel stay CRUD with PLATFORM_SUPER_ADMIN")
    @Description("Verify that authenticated PLATFORM_SUPER_ADMIN can perform CRUD for hotel stay.\n" +
            "1. Authorize user\n" +
            "2. Authorize PLATFORM_SUPER_ADMIN\n" +
            "3. Create Hotel\n" +
            "4. Create Stay, expect 200, do assertions\n" +
            "5. Create Stay with overlapping dates, expect 409\n" +
            "6. Get Stay by Id from list, do assertions\n" +
            "7. Update Stay, do assertions\n" +
            "8. Delete Stay by Id, expect 200\n" +
            "9. Delete Stay one more time, expect 404\n")
    public void hotel005(){
        String email = DataGenerator.generateEmail();
        authorizeUserWithCredentials(email, "123456");
        String userId = UserApiClient.getMe().as(User.class).getId();

        authorizeUserWithCredentials(UserType.PLATFORM_SUPER_ADMIN.getValue(), "123456");

        HotelCreateRequest hotelCreateRequest = new HotelCreateRequest()
                .setName(DataGenerator.generateHotelName())
                .setDescription(DataGenerator.generateDescription("Hotel"))
                .setContactsJson(DataGenerator.generateContactsJson())
                .setAddressText(DataGenerator.generateAddressText())
                .setLat(DataGenerator.generateLatitude())
                .setLon(DataGenerator.generateLongitude())
                .setIsActive(DataGenerator.generateBoolean())
                .setPreModerated(DataGenerator.generateBoolean())
                .setHotelType(DataGenerator.generateHotelType())
                .setEventPolicy(DataGenerator.generateEventPolicy());
        Hotel hotel = HotelApiClient.createHotel(hotelCreateRequest).as(Hotel.class);

        StayCreateRequest stayCreateRequest = new StayCreateRequest()
                .setUserId(userId)
                .setRoomNumber(DataGenerator.generateRoomNumber())
                .setStartAt(DataGenerator.generateFutureDate(1))
                .setEndAt(DataGenerator.generateFutureDate(7));
        Response stayCreateResponse = HotelApiClient.createHotelStay(hotel.getId(), stayCreateRequest);
        assertEquals(stayCreateResponse.getStatusCode(), 200, "Status code isn't correct");

        Stay stayFromResponse = stayCreateResponse.as(Stay.class);
        assertEquals(stayFromResponse.getUserId(), stayCreateRequest.getUserId(), "User Id isn't correctly set.");
        assertEquals(stayFromResponse.getRoomNumber(), stayCreateRequest.getRoomNumber(), "Room Number isn't correctly set.");
        assertEquals(stayFromResponse.getStartAt(), stayCreateRequest.getStartAt(), "StartAt isn't correctly set.");
        assertEquals(stayFromResponse.getEndAt(), stayCreateRequest.getEndAt(), "EndAt isn't correctly set.");

        StayCreateRequest stayCreateRequestWithOverlappingDates = new StayCreateRequest()
                .setUserId(userId)
                .setRoomNumber(DataGenerator.generateRoomNumber())
                .setStartAt(DataGenerator.generateFutureDate(2))
                .setEndAt(DataGenerator.generateFutureDate(8));
        Response stayCreateResponse1 = HotelApiClient.createHotelStay(hotel.getId(), stayCreateRequestWithOverlappingDates);
        assertEquals(stayCreateResponse1.getStatusCode(), 409, "Status code isn't correct");

        Response getStayByIdResponse = HotelApiClient.getHotelStayById(hotel.getId(), stayFromResponse.getId());
        Stay stayFromGetById = getStayByIdResponse.as((new TypeRef<List<Stay>>() {})).get(0);
        assertEquals(stayFromGetById.getUserId(), stayCreateRequest.getUserId(), "User Id isn't correctly returned.");
        assertEquals(stayFromGetById.getRoomNumber(), stayCreateRequest.getRoomNumber(), "Room Number isn't correctly returned.");
        assertEquals(stayFromGetById.getStartAt(), stayCreateRequest.getStartAt(), "StartAt isn't correctly returned.");
        assertEquals(stayFromGetById.getEndAt(), stayCreateRequest.getEndAt(), "EndAt isn't correctly returned.");

        StayCreateRequest stayUpdateRequest = new StayCreateRequest()
                .setUserId(userId)
                .setRoomNumber(DataGenerator.generateRoomNumber())
                .setStartAt(DataGenerator.generateFutureDate(2))
                .setEndAt(DataGenerator.generateFutureDate(9));

        Response updateStayByIdResponse = HotelApiClient.updateHotelStay(hotel.getId(), stayFromGetById.getId(), stayUpdateRequest);
        assertEquals(updateStayByIdResponse.getStatusCode(), 200, "Status code isn't correct");

        Stay stayAfterUpdateResponse = updateStayByIdResponse.as(Stay.class);
        assertEquals(stayAfterUpdateResponse.getUserId(), stayUpdateRequest.getUserId(), "User Id isn't correctly updated.");
        assertEquals(stayAfterUpdateResponse.getRoomNumber(), stayUpdateRequest.getRoomNumber(), "Room Number isn't correctly updated.");
        assertEquals(stayAfterUpdateResponse.getStartAt(), stayUpdateRequest.getStartAt(), "StartAt isn't correctly updated.");
        assertEquals(stayAfterUpdateResponse.getEndAt(), stayUpdateRequest.getEndAt(), "EndAt isn't correctly updated.");

        Response stayDeleteByIdResponse = HotelApiClient.deleteHotelStay(hotel.getId(), stayFromGetById.getId());
        assertEquals(stayDeleteByIdResponse.statusCode(), 200, "Status code isn't correct.");

        Response stayAfterDelete = HotelApiClient.getHotelStayById(hotel.getId(), stayFromGetById.getId());
        assertEquals(stayAfterDelete.statusCode(), 404);
    }

}

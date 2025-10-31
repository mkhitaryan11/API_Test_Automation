package com.apitest.tests;

import com.apitest.client.UserApiClient;
import com.apitest.models.response.auth.VerifyResponse;
import com.apitest.models.response.user.AvatarDeleteResponse;
import com.apitest.models.response.user.AvatarUploadResponse;
import com.apitest.models.entity.user.User;
import com.apitest.models.request.user.UserUpdateRequest;
import com.apitest.utils.DataGenerator;
import com.apitest.utils.TestDataLoader;
import com.apitest.utils.ResponseValidator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("User Management")
@Feature("User API")
public class UserApiTests extends BaseTest {

    private UserUpdateRequest userUpdateRequest;
    private String testUserId;
    private User currentUser;
    private String testImagePath;
    private String email;


    @BeforeClass
    public void setUpUserTests() {
        logger.info("Setting up User API tests");

        email = DataGenerator.generateEmail();

        // Load test data
        userUpdateRequest = TestDataLoader.loadData("testdata/user_update_request.json", UserUpdateRequest.class);
        testUserId = "ad5ba766-3d12-4019-aa99-e5e498eefd3d"; // Test user ID from Postman collection
        testImagePath = System.getProperty("user.dir") + "/src/test/resources/testdata/test-avatar.png";

        logger.info("User API tests setup completed");
    }

    @Test(priority = 1, description = "GET /users/me - Get current user profile")
    @Description("Verify that authenticated user can retrieve their own profile information. This is the baseline test.")
    public void test01_GetCurrentUserProfile() {
        authorizeUserWithCredentials(email, "123456");

        Response response = UserApiClient.getMe();
        User user = response.as(User.class);
        ResponseValidator.validateResponse(response, 200, "application/json", 10000, false);
        assertEquals(user.getEmail(), email, "User email isn't correct");
    }

    @Test(priority = 2, description = "OTP negative test")
    @Description("Verify that authentication will fail with wrong otp")
    public void test02_OTP_Negative_Test() {
        VerifyResponse verifyResponse = authorizeUserWithCredentials(email, "111111");
        assertTrue(verifyResponse.getAccessToken().isEmpty(), "Token should be null");
    }

    @Test(priority = 3, description = "PATCH /users/me - Update current user profile")
    @Description("Verify that authenticated user can update their profile information")
    public void test03_UpdateCurrentUserProfile() {
        authorizeUserWithCredentials(email, "123456");
        UserUpdateRequest updatedRequestUserData = new UserUpdateRequest()
                .setFirstName(DataGenerator.generateFirstName())
                .setLastName(DataGenerator.generateLastName())
                .setGender(DataGenerator.generateGender())
                .setBirthdate(DataGenerator.generateBirthdate(DataGenerator.generateAge()));
        Response response = UserApiClient.updateMe(updatedRequestUserData);
        ResponseValidator.validateResponse(response, 200, "application/json", 10000, false);

        User userFromResponseBody = response.as(User.class);
        softAssert.assertNotNull(userFromResponseBody, "Updated user object should not be null");
        softAssert.assertEquals(userFromResponseBody.getFirstName(), updatedRequestUserData.getFirstName(), "First name should be updated");
        softAssert.assertEquals(userFromResponseBody.getLastName(), updatedRequestUserData.getLastName(), "Last name should be updated");
        softAssert.assertEquals(userFromResponseBody.getGender(), updatedRequestUserData.getGender(), "Gender should be updated");
        softAssert.assertEquals(userFromResponseBody.getBirthdate(), updatedRequestUserData.getBirthdate(), "Birthdate should be updated");

        User updatedUser = UserApiClient.getMe().as(User.class);
        softAssert.assertEquals(updatedUser.getFirstName(), updatedRequestUserData.getFirstName(), "First name should be updated");
        softAssert.assertEquals(updatedUser.getLastName(), updatedRequestUserData.getLastName(), "Last name should be updated");
        softAssert.assertEquals(updatedUser.getGender(), updatedRequestUserData.getGender(), "Gender should be updated");
        softAssert.assertEquals(updatedUser.getBirthdate(), updatedRequestUserData.getBirthdate(), "Birthdate should be updated");
        softAssert.assertAll();
    }

//TODO status code problem
    @Test(priority = 4, description = "POST /users/me/avatar - Upload user avatar")
    @Description("Verify that authenticated user can upload an avatar image")
    public void test04_UploadUserAvatarAndDelete() {
        authorizeUserWithCredentials(email, "123456");
        Response uploadResponse = UserApiClient.uploadAvatar(testImagePath);
        ResponseValidator.validateResponse(uploadResponse, 200, "application/json", 15000, false);

        AvatarUploadResponse avatarUploadResponse = uploadResponse.as(AvatarUploadResponse.class);
        assertNotNull(avatarUploadResponse.getAvatarId(), "AvatarId should not be null after avatar upload");

        User userWithAvatar = UserApiClient.getMe().as(User.class);
        assertEquals(userWithAvatar.getAvatarId(), avatarUploadResponse.getAvatarId(), "Avatar id doesn't match");

        Response deleteResponse = UserApiClient.deleteAvatar();
        AvatarDeleteResponse avatarDeletedUserFromResponse = deleteResponse.as(AvatarDeleteResponse.class);
        assertEquals(deleteResponse.getStatusCode(), 200);
        assertEquals(avatarDeletedUserFromResponse.getMessage(), "Avatar deleted successfully", "Delete response isn't correct");

//TODO status code problem
        Response oneMoreDeleteResponse = UserApiClient.deleteAvatar();
        assertEquals(oneMoreDeleteResponse.getStatusCode(), 404);


        User avatarDeletedUser = UserApiClient.getMe().as(User.class);
        assertNull(avatarDeletedUser.getAvatarId());
    }

    @Test(priority = 5, description = "GET /users/{user_id} - Get user by ID")
    @Description("Verify that authenticated but not completed user can't retrieve another user's profile by ID")
    public void test05_GetUserByIdWithoutCompletedUser() {
        authorizeUserWithCredentials(email, "123456");
        User user1 = UserApiClient.getMe().as(User.class);

        String newEmail = DataGenerator.generateEmail();
        authorizeUserWithCredentials(newEmail, "123456");
        assertEquals(UserApiClient.getUserById(user1.getId()).getStatusCode(), 403, "Not completed user can't retrieve another user's profile by ID");
    }

    @Test(priority = 6, description = "GET /users/{user_id} - Get user by ID")
    @Description("Verify that authenticated and completed user can retrieve another user's profile by ID")
    public void test06_GetUserById() {
        authorizeUserWithCredentials(email, "123456");
        User user1 = UserApiClient.getMe().as(User.class);

        String newEmail = DataGenerator.generateEmail();
        authorizeUserWithCredentials(newEmail, "123456");
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest()
                .setFirstName(DataGenerator.generateFirstName())
                .setLastName(DataGenerator.generateLastName())
                .setGender(DataGenerator.generateGender())
                .setBirthdate(DataGenerator.generateBirthdate(20));
        UserApiClient.updateMe(userUpdateRequest);

        Response response = UserApiClient.getUserById(user1.getId());
        assertEquals(response.getStatusCode(), 200, "Status code isn't correct.");
        assertEquals(response.as(User.class).getId(), user1.getId(), "User's data isn't retrieved correctly.");
    }

//TODO can any user do this request, it returns 403 for random user
    @Test(priority = 7, description = "GET /users/me/hotel-admin - Get hotel admin info")
    @Description("Verify that authenticated user can retrieve their hotel admin information")
    public void test07() {
        authorizeUserWithCredentials(email, "123456");
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest()
                .setFirstName(DataGenerator.generateFirstName())
                .setLastName(DataGenerator.generateLastName())
                .setGender(DataGenerator.generateGender())
                .setBirthdate(DataGenerator.generateBirthdate(20));
        UserApiClient.updateMe(userUpdateRequest);
        Response hotelAdminResponse = UserApiClient.getHotelAdmin();
        assertEquals(hotelAdminResponse.getStatusCode(), 200, "Status code isn't correct.");
    }

//TODO can any user do this request, it returns 403 for random user
    @Test(priority = 8, description = "GET /users/me/stays - Get my active stay")
    @Description("Verify that authenticated user can retrieve their active stay information")
    public void test08() {
        authorizeUserWithCredentials(email, "123456");
        Response staysResponse = UserApiClient.getMyStays();
        assertEquals(staysResponse.getStatusCode(), 200, "Status code isn't correct.");

//        UserUpdateRequest userUpdateRequest = new UserUpdateRequest()
//                .setFirstName(DataGenerator.generateFirstName())
//                .setLastName(DataGenerator.generateLastName())
//                .setGender(DataGenerator.generateGender())
//                .setBirthdate(DataGenerator.generateBirthdate(20));

    }

//TODO status code problem
    @Test(priority = 9, description = "GET /users/{user_id} - Invalid user ID")
    @Description("Verify proper error handling for invalid user ID")
    public void test09() {
        authorizeUserWithCredentials(email, "123456");
        UserUpdateRequest updatedRequestUserData = new UserUpdateRequest()
                .setFirstName(DataGenerator.generateFirstName())
                .setLastName(DataGenerator.generateLastName())
                .setGender(DataGenerator.generateGender())
                .setBirthdate(DataGenerator.generateBirthdate(DataGenerator.generateAge()));
        UserApiClient.updateMe(updatedRequestUserData);

        String invalidUserId = "invalid-user-id-12345";
        Response response = UserApiClient.getUserById(invalidUserId);
        assertEquals(response.getStatusCode(), 422, "Should return 404 for invalid user ID");
    }

    @Test(priority = 10, description = "PATCH /users/me - Invalid data")
    @Description("Verify proper error handling for invalid profile update data")
    public void test10() {
        authorizeUserWithCredentials(email, "123456");
        UserUpdateRequest updatedRequestUserData = new UserUpdateRequest()
                .setFirstName(DataGenerator.generateFirstName())
                .setLastName(DataGenerator.generateLastName())
                .setGender("invalid-gender")
                .setBirthdate("invalid-date-format");
        Response updateResponse = UserApiClient.updateMe(updatedRequestUserData);
        assertEquals(updateResponse.getStatusCode(), 422, "Should return 422");
    }
}

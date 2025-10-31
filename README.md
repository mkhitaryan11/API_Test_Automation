 allure:serve# API Test Automation Framework

A professional, production-ready API test automation framework built with Java, TestNG, RestAssured, Jackson, Log4j2, and Allure reporting. Designed for testing the Traveler API with comprehensive test coverage, authentication management, and detailed reporting.

## ✨ Key Features

- **🔐 2-Step Authentication**: Automated authentication flow with verifyResponse management
- **🎯 Service-Oriented Architecture**: Dedicated API clients for each service (Auth, User, Hotel, Event, Comment)
- **📊 Allure Reporting**: Beautiful, detailed test reports with history tracking
- **🏗️ POJO Models**: Clean, organized models using Lombok annotations
- **✅ Response Validation**: Comprehensive validation utilities for status codes, content types, and response times
- **📝 Comprehensive Logging**: Log4j2 with separate files for general logs and errors
- **🔄 Test Data Management**: JSON-based test data with flexible loading utilities
- **⚡ Performance Testing**: Response time validation and tracking
- **🧪 Negative Testing**: Error handling and edge case scenarios
- **📈 History Tracking**: Optional test history for trend analysis and CI/CD integration

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 11+ | Programming language |
| **Maven** | 3.6+ | Build and dependency management |
| **TestNG** | 7.8.0 | Test framework and execution |
| **RestAssured** | 5.3.1 | REST API testing library |
| **Jackson** | 2.15.2 | JSON serialization/deserialization |
| **Lombok** | 1.18.30 | Reduce boilerplate code |
| **Log4j2** | 2.20.0 | Logging framework |
| **Allure** | 2.24.0 | Test reporting and visualization |

## 📁 Project Structure

```
API_Test_Automation/
├── run-tests.sh              # Quick test runner (no history)
├── test-with-history.sh      # Test runner with history tracking
├── pom.xml                   # Maven configuration
├── README.md                 # This file
├── src/
│   ├── main/java/com/apitest/
│   │   ├── client/           # API client classes
│   │   │   ├── ApiClient.java         # Base API client with common methods
│   │   │   ├── AuthApiClient.java     # Authentication API client
│   │   │   ├── UserApiClient.java     # User API client
│   │   │   ├── HotelApiClient.java    # Hotel API client
│   │   │   ├── EventApiClient.java    # Event API client
│   │   │   └── CommentApiClient.java  # Comment API client
│   │   ├── model/            # POJO classes organized by service
│   │   │   ├── auth/         # Authentication models
│   │   │   │   ├── InitiateRequest.java
│   │   │   │   ├── VerifyRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   └── Token.java
│   │   │   ├── user/         # User models
│   │   │   │   ├── User.java
│   │   │   │   ├── UserUpdateRequest.java
│   │   │   │   └── HotelAdmin.java
│   │   │   ├── hotel/        # Hotel models (Hotel, Location, Member, Stay)
│   │   │   ├── event/        # Event models (Event, RecurringEvent, Exception)
│   │   │   └── comment/      # Comment models
│   │   ├── service/          # Service classes
│   │   │   ├── AuthorizationService.java  # 2-step authentication service
│   │   │   └── TokenManager.java          # Token storage and management
│   │   └── utils/            # Utility classes
│   │       ├── JsonUtils.java             # JSON serialization utilities
│   │       ├── ResponseValidator.java     # Response validation utilities
│   │       └── TestDataLoader.java        # Test data loading utilities
│   └── test/
│       ├── java/com/apitest/tests/    # Test classes
│       │   ├── BaseTest.java          # Base test class with common functionality
│       │   ├── AuthEndpointTests.java # Authentication endpoint tests
│       │   ├── UserApiTests.java      # User API test cases
│       │   └── AuthorizedApiTests.java # Authorized API tests
│       └── resources/
│           ├── testdata/              # JSON test data files
│           │   ├── initiate_request.json
│           │   ├── verify_request.json
│           │   ├── user_update_request.json
│           │   ├── hotel_create_request.json
│           │   ├── event_create_request.json
│           │   ├── comment_create_request.json
│           │   └── test-avatar.png
│           ├── testng.xml             # TestNG suite configuration
│           ├── log4j2.xml             # Log4j2 configuration
│           └── allure.properties      # Allure configuration
└── target/                            # Build artifacts (auto-generated)
    ├── allure-results/                # Allure test results
    ├── logs/                          # Application logs
    └── site/allure-maven-plugin/      # Generated Allure reports
```

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Internet connection (for API calls)

### Installation

1. Clone or download the project
2. Navigate to the project directory
3. Run Maven clean and compile:

```bash
mvn clean compile
```

### Running Tests

The framework provides two ways to run tests:

#### Option 1: Quick Test Runs (No History)

Use this for development and quick testing:

```bash
# Run all tests
./run-tests.sh

# Run specific test class
./run-tests.sh UserApiTests

# Run specific test method
./run-tests.sh UserApiTests#testGetCurrentUserProfile

# View report after tests
mvn allure:serve
```

Or use Maven directly:

```bash
# Run all tests
mvn clean test

# Run specific test class
mvn clean test -Dtest=UserApiTests

# Run specific test method
mvn clean test -Dtest=UserApiTests#testGetCurrentUserProfile

# Generate and view report
mvn allure:report
mvn allure:serve
```

#### Option 2: Tests with History Tracking

Use this to track test trends over time:

```bash
# Run tests with history preservation
./test-with-history.sh UserApiTests#testGetCurrentUserProfile

# Run all tests with history
./test-with-history.sh

# View report (history will be visible)
mvn allure:serve
```

**Note:** History tracking preserves test results across multiple runs, allowing you to see trends and patterns over time.

## Test Data Management

Test data is stored in JSON files under `src/test/resources/testdata/`:

- `user_update_request.json`: User profile update data
- `hotel_create_request.json`: Hotel creation data
- `location_create_request.json`: Location creation data
- `event_create_request.json`: Event creation data
- `comment_create_request.json`: Comment creation data
- `test-avatar.png`: Test image file for avatar uploads

### Simple TestDataLoader

The `TestDataLoader` class provides simple, clean methods for loading test data:

#### Core Methods
```java
// Load single object
User user = TestDataLoader.loadData("user_data.json", User.class);
Post post = TestDataLoader.loadData("new_post_data.json", Post.class);

// Load list of objects
List<User> users = TestDataLoader.loadDataList("users_data.json", new TypeReference<List<User>>() {});
List<Post> posts = TestDataLoader.loadDataList("posts_data.json", new TypeReference<List<Post>>() {});
```

#### Path Flexibility
- **Simple filename**: `"user_data.json"` → automatically prepends `testdata/`
- **Full path**: `"testdata/user_data.json"` → uses path as provided
- **Absolute path**: `"/config/settings.json"` → uses absolute path from resources root

#### Usage Examples
```java
// Load from default testdata directory
User user = TestDataLoader.loadData("user_data.json", User.class);

// Load from specific path
User user2 = TestDataLoader.loadData("testdata/users/user1.json", User.class);

// Load lists
List<User> users = TestDataLoader.loadDataList("users_data.json", new TypeReference<List<User>>() {});
```

## API Endpoints Tested

The framework tests the following endpoints using the Traveler API:

### Authentication Endpoints
- `POST /auth/initiate` - Initiate authentication with email
- `POST /auth/verify` - Verify authentication with email and code
- `POST /auth/refresh` - Refresh access verifyResponse
- `POST /auth/logout` - Logout and invalidate verifyResponse

### User Endpoints
- `GET /users/me` - Get current user profile
- `PATCH /users/me` - Update current user profile
- `GET /users/me/hotel-admin` - Get current user hotel admin info
- `GET /users/{user_id}` - Get user by ID
- `POST /users/me/avatar` - Upload user avatar (multipart/form-data)
- `DELETE /users/me/avatar` - Delete user avatar

### Hotel Endpoints
- `GET /hotels` - List all hotels (with pagination)
- `GET /hotels/{hotel_id}` - Get hotel by ID
- `POST /hotels` - Create new hotel
- `PATCH /hotels/{hotel_id}` - Update hotel
- `GET /hotels/{hotel_id}/locations` - List hotel locations
- `POST /hotels/{hotel_id}/locations` - Create hotel location
- `PATCH /hotels/locations/{location_id}` - Update location
- `DELETE /hotels/locations/{location_id}` - Delete location
- `GET /hotels/{hotel_id}/members` - Get hotel members
- `POST /hotels/{hotel_id}/members` - Add hotel member
- `DELETE /hotels/{hotel_id}/members/{user_id}` - Remove hotel member
- `GET /hotels/{hotel_id}/stays` - Get hotel stays
- `POST /hotels/{hotel_id}/stays` - Create stay
- `PATCH /hotels/{hotel_id}/stays/{stay_id}` - Update stay
- `DELETE /hotels/{hotel_id}/stays/{stay_id}` - Delete stay

### Event Endpoints
- `GET /events` - List events (with filtering)
- `GET /events/{event_id}` - Get event by ID
- `POST /events` - Create new event
- `PATCH /events/{event_id}` - Update event
- `POST /events/{event_id}/attend` - Attend event
- `DELETE /events/{event_id}/attend` - Leave event
- `POST /events/{event_id}/cancel` - Cancel event
- `POST /events/{event_id}/archive` - Archive event
- `POST /events/{event_id}/like` - Like event
- `GET /events/{event_id}/likes/count` - Get likes count
- `POST /events/{event_id}/images` - Upload event image
- `GET /events/{event_id}/images` - Get event images
- `DELETE /events/{event_id}/images/{image_id}` - Delete event image

### Recurring Event Endpoints
- `POST /events/recurring` - Create recurring event
- `GET /events/recurring/{recurring_event_id}/instances` - Get recurring instances
- `POST /events/recurring/{recurring_event_id}/exceptions` - Add recurring exception
- `GET /events/recurring/instances` - Get all recurring instances
- `DELETE /events/recurring/{recurring_event_id}` - Deactivate recurring event
- `GET /events/recurring/rrule-examples` - Get RRULE examples

### Comment Endpoints
- `GET /events/{event_id}/comments` - List event comments
- `POST /events/{event_id}/comments` - Create comment
- `DELETE /events/{event_id}/comments/{comment_id}` - Delete comment

### Meta Endpoints
- `GET /meta/users` - Get user metadata

## Test Categories

### Unit Tests
- Individual API endpoint testing
- Data validation testing
- Error handling testing

### Integration Tests
- End-to-end workflow testing
- Data consistency validation
- Performance validation

## User API Testing

The `UserApiTests` class provides comprehensive testing for user-related endpoints:

### Test Methods

1. **`testGetCurrentUserProfile()`** - Tests GET /users/me endpoint
   - Validates authenticated user can retrieve their profile
   - Checks response structure and required fields
   - Verifies user ID and email are present

2. **`testUpdateCurrentUserProfile()`** - Tests PATCH /users/me endpoint
   - Validates profile update functionality
   - Tests with UserUpdateRequest containing firstName, lastName, gender, birthdate
   - Verifies updated fields are reflected in response

3. **`testGetCurrentUserHotelAdmin()`** - Tests GET /users/me/hotel-admin endpoint
   - Retrieves hotel admin information for current user
   - Handles cases where user may not be a hotel admin
   - Validates response structure when data is available

4. **`testGetUserById()`** - Tests GET /users/{user_id} endpoint
   - Retrieves user profile by specific user ID
   - Validates user ID matches requested ID
   - Tests user profile data structure

5. **`testUploadUserAvatar()`** - Tests POST /users/me/avatar endpoint
   - Uploads avatar image using multipart/form-data
   - Tests file upload functionality
   - Uses test image file from resources

6. **`testDeleteUserAvatar()`** - Tests DELETE /users/me/avatar endpoint
   - Removes user's avatar image
   - Validates successful deletion response

7. **`testUserApiClientValidationMethods()`** - Tests UserApiClient functionality
   - Validates UserApiClient class accessibility
   - Tests UserUpdateRequest object creation
   - Verifies builder pattern functionality

8. **`testUserApiErrorScenarios()`** - Tests error handling
   - Tests unauthorized access (401 response)
   - Tests invalid user ID (404 response)
   - Validates proper error responses

### Test Data

User API tests use the following test data files:

- **`user_update_request.json`** - Contains sample user profile update data:
  ```json
  {
      "first_name": "Karenn",
      "last_name": "Mkhitaryan", 
      "gender": "male",
      "birthdate": "1994-07-12"
  }
  ```

- **`test-avatar.png`** - Small test image file for avatar upload testing

### Authentication

All User API tests require authentication. The tests use:
- **Email**: `test@example.com`
- **Code**: `123456` (fixed test code)
- **Method**: `authorizeUserWithCredentials()` from BaseTest

### Running User API Tests

```bash
# Run all User API tests
mvn test -Dtest=UserApiTests

# Run specific test method
mvn test -Dtest=UserApiTests#testGetCurrentUserProfile

# Run validation test
mvn test -Dtest=UserApiTests#testUserApiClientValidationMethods
```

## Logging

Logs are generated in the following locations:
- Console output
- `target/logs/api-test.log` - General logs
- `target/logs/api-test-error.log` - Error logs only

## Reporting

### Allure Reports
- Detailed test execution reports
- Test results with screenshots and logs
- Historical test data
- Test trends and analytics

### Surefire Reports
- Basic test execution reports
- Available in `target/surefire-reports/`

## Configuration

### Base URL
The framework is configured to use the Traveler API (`https://treveler-api-470986740614.europe-west1.run.app`). To change the base URL, modify the `BASE_URL` constant in `ApiClient.java`.

### Authentication
The framework supports 2-step authentication:
1. **Initiate**: POST `/auth/initiate` with email
2. **Verify**: POST `/auth/verify` with email and code
3. **Automatic Token Injection**: Bearer tokens are automatically added to requests

### Response Time Validation
Default maximum response time is set to 5000ms (5 seconds). This can be adjusted in the test methods.

### Logging Level
Logging levels can be configured in `log4j2.xml`. Default levels:
- Console: INFO
- File: DEBUG
- Error File: ERROR

## BaseTest Class

The framework includes a `BaseTest` class that provides:

### Common Setup and Teardown
- **@BeforeClass**: Sets up API base URI and logs test class information
- **@AfterClass**: Cleans up test class resources
- **@BeforeMethod**: Sets up individual test methods
- **@AfterMethod**: Cleans up individual test methods

### Utility Methods
- **Test Step Management**: Add test steps to Allure reports
- **Request/Response Logging**: Detailed logging for API calls
- **Test Data Management**: Add test data to reports
- **Error Handling**: Comprehensive error logging
- **Validation Helpers**: String and object validation utilities
- **Test Summary**: Log test execution summaries

### Usage
All test classes extend `BaseTest` to inherit common functionality:

```java
public class UserApiTests extends BaseTest {
    // Test methods automatically get setup/teardown
    // Access to utility methods like addTestStep(), addRequestDetails(), etc.
}
```

## Best Practices

1. **Test Data**: Keep test data in JSON files for easy maintenance
2. **Assertions**: Use descriptive assertion messages
3. **Logging**: Log important test steps and results
4. **Error Handling**: Test both positive and negative scenarios
5. **Performance**: Validate response times for critical endpoints
6. **Documentation**: Use Allure annotations for better reporting
7. **BaseTest**: Extend BaseTest for common functionality
8. **Test Steps**: Use addTestStep() for better Allure reporting

## Troubleshooting

### Common Issues

1. **Connection Issues**: Ensure internet connectivity for API calls
2. **Test Failures**: Check logs in `target/logs/` directory
3. **Allure Reports**: Ensure Allure is installed and configured
4. **Maven Issues**: Verify Java and Maven versions

### Debug Mode
To run tests in debug mode, add the following VM argument:
```bash
-Dlog4j.configurationFile=src/test/resources/log4j2.xml
```

## 🚀 Quick Start Guide

### **1. Clone and Setup**
```bash
cd /path/to/your/workspace
git clone <repository-url>
cd API_Test_Automation
mvn clean compile
```

### **2. Run Your First Test**
```bash
# Quick test run
./run-tests.sh UserApiTests#testGetCurrentUserProfile

# View the report
mvn allure:serve
```

### **3. Explore the Framework**
- Check `src/test/java/com/apitest/tests/` for test examples
- Review `src/test/resources/testdata/` for test data structure
- Examine `src/main/java/com/apitest/client/` for API client patterns

---

## 📊 Test Execution Summary

| Test Suite | Tests | Status | Coverage |
|------------|-------|--------|----------|
| **Authentication** | 8 tests | ✅ Complete | Initiate, Verify, Refresh, Logout |
| **User API** | 8 tests | ✅ Complete | Profile, Update, Avatar, Admin |
| **Hotel API** | - | 🔄 Ready | Clients & POJOs created |
| **Event API** | - | 🔄 Ready | Clients & POJOs created |
| **Comment API** | - | 🔄 Ready | Clients & POJOs created |

---

## 🤝 Contributing

1. Follow the existing code structure and patterns
2. Add proper logging and Allure annotations
3. Include both positive and negative test cases
4. Update test data files as needed
5. Ensure all tests pass before committing
6. Update README if adding new features

---

## 📝 License

This project is for educational and testing purposes.

---

## 📞 Support

For issues, questions, or contributions:
- Check the logs in `target/logs/`
- Review Allure reports for detailed test results
- Examine test data in `src/test/resources/testdata/`

---

**Built with ❤️ using Java, TestNG, RestAssured, and Allure**

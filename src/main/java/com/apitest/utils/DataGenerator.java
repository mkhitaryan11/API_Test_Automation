package com.apitest.utils;

import com.apitest.enums.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * Utility class for generating test data
 * Provides methods for generating unique names, emails, dates, and other test data
 */
public class DataGenerator {
    
    private static final Logger logger = LogManager.getLogger(DataGenerator.class);
    private static final Random random = new Random();
    
    // Date/Time formatters
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    /**
     * Generate a unique name with timestamp
     * @param prefix Name prefix (e.g., "Hotel", "Event", "User")
     * @return Unique name like "Hotel-20251013210500"
     */
    public static String generateName(String prefix) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String name = prefix + "-" + timestamp;
        logger.debug("Generated name: {}", name);
        return name;
    }
    
    /**
     * Generate a unique name with custom separator
     * @param prefix Name prefix
     * @param separator Separator character (e.g., "_", "-", ".")
     * @return Unique name like "Hotel_20251013210500"
     */
    public static String generateName(String prefix, String separator) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String name = prefix + separator + timestamp;
        logger.debug("Generated name with separator: {}", name);
        return name;
    }
    
    /**
     * Generate a unique email address
     * @param prefix Email prefix (e.g., "user", "admin", "test")
     * @return Unique email like "user-20251013210500@test.com"
     */
    public static String generateEmail() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String email = "test-" + timestamp + "@test.com";
        logger.debug("Generated email: {}", email);
        return email;
    }
    
    /**
     * Generate a unique email with custom domain
     * @param prefix Email prefix
     * @param domain Email domain (e.g., "example.com", "test.org")
     * @return Unique email like "user-20251013210500@example.com"
     */
    public static String generateEmail(String prefix, String domain) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String email = prefix + "-" + timestamp + "@" + domain;
        logger.debug("Generated email with custom domain: {}", email);
        return email;
    }
    
    /**
     * Generate current timestamp string
     * @return Timestamp like "20251013210500"
     */
    public static String generateTimestamp() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        logger.debug("Generated timestamp: {}", timestamp);
        return timestamp;
    }
    
    /**
     * Generate current date string
     * @return Date like "2025-10-13"
     */
    public static String generateDate() {
        String date = LocalDateTime.now().format(DATE_FORMATTER);
        logger.debug("Generated date: {}", date);
        return date;
    }
    
    /**
     * Generate current datetime string
     * @return DateTime like "2025-10-13T21:05:00"
     */
    public static String generateDateTime() {
        String dateTime = LocalDateTime.now().format(DATETIME_FORMATTER);
        logger.debug("Generated datetime: {}", dateTime);
        return dateTime;
    }
    
    /**
     * Generate a random UUID
     * @return Random UUID string
     */
    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        logger.debug("Generated UUID: {}", uuid);
        return uuid;
    }
    
    /**
     * Generate a random integer within range
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return Random integer between min and max
     */
    public static int generateRandomInt(int min, int max) {
        int value = random.nextInt((max - min) + 1) + min;
        logger.debug("Generated random int: {} (range: {}-{})", value, min, max);
        return value;
    }
    
    /**
     * Generate a random string of specified length
     * @param length Length of the string
     * @return Random alphanumeric string
     */
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        
        String randomString = sb.toString();
        logger.debug("Generated random string: {} (length: {})", randomString, length);
        return randomString;
    }
    
    /**
     * Generate a random phone number
     * @return Phone number like "+1234567890"
     */
    public static String generatePhoneNumber() {
        String phoneNumber = "+1" + generateRandomInt(1000000000, 1999999999);
        logger.debug("Generated phone number: {}", phoneNumber);
        return phoneNumber;
    }
    
    /**
     * Generate a unique description with timestamp
     * @param prefix Description prefix
     * @return Description like "Test description - 20251013210500"
     */
    public static String generateDescription(String prefix) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String description = prefix + " - " + timestamp;
        logger.debug("Generated description: {}", description);
        return description;
    }
    
    /**
     * Generate a random price
     * @param min Minimum price
     * @param max Maximum price
     * @return Random price with 2 decimal places
     */
    public static double generatePrice(double min, double max) {
        double price = min + (max - min) * random.nextDouble();
        double roundedPrice = Math.round(price * 100.0) / 100.0;
        logger.debug("Generated price: {} (range: {}-{})", roundedPrice, min, max);
        return roundedPrice;
    }
    
    /**
     * Generate a random boolean value
     * @return Random true or false
     */
    public static boolean generateBoolean() {
        boolean value = random.nextBoolean();
        logger.debug("Generated boolean: {}", value);
        return value;
    }
    
    /**
     * Generate a future date (days from now)
     * @param daysFromNow Number of days in the future
     * @return Future date like "2025-10-20T00:00:00Z"
     */
//    public static String generateFutureDate(int daysFromNow) {
//        String futureDate = LocalDateTime.now().plusDays(daysFromNow).format(DATE_FORMATTER);
//        logger.debug("Generated future date: {} ({} days from now)", futureDate, daysFromNow);
//        return futureDate;
//    }
    public static String generateFutureDate(int daysFromNow) {
        // Generate the future date and convert to UTC midnight (00:00:00Z)
        String futureDateUtc = LocalDateTime.now()
                .plusDays(daysFromNow)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);

        logger.debug("Generated future UTC date: {} ({} days from now)", futureDateUtc, daysFromNow);
        return futureDateUtc;
    }
    
    /**
     * Generate a future datetime (hours from now)
     * @param hoursFromNow Number of hours in the future
     * @return Future datetime like "2025-10-13T23:05:00"
     */
    public static String generateFutureDateTime(int hoursFromNow) {
        String futureDateTime = LocalDateTime.now().plusHours(hoursFromNow).format(DATETIME_FORMATTER);
        logger.debug("Generated future datetime: {} ({} hours from now)", futureDateTime, hoursFromNow);
        return futureDateTime;
    }
    
    /**
     * Generate a past date (days ago)
     * @param daysAgo Number of days in the past
     * @return Past date like "2025-10-01"
     */
    public static String generatePastDate(int daysAgo) {
        String pastDate = LocalDateTime.now().minusDays(daysAgo).format(DATE_FORMATTER);
        logger.debug("Generated past date: {} ({} days ago)", pastDate, daysAgo);
        return pastDate;
    }
    
    /**
     * Generate a random age between 18 and 80
     * @return Random age
     */
    public static int generateAge() {
        return generateRandomInt(18, 80);
    }
    
    /**
     * Generate a birthdate based on age
     * @param age Age in years
     * @return Birthdate like "1994-07-12"
     */
    public static String generateBirthdate(int age) {
        String birthdate = LocalDateTime.now().minusYears(age).format(DATE_FORMATTER);
        logger.debug("Generated birthdate: {} (age: {})", birthdate, age);
        return birthdate;
    }
    
    /**
     * Generate a random gender from Gender enum
     * @return "male", "female", or "other"
     */
    public static String generateGender() {
        Gender gender = Gender.random();
        logger.debug("Generated gender: {}", gender.getValue());
        return gender.getValue();
    }
    
    /**
     * Generate a random Gender enum value
     * @return Random Gender enum
     */
    public static Gender generateGenderEnum() {
        return Gender.random();
    }
    
    /**
     * Generate a unique hotel name
     * @return Hotel name like "Hotel-20251013210500"
     */
    public static String generateHotelName() {
        return generateName("Hotel");
    }

    /**
     * Generate a unique room number
     * @return Room number like "Room-20251013210500"
     */
    public static String generateRoomNumber() {
        return generateName("Room");
    }
    
    /**
     * Generate a unique event name
     * @return Event name like "Event-20251013210500"
     */
    public static String generateEventName() {
        return generateName("Event");
    }

    /**
     * Generate a random location type from LocationType enum
     * Valid values: "public", "private"
     * @return Random location type
     */
    public static String generateLocationType() {
        LocationType locationType = LocationType.random();
        logger.debug("Generated location type: {}", locationType.getValue());
        return locationType.getValue();
    }

    /**
     * Generate a unique location name
     * @return Location name like "Location-20251013210500"
     */
    public static String generateLocationName() {
        return generateName("Location");
    }

    /**
     * Generate a unique location text
     * @return Location text like "Location-20251013210500"
     */
    public static String generateLocationText() {
        return generateName("LocationText");
    }

    /**
     * Generate a random LocationType enum value
     * @return Random LocationType enum
     */
    public static LocationType generateLocationTypeEnum() {
        return LocationType.random();
    }
    
    /**
     * Generate a unique comment body
     * @return Comment like "Test comment - 20251013210500"
     */
    public static String generateCommentBody() {
        return generateDescription("Test comment");
    }
    
    /**
     * Generate random coordinates (latitude)
     * @return Latitude between -90 and 90
     */
    public static double generateLatitude() {
        double lat = -90 + (90 - (-90)) * random.nextDouble();
        double roundedLat = Math.round(lat * 1000000.0) / 1000000.0;
        logger.debug("Generated latitude: {}", roundedLat);
        return roundedLat;
    }
    
    /**
     * Generate random coordinates (longitude)
     * @return Longitude between -180 and 180
     */
    public static double generateLongitude() {
        double lon = -180 + (180 - (-180)) * random.nextDouble();
        double roundedLon = Math.round(lon * 1000000.0) / 1000000.0;
        logger.debug("Generated longitude: {}", roundedLon);
        return roundedLon;
    }
    
    /**
     * Generate a random duration in minutes
     * @param min Minimum duration
     * @param max Maximum duration
     * @return Duration in minutes
     */
    public static int generateDuration(int min, int max) {
        return generateRandomInt(min, max);
    }
    
    /**
     * Generate a random currency code from CurrencyCode enum
     * @return Currency code like "USD", "EUR", "GBP"
     */
    public static String generateCurrencyCode() {
        CurrencyCode currency = CurrencyCode.random();
        logger.debug("Generated currency: {}", currency.getValue());
        return currency.getValue();
    }
    
    /**
     * Generate a random CurrencyCode enum value
     * @return Random CurrencyCode enum
     */
    public static CurrencyCode generateCurrencyCodeEnum() {
        return CurrencyCode.random();
    }
    
    /**
     * Generate a unique test user first name
     * @return First name like "TestUser-20251013210500"
     */
    public static String generateFirstName() {
        return "TestUser-" + generateTimestamp();
    }
    
    /**
     * Generate a unique test user last name
     * @return Last name like "Automation-20251013210500"
     */
    public static String generateLastName() {
        return "Automation-" + generateTimestamp();
    }
    
    /**
     * Generate a complete unique test user name
     * @return Full name like "TestUser-20251013210500 Automation-20251013210500"
     */
    public static String generateFullName() {
        return generateFirstName() + " " + generateLastName();
    }
    
    /**
     * Sleep for specified milliseconds (useful for rate limiting)
     * @param milliseconds Time to sleep
     */
    public static void sleep(long milliseconds) {
        try {
            logger.debug("Sleeping for {} ms", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Generate a random URL
     * @param domain Domain name
     * @return URL like "https://example.com/path-20251013210500"
     */
    public static String generateUrl(String domain) {
        String timestamp = generateTimestamp();
        String url = "https://" + domain + "/path-" + timestamp;
        logger.debug("Generated URL: {}", url);
        return url;
    }
    
    /**
     * Generate a random image URL
     * @return Image URL
     */
    public static String generateImageUrl() {
        String timestamp = generateTimestamp();
        String imageUrl = "https://example.com/images/test-" + timestamp + ".jpg";
        logger.debug("Generated image URL: {}", imageUrl);
        return imageUrl;
    }
    
    // ==================== Hotel-Specific Data Generators ====================
    
    /**
     * Generate contacts JSON string for hotel
     * @return JSON string with contact information
     */
    public static String generateContactsJson() {
        String timestamp = generateTimestamp();
        String contactsJson = "{\"phone\":\"+1234567890\",\"email\":\"hotel-" + timestamp + "@test.com\",\"website\":\"https://hotel-" + timestamp + ".com\"}";
        logger.debug("Generated contacts JSON: {}", contactsJson);
        return contactsJson;
    }
    
    /**
     * Generate a unique address text
     * @return Address like "123 Main St, City-20251014181700"
     */
    public static String generateAddressText() {
        String timestamp = generateTimestamp();
        int streetNumber = generateRandomInt(1, 9999);
        String[] streets = {"Main St", "Oak Ave", "Park Blvd", "Broadway", "Sunset Dr", "River Rd"};
        String street = streets[random.nextInt(streets.length)];
        String address = streetNumber + " " + street + ", City-" + timestamp;
        logger.debug("Generated address: {}", address);
        return address;
    }
    
    /**
     * Generate a random hotel type from HotelType enum
     * Valid values: "open", "closed", "private"
     * @return Random hotel type
     */
    public static String generateHotelType() {
        HotelType hotelType = HotelType.random();
        logger.debug("Generated hotel type: {}", hotelType.getValue());
        return hotelType.getValue();
    }
    
    /**
     * Generate a random HotelType enum value
     * @return Random HotelType enum
     */
    public static HotelType generateHotelTypeEnum() {
        return HotelType.random();
    }
    
    /**
     * Generate a random event policy from EventPolicy enum
     * Valid values: "free", "paid", "invite_only", "members_only"
     * @return Random event policy
     */
    public static String generateEventPolicy() {
        EventPolicy eventPolicy = EventPolicy.random();
        logger.debug("Generated event policy: {}", eventPolicy.getValue());
        return eventPolicy.getValue();
    }
    
    /**
     * Generate a random EventPolicy enum value
     * @return Random EventPolicy enum
     */
    public static EventPolicy generateEventPolicyEnum() {
        return EventPolicy.random();
    }
    
    
    /**
     * Generate a random activity type code
     * @return Activity type code like "ACT-20251014181700"
     */
    public static String generateActivityTypeCode() {
        String timestamp = generateTimestamp();
        String activityCode = "ACT-" + timestamp;
        logger.debug("Generated activity type code: {}", activityCode);
        return activityCode;
    }
    
    /**
     * Generate a random language code from LanguageCode enum
     * @return Language code like "en", "es", "fr", "de", "hy"
     */
    public static String generateLanguageCode() {
        LanguageCode language = LanguageCode.random();
        logger.debug("Generated language code: {}", language.getValue());
        return language.getValue();
    }
    
    /**
     * Generate a random LanguageCode enum value
     * @return Random LanguageCode enum
     */
    public static LanguageCode generateLanguageCodeEnum() {
        return LanguageCode.random();
    }
    
    /**
     * Generate a random mode for events from EventMode enum
     * Valid values: "open", "closed", "private"
     * @return Random event mode
     */
    public static String generateEventMode() {
        EventMode mode = EventMode.random();
        logger.debug("Generated event mode: {}", mode.getValue());
        return mode.getValue();
    }
    
    /**
     * Generate a random EventMode enum value
     * @return Random EventMode enum
     */
    public static EventMode generateEventModeEnum() {
        return EventMode.random();
    }
}



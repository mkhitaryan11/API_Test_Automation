package com.apitest.enums;

/**
 * Enum for currency codes
 */
public enum UserType {
    GUEST("asd@example.com"),
    HOTEL_SUPER_ADMIN("hotel_admin@example.com"),
    PLATFORM_SUPER_ADMIN("admin@example.com"),
    AMD("AMD"),
    RUB("RUB");

    private final String value;

    UserType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    /**
     * Get a random currency code
     * @return Random CurrencyCode enum value
     */
    public static UserType random() {
        UserType[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


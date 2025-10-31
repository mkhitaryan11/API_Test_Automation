package com.apitest.enums;

/**
 * Enum for hotel type values
 */
public enum LocationType {
    PUBLIC("public"),
    PRIVATE("private");

    private final String value;

    LocationType(String value) {
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
     * Get a random hotel type
     * @return Random HotelType enum value
     */
    public static LocationType random() {
        LocationType[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


package com.apitest.enums;

/**
 * Enum for hotel type values
 */
public enum HotelType {
    OPEN("open"),
    CLOSED("closed");
    
    private final String value;
    
    HotelType(String value) {
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
    public static HotelType random() {
        HotelType[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


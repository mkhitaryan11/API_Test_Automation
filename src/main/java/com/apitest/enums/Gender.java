package com.apitest.enums;

/**
 * Enum for user gender values
 */
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");
    
    private final String value;
    
    Gender(String value) {
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
     * Get a random gender
     * @return Random Gender enum value
     */
    public static Gender random() {
        Gender[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


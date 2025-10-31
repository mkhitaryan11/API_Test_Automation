package com.apitest.enums;

/**
 * Enum for event mode values
 */
public enum EventMode {
    OPEN("open"),
    CLOSED("closed"),
    PRIVATE("private");
    
    private final String value;
    
    EventMode(String value) {
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
     * Get a random event mode
     * @return Random EventMode enum value
     */
    public static EventMode random() {
        EventMode[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


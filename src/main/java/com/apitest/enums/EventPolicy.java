package com.apitest.enums;

/**
 * Enum for event policy values
 */
public enum EventPolicy {
    FREE("free"),
    MODERATED("moderated");
    
    private final String value;
    
    EventPolicy(String value) {
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
     * Get a random event policy
     * @return Random EventPolicy enum value
     */
    public static EventPolicy random() {
        EventPolicy[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


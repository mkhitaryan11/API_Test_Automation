package com.apitest.enums;

/**
 * Enum for currency codes
 */
public enum CurrencyCode {
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    AMD("AMD"),
    RUB("RUB");
    
    private final String value;
    
    CurrencyCode(String value) {
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
    public static CurrencyCode random() {
        CurrencyCode[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


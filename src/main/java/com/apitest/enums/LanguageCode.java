package com.apitest.enums;

/**
 * Enum for language codes
 */
public enum LanguageCode {
    EN("en"),
    ES("es"),
    FR("fr"),
    DE("de"),
    HY("hy"),
    RU("ru"),
    IT("it"),
    PT("pt");
    
    private final String value;
    
    LanguageCode(String value) {
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
     * Get a random language code
     * @return Random LanguageCode enum value
     */
    public static LanguageCode random() {
        LanguageCode[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}


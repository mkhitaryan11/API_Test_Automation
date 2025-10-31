package com.apitest.examples;

import com.apitest.enums.*;
import com.apitest.utils.DataGenerator;
import org.testng.annotations.Test;

/**
 * Example demonstrating enum-based data generation
 */
public class EnumDataGeneratorExample {
    
    @Test
    public void demonstrateEnumDataGeneration() {
        System.out.println("\n========================================");
        System.out.println("Enum-Based Data Generation Examples");
        System.out.println("========================================\n");
        
        // Gender enum
        System.out.println("1. Gender Generation:");
        System.out.println("   String value: " + DataGenerator.generateGender());
        System.out.println("   Enum value: " + DataGenerator.generateGenderEnum());
        System.out.println("   Direct enum: " + Gender.random().getValue());
        System.out.println("   All genders: MALE, FEMALE, OTHER");
        
        // HotelType enum
        System.out.println("\n2. Hotel Type Generation:");
        System.out.println("   String value: " + DataGenerator.generateHotelType());
        System.out.println("   Enum value: " + DataGenerator.generateHotelTypeEnum());
        System.out.println("   Direct enum: " + HotelType.random().getValue());
        System.out.println("   All types: OPEN, CLOSED, PRIVATE");
        
        // EventPolicy enum
        System.out.println("\n3. Event Policy Generation:");
        System.out.println("   String value: " + DataGenerator.generateEventPolicy());
        System.out.println("   Enum value: " + DataGenerator.generateEventPolicyEnum());
        System.out.println("   Direct enum: " + EventPolicy.random().getValue());
        System.out.println("   All policies: FREE, PAID, INVITE_ONLY, MEMBERS_ONLY");
        
        // EventMode enum
        System.out.println("\n4. Event Mode Generation:");
        System.out.println("   String value: " + DataGenerator.generateEventMode());
        System.out.println("   Enum value: " + DataGenerator.generateEventModeEnum());
        System.out.println("   Direct enum: " + EventMode.random().getValue());
        System.out.println("   All modes: OPEN, CLOSED, PRIVATE");
        
        // CurrencyCode enum
        System.out.println("\n5. Currency Code Generation:");
        System.out.println("   String value: " + DataGenerator.generateCurrencyCode());
        System.out.println("   Enum value: " + DataGenerator.generateCurrencyCodeEnum());
        System.out.println("   Direct enum: " + CurrencyCode.random().getValue());
        System.out.println("   All currencies: USD, EUR, GBP, AMD, RUB");
        
        // LanguageCode enum
        System.out.println("\n6. Language Code Generation:");
        System.out.println("   String value: " + DataGenerator.generateLanguageCode());
        System.out.println("   Enum value: " + DataGenerator.generateLanguageCodeEnum());
        System.out.println("   Direct enum: " + LanguageCode.random().getValue());
        System.out.println("   All languages: EN, ES, FR, DE, HY, RU, IT, PT");
        
        // Complete Hotel example
        System.out.println("\n========================================");
        System.out.println("Complete Hotel Creation Example:");
        System.out.println("========================================");
        System.out.println("name: " + DataGenerator.generateHotelName());
        System.out.println("description: " + DataGenerator.generateDescription("Luxury hotel"));
        System.out.println("contacts_json: " + DataGenerator.generateContactsJson());
        System.out.println("address_text: " + DataGenerator.generateAddressText());
        System.out.println("lat: " + DataGenerator.generateLatitude());
        System.out.println("lon: " + DataGenerator.generateLongitude());
        System.out.println("is_active: " + DataGenerator.generateBoolean());
        System.out.println("pre_moderated: " + DataGenerator.generateBoolean());
        System.out.println("hotel_type: " + DataGenerator.generateHotelType());
        System.out.println("event_policy: " + DataGenerator.generateEventPolicy());
        
        System.out.println("\n========================================");
        System.out.println("Benefits of Using Enums:");
        System.out.println("========================================");
        System.out.println("✅ Type safety - compile-time validation");
        System.out.println("✅ IDE autocomplete - easy to discover valid values");
        System.out.println("✅ Centralized - single source of truth");
        System.out.println("✅ Maintainable - easy to add/remove values");
        System.out.println("✅ Testable - can iterate through all values");
        System.out.println("✅ Random generation - built-in random() method");
        
        System.out.println("\n========================================\n");
    }
}


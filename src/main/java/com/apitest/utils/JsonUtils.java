package com.apitest.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Utility class for JSON operations
 */
public class JsonUtils {
    
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        // Configure ObjectMapper
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Deserialize JSON string to POJO object
     * @param jsonString JSON string
     * @param clazz Target class
     * @param <T> Generic type
     * @return Deserialized object
     */
    public static <T> T deserializeFromString(String jsonString, Class<T> clazz) {
        try {
            logger.info("Deserializing JSON string to class: {}", clazz.getSimpleName());
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("Error deserializing JSON string to class {}: {}", clazz.getSimpleName(), e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON string", e);
        }
    }

    /**
     * Deserialize JSON file to POJO object
     * @param filePath Path to JSON file
     * @param clazz Target class
     * @param <T> Generic type
     * @return Deserialized object
     */
    public static <T> T deserializeFromFile(String filePath, Class<T> clazz) {
        try {
            logger.info("Deserializing JSON file: {} to class: {}", filePath, clazz.getSimpleName());
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            logger.error("Error deserializing JSON file {} to class {}: {}", filePath, clazz.getSimpleName(), e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON file: " + filePath, e);
        }
    }

    /**
     * Deserialize JSON file from resources to POJO object
     * @param resourcePath Path to JSON file in resources
     * @param clazz Target class
     * @param <T> Generic type
     * @return Deserialized object
     */
    public static <T> T deserializeFromResource(String resourcePath, Class<T> clazz) {
        try {
            logger.info("Deserializing JSON resource: {} to class: {}", resourcePath, clazz.getSimpleName());
            InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            return objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            logger.error("Error deserializing JSON resource {} to class {}: {}", resourcePath, clazz.getSimpleName(), e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON resource: " + resourcePath, e);
        }
    }

    /**
     * Deserialize JSON string to List of POJO objects
     * @param jsonString JSON string
     * @param typeReference Type reference for the list
     * @param <T> Generic type
     * @return Deserialized list
     */
    public static <T> List<T> deserializeToList(String jsonString, TypeReference<List<T>> typeReference) {
        try {
            logger.info("Deserializing JSON string to list");
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            logger.error("Error deserializing JSON string to list: {}", e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON string to list", e);
        }
    }

    /**
     * Deserialize JSON file to List of POJO objects
     * @param filePath Path to JSON file
     * @param typeReference Type reference for the list
     * @param <T> Generic type
     * @return Deserialized list
     */
    public static <T> List<T> deserializeToListFromFile(String filePath, TypeReference<List<T>> typeReference) {
        try {
            logger.info("Deserializing JSON file: {} to list", filePath);
            return objectMapper.readValue(new File(filePath), typeReference);
        } catch (IOException e) {
            logger.error("Error deserializing JSON file {} to list: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON file to list: " + filePath, e);
        }
    }

    /**
     * Deserialize JSON file from resources to List of POJO objects
     * @param resourcePath Path to JSON file in resources
     * @param typeReference Type reference for the list
     * @param <T> Generic type
     * @return Deserialized list
     */
    public static <T> List<T> deserializeToListFromResource(String resourcePath, TypeReference<List<T>> typeReference) {
        try {
            logger.info("Deserializing JSON resource: {} to list", resourcePath);
            InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            logger.error("Error deserializing JSON resource {} to list: {}", resourcePath, e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON resource to list: " + resourcePath, e);
        }
    }

    /**
     * Serialize POJO object to JSON string
     * @param object Object to serialize
     * @return JSON string
     */
    public static String serializeToString(Object object) {
        try {
            logger.info("Serializing object to JSON string: {}", object.getClass().getSimpleName());
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("Error serializing object to JSON string: {}", e.getMessage());
            throw new RuntimeException("Failed to serialize object to JSON string", e);
        }
    }

    /**
     * Serialize POJO object to JSON file
     * @param object Object to serialize
     * @param filePath Path to output file
     */
    public static void serializeToFile(Object object, String filePath) {
        try {
            logger.info("Serializing object to JSON file: {}", filePath);
            objectMapper.writeValue(new File(filePath), object);
        } catch (IOException e) {
            logger.error("Error serializing object to JSON file {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to serialize object to JSON file: " + filePath, e);
        }
    }

    /**
     * Get ObjectMapper instance
     * @return ObjectMapper instance
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}

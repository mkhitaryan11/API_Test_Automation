package com.apitest.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Simple utility class for loading test data from JSON files
 */
public class TestDataLoader {
    
    private static final Logger logger = LogManager.getLogger(TestDataLoader.class);
    private static final String DEFAULT_TEST_DATA_PATH = "testdata/";

    /**
     * Load test data from JSON file
     * @param filePath File path (can be relative to testdata/ or absolute path)
     * @param clazz Target class
     * @param <T> Generic type
     * @return Deserialized object
     */
    public static <T> T loadData(String filePath, Class<T> clazz) {
        String resourcePath = buildResourcePath(filePath);
        logger.info("Loading test data from: {}", resourcePath);
        return JsonUtils.deserializeFromResource(resourcePath, clazz);
    }

    /**
     * Load test data list from JSON file
     * @param filePath File path (can be relative to testdata/ or absolute path)
     * @param typeReference Type reference for the list
     * @param <T> Generic type
     * @return Deserialized list
     */
    public static <T> List<T> loadDataList(String filePath, TypeReference<List<T>> typeReference) {
        String resourcePath = buildResourcePath(filePath);
        logger.info("Loading test data list from: {}", resourcePath);
        return JsonUtils.deserializeToListFromResource(resourcePath, typeReference);
    }

    /**
     * Build the complete resource path based on the provided file path
     * @param filePath File path (can be relative to testdata/ or absolute path)
     * @return Complete resource path
     */
    private static String buildResourcePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        
        // If the path already starts with testdata/ or is an absolute path, use it as is
        if (filePath.startsWith("testdata/") || filePath.startsWith("/")) {
            return filePath;
        }
        
        // Otherwise, prepend the default test data path
        return DEFAULT_TEST_DATA_PATH + filePath;
    }
}

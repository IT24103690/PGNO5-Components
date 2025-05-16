package com.inventory.inventorymanagement.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonFileHandler {

    private final ObjectMapper objectMapper;

    public JsonFileHandler() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> List<T> readFromJson(String filePath, Class<T> clazz) throws Exception {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<T>>() {});
        } catch (Exception e) {
            System.err.println("Error reading JSON from " + filePath + ": " + e.getMessage());
            throw e;
        }
    }

    public <T> void writeToJson(List<T> list, String filePath) throws Exception {
        try {
            objectMapper.writeValue(new File(filePath), list);
        } catch (Exception e) {
            System.err.println("Error writing JSON to " + filePath + ": " + e.getMessage());
            throw e;
        }
    }
}
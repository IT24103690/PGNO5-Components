package com.inventory.inventorymanagement.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileHandler {

    private final ObjectMapper objectMapper;

    public JsonFileHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        this.objectMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT); // Pretty printing
    }

    public <T> List<T> readFromJson(String filePath, Class<T> clazz) throws Exception {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Files.createDirectories(file.getParentFile().toPath());
                Files.write(Paths.get(filePath), "[]".getBytes()); // Create empty JSON array if file doesn't exist
            }
            return objectMapper.readValue(file, new TypeReference<List<T>>() {});
        } catch (Exception e) {
            System.err.println("Error reading JSON from " + filePath + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public <T> void writeToJson(List<T> list, String filePath) throws Exception {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Files.createDirectories(file.getParentFile().toPath());
            }
            objectMapper.writeValue(file, list);
        } catch (Exception e) {
            System.err.println("Error writing JSON to " + filePath + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
package com.inventory.inventorymanagement.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileHandler {
    private final ObjectMapper objectMapper;

    public JsonFileHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> List<T> readFromJson(String filePath, Class<T> clazz) throws IOException {
        File file = new File(filePath);
        System.out.println("Attempting to read JSON from: " + file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath() + ". Creating empty file.");
            Files.createDirectories(file.getParentFile().toPath());
            Files.write(Paths.get(filePath), "[]".getBytes());
        }
        try {
            List<T> result = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            System.out.println("Successfully read " + (result != null ? result.size() : 0) + " items from JSON: " + result);
            return result != null ? result : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error reading JSON from " + file.getAbsolutePath() + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public <T> void writeToJson(List<T> data, String filePath) throws IOException {
        File file = new File(filePath);
        System.out.println("Writing JSON to: " + file.getAbsolutePath() + " with data: " + data);
        try {
            Files.createDirectories(file.getParentFile().toPath());
            if (data == null) {
                System.out.println("Data is null, writing empty list to JSON.");
                objectMapper.writeValue(file, new ArrayList<>());
            } else {
                objectMapper.writeValue(file, data);
            }
            System.out.println("Successfully wrote " + (data != null ? data.size() : 0) + " items to JSON.");
        } catch (IOException e) {
            System.err.println("Error writing JSON to " + file.getAbsolutePath() + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
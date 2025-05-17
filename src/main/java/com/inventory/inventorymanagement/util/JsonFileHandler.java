package com.inventory.inventorymanagement.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> List<T> readFromJson(String filePath, Class<T> clazz) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new java.util.ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<T>>(){});
    }

    public <T> void writeToJson(List<T> data, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), data);
    }
}
package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.model.User;
import com.inventory.inventorymanagement.util.JsonFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final JsonFileHandler jsonFileHandler;
    private final ResourceLoader resourceLoader;
    private static final String USERS_FILE_PATH = "src/main/resources/data/users.json";
    private static final String WRITABLE_FILE_PATH = "src/main/resources/data/users.json";

    @Autowired
    public UserService(ResourceLoader resourceLoader) {
        this.jsonFileHandler = new JsonFileHandler();
        this.resourceLoader = resourceLoader;
        initializeWritableFile();
    }

    private void initializeWritableFile() {
        try {
            Path writablePath = Paths.get(WRITABLE_FILE_PATH);
            if (!Files.exists(writablePath.getParent())) {
                Files.createDirectories(writablePath.getParent());
            }
            if (!Files.exists(writablePath)) {
                Resource resource = resourceLoader.getResource(USERS_FILE_PATH);
                if (resource.exists()) {
                    Files.copy(resource.getInputStream(), writablePath);
                } else {
                    Files.write(writablePath, "[]".getBytes()); // Create empty JSON array
                }
            }
            System.out.println("Initialized writable file at: " + writablePath.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error initializing writable file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createUser(User user) throws Exception {
        List<User> users = getAllUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        jsonFileHandler.writeToJson(users, WRITABLE_FILE_PATH);
        System.out.println("User created, saved to: " + new File(WRITABLE_FILE_PATH).getAbsolutePath());
    }

    public List<User> getAllUsers() throws Exception {
        Path writablePath = Paths.get(WRITABLE_FILE_PATH);
        if (!Files.exists(writablePath)) {
            initializeWritableFile();
        }
        List<User> users = jsonFileHandler.readFromJson(WRITABLE_FILE_PATH, User.class);
        return users != null ? users : new ArrayList<>();
    }

    public User getUserById(String id) throws Exception {
        List<User> users = getAllUsers();
        Optional<User> user = users.stream()
                .filter(u -> u.getId() != null && u.getId().equals(id))
                .findFirst();
        return user.orElse(null);
    }

    public User getUserByName(String name) throws Exception {
        List<User> users = getAllUsers();
        Optional<User> user = users.stream()
                .filter(u -> u.getName() != null && u.getName().equals(name))
                .findFirst();
        return user.orElse(null);
    }

    public void updateUser(User updatedUser) throws Exception {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() != null && users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                break;
            }
        }
        jsonFileHandler.writeToJson(users, WRITABLE_FILE_PATH);
    }

    public void deleteUser(String id) throws Exception {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId() != null && user.getId().equals(id));
        jsonFileHandler.writeToJson(users, WRITABLE_FILE_PATH);
    }
}
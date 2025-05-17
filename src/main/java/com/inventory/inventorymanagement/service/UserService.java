package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.model.User;
import com.inventory.inventorymanagement.util.JsonFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private static final String USERS_FILE_PATH = "src/main/resources/data/users.json";
    private static final String WRITABLE_FILE_PATH = "src/main/resources/data/users.json";

    @Autowired
    public UserService(ResourceLoader resourceLoader, PasswordEncoder passwordEncoder) {
        this.jsonFileHandler = new JsonFileHandler();
        this.resourceLoader = resourceLoader;
        this.passwordEncoder = passwordEncoder;
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
                    Files.write(writablePath, "[]".getBytes());
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.add(user);
        jsonFileHandler.writeToJson(users, WRITABLE_FILE_PATH);
        System.out.println("User created and saved: " + user);
    }

    public List<User> getAllUsers() throws Exception {
        Path writablePath = Paths.get(WRITABLE_FILE_PATH);
        if (!Files.exists(writablePath)) {
            initializeWritableFile();
        }
        List<User> users = jsonFileHandler.readFromJson(WRITABLE_FILE_PATH, User.class);
        System.out.println("Loaded users: " + users);
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
        System.out.println("Searching for user: " + name);
        List<User> users = getAllUsers();
        System.out.println("Total users loaded: " + (users != null ? users.size() : 0));
        System.out.println("Usernames in list: " + users.stream()
                .map(u -> u.getName() != null ? u.getName() : "null")
                .collect(java.util.stream.Collectors.joining(", ")));
        Optional<User> user = users.stream()
                .filter(u -> u.getName() != null && u.getName().equalsIgnoreCase(name))
                .findFirst();
        System.out.println("Found user: " + user.orElse(null));
        return user.orElse(null);
    }

    public void updateUser(User updatedUser, String originalId) throws Exception {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() != null && users.get(i).getId().equals(originalId)) {
                // If a new password is provided, encode it; otherwise, retain the existing password
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                } else {
                    updatedUser.setPassword(users.get(i).getPassword());
                }
                users.set(i, updatedUser);
                break;
            }
        }
        jsonFileHandler.writeToJson(users, WRITABLE_FILE_PATH);
    }

    public void deleteUser(String id) throws Exception {
        List<User> users = getAllUsers();
        System.out.println("Before deletion - Users: " + users + ", Deleting ID: " + id);
        users.removeIf(user -> user.getId() != null && user.getId().equals(id));
        System.out.println("After deletion - Users: " + users);
        jsonFileHandler.writeToJson(users, WRITABLE_FILE_PATH);
    }
}
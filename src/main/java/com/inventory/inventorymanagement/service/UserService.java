package com.inventory.inventorymanagement.service;

import com.inventory.inventorymanagement.model.User;
import com.inventory.inventorymanagement.util.JsonFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final JsonFileHandler jsonFileHandler;
    private final ResourceLoader resourceLoader;
    private static final String USERS_FILE_PATH = "src/main/resources/data/users.json";

    @Autowired
    public UserService(ResourceLoader resourceLoader) {
        this.jsonFileHandler = new JsonFileHandler();
        this.resourceLoader = resourceLoader;
    }

    public void createUser(User user) throws Exception {
        Resource resource = resourceLoader.getResource(USERS_FILE_PATH);
        if (!resource.exists()) {
            throw new Exception("users.json file not found at " + USERS_FILE_PATH);
        }
        List<User> users = jsonFileHandler.readFromJson(resource.getFile().getPath(), User.class);

        if (users == null) {
            users = new ArrayList<>();
        }

        users.add(user);
        jsonFileHandler.writeToJson(users, resource.getFile().getPath());
    }

    public List<User> getAllUsers() throws Exception {
        Resource resource = resourceLoader.getResource(USERS_FILE_PATH);
        if (!resource.exists()) {
            System.err.println("users.json file not found at " + USERS_FILE_PATH + ". Returning empty list.");
            return new ArrayList<>(); // Return empty list instead of throwing an exception
        }
        List<User> users = jsonFileHandler.readFromJson(resource.getFile().getPath(), User.class);
        return users != null ? users : new ArrayList<>();
    }

    public User getUserById(String id) throws Exception {
        List<User> users = getAllUsers();
        Optional<User> user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        return user.orElse(null);
    }

    public User getUserByName(String name) throws Exception {
        List<User> users = getAllUsers();
        Optional<User> user = users.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
        return user.orElse(null);
    }

    public void updateUser(User updatedUser) throws Exception {
        Resource resource = resourceLoader.getResource(USERS_FILE_PATH);
        if (!resource.exists()) {
            throw new Exception("users.json file not found at " + USERS_FILE_PATH);
        }
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                break;
            }
        }
        jsonFileHandler.writeToJson(users, resource.getFile().getPath());
    }

    public void deleteUser(String id) throws Exception {
        Resource resource = resourceLoader.getResource(USERS_FILE_PATH);
        if (!resource.exists()) {
            throw new Exception("users.json file not found at " + USERS_FILE_PATH);
        }
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId().equals(id));
        jsonFileHandler.writeToJson(users, resource.getFile().getPath());
    }
}
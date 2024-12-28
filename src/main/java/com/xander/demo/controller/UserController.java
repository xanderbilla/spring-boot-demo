package com.xander.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /*
     * Add a new user
     * 
     * @param userEntity
     * 
     * @return String
     * 
     * @RequestBody UserEntity userEntity
     * 
     * {
     *     "username": "john.doe",
     *     "password": "password"
     * }
     * 
     * POST /user
     * 
     * Response: User created successfully
     * 
     */
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity) {
        try {
            userEntity.setCreateDate(LocalDateTime.now());
            if (userService.findByUsername(userEntity.getUsername()) == null) {
                userService.saveUser(userEntity);
                return ResponseEntity.ok().body("User created successfully");
            } else {
                return ResponseEntity.badRequest().body("User already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     * Get all users
     * 
     * @return List<UserEntity>
     * 
     * GET /user
     * 
     * Response: [
     *     {
     *         "id": "5f7b3b7b7b3b7b3b7b3b7b3b",
     *         "username": "john.doe",
     *         "password": "password",
     *         "createDate": "2020-10-05T12:00:00",
     *         "updateDate": "2020-10-05T12:00:00"
     *     }
     * ]
     * 
     */
    @GetMapping
    public ResponseEntity<?> getUsers() {
        try {
            List<UserEntity> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok().body(users);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     * Get a user by username
     * 
     * @param username
     * @return UserEntity
     * 
     * GET /user/john.doe
     * 
     * Response: {
     *     "id": "5f7b3b7b7b3b7b3b7b3b7b3b",
     *     "username": "john.doe",
     *     "password": "password",
     *     "createDate": "2020-10-05T12:00:00",
     *     "updateDate": "2020-10-05T12:00:00"
     * }
     * 
     */
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity, @PathVariable String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            if (user != null) {
                user.setUsername(userEntity.getUsername());
                user.setPassword(userEntity.getPassword());
                user.setUpdateDate(LocalDateTime.now());
                userService.saveUser(user);
                return ResponseEntity.ok().body("User updated successfully");
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

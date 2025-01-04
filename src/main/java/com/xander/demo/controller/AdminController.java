package com.xander.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Get all users
     * 
     * @return List<UserEntity>
     * 
     * GET /admin/all-users
     * 
     * Response: [
     *      {
     *           "id": 1,
     *           "username": "john.doe",
     *           "password": "password",
     *           "createDate": "2021-08-01T12:00:00"
     *           "role": ["ADMIN"]
     *      }
     * ]
     */
    @GetMapping("/all-users")
    public ResponseEntity<List<UserEntity>> fetchAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
     * Add a new admin user
     * 
     * @param userEntity
     * 
     * @return String
     * 
     * @RequestBody UserEntity userEntity
     * 
     * {
     *      "username": "john.doe",
     *      "password": "password"
     * }
     */
    @PostMapping("/add-admin-user")
    public ResponseEntity<String> addAdminUser(@RequestBody UserEntity userEntity) {
        try {
            userEntity.setCreateDate(LocalDateTime.now());
            if (userService.findByUsername(userEntity.getUsername()) == null) {
                userService.saveNewUser(userEntity);
                return new ResponseEntity<>("User created successfully! ✅", HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("User already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     * Promote a user to admin
     * 
     * @param userEntity
     * 
     * @return String
     * 
     * @RequestBody UserEntity userEntity
     * 
     * 
     */
    @PutMapping("/promote-user/{username}")
    public ResponseEntity<String> promoteUser(@PathVariable String username) {
        UserEntity getUser = userService.findByUsername(username);
        if (getUser.getRoles().contains("ADMIN")) {
            return ResponseEntity.ok().body("User is already an admin");
        } else {
            getUser.getRoles().add("ADMIN");
            getUser.setUpdateDate(LocalDateTime.now());
            userService.saveUser(getUser);
            return ResponseEntity.ok().body("User promoted to admin");
        }
    }
}

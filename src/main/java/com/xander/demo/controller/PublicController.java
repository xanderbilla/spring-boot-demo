package com.xander.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.service.UserService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    
    /*
     * Health Check
     */
    @GetMapping("/health")
    public String Health() {
        return "Health Check Passed! ✅";
    }

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
    @PostMapping("/create-user")
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

}

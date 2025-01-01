package com.xander.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.service.UserService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping("/health-check")
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
     * "username": "john.doe",
     * "password": "password"
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
                userService.saveNewUser(userEntity);
                return new ResponseEntity<>("User created successfully! ✅", HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("User already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

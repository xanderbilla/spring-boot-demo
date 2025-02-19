package com.xander.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.config.AppCache;
import com.xander.demo.entity.UserEntity;
import com.xander.demo.entity.VideoEntity;
import com.xander.demo.service.TMDBService;
import com.xander.demo.service.UserService;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/public")
// @Profile("dev")
public class PublicController {

    private final UserService userService;
    private final TMDBService tmdbService;
    private final AppCache appCache;

    public PublicController(UserService userService, TMDBService tmdbService, AppCache appCache) {
        this.appCache = appCache;
        this.userService = userService;
        this.tmdbService = tmdbService;
    }

    /*
     * Health Check
     */
    @GetMapping("/health-check")
    public String health() {
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

    /*
     * To generate an error and print log in the console use the following endpoint
     */
    @PostMapping("/generate-error")
    public ResponseEntity<String> generateError(@RequestBody UserEntity userEntity) {
        try {
            userEntity.setCreateDate(LocalDateTime.now());
            userService.saveNewUser(userEntity);
            return new ResponseEntity<>("User created successfully! ✅", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tv-show")
    public ResponseEntity<VideoEntity> getTVShow() {
        try {
            VideoEntity videoEntity = tmdbService.getTVTitle(46260);
            if (videoEntity != null) {
                return new ResponseEntity<>(videoEntity, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*
     * Clear cache
     * 
     * @return String
     * 
     * GET /admin/clear-cache
     */

    @GetMapping("/clear-cache")
    public ResponseEntity<String> clearCache() {
        appCache.init();
        return ResponseEntity.ok().body("Cache cleared");
    }

}

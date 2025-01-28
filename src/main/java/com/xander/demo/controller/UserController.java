package com.xander.demo.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Update a user by username
     * 
     * @param username
     * @return UserEntity
     * 
     * PUT /user/john.doe
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
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserEntity userEntity) {
        try {

            /*
             * 
             */
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            UserEntity user = userService.findByUsername(username);
            if (user != null) {
                user.setUsername(userEntity.getUsername());
                user.setPassword(userEntity.getPassword());
                user.setUpdateDate(LocalDateTime.now());
                userService.saveNewUser(user);
                return ResponseEntity.ok().body("User updated successfully");
            } else {
                return ResponseEntity.noContent().build();
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
     *          "id": "5f7b3b7b7b3b7b3b7b3b7b3b
     *    "username": "john.doe",
     *    "password": "password",
     * "demoEntries": [],
     *  "createDate": "2020-10-05T12:00:00",
     *  "updateDate": "2020-10-05T12:00:00"
     * }
     * 
     */

     @GetMapping
     public ResponseEntity<UserEntity> getUserByUsername() {
         try {
             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String username = authentication.getName();
             UserEntity user = userService.findByUsername(username);
             if (user != null) {
                 return ResponseEntity.ok().body(user);
             } else {
                 return ResponseEntity.noContent().build();
             }
         } catch (Exception e) {
             return ResponseEntity.badRequest().build();
         }
     }

    /*
     * Delete a user by username
     * 
     * @param username
     * 
     * DELETE /user/john.doe
     * 
     * Response: User deleted successfully
     * 
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            UserEntity user = userService.findByUsername(username);
            if (user != null) {
                userService.deleteUser(user.getId());
                return ResponseEntity.ok().body("User deleted successfully");
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

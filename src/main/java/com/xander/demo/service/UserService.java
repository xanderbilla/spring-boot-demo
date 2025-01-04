package com.xander.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
/*
 * @Slf4j is a Lombok annotation to auto-generate an SLF4J logger in the class.
 * 
 * It is equivalent to the following:
 * 
 * private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());
 * 
 * If we are using @Slf4j, we don't need to declare the `logger` variable instead 
 * we can use `log` directly.
 * 
 * e.g. logger.error("An error occured while saving demo entry", e); ❎
 * e.g. log.info("An error occured while saving demo entry", e); ✅
 */
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Following is replaced by @Slf4j annotation
    // private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /*
     * Save/Create a new user
     * 
     * @param userEntity
     * 
     */
    // For new users so that we can encode the password
    public boolean saveNewUser(UserEntity userEntity) {
        try {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(Arrays.asList("USER"));
            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            // System.out.println(e);
            /*
             *  Log the error
             * 
             * e.g. logger.error("An error occured while saving demo entry", e);
             * e.g. logger.error("An error occured for user {} while saving demo entry", userEntity.getUsername(), e);
             */
            log.info("This is a custom message for the error");
            return false;
        }
    }

    // For existing users
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public void saveAdminUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(userEntity);
    }

    /*
     * List users
     * 
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /*
     * Get a user by id
     * 
     * @param id
     * 
     * @return Optional<UserEntity>
     */
    public Optional<UserEntity> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    /*
     * Delete a user
     * 
     * @param id
     */
    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }

    /*
     * Find a user by username
     * 
     * @param username
     * 
     * @return UserEntity
     */
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

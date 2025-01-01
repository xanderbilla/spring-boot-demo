package com.xander.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.UserRepository;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /*
     * Save/Create a new user
     * 
     * @param userEntity
     * 
     */
    // For new users so that we can encode the password
    public void saveNewUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        userRepository.save(userEntity);
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

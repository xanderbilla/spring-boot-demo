package com.xander.demo.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.UserRepository;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*
     * Save/Create a new user
     * 
     * @param userEntity
     * 
     */
    public void saveUser(UserEntity userEntity) {        
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
     * @return UserEntity
     */
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

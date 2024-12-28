package com.xander.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.xander.demo.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    
    /*
     * findByUsername() is a custom query method which is not provided by the Spring Data MongoDB. 
     * So we're declaring it here to use it in the service layer because Service layer is 
     * the layer where we write the business logic by using the repository layer.
     * 
     * In order to use this method, the developer must provide the implementation.
     * 
     * 
     * UserEntity is ra return type 
     * 
     */
    UserEntity findByUsername(String username);
}

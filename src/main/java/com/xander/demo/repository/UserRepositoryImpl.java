package com.xander.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xander.demo.entity.UserEntity;

public class UserRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<UserEntity> getUserForSA() {
        Query query = new Query();

        /*
         * - Criteria is a class that provides static methods to create Criteria for the
         * Query class.
         * - It will use Mongo Template to create a query object and add criteria to it
         * in MongoDB.
         * - Criteria is used to provide a set of rules to the Query object
         * to filter the documents in the collection.
         * 
         * For example, the following code snippet creates a Criteria object to filter
         * the documents in the collection where the name field is equal to "Xander":
         */
        // query.addCriteria(Criteria.where("username").is("taklu"));

        /*
         * Other usage of Criteria:
         * 
         * - Criteria.where("age").is("20") - This will filter the documents in the
         * collection where the age field is equal to 20.
         * - Criteria.where("age").gt(20) - This will filter the documents in the
         * collection where the age field is greater than 20.
         * - Criteria.where("age").lt(20) - This will filter the documents in the
         * collection where the age field is less than 20.
         * - Criteria.where("age").gte(20) - This will filter the documents in the
         * collection where the age field is greater than or equal to 20.
         */

        /*
         * - The find() method is used to execute the query and return the result.
         * - From UserEntity class, it will use the collection name "users" to invoke
         * the query.
         */

        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        // query.addCriteria(Criteria.where("email").exists(true));
        // query.addCriteria(Criteria.where("email").ne(""));
        query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));
        List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);
        return users;
    }
}

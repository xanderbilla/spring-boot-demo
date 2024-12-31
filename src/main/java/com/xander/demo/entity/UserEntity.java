package com.xander.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection = "users")
@Data
public class UserEntity {

    /*
     * The user wiil have the following fields:
     * 
     * {
     *           "id": "5f8f4b3b7f3b9b0b3c7b3b9b",
     *     "username": "john.doe",
     *     "password": "password",
     *   "createDate": "2020-10-20T12:00:00",
     *  "demoEntries": [
            DBRef("demoEntries", "5f8f4b3b7f3b9b0b3c7b3b9b"),
            DBRef("demoEntries", "5f8f4b3b7f3b9b0b3c7b3b9b")
    *       ]
     * }
     * 
     */

    @Id
    private ObjectId id;

    /*
     * @Indexed is used to create an index on the field.
     * It is used to create a primary key in a collection/database.
     * 
     * In this case, we are creating an index on the username field and making it unique.
     */
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    
    /*
     * @DBRef is used to reference a collection in MongoDB.
     * Act as a foreign key in a relational database.
     * 
     * In this case, we are referencing the DemoEntity collection
     */
    @DBRef

    /*
     * When a user is created, it will have an empty list of demo entries
     */
    private List<DemoEntity> demoEntries = new ArrayList<>();
    
    /*
     * Declaring Roles
     */
    private List<String> roles;
}

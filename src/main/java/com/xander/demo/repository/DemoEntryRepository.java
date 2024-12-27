package com.xander.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.xander.demo.entity.DemoEntity;

public interface DemoEntryRepository extends MongoRepository<DemoEntity, ObjectId> {
    
}

package com.xander.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.xander.demo.entity.ConfigDemoEntity;

public interface ConfigDemoRepository extends MongoRepository<ConfigDemoEntity, ObjectId> {

}
package com.xander.demo.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection = "demo")

/*
 * @Data is a Lombok annotation to create all the getters, 
 * setters, equals, hash, and toString methods, based on the fields.
 */

@Data
public class DemoEntity {

    @Id
    private ObjectId id;
    @NonNull //MongoDB specific annotation to mark a field as mandatory
    private String name; //NMandatory field due to the @NonNull annotation
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    /*
     * public ObjectId getId() {
     *      return id;
     * }
     *
     * public String getName() {
     *      return name;
     * }
     *
     * public String getDescription() {
     *      return description;
     * }
     *
     * public LocalDateTime getCreatedDate() {
     *      return createdDate;
     * }
     *
     * public LocalDateTime getUpdatedDate() {
     *      return updatedDate;
     * }
     *
     * public void setId(ObjectId id) {
     *      this.id = id;
     * }
     *
     * public void setName(String name) {
     *      this.name = name;
     * }
     *
     * public void setDescription(String description) {
     *      this.description = description;
     * }
     *
     * public void setCreatedDate(LocalDateTime createdDate) {
     *      this.createdDate = createdDate;
     * }
     *
     * public void setUpdatedDate(LocalDateTime updatedDate) {
     *      this.updatedDate = updatedDate;
     * }
     */

}

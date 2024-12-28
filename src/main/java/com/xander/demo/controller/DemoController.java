package com.xander.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.entity.DemoEntity;
import com.xander.demo.service.DemoEntryService;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoEntryService demoEntryService;

    /*
     * Add a new entry
     * 
     * @param entity
     * 
     * @return String
     * 
     * @RequestBody DemoEntity entity
     * 
     * {
     *      "name": "John Doe",
     *      "description": "This is a demo entry"
     * }
     * 
     * POST /demo/add
     * 
     * Response: Entry created successfully
     */
    @PostMapping("/add")
    public ResponseEntity<String> addEntry(@RequestBody DemoEntity entity) {
        try {
            entity.setCreatedDate(LocalDateTime.now());
            demoEntryService.saveDemoEntry(entity);
            return ResponseEntity.ok().body("Entry created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     * Get all entries
     * 
     * @return List<DemoEntity>
     * 
     * GET /demo
     * 
     * Response: [
     *      {
     *          "id": "60f3b3b3b3b3b3b3b3b3b3b3",
     *          "name": "John Doe",
     *          "description": "This is a demo entry",
     *          "createdDate": "2021-07-19T12:00:00"
     *          "updatedDate": "2021-07-19T12:01:00"
     *      }
     * ]
     */
    @GetMapping
    public ResponseEntity<?> getAllEntries() {
        try {
            List<DemoEntity> entries = demoEntryService.getAllDemoEntries();
            return ResponseEntity.ok().body(entries);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Get an entry by id
     * 
     * @param id
     * @return DemoEntity
     * 
     * GET /demo/60f3b3b3b3b3b3b3b3b3b3
     * 
     * Response: [
     *      {
     *          "id": "60f3b3b3b3b3b3b3b3b3b3b3",
     *          "name": "John Doe",
     *          "description": "This is a demo entry",
     *          "createdDate": "2021-07-19T12:00:00"
     *          "updatedDate": "2021-07-19T12:01:00"
     *      }
     * ]
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEntry(@PathVariable ObjectId id) {
        try {
            Optional<DemoEntity> entity = demoEntryService.getDemoEntry(id);
            return ResponseEntity.ok().body(entity.get());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Delete an entry
     * 
     * @param id
     * 
     * @return String
     * 
     * POST /demo/delete/60f3b3b3b3b3b3b3b3b3b3
     * 
     * Response: Entry deleted successfully *
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id) {
        try {
            demoEntryService.deleteDemoEntry(id);
            return ResponseEntity.ok().body("Entry deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     * Update an entry
     * 
     * @param entity
     * @return String
     * 
     * @RequestBody DemoEntity entity
     * 
     * {
     * "name": "John Doe",
     * "description": "This is a demo entry"
     * }
     * 
     * POST /demo/update/60f3b3b3b3b3b3b3b3b3b3
     * 
     * Response: Entry updated successfully
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody DemoEntity entity, @PathVariable ObjectId id) {
        try {
            DemoEntity oldEntity = demoEntryService.getDemoEntry(id).orElse(null);
            if (oldEntity != null) {
                oldEntity.setName(
                        entity.getName() != null && !entity.getName().equals("") ? entity.getName()
                                : oldEntity.getName());
                oldEntity.setDescription(
                        entity.getDescription() != null && !entity.getDescription().equals("") ? entity.getDescription()
                                : oldEntity.getDescription());
                oldEntity.setUpdatedDate(LocalDateTime.now());
                demoEntryService.saveDemoEntry(oldEntity);
                return ResponseEntity.ok().body("Entry updated successfully");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

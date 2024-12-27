package com.xander.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return String
     * 
     * @RequestBody DemoEntity entity
     * 
     * {
     *          "name": "John Doe",
     *   "description": "This is a demo entry"
     * }
     * 
     * POST /demo/add
     * 
     * Response: Entry created successfully
     */
    @PostMapping("/add")
    public String addEntry(@RequestBody DemoEntity entity) {
        entity.setCreatedDate(LocalDateTime.now());
        demoEntryService.saveDemoEntry(entity);
        return "Entry created successfully";  
    }

    /*
     * Get all entries
     * 
     * @return List<DemoEntity>
     * 
     * GET /demo
     * 
     * Response: [
     *             {
     *                          "id": "60f3b3b3b3b3b3b3b3b3b3b3",
     *                         "name": "John Doe",
     *                  "description": "This is a demo entry",
     *                 "createdDate": "2021-07-19T12:00:00"
     *            }
     *         ]
     */
    @GetMapping
    public List<DemoEntity> getAllEntries() {
        return demoEntryService.getAllDemoEntries();
    }

    
    /*
     * Get an entry by id
     * 
     * @param id
     * @return DemoEntity
     * 
     * GET /demo/60f3b3b3b3b3b3b3b3b3b3
     * 
     * Response: {
     *             "id": "60f3b3b3b3b3b3b3b3b3b3",
     *           "name": "John Doe",
     *    "description": "This is a demo entry",
     *    "createdDate": "2021-07-19T12:00:00"
     * }
     */
    @GetMapping("/{id}")
    public Optional<DemoEntity> getEntry(@PathVariable ObjectId id) {
        return demoEntryService.getDemoEntry(id);
    }

    /*
     * Delete an entry
     * 
     * @param id
     * @return String
     * 
     * POST /demo/delete/60f3b3b3b3b3b3b3b3b3b3
     * 
     * Response: Entry deleted successfully     * 
     */
    @DeleteMapping("/delete/{id}")
    public String deleteEntry(@PathVariable ObjectId id) {
        demoEntryService.deleteDemoEntry(id);
        return "Entry deleted successfully";
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
     *        "name": "John Doe",
     * "description": "This is a demo entry"
     * }
     * 
     * POST /demo/update/60f3b3b3b3b3b3b3b3b3b3
     * 
     * Response: Entry updated successfully
     */
    @PutMapping("/update/{id}")
    public String updateEntry(@RequestBody DemoEntity entity, @PathVariable ObjectId id) {
        DemoEntity oldEntity = demoEntryService.getDemoEntry(id).orElse(null);
        if (oldEntity != null) {
            oldEntity.setName(entity.getName() != null && !entity.getName().equals("")? entity.getName() : oldEntity.getName());
            oldEntity.setDescription(entity.getDescription() != null && !entity.getDescription().equals("")? entity.getDescription() : oldEntity.getDescription());
            oldEntity.setUpdatedDate(LocalDateTime.now());
            demoEntryService.saveDemoEntry(oldEntity);
            return "Entry found and updated successfully! ✅";
        }
        return "Entry not found ❎";
    }
}

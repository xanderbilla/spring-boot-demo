package com.xander.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.entity.DemoEntity;
import com.xander.demo.entity.UserEntity;
import com.xander.demo.service.DemoService;
import com.xander.demo.service.UserService;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private UserService userService;

    /*
     * Get all demo entries for a user
     * 
     * @param username
     * 
     * @return List<DemoEntity>
     * 
     * GET /demo
     * 
     * Response: [
     *    {
     *              "id": "5f7b3b7b7b3b7b3b7b3b7b3b",
     *       "demoTitle": "Demo Title",
     *     "description": "Description for demo ",
     *     "createdDate": "2020-10-05T12:00:00"
     *   }
     * ]
     */
    @GetMapping
    public ResponseEntity<?> getAllUserDemo() {
        try {            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(401).body("Unauthorized");
            }
            String username = auth.getName();
            UserEntity user = userService.findByUsername(username);
            // get all demo entries for the user have key "demoEntries"
            List<DemoEntity> all = user.getDemoEntries(); 
            if(all == null || all.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(all);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     * Get demo entry by id for a user
     * 
     * @return List<DemoEntity>
     * 
     * GET /demo/{username}/{id}
     * 
     * Response: [
     *   {
     *            "id": "5f7b3b7b7b3b7b3b7b3b7b3b",
     *     "demoTitle": "Demo Title",
     *   "description": "Description for demo",
     *   "createdDate": "2020-10-05T12:00:00"
     *      }
     * ]
     */
    @GetMapping("/{username}/{id}")
    public ResponseEntity<List<DemoEntity>> getAllDemoEntries(@PathVariable String username, @PathVariable ObjectId id) {
        try {
            UserEntity user = userService.findByUsername(username);
            List<DemoEntity> entries = user.getDemoEntries();
            if(entries == null || entries.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            if(id != null){
                entries = entries.stream().filter(entry -> entry.getId().equals(id)).toList();
            }
            return ResponseEntity.ok().body(entries);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /*
     * Add a new demo entry for a user
     * 
     * @param username
     * @param demoEntity
     * 
     * @return String
     * 
     * @RequestBody DemoEntity demoEntity
     * 
     * {
     *     "demoTitle": "Demo Title",
     *   "description": "Description for demo"
     * }
     * 
     * POST /demo/{username}
     * 
     * Response: Entry created successfully
     * 
     */
    @PostMapping
    public ResponseEntity<String> addEntryOfUser(@RequestBody DemoEntity demoEntity){
        try {        
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();            
            demoService.saveDemoEntry(demoEntity, username);
            return ResponseEntity.ok().body("Entry created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
     * Delete a demo entry using username and id
     * 
     * @param id
     * @param username
     * 
     * @return String
     * 
     * DELETE /demo/{username}/{id}
     * 
     * Response: Entry deleted successfully
     * 
     */
    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<String> deleteEntity(@PathVariable String username, @PathVariable ObjectId id){
        demoService.deleteDemoEntry(username, id);
        return ResponseEntity.ok().body("Entry deleted successfully");
    }

    /*
     * Update a demo entry using username and id
     * 
     * @param id
     * @param username
     * 
     * @return String
     * 
     * PUT /demo/{username}/{id}
     * 
     * Response: Entry updated successfully
     * 
     * {
     *    "demoTitle": "Demo Title",
     *  "description": "Description for demo"
     * }
     */
    @PutMapping("/{username}/{id}")
    public ResponseEntity<String> updateEntity(
        @PathVariable String username, 
        @PathVariable ObjectId id,
        @RequestBody DemoEntity demoEntity){
        DemoEntity oldDemoEntity = demoService.getDemoEntry(id).orElse(null);
        if(oldDemoEntity == null){
            return ResponseEntity.badRequest().body("Entry not found");
        }
        oldDemoEntity.setDemoTitle(demoEntity.getDemoTitle() != null && !demoEntity.getDemoTitle().isEmpty() ? demoEntity.getDemoTitle() : oldDemoEntity.getDemoTitle());
        oldDemoEntity.setDescription(demoEntity.getDescription() != null && !demoEntity.getDescription().isEmpty() ? demoEntity.getDescription() : oldDemoEntity.getDescription());        
        oldDemoEntity.setUpdatedDate(LocalDateTime.now());
        demoService.saveDemoEntry(oldDemoEntity);
        return ResponseEntity.ok().body("Entry updated successfully");
    }
}

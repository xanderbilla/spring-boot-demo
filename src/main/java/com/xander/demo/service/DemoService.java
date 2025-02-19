package com.xander.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xander.demo.entity.DemoEntity;
import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.DemoRepository;

@Service
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private UserService userService;


    // CRUD operations
    /*
     * Save/Create a new demo entry
     * 
     * @param demoEntity
     */
    @Transactional
    public void saveDemoEntry(DemoEntity demoEntity, String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            demoEntity.setCreatedDate(LocalDateTime.now());
            DemoEntity saved = demoRepository.save(demoEntity);
            user.getDemoEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occured while saving demo entry");
        }
    }

    public void saveDemoEntry(DemoEntity demoEntity) {
        demoRepository.save(demoEntity);
    }

    /*
     * Get all demo entries
     * 
     * @return List<DemoEntity>
     */
    public List<DemoEntity> getAllDemoEntries() {
        return demoRepository.findAll();
    }

    @Transactional
    /*
     * Delete a demo entry
     * 
     * @param id
     */
    public boolean deleteDemoEntry(String username, ObjectId id) {
        try {
            UserEntity user = userService.findByUsername(username);

            // Remove the demo entry from the user's list of demo entries
            user.getDemoEntries().removeIf(demo -> demo.getId().equals(id));

            userService.saveUser(user);
            demoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("An error occured while deleting demo entry");
        }
    }

    /*
     * Get a demo entry by id
     * 
     * @param id
     * 
     * @return DemoEntity
     */
    public Optional<DemoEntity> getDemoEntry(ObjectId id) {
        return demoRepository.findById(id);
    }
}

package com.xander.demo.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xander.demo.entity.DemoEntity;
import com.xander.demo.repository.DemoEntryRepository;

@Component
public class DemoEntryService {
    
    @Autowired
    private DemoEntryRepository demoEntryRepository;

    // CRUD operations
    /*
     * Save/Create a new demo entry
     * 
     * @param demoEntity
     */
    public void saveDemoEntry(DemoEntity demoEntity) {
        demoEntryRepository.save(demoEntity);      
    }

    /*
     * Get all demo entries
     * 
     * @return List<DemoEntity>
     */
    public List<DemoEntity> getAllDemoEntries() {
        return demoEntryRepository.findAll();
    }

    /*
     * Delete a demo entry
     * 
     * @param id 
     */
    public void deleteDemoEntry(ObjectId id) {
        demoEntryRepository.deleteById(id);
    }

    /*
     * Get a demo entry by id
     * 
     * @param id
     * @return DemoEntity
     */
    public Optional<DemoEntity> getDemoEntry(ObjectId id) {
        return demoEntryRepository.findById(id);
    }
}

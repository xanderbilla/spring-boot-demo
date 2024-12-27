package com.xander.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.entity.DemoEntity;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private Map<Long, DemoEntity> demoEntities = new HashMap<>();

    @GetMapping("/get")
    public List<DemoEntity> getAll() {
        return new ArrayList<>(demoEntities.values());
    }

    /*
     * For 'request body' in Postman, use the following JSON format:
     * 
     * {
     *   "id": 1,
     *   "name": "Xander",
     *   "description": "Software Engineer"
     * }
     */

    @PostMapping("/add")
    public String add(@RequestBody DemoEntity demoEntity) {
        demoEntities.put(demoEntity.getId(), demoEntity);
        return "Added! ✅";
    }


    /*
     *  @GetMapping("/get/{entryId}")
     *  Required URI template variable 'id' for method parameter type long is not present
     */
    @GetMapping("/get/{id}")
    public DemoEntity getEntryById(@PathVariable long id) {
        return demoEntities.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEntry(@PathVariable long id) {
        demoEntities.remove(id);
        return "Deleted! ✅";
    }

    @PutMapping("/update/{id}")
    public String updateEntry(@PathVariable long id, @RequestBody DemoEntity demoEntity) {
        demoEntities.put(id, demoEntity);
        return "Updated! ✅";
    }

}

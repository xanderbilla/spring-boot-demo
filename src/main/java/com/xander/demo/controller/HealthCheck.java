package com.xander.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HealthCheck {
    
    // @GetMapping
    @GetMapping("/health")
    public String Health() {
        return "Health Check Passed! ✅";
    }
    

}

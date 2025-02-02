package com.xander.demo.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xander.demo.entity.ConfigDemoEntity;
import com.xander.demo.repository.ConfigDemoRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    public enum keys {
        TMDB_API
    }

    private final ConfigDemoRepository configDemoRepository;

    public AppCache(ConfigDemoRepository configDemoRepository) {
        this.configDemoRepository = configDemoRepository;
    }

    public Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigDemoEntity> all = configDemoRepository.findAll();
        all.forEach(configDemoEntity -> appCache.put(configDemoEntity.getKey(), configDemoEntity.getValue()));
    }
}

package com.xander.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xander.demo.config.AppCache;
import com.xander.demo.constants.Placeholders;
import com.xander.demo.entity.VideoEntity;

@Service
public class TMDBService {

    private final AppCache appCache;

    public TMDBService(AppCache appCache) {
        this.appCache = appCache;
    }

    @Value("${app.tmdb.api.key}")
    public String API;

    @Autowired
    private RestTemplate restTemplate;

    // Get the TV title by ID
    public VideoEntity getTVTitle(int id) {
        String url = appCache.appCache.get(AppCache.keys.TMDB_API.toString())
                .replace(Placeholders.MOVIE_ID, String.valueOf(id)).replace(Placeholders.API_KEY, API);
        ResponseEntity<VideoEntity> response = restTemplate.exchange(url, HttpMethod.GET, null,
         VideoEntity.class);
        // Converting the JSON response to a Java object is called deserialization and
        // vice versa is called serialization.
        return response.getBody();
    }

    // Post the TV title by ID
    public VideoEntity postTVTitle(int id, VideoEntity videoEntity) {
        String url = appCache.appCache.get(AppCache.keys.TMDB_API.toString())
                .replace(Placeholders.MOVIE_ID, String.valueOf(id)).replace(Placeholders.API_KEY, API);
        HttpEntity<VideoEntity> request = new HttpEntity<>(videoEntity);
        ResponseEntity<VideoEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, VideoEntity.class);
        return response.getBody();
    }
}

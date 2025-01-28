package com.xander.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.xander.demo.entity.VideoEntity;

@Component
public class TMDBService {

    public static final String API = "09389ac5239422a440a1fcb9738328fd";
    public static final String BASE_URL = "https://api.themoviedb.org/3/tv/MOVIE_ID?api_key=API_KEY";

    @Autowired
    private RestTemplate restTemplate;

    //Get the TV title by ID
    public VideoEntity getTVTitle(int id) {
        String url = BASE_URL.replace("MOVIE_ID", String.valueOf(id)).replace("API_KEY", API);
        ResponseEntity<VideoEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, VideoEntity.class);
        // Converting the JSON response to a Java object is called deserialization and
        // vice versa is called serialization.
        return response.getBody();
    }

    //Post the TV title by ID
    public VideoEntity postTVTitle(int id, VideoEntity videoEntity) {
        String url = BASE_URL.replace("MOVIE_ID", String.valueOf(id)).replace("API_KEY", API);
        HttpEntity<VideoEntity> request = new HttpEntity<>(videoEntity);
        ResponseEntity<VideoEntity> response = restTemplate.exchange(url, HttpMethod.POST, request, VideoEntity.class);
        return response.getBody();
    }    
}

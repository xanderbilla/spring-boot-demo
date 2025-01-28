package com.xander.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoEntity{
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("first_air_date")
    private String firstAirDate;
    private String name;
    @JsonProperty("number_of_episodes")
    private int numberOfEpisodes;
    @JsonProperty("number_of_seasons")
    private int numberOfSeasons;
}
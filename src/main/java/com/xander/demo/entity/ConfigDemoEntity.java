package com.xander.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "config_spring")
public class ConfigDemoEntity {
    private String key;
    private String value;
}

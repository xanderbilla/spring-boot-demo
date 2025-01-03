package com.xander.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.UserRepository;

public class UserDetailsServiceImplTest{

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUsername("taklu"))
            .thenReturn(UserEntity.builder()
                .username("taklu")
                .password("taklumc")
                .roles(new ArrayList<>())
                .build());
                UserDetails user = userDetailsService.loadUserByUsername("taklu");
                assertNotNull(user);
            }
}
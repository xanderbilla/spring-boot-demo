package com.xander.demo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.UserRepository;

@SpringBootTest
public class UserDetailsServiceImplTest{

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUsername("taklu"))
            .thenReturn(UserEntity.builder()
                .username("taklu")
                .build());
                UserDetails userDetails = userDetailsService.loadUserByUsername("taklu");
                assertTrue(null);
            }

}
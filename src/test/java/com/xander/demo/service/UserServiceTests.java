package com.xander.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xander.demo.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository UserRepository;

    @Test
    public void testFooFoo() {
        assertEquals(3, 1 + 2);
    }

    @Disabled
    @Test
    public void testFindByUsername() {
        assertNotNull(UserRepository.findByUsername("taklu"));
    }

    @Disabled
    @Test
    @ParameterizedTest
    /*
     * We can also pass -
     * 
     * @ValueSource(strings = {"taklu", "admin", "xander"})
     * 
     * @MethodSource("provideStringsForIsBlank")
     * 
     * @CsvFileSource(resources = "/test.csv")
     * 
     * @EnumSource(value = MyEnum.class, names = {"ENUM1", "ENUM2"})
     * 
     * We can also create our own custom source using @ArgumentsSource
     * 
     * @ArgumentsSource(UserArgumentProvider.class)
     * and the argument type for the method that will use this annotation 
     * should be UserEntity type.
     * 
     * Note: Make sure to make changes in UserArgumentProvider.java
     */
    @CsvSource({
            "taklu",
            "admin",
            "xander"
    })
    public void testFindByUsername(String username) {
        assertNotNull(UserRepository.findByUsername(username));
    }

}

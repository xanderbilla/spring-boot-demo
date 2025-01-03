package com.xander.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xander.demo.entity.UserEntity;
import com.xander.demo.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private UserService userService;

    @Disabled
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
    // @CsvSource({ //for key-value pairs
    @ValueSource(strings = { // for single value
            "taklu",
            "admin",
            "xander4"
    })
    public void testFindByUsername(String username) {
        assertNotNull(UserRepository.findByUsername(username));
    }

    @Disabled
    @Test
    @ParameterizedTest
    /*
     * @ArgumentsSource(UserArgumentsProvider.class) is a custom source
     * Used to pass multiple arguments to the test method.
     */
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(UserEntity userEntity) {
        assertTrue(userService.saveNewUser(userEntity));
    }

}

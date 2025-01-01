package com.xander.demo.service;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

// import com.xander.demo.entity.UserEntity;

public class UserArgumentProvider implements ArgumentsProvider {

    /*
     * Use @Builder annotation to create a builder class 
     * for the entity class i.e., UserEntity to make this work.
     */

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context)
            throws Exception {
        // return Stream.of(
        //         Arguments.of(UserEntity.builder()
        //                 .username("taklu")
        //                 .password("taklumc").build()));
        return null;
    }

}

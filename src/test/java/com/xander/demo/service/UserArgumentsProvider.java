package com.xander.demo.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.xander.demo.entity.UserEntity;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(UserEntity.builder()
                    .username("user+rand").password("rand").build()),
                Arguments.of(UserEntity.builder()
                    .username("root").password("toor").build())
                );
    }
}

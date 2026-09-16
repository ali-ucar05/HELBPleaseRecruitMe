package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ApplicationProfileValidatorTest {

    private static Stream<Arguments> motivationLetters() {
        return Stream.of(
            Arguments.of("je suis motivé", true),
            Arguments.of("bla bla http://test.com", true),
            Arguments.of("suivez moi sur mon site https://www.google.com/", false),
            Arguments.of("0123456789", false)
        );
    }

    @ParameterizedTest
    @MethodSource("motivationLetters")
    public void testIsValidMotivation(String motivation, boolean expected) {
        boolean result = ApplicationProfileValidator.isValidMotivation(motivation);
        assertEquals(expected, result);
    }
}
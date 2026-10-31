package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ExistsFilterTest {

    @Test
    void matchesWhenPropertyIsPresent() {
        assertTrue(Filters.exists("batteryLevel").matches(Map.of("batteryLevel", "85")));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("assetsWithoutProperty")
    void doesNotMatchWhenPropertyIsAbsent(String description, Map<String, String> asset) {
        assertFalse(Filters.exists("batteryLevel").matches(asset));
    }

    private static Stream<Arguments> assetsWithoutProperty() {
        return Stream.of(
            Arguments.of("property is missing", Map.of("temperature", "20")),
            Arguments.of("asset is empty", Map.of()),
            Arguments.of("asset is null", (Map<String, String>) null)
        );
    }
}

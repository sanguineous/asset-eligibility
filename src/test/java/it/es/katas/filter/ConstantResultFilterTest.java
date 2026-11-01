package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ConstantResultFilterTest {

    @ParameterizedTest(name = "always filter matches {0}")
    @MethodSource("assets")
    void alwaysFilterMatchesAnyAsset(String description, Map<String, String> asset) {
        assertTrue(Filters.always().matches(asset));
    }

    @ParameterizedTest(name = "never filter does not match {0}")
    @MethodSource("assets")
    void neverFilterDoesNotMatchAnyAsset(String description, Map<String, String> asset) {
        assertFalse(Filters.never().matches(asset));
    }

    @Test
    void describesConstantFilters() {
        assertEquals("always()", Filters.always().toString());
        assertEquals("never()", Filters.never().toString());
    }

    private static Stream<Arguments> assets() {
        return Stream.of(
            Arguments.of("an asset with properties", Map.of("status", "online")),
            Arguments.of("an empty asset", Map.of()),
            Arguments.of("a null asset", (Map<String, String>) null)
        );
    }
}

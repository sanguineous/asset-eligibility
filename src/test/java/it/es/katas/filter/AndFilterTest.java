package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AndFilterTest {

    @Test
    void matchesWhenEveryCriterionMatches() {
        Map<String, String> asset = Map.of("isOnline", "true", "batteryLevel", "85");

        assertTrue(eligibilityFilter().matches(asset));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("assetsThatFailACriterion")
    void doesNotMatchWhenAnyCriterionFails(String description, Map<String, String> asset) {
        assertFalse(eligibilityFilter().matches(asset));
    }

    private static Filter eligibilityFilter() {
        return Filters.and(
            asset -> "true".equals(asset.get("isOnline")),
            asset -> Integer.parseInt(asset.getOrDefault("batteryLevel", "0")) >= 20
        );
    }

    private static Stream<Arguments> assetsThatFailACriterion() {
        return Stream.of(
            Arguments.of("asset is offline", Map.of("isOnline", "false", "batteryLevel", "85")),
            Arguments.of("battery is below the minimum", Map.of("isOnline", "true", "batteryLevel", "10")),
            Arguments.of("asset has no online status", Map.of("batteryLevel", "85"))
        );
    }
}

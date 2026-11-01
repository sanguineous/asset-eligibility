package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.es.katas.comparator.Comparators;
import it.es.katas.comparator.numeric.NumericComparators;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FilterIntegrationTest {

    @Test
    void matchesWhenBatteryLevelExistsAndMeetsMinimum() {
        Filter eligibility = batteryLevelEligibilityFilter();

        assertTrue(eligibility.matches(Map.of("batteryLevel", "85")));
    }

    @Test
    void doesNotMatchWhenBatteryLevelIsBelowMinimum() {
        Filter eligibility = batteryLevelEligibilityFilter();

        assertFalse(eligibility.matches(Map.of("batteryLevel", "10")));
    }

    @Test
    void doesNotMatchWhenBatteryLevelIsMissing() {
        Filter eligibility = batteryLevelEligibilityFilter();

        assertFalse(eligibility.matches(Map.of("firmwareVersion", "2.4.1")));
    }

    private static Filter batteryLevelEligibilityFilter() {
        return Filters.and(
            Filters.exists("batteryLevel"),
            Filters.compare(
                "batteryLevel",
                "20",
                Comparators.numeric(NumericComparators.greaterThanOrEqual())
            )
        );
    }
}

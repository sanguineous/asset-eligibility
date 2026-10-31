package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

class FiltersTest {

    @Test
    void constantComparisonsHandleStringAndNumericValues() {
        Map<String, String> asset = Map.of("batteryLevel", "85", "zone", "Lombardy-Plant-A");

        assertTrue(Filters.compare("batteryLevel", "20", Filters.numericGreaterThanOrEqual()).matches(asset));
        assertFalse(Filters.compare("batteryLevel", "90", Filters.numericGreaterThanOrEqual()).matches(asset));
        assertFalse(Filters.compare("batteryLevel", "20", Filters.numericLessThan()).matches(asset));
        assertTrue(Filters.compare("batteryLevel", "85", Filters.numericLessThanOrEqual()).matches(asset));
        assertFalse(Filters.compare("batteryLevel", "80", Filters.numericLessThanOrEqual()).matches(asset));
        assertFalse(Filters.compare("missing", "20", Filters.numericGreaterThan()).matches(asset));
    }

    @Test
    void propertyToPropertyComparisonsUseNumericAndStringSemantics() {
        Map<String, String> asset = Map.of("currentTemperature", "18", "maxAllowedTemperature", "25");

        assertTrue(Filters.compareProperties("currentTemperature", "maxAllowedTemperature", Filters.numericLessThan()).matches(asset));
        assertFalse(Filters.compareProperties("currentTemperature", "maxAllowedTemperature", Filters.numericGreaterThan()).matches(asset));

    }

    @Test
    void logicalCompositionWorksWithNestedExpressions() {
        Map<String, String> asset = Map.of("zone", "Lombardy-Plant-A", "isOnline", "true", "batteryLevel", "85");

        Filter eligibility = Filters.and(
            Filters.compare("zone", "Lombardy-Plant-A", Filters.caseInsensitiveEquals()),
            Filters.compare("isOnline", "true", Filters.caseInsensitiveEquals()),
            Filters.compare("batteryLevel", "20", Filters.numericGreaterThanOrEqual())
        );

        assertTrue(eligibility.matches(asset));
        assertFalse(Filters.and(eligibility, Filters.compare("batteryLevel", "100", Filters.numericGreaterThan())).matches(asset));
        assertTrue(Filters.or(Filters.compare("batteryLevel", "100", Filters.numericGreaterThan()), Filters.exists("zone")).matches(asset));
        assertFalse(Filters.or(
            Filters.compare("batteryLevel", "100", Filters.numericGreaterThan()),
            Filters.compare("isOnline", "false", Filters.caseInsensitiveEquals())
        ).matches(asset));
        assertTrue(Filters.not(Filters.compare("batteryLevel", "100", Filters.numericGreaterThan())).matches(asset));
        assertFalse(Filters.not(Filters.exists("zone")).matches(asset));
    }

    @Test
    void andAndOrHandleEmptyAndSingleChildInputs() {
        Map<String, String> asset = Map.of("isOnline", "true");
        Filter matching = Filters.exists("isOnline");
        Filter nonMatching = Filters.exists("batteryLevel");

        assertTrue(Filters.and().matches(asset));
        assertFalse(Filters.or().matches(asset));
        assertTrue(Filters.and(matching).matches(asset));
        assertTrue(Filters.or(matching).matches(asset));
        assertFalse(Filters.and(nonMatching).matches(asset));
        assertFalse(Filters.or(nonMatching).matches(asset));
    }

    @Test
    void invalidNumericValuesAndMissingPropertiesReturnFalse() {
        Map<String, String> asset = Map.of("batteryLevel", "NaN", "firmwareVersion", "2.4.1");

        assertFalse(Filters.compare("batteryLevel", "20", Filters.numericGreaterThan()).matches(asset));
        assertFalse(Filters.compare("firmwareVersion", "2.4.1", Filters.numericGreaterThan()).matches(asset));
        assertFalse(Filters.compareProperties("batteryLevel", "otherValue", Filters.numericGreaterThan()).matches(asset));
        assertFalse(Filters.compareProperties("batteryLevel", "other", Filters.numericGreaterThan()).matches(Map.of("batteryLevel", "10")));
    }

    @Test
    void toStringProvidesMeaningfulRepresentations() {
        Filter always = Filters.always();
        Filter never = Filters.never();
        Filter exists = Filters.exists("zone");
        Filter equals = Filters.compare("zone", "north", Filters.caseInsensitiveEquals());
        Filter compare = Filters.compare("batteryLevel", "20", Filters.numericGreaterThanOrEqual());
        Filter propertyCompare = Filters.compareProperties("temperature", "limit", Filters.numericLessThan());
        Filter not = Filters.not(exists);
        Filter and = Filters.and(compare, not);
        Filter or = Filters.or(equals, propertyCompare);

        assertEquals("always()", always.toString());
        assertEquals("never()", never.toString());
        assertEquals("exists(zone)", exists.toString());
        assertEquals("zone equals north", equals.toString());
        assertEquals("batteryLevel >= 20", compare.toString());
        assertEquals("temperature < limit", propertyCompare.toString());
        assertEquals("NOT (exists(zone))", not.toString());
        assertEquals("(batteryLevel >= 20 AND NOT (exists(zone)))", and.toString());
        assertEquals("(zone equals north OR temperature < limit)", or.toString());
    }

    @Test
    void propertyNamesAreCaseSensitiveWhileEqualityIsCaseInsensitive() {
        Map<String, String> asset = Map.of("Zone", "Lombardy-Plant-A", "zone", "Other");

        assertTrue(Filters.compare("Zone", "lombardy-plant-a", Filters.caseInsensitiveEquals()).matches(asset));
        assertFalse(Filters.compare("ZONE", "Lombardy-Plant-A", Filters.caseInsensitiveEquals()).matches(asset));
    }
}

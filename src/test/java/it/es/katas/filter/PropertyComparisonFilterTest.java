package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import it.es.katas.comparator.ValueComparator;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class PropertyComparisonFilterTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void matchesWhenComparatorMatches(boolean comparatorResult) {
        ValueComparator comparator = mock(ValueComparator.class);
        when(comparator.compare("18", "25")).thenReturn(comparatorResult);

        Filter filter = Filters.compareProperties("currentTemperature", "maximumTemperature", comparator);

        assertEquals(
            comparatorResult,
            filter.matches(Map.of("currentTemperature", "18", "maximumTemperature", "25"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("assetsMissingAnOperand")
    void doesNotMatchWhenEitherPropertyIsMissing(String description, Map<String, String> asset) {
        ValueComparator comparator = mock(ValueComparator.class);
        Filter filter = Filters.compareProperties("currentTemperature", "maximumTemperature", comparator);

        assertFalse(filter.matches(asset));
        verifyNoInteractions(comparator);
    }

    private static Stream<Arguments> assetsMissingAnOperand() {
        return Stream.of(
            Arguments.of("left property is missing", Map.of("maximumTemperature", "25")),
            Arguments.of("right property is missing", Map.of("currentTemperature", "18"))
        );
    }

    @Test
    void doesNotMatchWhenAssetIsNull() {
        ValueComparator comparator = mock(ValueComparator.class);
        Filter filter = Filters.compareProperties("currentTemperature", "maximumTemperature", comparator);

        assertFalse(filter.matches(null));
        verifyNoInteractions(comparator);
    }

    @Test
    void rejectsNullComparator() {
        assertThrows(
            NullPointerException.class,
            () -> Filters.compareProperties("currentTemperature", "maximumTemperature", null)
        );
    }

    @Test
    void describesTheComparedPropertiesAndOperator() {
        ValueComparator comparator = mock(ValueComparator.class);
        when(comparator.description()).thenReturn("<");
        Filter filter = Filters.compareProperties(
            "currentTemperature",
            "maximumTemperature",
            comparator
        );

        assertEquals("currentTemperature < maximumTemperature", filter.toString());
    }
}

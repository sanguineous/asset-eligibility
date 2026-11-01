package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import it.es.katas.comparator.ValueComparator;
import java.util.Map;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComparisonFilterTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void matchesWhenComparatorMatches(boolean comparatorResult) {
        ValueComparator comparator = mock(ValueComparator.class);
        when(comparator.compare("85", "20")).thenReturn(comparatorResult);
        
        Filter filter = Filters.compare("batteryLevel", "20", comparator);

        assertEquals(comparatorResult, filter.matches(Map.of("batteryLevel", "85")));
    }

    @Test
    void doesNotMatchWhenAssetDoesNotContainProperty() {
        ValueComparator comparator = mock(ValueComparator.class);
        
        Filter filter = Filters.compare("batteryLevel", "20", comparator);

        assertFalse(filter.matches(Map.of("temperature", "18")));
        verifyNoInteractions(comparator);
    }

    @Test
    void doesNotMatchWhenAssetIsNull() {
        ValueComparator comparator = mock(ValueComparator.class);
        
        Filter filter = Filters.compare("batteryLevel", "20", comparator);

        assertFalse(filter.matches(null));
        verifyNoInteractions(comparator);
    }

    @Test
    void describesThePropertyComparisonAndExpectedValue() {
        ValueComparator comparator = mock(ValueComparator.class);
        when(comparator.description()).thenReturn(">=");
        Filter filter = Filters.compare("batteryLevel", "20", comparator);

        assertEquals("batteryLevel >= 20", filter.toString());
    }
}

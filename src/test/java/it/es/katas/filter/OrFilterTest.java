package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrFilterTest {
    private static final Map<String, String> ASSET = Map.of("status", "online");

    @Test
    void matchesWhenOnlySecondFilterMatches() {
        Filter first = filterReturningForAsset(false);
        Filter second = filterReturningForAsset(true);

        assertTrue(Filters.or(first, second).matches(ASSET));
    }

    @Test
    void doesNotMatchWhenNeitherFilterMatches() {
        Filter first = filterReturningForAsset(false);
        Filter second = filterReturningForAsset(false);

        assertFalse(Filters.or(first, second).matches(ASSET));
    }

    @Test
    void matchesAndDoesNotEvaluateSecondFilterWhenFirstFilterMatches() {
        Filter first = filterReturningForAsset(true);
        Filter second = mock(Filter.class);

        assertTrue(Filters.or(first, second).matches(ASSET));
        verifyNoInteractions(second);
    }

    @Test
    void rejectsNullFirstFilter() {
        assertThrows(NullPointerException.class, () -> Filters.or(null, Filters.never()));
    }

    @Test
    void rejectsNullSecondFilter() {
        assertThrows(NullPointerException.class, () -> Filters.or(Filters.never(), null));
    }

    @Test
    void describesBothChildFilters() {
        Filter first = mock(Filter.class);
        Filter second = mock(Filter.class);
        when(first.toString()).thenReturn("first");
        when(second.toString()).thenReturn("second");

        assertEquals("(first OR second)", Filters.or(first, second).toString());
    }

    private static Filter filterReturningForAsset(boolean result) {
        Filter filter = mock(Filter.class);
        when(filter.matches(same(ASSET))).thenReturn(result);
        return filter;
    }
}

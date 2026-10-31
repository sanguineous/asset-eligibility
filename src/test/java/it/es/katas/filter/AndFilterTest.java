package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AndFilterTest {
    private static final Map<String, String> ASSET = Map.of("status", "online");

    @Test
    void matchesWhenBothFiltersMatch() {
        Filter first = filterReturningForAsset(true);
        Filter second = filterReturningForAsset(true);

        assertTrue(Filters.and(first, second).matches(ASSET));
    }

    @Test
    void doesNotMatchWhenSecondFilterDoesNotMatch() {
        Filter first = filterReturningForAsset(true);
        Filter second = filterReturningForAsset(false);

        assertFalse(Filters.and(first, second).matches(ASSET));
    }

    @Test
    void doesNotMatchAndDoesNotEvaluateSecondFilterWhenFirstFilterDoesNotMatch() {
        Filter first = filterReturningForAsset(false);
        Filter second = mock(Filter.class);

        assertFalse(Filters.and(first, second).matches(ASSET));
        verifyNoInteractions(second);
    }

    @Test
    void rejectsNullFirstFilter() {
        assertThrows(NullPointerException.class, () -> Filters.and(null, mock(Filter.class)));
    }

    @Test
    void rejectsNullSecondFilter() {
        assertThrows(NullPointerException.class, () -> Filters.and(mock(Filter.class), null));
    }

    private static Filter filterReturningForAsset(boolean result) {
        Filter filter = mock(Filter.class);
        when(filter.matches(same(ASSET))).thenReturn(result);
        return filter;
    }
}

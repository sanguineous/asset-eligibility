package it.es.katas.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotFilterTest {
    private static final Map<String, String> ASSET = Map.of("status", "online");

    @Test
    void matchesWhenWrappedFilterDoesNotMatch() {
        Filter child = filterReturningForAsset(false);

        assertTrue(Filters.not(child).matches(ASSET));
    }

    @Test
    void doesNotMatchWhenWrappedFilterMatches() {
        Filter child = filterReturningForAsset(true);

        assertFalse(Filters.not(child).matches(ASSET));
    }

    @Test
    void rejectsNullWrappedFilter() {
        assertThrows(NullPointerException.class, () -> Filters.not(null));
    }

    @Test
    void describesTheNegatedFilter() {
        Filter child = mock(Filter.class);
        when(child.toString()).thenReturn("child");

        assertEquals("NOT (child)", Filters.not(child).toString());
    }

    private static Filter filterReturningForAsset(boolean result) {
        Filter filter = mock(Filter.class);
        when(filter.matches(same(ASSET))).thenReturn(result);
        return filter;
    }
}

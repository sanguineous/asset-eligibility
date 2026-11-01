package it.es.katas.comparator.numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class NumericGreaterThanComparatorTest {

    @Test
    void comparesValues() {
        NumericValueComparator comparator = NumericComparators.greaterThan();

        assertTrue(comparator.compare(new BigDecimal("2"), new BigDecimal("1")));
        assertFalse(comparator.compare(new BigDecimal("1"), new BigDecimal("1")));
    }

    @Test
    void describesItsComparison() {
        assertEquals(">", NumericComparators.greaterThan().description());
    }
}

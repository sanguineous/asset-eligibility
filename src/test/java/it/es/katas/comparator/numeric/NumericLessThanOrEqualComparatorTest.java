package it.es.katas.comparator.numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class NumericLessThanOrEqualComparatorTest {

    @Test
    void comparesValues() {
        NumericLessThanOrEqualComparator comparator = new NumericLessThanOrEqualComparator();

        assertTrue(comparator.compare(new BigDecimal("1"), new BigDecimal("1.00")));
        assertFalse(comparator.compare(new BigDecimal("2"), new BigDecimal("1")));
    }

    @Test
    void describesItsComparison() {
        assertEquals("<=", new NumericLessThanOrEqualComparator().description());
    }
}

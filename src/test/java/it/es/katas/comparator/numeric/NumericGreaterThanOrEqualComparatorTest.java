package it.es.katas.comparator.numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class NumericGreaterThanOrEqualComparatorTest {

    @Test
    void comparesValues() {
        NumericGreaterThanOrEqualComparator comparator = new NumericGreaterThanOrEqualComparator();

        assertTrue(comparator.compare(new BigDecimal("1.0"), new BigDecimal("1")));
        assertFalse(comparator.compare(new BigDecimal("1"), new BigDecimal("2")));
    }

    @Test
    void describesItsComparison() {
        assertEquals(">=", new NumericGreaterThanOrEqualComparator().description());
    }
}

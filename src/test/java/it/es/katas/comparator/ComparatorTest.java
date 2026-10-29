package it.es.katas.comparator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ComparatorTest {

    @Test
    void caseInsensitiveEqualsComparesWithoutConsideringCase() {
        CaseInsensitiveEqualsComparator comparator = new CaseInsensitiveEqualsComparator();

        assertTrue(comparator.compare("Plant-A", "plant-a"));
        assertFalse(comparator.compare("Plant-A", "Plant-B"));
        assertFalse(comparator.compare(null, "Plant-A"));
    }

    @Test
    void numericGreaterThanComparatorComparesValues() {
        NumericGreaterThanComparator comparator = new NumericGreaterThanComparator();

        assertTrue(comparator.compare(new BigDecimal("2"), new BigDecimal("1")));
        assertFalse(comparator.compare(new BigDecimal("1"), new BigDecimal("1")));
    }

    @Test
    void numericGreaterThanOrEqualComparatorComparesValues() {
        NumericGreaterThanOrEqualComparator comparator = new NumericGreaterThanOrEqualComparator();

        assertTrue(comparator.compare(new BigDecimal("1.0"), new BigDecimal("1")));
        assertFalse(comparator.compare(new BigDecimal("1"), new BigDecimal("2")));
    }

    @Test
    void numericLessThanComparatorComparesValues() {
        NumericLessThanComparator comparator = new NumericLessThanComparator();

        assertTrue(comparator.compare(new BigDecimal("-2"), new BigDecimal("1")));
        assertFalse(comparator.compare(new BigDecimal("1"), new BigDecimal("1")));
    }

    @Test
    void numericLessThanOrEqualComparatorComparesValues() {
        NumericLessThanOrEqualComparator comparator = new NumericLessThanOrEqualComparator();

        assertTrue(comparator.compare(new BigDecimal("1"), new BigDecimal("1.00")));
        assertFalse(comparator.compare(new BigDecimal("2"), new BigDecimal("1")));
    }

    @Test
    void numericComparatorRejectsMissingInvalidAndNonFiniteValues() {
        NumericComparator comparator = new NumericComparator(new NumericGreaterThanComparator());

        assertFalse(comparator.compare(null, "1"));
        assertFalse(comparator.compare("1", null));
        assertFalse(comparator.compare("", "1"));
        assertFalse(comparator.compare("not-a-number", "1"));
        assertFalse(comparator.compare("NaN", "1"));
        assertFalse(comparator.compare("Infinity", "1"));
        assertFalse(comparator.compare("-Infinity", "1"));
    }

    @Test
    void comparatorDescriptionsExposeTheirOperators() {
        assertEquals("equals", new CaseInsensitiveEqualsComparator().description());
        assertEquals(">", new NumericComparator(new NumericGreaterThanComparator()).description());
        assertEquals(">=", new NumericComparator(new NumericGreaterThanOrEqualComparator()).description());
        assertEquals("<", new NumericComparator(new NumericLessThanComparator()).description());
        assertEquals("<=", new NumericComparator(new NumericLessThanOrEqualComparator()).description());
    }
}

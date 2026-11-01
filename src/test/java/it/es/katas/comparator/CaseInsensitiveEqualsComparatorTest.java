package it.es.katas.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CaseInsensitiveEqualsComparatorTest {

    @Test
    void matchesEqualStringsRegardlessOfCase() {
        assertTrue(Comparators.caseInsensitiveEquals().compare("Plant-A", "plant-a"));
    }

    @Test
    void doesNotMatchDifferentStrings() {
        assertFalse(Comparators.caseInsensitiveEquals().compare("Plant-A", "Plant-B"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("comparisonsWithNullOperand")
    void doesNotMatchWhenEitherOperandIsNull(String description, String left, String right) {
        assertFalse(Comparators.caseInsensitiveEquals().compare(left, right));
    }

    @Test
    void describesItsComparisonAsEquality() {
        assertEquals("equals", Comparators.caseInsensitiveEquals().description());
    }

    private static Stream<Arguments> comparisonsWithNullOperand() {
        return Stream.of(
            Arguments.of("left operand is null", null, "Plant-A"),
            Arguments.of("right operand is null", "Plant-A", null)
        );
    }
}

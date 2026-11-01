package it.es.katas.comparator.numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import it.es.katas.comparator.Comparators;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NumericComparatorTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void parsesValuesAndReturnsTheNumericComparatorResult(boolean numericComparatorResult) {
        NumericValueComparator numericValueComparator = mock(NumericValueComparator.class);
        when(numericValueComparator.compare(new BigDecimal("12.50"), new BigDecimal("3.25")))
            .thenReturn(numericComparatorResult);
        
        NumericComparator comparator = Comparators.numeric(numericValueComparator);

        assertEquals(numericComparatorResult, comparator.compare(" 12.50 ", "3.25"));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidComparisons")
    void doesNotDelegateWhenEitherValueIsNullOrInvalid(String description, String left, String right) {
        NumericValueComparator numericComparator = mock(NumericValueComparator.class);
        NumericComparator comparator = Comparators.numeric(numericComparator);

        assertFalse(comparator.compare(left, right));
        verifyNoInteractions(numericComparator);
    }

    @Test
    void describesTheComparisonUsingTheNumericComparatorDescription() {
        NumericValueComparator numericComparator = mock(NumericValueComparator.class);
        when(numericComparator.description()).thenReturn(">=");
        NumericComparator comparator = Comparators.numeric(numericComparator);

        assertEquals(">=", comparator.description());
    }

    @Test
    void rejectsNullNumericValueComparator() {
        assertThrows(NullPointerException.class, () -> Comparators.numeric(null));
    }

    private static Stream<Arguments> invalidComparisons() {
        return Stream.of(
            Arguments.of("left value is null", null, "1"),
            Arguments.of("right value is null", "1", null),
            Arguments.of("left value is empty", "", "1"),
            Arguments.of("right value is blank", "1", " "),
            Arguments.of("left value is not numeric", "not-a-number", "1"),
            Arguments.of("right value is NaN", "1", "NaN"),
            Arguments.of("right value is positive infinity", "1", "Infinity"),
            Arguments.of("right value is negative infinity", "1", "-Infinity")
        );
    }
}

package it.es.katas.comparator.numeric;

import java.math.BigDecimal;
import java.util.Objects;

import it.es.katas.comparator.ValueComparator;

public class NumericComparator implements ValueComparator {
    private final NumericValueComparator operator;

    public NumericComparator(NumericValueComparator operator) {
        this.operator = Objects.requireNonNull(operator, "operator");
    }

    @Override
    public boolean compare(String left, String right) {
        if (left == null || right == null) {
            return false;
        }

        try {
            BigDecimal leftValue = parseNumericValue(left);
            BigDecimal rightValue = parseNumericValue(right);

            return operator.compare(leftValue, rightValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private BigDecimal parseNumericValue(String value) {
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            throw new NumberFormatException("Missing numeric value");
        }

        return new BigDecimal(normalized);
    }

    @Override
    public String description() {
        return operator.description();
    }
}

package it.es.katas.comparator.numeric;

import java.math.BigDecimal;

@FunctionalInterface
public interface NumericValueComparator {
    boolean compare(BigDecimal left, BigDecimal right);

    default String description() {
        return "compare";
    }
}

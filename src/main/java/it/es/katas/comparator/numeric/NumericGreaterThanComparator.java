package it.es.katas.comparator.numeric;

import java.math.BigDecimal;

public class NumericGreaterThanComparator implements NumericValueComparator {
    @Override
    public boolean compare(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) > 0;
    }

    @Override
    public String description() {
        return ">";
    }
}

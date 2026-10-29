package it.es.katas.comparator;

import java.math.BigDecimal;

public class NumericLessThanComparator implements NumericValueComparator {
    @Override
    public boolean compare(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) < 0;
    }

    @Override
    public String description() {
        return "<";
    }
}

package it.es.katas.comparator.numeric;

import java.math.BigDecimal;

public interface NumericValueComparator {
    boolean compare(BigDecimal left, BigDecimal right);
    String description();
}

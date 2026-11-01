package it.es.katas.comparator;

import it.es.katas.comparator.numeric.NumericComparator;
import it.es.katas.comparator.numeric.NumericValueComparator;

public final class Comparators {
    private Comparators() {
    }

    public static ValueComparator caseInsensitiveEquals() {
        return new CaseInsensitiveEqualsComparator();
    }

    public static NumericComparator numeric(NumericValueComparator comparator) {
        return new NumericComparator(comparator);
    }
}

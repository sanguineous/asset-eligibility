package it.es.katas.comparator.numeric;

public final class NumericComparators {
    private NumericComparators() {
    }

    public static NumericValueComparator greaterThan() {
        return new NumericGreaterThanComparator();
    }

    public static NumericValueComparator greaterThanOrEqual() {
        return new NumericGreaterThanOrEqualComparator();
    }

    public static NumericValueComparator lessThan() {
        return new NumericLessThanComparator();
    }

    public static NumericValueComparator lessThanOrEqual() {
        return new NumericLessThanOrEqualComparator();
    }
}

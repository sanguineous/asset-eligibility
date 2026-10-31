package it.es.katas.filter;

import it.es.katas.comparator.CaseInsensitiveEqualsComparator;
import it.es.katas.comparator.NumericComparator;
import it.es.katas.comparator.NumericGreaterThanComparator;
import it.es.katas.comparator.NumericGreaterThanOrEqualComparator;
import it.es.katas.comparator.NumericLessThanComparator;
import it.es.katas.comparator.NumericLessThanOrEqualComparator;
import it.es.katas.comparator.ValueComparator;
import java.util.Arrays;
import java.util.List;

public final class Filters {
    private Filters() {
    }

    public static Filter always() {
        return new AlwaysFilter();
    }

    public static Filter never() {
        return new NeverFilter();
    }

    public static Filter exists(String propertyName) {
        return new ExistsFilter(propertyName);
    }

    public static Filter compare(String propertyName, String expectedValue, ValueComparator comparator) {
        return new ComparisonFilter(propertyName, expectedValue, comparator);
    }

    public static Filter compareProperties(String leftProperty, String rightProperty, ValueComparator comparator) {
        return new PropertyComparisonFilter(leftProperty, rightProperty, comparator);
    }

    public static Filter and(Filter first, Filter second) {
        java.util.Objects.requireNonNull(first, "first");
        java.util.Objects.requireNonNull(second, "second");
        return new AndFilter(List.of(first, second));
    }

    public static Filter or(Filter... filters) {
        List<Filter> normalized = normalize(filters);
        if (normalized.isEmpty()) {
            return never();
        }
        if (normalized.size() == 1) {
            return normalized.get(0);
        }
        return new OrFilter(normalized);
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }

    public static ValueComparator caseInsensitiveEquals() {
        return new CaseInsensitiveEqualsComparator();
    }

    public static ValueComparator numericGreaterThan() {
        return new NumericComparator(new NumericGreaterThanComparator());
    }

    public static ValueComparator numericGreaterThanOrEqual() {
        return new NumericComparator(new NumericGreaterThanOrEqualComparator());
    }

    public static ValueComparator numericLessThan() {
        return new NumericComparator(new NumericLessThanComparator());
    }

    public static ValueComparator numericLessThanOrEqual() {
        return new NumericComparator(new NumericLessThanOrEqualComparator());
    }

    private static List<Filter> normalize(Filter... filters) {
        if (filters == null || filters.length == 0) {
            return List.of();
        }
        return Arrays.stream(filters)
            .filter(java.util.Objects::nonNull)
            .toList();
    }
}

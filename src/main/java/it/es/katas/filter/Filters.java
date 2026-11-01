package it.es.katas.filter;

import it.es.katas.comparator.ValueComparator;
import java.util.Arrays;

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
        return new AndFilter(Arrays.asList(first, second));
    }

    public static Filter or(Filter first, Filter second) {
        return new OrFilter(Arrays.asList(first, second));
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }
}

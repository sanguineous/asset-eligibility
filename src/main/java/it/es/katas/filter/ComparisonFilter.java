package it.es.katas.filter;

import it.es.katas.comparator.ValueComparator;
import java.util.Map;
import java.util.Objects;

public class ComparisonFilter implements Filter {
    private final String propertyName;
    private final String expectedValue;
    private final ValueComparator comparator;

    public ComparisonFilter(String propertyName, String expectedValue, ValueComparator comparator) {
        this.propertyName = propertyName;
        this.expectedValue = expectedValue;
        this.comparator = Objects.requireNonNull(comparator, "comparator");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        if (asset == null) {
            return false;
        }
        if (!asset.containsKey(propertyName)) {
            return false;
        }
        return comparator.compare(asset.get(propertyName), expectedValue);
    }

    @Override
    public String toString() {
        return propertyName + " " + comparator.description() + " " + expectedValue;
    }
}

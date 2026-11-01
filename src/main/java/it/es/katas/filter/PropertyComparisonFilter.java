package it.es.katas.filter;

import it.es.katas.comparator.ValueComparator;
import java.util.Map;
import java.util.Objects;

public class PropertyComparisonFilter implements Filter {
    private final String leftProperty;
    private final String rightProperty;
    private final ValueComparator comparator;

    public PropertyComparisonFilter(String leftProperty, String rightProperty, ValueComparator comparator) {
        this.leftProperty = leftProperty;
        this.rightProperty = rightProperty;
        this.comparator = Objects.requireNonNull(comparator, "comparator");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        if (asset == null) {
            return false;
        }
        if (!asset.containsKey(leftProperty) || !asset.containsKey(rightProperty)) {
            return false;
        }
        return comparator.compare(asset.get(leftProperty), asset.get(rightProperty));
    }

    @Override
    public String toString() {
        return leftProperty + " " + comparator.description() + " " + rightProperty;
    }
}

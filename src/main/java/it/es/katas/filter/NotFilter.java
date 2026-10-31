package it.es.katas.filter;

import java.util.Map;
import java.util.Objects;

public class NotFilter implements Filter {
    private final Filter filter;

    public NotFilter(Filter filter) {
        this.filter = Objects.requireNonNull(filter, "filter");
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return !filter.matches(asset);
    }

    @Override
    public String toString() {
        return "NOT (" + filter + ")";
    }
}

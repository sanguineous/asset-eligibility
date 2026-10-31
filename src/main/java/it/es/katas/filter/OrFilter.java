package it.es.katas.filter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrFilter implements Filter {
    private final List<Filter> filters;

    public OrFilter(List<Filter> filters) {
        this.filters = List.copyOf(Objects.requireNonNull(filters, "filters"));
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        for (Filter filter : filters) {
            if (filter.matches(asset)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + filters.stream().map(String::valueOf).collect(Collectors.joining(" OR ")) + ")";
    }
}

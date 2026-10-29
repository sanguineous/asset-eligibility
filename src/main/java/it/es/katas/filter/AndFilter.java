package it.es.katas.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AndFilter implements Filter {
    private final List<Filter> filters;

    public AndFilter(List<Filter> filters) {
        this.filters = new ArrayList<>(filters);
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        for (Filter filter : filters) {
            if (!filter.matches(asset)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + filters.stream().map(String::valueOf).collect(Collectors.joining(" AND ")) + ")";
    }
}

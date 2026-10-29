package it.es.katas.filter;

import java.util.Map;

public class ExistsFilter implements Filter {
    private final String propertyName;

    public ExistsFilter(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean matches(Map<String, String> asset) {
        return asset != null && asset.containsKey(propertyName);
    }

    @Override
    public String toString() {
        return "exists(" + propertyName + ")";
    }
}

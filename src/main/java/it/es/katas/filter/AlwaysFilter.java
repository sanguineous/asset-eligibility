package it.es.katas.filter;

import java.util.Map;

public class AlwaysFilter implements Filter {
    @Override
    public boolean matches(Map<String, String> asset) {
        return true;
    }

    @Override
    public String toString() {
        return "always()";
    }
}

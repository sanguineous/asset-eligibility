package it.es.katas.filter;

import java.util.Map;

public class NeverFilter implements Filter {
    @Override
    public boolean matches(Map<String, String> asset) {
        return false;
    }

    @Override
    public String toString() {
        return "never()";
    }
}

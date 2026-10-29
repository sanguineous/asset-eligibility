package it.es.katas.filter;

import java.util.Map;

public interface Filter {
    boolean matches(Map<String, String> asset);
}

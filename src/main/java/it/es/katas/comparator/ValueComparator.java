package it.es.katas.comparator;

@FunctionalInterface
public interface ValueComparator {
    boolean compare(String left, String right);

    default String description() {
        return "compare";
    }
}

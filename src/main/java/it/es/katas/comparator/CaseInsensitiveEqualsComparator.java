package it.es.katas.comparator;

public class CaseInsensitiveEqualsComparator implements ValueComparator {
    @Override
    public boolean compare(String left, String right) {
        return left != null && right != null && left.equalsIgnoreCase(right);
    }

    @Override
    public String description() {
        return "equals";
    }
}

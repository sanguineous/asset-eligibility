# Asset Eligibility

A Java library for defining and evaluating composable eligibility filters over assets represented as `Map<String, String>`.

## Usage

```java
Map<String, String> asset = Map.of(
    "zone", "Plant-A",
    "batteryLevel", "85"
);

Filter eligible = Filters.and(
    Filters.compare("zone", "plant-a", Comparators.caseInsensitiveEquals()),
    Filters.compare(
        "batteryLevel",
        "20",
        Comparators.numeric(NumericComparators.greaterThanOrEqual())
    )
);

boolean matches = eligible.matches(asset);
```

`Filters` creates property-existence, constant-comparison, property-to-property, and logical filters. `AND`, `OR`, and `NOT` filters can be nested. Filters provide meaningful string representations for inspection.

## Design decisions

- **Operators:** The library implements case-insensitive string equality and the four numeric comparisons (`>`, `>=`, `<`, `<=`). Together they support equality and common threshold/range checks, including explicit boundary behavior.
- **Composition:** Filters implement `Filter`, so composite filters can combine other filters without depending on their concrete types. `ValueComparator` separates comparison behavior from property lookup. `NumericComparator` parses strings to `BigDecimal` and delegates the operation to a `NumericValueComparator`.
- **Missing and invalid values:** A missing property, null asset, or invalid numeric operand makes the relevant filter return `false`. Property names are case-sensitive; string equality is case-insensitive.

## Adding a numeric operator

Implement `NumericValueComparator`, then expose it from `NumericComparators`. `NumericComparator` handles parsing and delegation, so filters do not need changes.

```java
public class NumericNotEqualComparator implements NumericValueComparator {
    @Override
    public boolean compare(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) != 0;
    }

    @Override
    public String description() {
        return "!=";
    }
}
```

Add a factory method such as `NumericComparators.notEqual()` that returns the new implementation. It can then be used with string-valued asset properties:

```java
Filters.compare("temperature", "25", Comparators.numeric(NumericComparators.notEqual()));
```

Add tests for the operator's comparison behavior and description.

## Build and test

Requires JDK 21 or later and Maven. Run the tests from the project root:

```bash
mvn test
```

# Asset Eligibility

A lightweight Java library for defining, composing, and evaluating asset eligibility criteria.

## Overview

In a Field Service Management (FSM) platform, assets such as IoT sensors, industrial machinery, gateways, and smart meters must satisfy specific criteria before they can be considered eligible for an operation.

This library provides a reusable API for evaluating those criteria. It is independent of scheduling, dispatching, asset persistence, and assignment optimization.

An asset is represented as a `Map<String, String>`, where each key identifies a property or telemetry attribute and each value contains its string representation.

## Requirements

- Define filters programmatically.
- Evaluate whether an asset matches a filter.
- Support filters that always match or never match.
- Check whether a property exists.
- Compare a property with a constant value.
- Compare two properties of the same asset.
- Support numeric comparisons.
- Combine filters using `AND`, `OR`, and `NOT`.
- Support nested filter expressions.
- Provide meaningful string representations of filters.

## Design

The API is built around three main concepts.

### Filter

`Filter` is the common abstraction for evaluating an asset against a criterion.

Conceptually, its contract is:

```java
boolean matches(Map<String, String> asset);
```

Both individual filters and composite filters implement this interface. This allows filters to be combined without knowing their concrete implementations.

### Filters factory

`Filters` provides static factory methods for constructing filters. It keeps filter creation separate from the `Filter` interface.

The API is intended to expose operations such as:

```java
Filters.always();
Filters.never();
Filters.exists("batteryLevel");

Filters.compare(
    "batteryLevel",
    "20",
    numericGreaterThan()
);

Filters.compareProperties(
    "currentTemperature",
    "maxAllowedTemperature",
    numericLessThan()
);

Filters.and(filter1, filter2);
Filters.or(filter1, filter2);
Filters.not(filter1);
```

These examples illustrate the intended API; the exact factory signatures depend on the implementation.

### ValueComparator

`ValueComparator` defines the comparison strategy used by comparison filters.

Conceptually:

```java
boolean compare(String left, String right);
```

A comparator returns `true` when the supplied values satisfy its comparison rule, and `false` otherwise.

Examples of comparison strategies include:

- Case-insensitive string equality.
- Numeric greater-than and less-than comparisons.
- Numeric greater-than-or-equal-to and less-than-or-equal-to comparisons.

The comparison filters are responsible for retrieving the operands from the asset. The comparator defines how those operands are interpreted and compared.

This separation avoids coupling filter composition to individual comparison operators.

## Example usage

Consider a sensor with the following properties:

```java
Map<String, String> sensor = Map.of(
    "zone", "Lombardy-Plant-A",
    "isOnline", "true",
    "batteryLevel", "85",
    "firmwareVersion", "2.4.1"
);
```

The sensor should be eligible when it is deployed in the required zone, is online, and has a battery level of at least 20%.

The intended filter expression is:

```java
Filter eligibility = Filters.and(
    Filters.compare(
        "zone",
        "Lombardy-Plant-A",
        caseInsensitiveEquals()
    ),
    Filters.compare(
        "isOnline",
        "true",
        caseInsensitiveEquals()
    ),
    Filters.compare(
        "batteryLevel",
        "20",
        numericGreaterThanOrEqual()
    )
);

boolean eligible = eligibility.matches(sensor);
```

The expression matches the sensor because all three conditions are satisfied.

If `batteryLevel` changes to `"10"`, the expression no longer matches.

## Evaluation semantics

The library defines consistent behaviour for missing properties and invalid values.

### Missing properties

If a filter requires a property that is absent from the asset, it evaluates to `false`.

This applies to property existence checks, comparisons against constants, and comparisons between properties as appropriate to their semantics.

### Invalid numeric values

Numeric comparison strategies evaluate to `false` when a required operand is missing or cannot be parsed as a valid number.

The implementation should also reject non-finite numeric values where they are not meaningful for the supported comparison.

### String equality

String equality comparisons are case-insensitive, as required by the specification.

Property names are case-sensitive.

### Logical composition

Composite filters follow standard Boolean logic:

- `AND` matches only when every child filter matches.
- `OR` matches when at least one child filter matches.
- `NOT` negates the result of its child filter.
- `always()` always returns `true`.
- `never()` always returns `false`.

Because missing and invalid values produce `false` at the individual comparison level, negating such a comparison can produce `true`. This is a consequence of ordinary Boolean logic and is intentional.

### Filter representations

Each filter provides a meaningful string representation that describes its criterion and, for composite filters, its constituent expressions. This helps with debugging and understanding nested filter expressions.

## Extensibility

The design follows the Open/Closed Principle by separating filter composition from comparison behaviour.

To introduce a new comparison operator:

1. Implement the `ValueComparator` contract with the new comparison semantics.
2. Supply the comparator to `Filters.compare()` or `Filters.compareProperties()`.
3. Add tests for the new behaviour and relevant edge cases.

Existing composite filters do not need to know which comparison operators are available. They operate only on the common `Filter` abstraction.

This approach allows the comparison vocabulary to evolve without requiring changes to `AND`, `OR`, or `NOT`.

## Testing

Automated tests cover the main behaviour of the library, including:

- Always-matching and never-matching filters.
- Property existence checks.
- Equality and numeric comparisons.
- Comparisons between two properties.
- Logical composition and nested expressions.
- Missing properties and invalid numeric values.
- Case-insensitive string equality and case-sensitive property names.
- Meaningful filter string representations.

Comparison strategies and filter composition should be tested independently where practical, with additional tests covering their integration.

## Build and test

### Prerequisites

- JDK 21 or a compatible later JDK.
- Apache Maven.

### Run the tests

From the project root:

```bash
mvn test
```

## Scope and limitations

The library focuses exclusively on filter definition and evaluation.

The following are outside the scope of this implementation:

- Asset persistence.
- REST APIs.
- Scheduling and dispatching.
- Ranking or assignment optimization.
- Parsing filter expressions from text.
- Serialization and deserialization of filters.

The API is intentionally small and framework-independent. Its purpose is to provide a clear, composable, and extensible foundation for asset eligibility evaluation.
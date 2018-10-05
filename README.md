# enum-reverse-lookup-table-jmh

Java MicroBenchmark Harness that compares the performance of various strategies with a TreeMap and HashMap for an
int -> Enum lookup table.

## Running the benchmarks

```sh
mvn clean install
```

```
java -jar target/benchmarks.jar
```

## Interpreting the benchmarks

    - t1: Check if a `java.util.TreeMap` contains the key and throw if it doesn't. `get` and return the value of the key.
    - t2: Use `get` only and throw if the resulting value is null. We know null is not a valid type.
    - t3: Perform the same check as t2 using the Java 8 `Optional.orElse` paradigm.

    - h1: Check if a `java.util.HashMap` contains the key and throw if it doesn't. `get` and return the value of the key.
    - h2: Use `get` only and throw if the resulting value is null. We know null is not a valid type.
    - h3: Perform the same check above using the Java 8 `Optional.orElse` paradigm.

h2 is the clear winner and t1 is the slowest.

    - A `HashMap` uses a O(1) constant table lookup whereas a `TreeMap` uses a O(log(n)) lookup and this manifests in the
    average time below.
    - `containsKey` followed by a `get` is redundant and has a non-trivial performance penalty if we know that null
    values will never be permitted.

```sh
Benchmark                                (iterations)  (lookupApproach)  Mode  Cnt   Score   Error  Units

MultihashTypeLookupBenchmark.testLookup          1000                t1  avgt    9  33.438 ± 4.514  us/op
MultihashTypeLookupBenchmark.testLookup          1000                t2  avgt    9  26.986 ± 0.405  us/op
MultihashTypeLookupBenchmark.testLookup          1000                t3  avgt    9  39.259 ± 1.306  us/op
MultihashTypeLookupBenchmark.testLookup          1000                h1  avgt    9  18.954 ± 0.414  us/op
MultihashTypeLookupBenchmark.testLookup          1000                h2  avgt    9  15.486 ± 0.395  us/op
MultihashTypeLookupBenchmark.testLookup          1000                h3  avgt    9  16.780 ± 0.719  us/op
```


package org.sample;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(org.openjdk.jmh.annotations.Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class MultihashTypeLookupBenchmark {
    @Param({ "1000" })
    public int iterations;

    @Param({"t1", "t2", "t3", "h1", "h2", "h3"})
    private String lookupApproach;

    @Benchmark
    public void testLookup(Blackhole bh) {
        for (int i = iterations; i > 0; i--) {
            switch (lookupApproach) {
                case "t1":
                    bh.consume(Multihash.Type.lookupTreeMapNotContainsKeyThrowGet(0));
                    break;
                case "t2":
                    bh.consume(Multihash.Type.lookupTreeMapGetThrowIfNull(0x41));
                    break;
                case "t3":
                    bh.consume(Multihash.Type.lookupTreeMapGetOptionalOrElseThrow(0x17));
                case "h1":
                    bh.consume(Multihash.Type.lookupHashMapNotContainsKeyThrowGet(0x1a));
                    break;
                case "h2":
                    bh.consume(Multihash.Type.lookupHashMapGetThrowIfNull(0xd5));
                    break;
                case "h3":
                    bh.consume(Multihash.Type.lookupHashMapGetOptionalOrElseThrow(0x1c));
                    break;
            }
        }
    }
}

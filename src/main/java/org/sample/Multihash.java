package org.sample;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Wraps the type enum that holds the reverse lookup logic that is benchmarked.
 */
class Multihash {
    // Modified variant of https://github.com/multiformats/java-multihash/blob/master/src/main/java/io/ipfs/multihash/Multihash.java
    // for benchmarking various flavors of index to Type lookup.
    enum Type {
        id(0, -1),
        md5(0xd5, 16),
        sha1(0x11, 20),
        sha2_256(0x12, 32),
        sha2_512(0x13, 64),
        sha3_224(0x17, 24),
        sha3_256(0x16, 32),
        sha3_512(0x14, 64),
        keccak_224(0x1a, 24),
        keccak_256(0x1b, 32),
        keccak_384(0x1c, 48),
        keccak_512(0x1d, 64),
        blake2b(0x40, 64),
        blake2s(0x41, 32);

        public final int index, length;

        Type(final int index, final int length) {
            this.index = index;
            this.length = length;
        }

        static Map<Integer, Type> lookupT = new TreeMap<>();
        static Map<Integer, Type> lookupH = new HashMap<>();

        static {
            for (Type t: Type.values()) {
                lookupT.put(t.index, t);
                lookupH.put(t.index, t);
            }

        }

        static Type lookupTreeMapNotContainsKeyThrowGet(int t) {
            if (!lookupT.containsKey(t))
                throw new IllegalStateException("Unknown Multihash type: " + t);
            return lookupT.get(t);
        }

        static Type lookupTreeMapGetThrowIfNull(int t) {
            Type type = lookupT.get(t);
            if (type == null)
                throw new IllegalStateException("Unknown Multihash type: " + t);
            return type;
        }

        static Type lookupTreeMapGetOptionalOrElseThrow(int t) {
            return Optional.ofNullable(lookupT.get(t)).orElseThrow(() -> new IllegalStateException("Unknown Multihash type: " + t));
        }

        static Type lookupHashMapNotContainsKeyThrowGet(int t) {
            if (!lookupH.containsKey(t))
                throw new IllegalStateException("Unknown Multihash type: " + t);
            return lookupH.get(t);
        }

        static Type lookupHashMapGetThrowIfNull(int t) {
            Type type = lookupH.get(t);
            if (type == null)
                throw new IllegalStateException("Unknown Multihash type: " + t);
            return type;
        }

        static Type lookupHashMapGetOptionalOrElseThrow(int t) {
            return Optional.ofNullable(lookupH.get(t)).orElseThrow(() -> new IllegalStateException("Unknown Multihash type: " + t));
        }
    }
}

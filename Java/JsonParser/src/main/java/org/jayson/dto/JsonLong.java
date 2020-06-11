package org.jayson.dto;

import java.util.Objects;

public class JsonLong implements JsonNumber {

    private final long longValue;

    public JsonLong(long value) {
        this.longValue = value;
    }

    @Override
    public boolean isLong() {
        return true;
    }

    @Override
    public double getDouble() {
        return longValue;
    }

    @Override
    public long getLong() {
        return longValue;
    }

    @Override
    public String toString() {
        return format();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonLong jsonLong = (JsonLong) o;
        return longValue == jsonLong.longValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longValue);
    }
}

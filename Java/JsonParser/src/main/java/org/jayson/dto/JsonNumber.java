package org.jayson.dto;

import java.util.Objects;

public class JsonNumber implements JsonElement {

    private double doubleValue;
    private long longValue;
    private final boolean isDouble;

    public JsonNumber(double value) {
        this.isDouble = true;
        this.doubleValue = value;
    }

    public JsonNumber(long value) {
        this.isDouble = false;
        this.longValue = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean isLong() {
        return !isDouble;
    }

    public double getDouble() {
        return isDouble ? doubleValue : longValue;
    }

    public long getLong() {
        return isDouble ? (long) doubleValue : longValue;
    }

    @Override
    public String toString() {
        return format();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonNumber that = (JsonNumber) o;
        return Double.compare(that.doubleValue, doubleValue) == 0 &&
                longValue == that.longValue &&
                isDouble == that.isDouble;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doubleValue, longValue, isDouble);
    }
}

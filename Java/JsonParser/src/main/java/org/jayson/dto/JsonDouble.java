package org.jayson.dto;

import java.util.Objects;

public class JsonDouble implements JsonNumber {

    private final double doubleValue;

    public JsonDouble(double value) {
        this.doubleValue = value;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public double getDouble() {
        return doubleValue;
    }

    @Override
    public long getLong() {
        return (long) doubleValue;
    }

    @Override
    public String toString() {
        return format();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonDouble that = (JsonDouble) o;
        return Double.compare(that.doubleValue, doubleValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doubleValue);
    }
}

package org.jayson.dto;

import java.util.Objects;

public class JsonString implements JsonElement {

    private final String value;

    public JsonString(String value) {
        this.value = value;
    }

    @Override
    public boolean isString() {
        return true;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return format();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonString that = (JsonString) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

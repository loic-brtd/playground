package org.jayson.dto;

import java.util.Objects;

public class JsonBoolean implements JsonElement {

    private boolean value;

    public JsonBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toJson() {
        return value ? "true" : "false";
    }

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonBoolean that = (JsonBoolean) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

package org.jayson.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonObject implements JsonElement {

    private Map<String, JsonElement> values;

    public JsonObject() {
        values = new HashMap<>();
    }

    @Override
    public boolean isObject() {
        return true;
    }

    public JsonElement get(String key) {
        return values.get(key);
    }

    public Set<String> keys() {
        return values.keySet();
    }

    public JsonObject put(String key, JsonElement value) {
        values.put(key, value);
        return this;
    }

    public JsonObject put(String key, String value) {
        values.put(key, new JsonString(value));
        return this;
    }

    public String getString(String key) {
        JsonElement element = values.get(key);
        if (element instanceof JsonString) {
            return ((JsonString) element).getValue();
        }
        return null;
    }

    public JsonObject put(String key, double value) {
        values.put(key, new JsonNumber(value));
        return this;
    }

    public Double getDouble(String key) {
        JsonElement element = values.get(key);
        if (element instanceof JsonNumber) {
            return ((JsonNumber) element).getDouble();
        }
        return null;
    }

    public JsonObject put(String key, long value) {
        values.put(key, new JsonNumber(value));
        return this;
    }

    public Long getLong(String key) {
        JsonElement element = values.get(key);
        if (element instanceof JsonNumber) {
            return ((JsonNumber) element).getLong();
        }
        return null;
    }

    public JsonObject put(String key, boolean value) {
        values.put(key, new JsonBoolean(value));
        return this;
    }

    public Boolean getBoolean(String key) {
        JsonElement element = values.get(key);
        if (element instanceof JsonBoolean) {
            return ((JsonBoolean) element).getValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return values.entrySet().stream()
                .map(e -> '"' + e.getKey() + '"' + ':' + e.getValue())
                .collect(Collectors.joining(",", "{", "}"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonObject object = (JsonObject) o;
        return Objects.equals(values, object.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}

package org.jayson.dto;

import java.util.*;
import java.util.stream.Collectors;

public class JsonObject implements JsonElement {

    private Map<String, JsonElement> map;
    private int level;

    public JsonObject() {
        map = new LinkedHashMap<>();
    }

    @Override
    public boolean isObject() {
        return true;
    }

    public JsonElement get(String key) {
        return map.get(key);
    }

    public Set<String> keys() {
        return map.keySet();
    }

    public JsonObject put(String key, JsonElement value) {
        // if (value instanceof JsonObject) {
        //     ((JsonObject) value).level = level + 1;
        // }
        map.put(key, value);
        return this;
    }

    public JsonObject put(String key, String value) {
        map.put(key, (value == null) ? null : new JsonString(value));
        return this;
    }

    public String getString(String key) {
        JsonElement element = map.get(key);
        if (element instanceof JsonString) {
            return ((JsonString) element).getValue();
        }
        return null;
    }

    public JsonObject put(String key, double value) {
        map.put(key, new JsonNumber(value));
        return this;
    }

    public Double getDouble(String key) {
        JsonElement element = map.get(key);
        if (element instanceof JsonNumber) {
            return ((JsonNumber) element).getDouble();
        }
        return null;
    }

    public JsonObject put(String key, long value) {
        map.put(key, new JsonNumber(value));
        return this;
    }

    public Long getLong(String key) {
        JsonElement element = map.get(key);
        if (element instanceof JsonNumber) {
            return ((JsonNumber) element).getLong();
        }
        return null;
    }

    public JsonObject put(String key, boolean value) {
        map.put(key, new JsonBoolean(value));
        return this;
    }

    public Boolean getBoolean(String key) {
        JsonElement element = map.get(key);
        if (element instanceof JsonBoolean) {
            return ((JsonBoolean) element).getValue();
        }
        return null;
    }

    public JsonObject putNull(String key) {
        map.put(key, null);
        return this;
    }

    @Override
    public String toJson() {
        return map.entrySet().stream()
                .map(e -> '"' + e.getKey() + '"' + ':' + e.getValue())
                .collect(Collectors.joining(",", "{", "}"));
    }

    @Override
    public String toJson(String indent, int level) {
        String indent1 = repeat(level + 1, indent);
        String indent2 = repeat(level, indent);
        return map.entrySet().stream()
                .map(e -> indent1 + '"' + e.getKey() + '"' + ": " + e.getValue().toJson(indent, level + 1))
                .collect(Collectors.joining(",\n", "{\n", "\n" + indent2 + "}"));
    }

    private static String repeat(int n, String s) {
        return new String(new char[n]).replace("\0", s);
    }

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonObject object = (JsonObject) o;
        return Objects.equals(map, object.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}

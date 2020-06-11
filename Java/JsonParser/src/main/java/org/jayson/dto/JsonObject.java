package org.jayson.dto;

import java.util.*;
import java.util.Map.Entry;

public class JsonObject implements JsonElement {

    private final Map<String, JsonElement> map;

    public JsonObject() {
        map = new LinkedHashMap<>();
    }

    @Override
    public boolean isObject() {
        return true;
    }

    public Set<String> keys() {
        return map.keySet();
    }

    public Collection<JsonElement> values() {
        return map.values();
    };

    public Set<Entry<String, JsonElement>> entries() {
        return map.entrySet();
    }

    public JsonElement get(String key) {
        return map.get(key);
    }

    public <T extends JsonElement> T get(String key, Class<T> type) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(type);
        JsonElement element = map.get(key);
        return type.isInstance(element) ? type.cast(element) : null;
    }

    public JsonArray getArray(String key) {
        return get(key, JsonArray.class);
    }

    public JsonObject getObject(String key) {
        return get(key, JsonObject.class);
    }

    public JsonNumber getNumber(String key) {
        return get(key, JsonNumber.class);
    }

    public String getString(String key) {
        JsonString string = get(key, JsonString.class);
        return string == null ? null : string.getValue();
    }

    public Double getDouble(String key) {
        JsonNumber number = get(key, JsonNumber.class);
        return number == null ? null : number.getDouble();
    }

    public Long getLong(String key) {
        JsonNumber number = get(key, JsonNumber.class);
        return number == null ? null : number.getLong();
    }

    public Boolean getBoolean(String key) {
        JsonBoolean bool = get(key, JsonBoolean.class);
        return bool == null ? null : bool.getValue();
    }

    public JsonObject put(String key, JsonElement value) {
        Objects.requireNonNull(key);
        map.put(key, value);
        return this;
    }

    public JsonObject put(String key, String value) {
        Objects.requireNonNull(key);
        map.put(key, (value == null) ? null : new JsonString(value));
        return this;
    }

    public JsonObject put(String key, double value) {
        Objects.requireNonNull(key);
        map.put(key, new JsonDouble(value));
        return this;
    }

    public JsonObject put(String key, long value) {
        Objects.requireNonNull(key);
        map.put(key, new JsonLong(value));
        return this;
    }

    public JsonObject put(String key, boolean value) {
        Objects.requireNonNull(key);
        map.put(key, new JsonBoolean(value));
        return this;
    }

    public JsonObject putNull(String key) {
        Objects.requireNonNull(key);
        map.put(key, null);
        return this;
    }

    @Override
    public String toString() {
        return format();
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

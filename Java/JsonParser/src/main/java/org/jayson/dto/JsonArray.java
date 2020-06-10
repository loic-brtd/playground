package org.jayson.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JsonArray implements JsonElement {

    private List<JsonElement> elements;

    public JsonArray() {
        this.elements = new ArrayList<>();
    }

    public JsonArray(JsonElement... elements) {
        this.elements = new ArrayList<>();
        Collections.addAll(this.elements, elements);
    }

    public int size() {
        return elements.size();
    }

    public List<JsonElement> values() {
        return elements;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    public JsonElement get(int index) {
        return elements.get(index);
    }

    public JsonArray push(String value) {
        elements.add(value == null ? null : new JsonString(value));
        return this;
    }

    public JsonArray push(boolean value) {
        elements.add(new JsonBoolean(value));
        return this;
    }

    public JsonArray push(double value) {
        elements.add(new JsonNumber(value));
        return this;
    }

    public JsonArray push(long value) {
        elements.add(new JsonNumber(value));
        return this;
    }

    public JsonArray push(JsonElement element) {
        elements.add(element);
        return this;
    }

    public JsonArray pushNull() {
        elements.add(null);
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
        JsonArray jsonArray = (JsonArray) o;
        return Objects.equals(elements, jsonArray.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}

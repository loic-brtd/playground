package org.jayson.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonArray implements JsonElement {

    private List<JsonElement> elements;

    public JsonArray() {
        this.elements = new ArrayList<>();
    }

    public int size() {
        return elements.size();
    }

    public JsonElement get(int index) {
        return elements.get(index);
    }

    public JsonArray(JsonElement... elements) {
        this.elements = new ArrayList<>();
        Collections.addAll(this.elements, elements);
    }

    @Override
    public boolean isArray() {
        return true;
    }

    public JsonArray push(String value) {
        elements.add(new JsonString(value));
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

    @Override
    public String toString() {
        return elements.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
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

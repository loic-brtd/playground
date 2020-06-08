package org.jayson.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

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
    public String toJson() {
        return elements.stream()
                .map(String::valueOf)
                .collect(joining(",", "[", "]"));
    }

    @Override
    public String toJson(String indent, int level) {
        String margin = repeat(level, indent);
        return elements.stream()
                .map(e -> formatElement(e, level, indent))
                .collect(joining(",\n", "[\n", "\n" + margin + "]"));
    }

    private String formatElement(JsonElement element, int level, String indent) {
        String margin = repeat(level + 1, indent);
        return margin + (element == null ? "null" : element.toJson(indent, level + 1));
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
        JsonArray jsonArray = (JsonArray) o;
        return Objects.equals(elements, jsonArray.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}

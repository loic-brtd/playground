package org.jayson.dto;

public class JsonString implements JsonElement {

    private String value;

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
        return '"' + value + '"';
    }
}

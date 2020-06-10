package org.jayson.dto;


import org.jayson.format.JsonFormatter;

public interface JsonElement {

    default boolean isBoolean() {
        return false;
    }

    default boolean isArray() {
        return false;
    }

    default boolean isObject() {
        return false;
    }

    default boolean isNumber() {
        return false;
    }

    default boolean isString() {
        return false;
    }

    default JsonArray asArray() {
        return (JsonArray) this;
    }

    default JsonObject asObject() {
        return (JsonObject) this;
    }

    default String format() {
        return JsonFormatter.INLINE.format(this);
    }

    default String format(JsonFormatter format) {
        return format.format(this);
    }

}

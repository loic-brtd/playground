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

    default boolean isNull() {
        return false;
    }

    default JsonArray asArray() {
        if (isNull()) return null;
        if (isArray())
            return (JsonArray) this;
        throw new IllegalStateException(getClass().getSimpleName() + " cannot be converted to JsonArray: " + this);
    }

    default JsonObject asObject() {
        if (isNull()) return null;
        if (isObject())
            return (JsonObject) this;
        throw new IllegalStateException(getClass().getSimpleName() + " cannot be converted to JsonObject: " + this);
    }

    default String asString() {
        if (isNull()) return null;
        if (isString())
            return ((JsonString) this).value();
        throw new IllegalStateException(getClass().getSimpleName() + " cannot be converted to String: " + this);
    }

    default boolean asBoolean() {
        if (isBoolean())
            return ((JsonBoolean) this).value();
        throw new IllegalStateException(getClass().getSimpleName() + " cannot be converted to boolean: " + this);
    }

    default long asLong() {
        if (isNumber())
            return ((JsonNumber) this).longValue();
        throw new IllegalStateException(getClass().getSimpleName() + " cannot be converted to long: " + this);
    }

    default int asInt() {
        if (isNumber())
            return ((JsonNumber) this).intValue();
        throw new IllegalStateException(getClass().getSimpleName() + " cannot be converted to int: " + this);
    }

    default double asDouble() {
        if (isNumber())
            return ((JsonNumber) this).doubleValue();
        throw new IllegalStateException(getClass().getSimpleName() + " cannot be converted to double: " + this);
    }

    default String format(JsonFormatter format) {
        return format.format(this);
    }
}

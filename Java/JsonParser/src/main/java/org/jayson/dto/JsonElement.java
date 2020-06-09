package org.jayson.dto;


import org.jayson.Json;
import org.jayson.format.JsonFormat;

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

    default String format() {
        return Json.INLINE.format(this);
    }

    default String format(JsonFormat format) {
        return format.format(this);
    }

}

package org.jayson.dto;


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

    String toJson();

    // default String toJson(String indent, int level) {
    //     return toJson();
    // }
    //
    // default String toJson(String indent) {
    //     return toJson(indent, 0);
    // }

    default String toJson(JsonFormat format) {
        return format.format(this);
    }

}

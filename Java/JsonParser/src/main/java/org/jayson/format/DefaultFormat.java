package org.jayson.format;

import org.jayson.dto.*;

import java.util.stream.Collectors;

public class DefaultFormat implements JsonFormat {

    public String format(JsonObject object) {
        return object.entries().stream()
                .map(e -> '"' + e.getKey() + "\": " + format(e.getValue()))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public String format(JsonArray array) {
        return array.values().stream()
                .map(this::format)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public String format(JsonBoolean bool) {
        return bool.getValue() ? "true" : "false";
    }

    public String format(JsonNumber number) {
        return number.isDouble()
                ? String.valueOf(number.getDouble())
                : String.valueOf(number.getLong());
    }

    public String format(JsonString string) {
        return '"' + string.getValue() + '"';
    }
}

package org.jayson.format;

import org.jayson.dto.*;

public interface JsonFormat {

    default String format(JsonElement element) {
        if (element == null) return formatNull();
        if (element instanceof JsonObject) return format((JsonObject) element);
        if (element instanceof JsonArray) return format((JsonArray) element);
        if (element instanceof JsonString) return format((JsonString) element);
        if (element instanceof JsonNumber) return format((JsonNumber) element);
        if (element instanceof JsonBoolean) return format((JsonBoolean) element);
        throw new IllegalStateException();
    }

    default String formatNull() {
        return "null";
    };

    String format(JsonObject object);

    String format(JsonArray array);

    String format(JsonBoolean bool);

    String format(JsonNumber number);

    String format(JsonString string);
}

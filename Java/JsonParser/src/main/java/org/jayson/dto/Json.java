package org.jayson.dto;

import org.jayson.parser.JsonLexer;
import org.jayson.parser.JsonParser;

public final class Json {

    private Json() {
    }

    public static JsonObject parse(String source) {
        JsonLexer lexer = new JsonLexer(source);
        JsonParser parser = new JsonParser(lexer);
        return parser.parse();
    }

    public static JsonObject object() {
        return new JsonObject();
    }

    public static JsonArray array() {
        return new JsonArray();
    }

    public static JsonArray array(Object... elements) {
        JsonArray jsonArray = new JsonArray();
        for (Object element : elements) {
            if (element instanceof String) {
                jsonArray.push((String) element);
            } else if (element instanceof Double) {
                jsonArray.push((Double) element);
            } else if (element instanceof Long) {
                jsonArray.push((Long) element);
            } else if (element instanceof Integer) {
                jsonArray.push((Integer) element);
            } else if (element instanceof Boolean) {
                jsonArray.push((Boolean) element);
            } else if (element instanceof JsonElement) {
                jsonArray.push((JsonElement) element);
            } else {
                throw new IllegalArgumentException("Incompatible type for vararg element <" + element + ">");
            }
        }
        return jsonArray;
    }
}

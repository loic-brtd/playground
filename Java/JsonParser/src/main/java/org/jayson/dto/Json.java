package org.jayson.dto;

import org.jayson.format.DefaultFormat;
import org.jayson.format.JsonFormat;
import org.jayson.parser.JsonLexer;
import org.jayson.parser.JsonParser;

public final class Json {

    public static final JsonFormat INLINE = new DefaultFormat();

    private Json() {
    }

    public static JsonObject parse(String source) {
        JsonLexer lexer = new JsonLexer(source);
        JsonParser parser = new JsonParser(lexer);
        return parser.parse();
    }

    public static String format(String source) {
        return Json.parse(source).toJson(INLINE);
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
            if (element == null) {
                jsonArray.pushNull();
            } else if (element instanceof String) {
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

    public static void main(String[] args) {
        try {
            String source = "{\"coucou\":{\"salut\":10,\"hello\":[12, 23, true, null]}}";

            System.out.println(source);
            System.out.println(Json.format(source));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

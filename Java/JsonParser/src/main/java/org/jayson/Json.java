package org.jayson;

import org.jayson.dto.JsonArray;
import org.jayson.dto.JsonElement;
import org.jayson.dto.JsonObject;
import org.jayson.format.CustomFormat;
import org.jayson.format.JsonFormat;
import org.jayson.parser.JsonLexer;
import org.jayson.parser.JsonParser;

public final class Json {

    public static final JsonFormat FOUR_SPACES = new CustomFormat();
    public static final JsonFormat MINIMIZED = new CustomFormat()
            .indent("").newline("").separator(",").colon(":");
    public static final JsonFormat INLINE = new CustomFormat()
            .indent("").newline("").separator(", ").colon(": ");

    private Json() {
    }

    public static JsonObject parse(String source) {
        JsonLexer lexer = new JsonLexer(source);
        JsonParser parser = new JsonParser(lexer);
        return parser.parse();
    }

    // public static JsonObject parseObject(String source) {
    //     return (JsonObject) Json.parse(source);
    // }

    public static String format(String source) {
        return Json.parse(source).format(MINIMIZED);
    }

    public static String format(String source, JsonFormat format) {
        return Json.parse(source).format(format);
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

        // String source = "{\"coucou\":{\"salut\":10,\"hello\":[12, 23, true, null]}, \"hey\": {\"salut\":10,\"hello\":[12, 23, true, null]}}";
        // System.out.println(Json.format(source, FOUR_SPACES));

        String source = "{\n" +
                "  \"widget\": {\n" +
                "    \"debug\": \"on\",\n" +
                "    \"window\": {\n" +
                "      \"title\": \"Sample Konfabulator Widget\",\n" +
                "      \"name\": \"main_window\",\n" +
                "      \"width\": 500,\n" +
                "      \"height\": 500\n" +
                "    },\n" +
                "    \"image\": {\n" +
                "      \"src\": \"Images/Sun.png\",\n" +
                "      \"name\": \"sun1\",\n" +
                "      \"hOffset\": 250,\n" +
                "      \"vOffset\": 250,\n" +
                "      \"alignment\": \"center\"\n" +
                "    },\n" +
                "    \"text\": {\n" +
                "      \"data\": \"Click Here\",\n" +
                "      \"size\": 36,\n" +
                "      \"style\": \"bold\",\n" +
                "      \"name\": \"text1\",\n" +
                "      \"hOffset\": 250,\n" +
                "      \"vOffset\": 100,\n" +
                "      \"alignment\": \"center\",\n" +
                "      \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
        JsonElement object = Json.parse("[21, 0.5, null, false, \"ahah\"]");
        System.out.println(object);

        // long startTime = System.nanoTime();
        // for (int i = 0; i < 1_000_000; i++) {
        // object.format(FOUR_SPACES);
        // }
        // long stopTime = System.nanoTime();
        // System.out.println(stopTime - startTime + "ns");

        // 1_000_000 times :

        // non optimized :
        // 8670455601ns
        // 8765304066ns

        // optimized with string cache :
        // 3927716681ns
        // 3778578130ns

        // with parameterized fields :
        // 4059617605ns
        // 4216941714ns

        // Java : fields in middle of file, close to place where used ?
        // Java : Arrays.copyOf vs System.arrayCopy
    }
}

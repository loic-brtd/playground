package org.jayson;

import org.jayson.dto.JsonArray;
import org.jayson.dto.JsonElement;
import org.jayson.dto.JsonObject;
import org.jayson.format.JsonFormatter;
import org.jayson.parser.JsonLexer;
import org.jayson.parser.JsonParser;

import java.io.IOException;

import static org.jayson.format.JsonFormatter.*;

public final class Json {

    private Json() {
    }

    public static JsonElement parse(String source) {
        JsonLexer lexer = new JsonLexer(source);
        JsonParser parser = new JsonParser(lexer);
        return parser.parse();
    }

    public static JsonObject parseObject(String source) {
        JsonElement element = parse(source);
        if (!element.isObject()) {
            throw new RuntimeException("Tried to parse a JsonObject but got a "
                    + element.getClass().getSimpleName());
        }
        return (JsonObject) element;
    }

    public static String format(String source, JsonFormatter format) {
        return parse(source).format(format);
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

    public static void main(String[] args) throws IOException {

        String source = "{\"coucou\":{\"salut\":10,\"hello\":[12, 23, true, null]}, " +
                "\"hey\": {\"salut\":10,\"hello\":[12, 23, true, null]}}";

        JsonObject object = Json.parse(source).asObject();
        System.out.println(object.format(INLINE));
        System.out.println(object
                .getObject("coucou")
                .getArray("hello")
                .get(2)
        );


        // if (args.length != 1) {
        //     throw new IllegalArgumentException();
        // }
        //
        // String source = Files.lines(Paths.get(args[0])).collect(joining());
        // System.out.println(Json.parse(source).format(MINIMIZED));

        // JsonObject o = object()
        //         .put("hello", array("world", "!"))
        //         .put("age", 12)
        //         .put("isNew", false);
        //
        // String formatted = o.format(FOUR_SPACES);
        // JsonObject other = Json.parseObject(formatted);
        //
        // System.out.println("o.equals(other) = " + o.equals(other));

        // String source = "{\"coucou\":{\"salut\":10,\"hello\":[12, 23, true, null]}, \"hey\": {\"salut\":10,\"hello\":[12, 23, true, null]}}";
        // JsonElement object = Json.parseObject(source);
        //
        // long startTime = System.nanoTime();
        // for (int i = 0; i < 1_000_000; i++) {
        //     object.format(FOUR_SPACES);
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

package org.jayson.parser;

import org.jayson.dto.*;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.stream.Collectors.*;
import static org.jayson.parser.JsonParser.UnexpectedTokenException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonParserTest {

    @Test
    public void testEmptyObject() {
        JsonObject object = Json.parse("{}");
        assertEquals(0, object.keys().size());
    }

    @Test
    public void testStringValue() {
        JsonObject object = Json.parse(" {  \"hello\"  :  \"world\"  } ");
        assertEquals(1, object.keys().size());
        assertEquals(new JsonString("world"), object.get("hello"));
    }

    @Test
    public void testStringAndBooleanValues() {
        JsonObject object = Json.parse("{\"first\":\"hello\",\"boolean\":true}");
        assertEquals(2, object.keys().size());
        assertEquals(new JsonString("hello"), object.get("first"));
        assertEquals(new JsonBoolean(true), object.get("boolean"));
    }

    @Test
    public void testObjectValues() {
        JsonObject actual = Json.parse("{'first':'string','second':{'third':'another','bool':false}}"
                .replaceAll("'", "\""));
        JsonObject expected = Json.object()
                .put("first", "string")
                .put("second", Json.object()
                        .put("third", "another")
                        .put("bool", false));
        assertEquals(expected, actual);
    }

    @Test
    public void testObjectNoClosed() {
        assertThrows(UnexpectedTokenException.class, () -> Json.parse("{\"key\":\"value\""));
    }

    @Test
    public void testEmptyArray() {
        JsonObject actual = Json.parse("{\"array\":[]}");
        JsonObject expected = Json.object()
                .put("array", Json.array());
        assertEquals(expected, actual);
    }

    @Test
    public void testArrayWithValues() {
        JsonObject actual = Json.parse("{\"array\":[\"str\", 12, 5.3, true]}");
        JsonObject expected = Json.object()
                .put("array", Json.array("str", 12, 5.3, true));
        assertEquals(expected, actual);
    }

    @Test
    public void testNullValue() {
        JsonObject expected = Json.object()
                .put("here", (String) null)
                .put("not_here", 12);
        JsonObject actual = Json.parse("{\"here\":null,\"not_here\":12}");
        assertEquals(expected, actual);
    }

    @Test
    public void testValid1() {
        String source = loadResource("valid1.json");
        String parsed = Json.parse(source).toJson();
        String parsedAgain = Json.parse(parsed).toJson();
        assertEquals(parsed, parsedAgain);
    }

    @Test
    public void testValid2() {
        String source = loadResource("valid2.json");
        String parsed = Json.parse(source).toJson();
        String parsedAgain = Json.parse(parsed).toJson();
        assertEquals(parsed, parsedAgain);
    }

    private static String loadResource(String fileName) {
        try {
            URI uri = ClassLoader.getSystemResource(fileName).toURI();
            return Files.lines(Paths.get(uri)).collect(joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
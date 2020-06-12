package org.jayson.parser;

import org.jayson.Json;
import org.jayson.dto.*;
import org.jayson.format.JsonFormatter;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;
import static org.jayson.parser.JsonParser.UnexpectedTokenException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonParserTest {

    @Test
    public void testEmptyObject() {
        JsonObject object = Json.parseObject("{}");
        assertEquals(0, object.keys().size());
    }

    @Test
    public void testStringValue() {
        JsonObject object = Json.parseObject(" {  \"hello\"  :  \"world\"  } ");
        assertEquals(1, object.keys().size());
        assertEquals(new JsonString("world"), object.get("hello"));
    }

    @Test
    public void testStringAndBooleanValues() {
        JsonObject object = Json.parseObject("{\"first\":\"hello\",\"boolean\":true}");
        assertEquals(2, object.keys().size());
        assertEquals(new JsonString("hello"), object.get("first"));
        assertEquals(new JsonBoolean(true), object.get("boolean"));
    }

    @Test
    public void testObjectValues() {
        JsonObject actual = Json.parseObject("{'first':'string','second':{'third':'another','bool':false}}"
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
        assertThrows(UnexpectedTokenException.class, () -> Json.parseObject("{\"key\":\"value\""));
    }

    @Test
    public void testEmptyArray() {
        JsonObject actual = Json.parseObject("{\"array\":[]}");
        JsonObject expected = Json.object()
                .put("array", Json.array());
        assertEquals(expected, actual);
    }

    @Test
    public void testArrayWithValues() {
        JsonObject actual = Json.parseObject("{\"array\":[\"str\", 12, 5.3, true]}");
        JsonObject expected = Json.object()
                .put("array", Json.array("str", 12, 5.3, true));
        assertEquals(expected, actual);
    }

    @Test
    public void testNullValue() {
        JsonObject expected = Json.object()
                .put("here", (String) null)
                .put("not_here", 12);
        JsonObject actual = Json.parseObject("{\"here\":null,\"not_here\":12}");
        assertEquals(expected, actual);
    }

    @Test
    public void testMissingComma() {
        String source = "{\"salut\":10\"hello\":10}";
        assertThrows(UnexpectedTokenException.class, () -> Json.parseObject(source));
    }

    @Test
    public void testNewlineEscape() {
        JsonElement actual = Json.parse("\"new\\nline\"");
        JsonElement expected = new JsonString("new\nline");
        assertEquals(expected, actual);
    }

    @Test
    public void testEscapeSequences() {
        JsonElement actual = Json.parse(loadResource("escape_sequence.json"));
        JsonElement expected = Json.object()
                .put("dq", "double\"quote")
                .put("bs", "back\\slash")
                .put("fs", "forward/slash")
                .put("b", "back\nspace")
                .put("f", "form\ffeed")
                .put("n", "new\nline")
                .put("r", "carriage\rreturn")
                .put("t", "tab\tulation");
        assertEquals(expected, actual);
    }

    @Test
    public void testHexadecimalSequence() {
        JsonElement actual = Json.parse("\"\\u0068\\u0065\\u006C\\u006C\\u006f world\" ");
        JsonElement expected = new JsonString("hello world");
        assertEquals(expected, actual);
    }

    @Test
    public void testHexadecimalSequence2() {
        JsonElement actual = Json.parse("\"\\u4567\\u1234\\uFFFF\" ");
        JsonElement expected = new JsonString("\u4567\u1234\uFFFF");
        assertEquals(expected, actual);
    }

    @Test
    public void testMenu() {
        String source = loadResource("menu.json");
        String parsed = Json.parse(source).format(JsonFormatter.MINIMIZED);
        String parsedAgain = Json.parse(parsed).format(JsonFormatter.MINIMIZED);
        assertEquals(parsed, parsedAgain);
    }

    @Test
    public void testWidget() {
        String source = loadResource("widget.json");
        String parsed = Json.parse(source).format(JsonFormatter.MINIMIZED);
        String parsedAgain = Json.parse(parsed).format(JsonFormatter.MINIMIZED);
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
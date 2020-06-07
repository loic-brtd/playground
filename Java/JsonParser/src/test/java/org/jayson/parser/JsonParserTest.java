package org.jayson.parser;

import org.jayson.dto.Json;
import org.jayson.dto.JsonBoolean;
import org.jayson.dto.JsonObject;
import org.jayson.dto.JsonString;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.jayson.parser.JsonParser.*;
import static org.junit.jupiter.api.Assertions.*;

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


}
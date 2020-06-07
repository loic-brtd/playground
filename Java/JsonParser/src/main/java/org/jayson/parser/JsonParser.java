package org.jayson.parser;

import org.jayson.dto.*;
import org.jayson.parser.Token.Type;

import static org.jayson.parser.Token.Type.*;

public class JsonParser {

    private final JsonLexer lexer;
    private Token token;

    public JsonParser(JsonLexer lexer) {
        this.lexer = lexer;
    }

    public JsonObject parse() {
        token = lexer.nextToken();
        JsonObject object = parseObject();
        assertNull(token);
        return object;
    }

    private JsonObject parseObject() {
        JsonObject object = Json.object();
        assertType(OPENING_CURLY, token);
        token = lexer.nextToken();
        while (token != null && token.type != CLOSING_CURLY) {
            String key = parseString().getValue();
            assertType(COLON, token);
            token = lexer.nextToken();
            JsonElement value = parseElement();
            object.put(key, value);
            if (token != null && token.type == COMMA) {
                token = lexer.nextToken();
                assertType(STRING, token);
            }
        }
        assertType(CLOSING_CURLY, token);
        token = lexer.nextToken();
        return object;
    }

    private JsonElement parseElement() {
        if (token == null)
            throw new UnexpectedTokenException("Expected a json element but reached the end");

        switch (token.type) {
            case STRING:
                return parseString();
            case BOOLEAN:
                return parseBoolean();
            case NUMBER:
                return parseNumber();
            case OPENING_BRACKET:
                return parseArray();
            case OPENING_CURLY:
                return parseObject();
            default:
                throw new UnexpectedTokenException("Expected a json element but got <" + token.value + ">");
        }
    }

    private JsonArray parseArray() {
        return new JsonArray();
    }

    private JsonBoolean parseBoolean() {
        assertType(BOOLEAN, token);
        boolean value = Boolean.parseBoolean(token.value);
        token = lexer.nextToken();
        return new JsonBoolean(value);
    }

    private JsonNumber parseNumber() {
        assertType(NUMBER, token);
        if (token.value.contains(".")) {
            double value = Double.parseDouble(token.value);
            token = lexer.nextToken();
            return new JsonNumber(value);
        } else {
            long value = Long.parseLong(token.value);
            token = lexer.nextToken();
            return new JsonNumber(value);
        }
    }

    private JsonString parseString() {
        assertType(STRING, token);
        String value = token.value.substring(1, token.value.length() - 1);
        token = lexer.nextToken();
        return new JsonString(value);
    }

    private void assertType(Type expected, Token actual) {
        if (actual == null || actual.type != expected)
            throw new UnexpectedTokenException("Expected a " + expected.name().toLowerCase() +
                    " but " + (actual == null ? "reached the end" : " got <" + actual.value + ">"));
    }

    private void assertNull(Token actual) {
        if (actual != null)
            throw new UnexpectedTokenException("Unexpected token <" + actual.value + '>');
    }

    public static class UnexpectedTokenException extends RuntimeException {
        public UnexpectedTokenException(String message) {
            super(message);
        }
    }
}

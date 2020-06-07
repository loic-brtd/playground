package org.jayson.parser;

import org.jayson.dto.Json;
import org.jayson.dto.JsonObject;

public class JsonParser {

    private JsonLexer lexer;

    public JsonParser(JsonLexer lexer) {
        this.lexer = lexer;
    }

    public JsonObject parse() {
        JsonObject result = Json.object();
        String token = lexer.nextTokenAsString();
        assertToken("{", token);
        token = lexer.nextTokenAsString();
        while (!"}".equals(token)) {
            assertIsString(token);
            String key = stripQuotes(token);
            token = lexer.nextTokenAsString();
            assertToken(":", token);
            token = lexer.nextTokenAsString();
            assertIsString(token);
            String value = stripQuotes(token);
            result.put(key, value);
            token = lexer.nextTokenAsString();
            if (",".equals(token)) {
                token = lexer.nextTokenAsString();
                assertIsString(token);
            }
        }
        token = lexer.nextTokenAsString();
        assertToken(null, token);
        return result;
    }

    private String stripQuotes(String string) {
        return string.substring(1, string.length() - 1);
    }

    private void assertIsString(String token) {
        if (token == null) {
            throw new UnexpectedEndException("Expected a string but reached the end");
        } else if (!token.startsWith("\"")) {
            throw new UnexpectedEndException("Expected a string but got <" + token + ">");
        }
    }

    private void assertToken(String expected, String actual) {
        if (expected == null) {
            if (actual != null) {
                throw new UnexpectedTokenException("Unexpected token <" + actual + '>');
            }
        } else if (actual == null) {
            throw new UnexpectedEndException("Expected token <" + expected + "> but reached the end");
        } else if (!expected.equals(actual)) {
            throw new UnexpectedTokenException("Expected token <" + expected + "> but was <" + actual + '>');
        }
    }

    public static class UnexpectedTokenException extends RuntimeException {
        public UnexpectedTokenException(String message) {
            super(message);
        }
    }

    public static class UnexpectedEndException extends RuntimeException {
        public UnexpectedEndException(String message) {
            super(message);
        }
    }
}

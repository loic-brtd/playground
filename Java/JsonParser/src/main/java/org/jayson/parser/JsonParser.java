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
        while (!hasType(CLOSING_CURLY, token)) {
            String key = parseString().getValue();
            assertType(COLON, token);
            token = lexer.nextToken();
            JsonElement value = parseElement();
            object.put(key, value);
            if (hasType(COMMA, token)) {
                token = lexer.nextToken();
                assertType(STRING, token);
            }
        }
        token = lexer.nextToken();
        return object;
    }

    private JsonElement parseElement() {
        if (hasType(STRING, token)) {
            return parseString();
        } else if (hasType(BOOLEAN, token)) {
            return parseBoolean();
        } else if (hasType(OPENING_CURLY, token)) {
            return parseObject();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private JsonBoolean parseBoolean() {
        assertType(BOOLEAN, token);
        boolean value = Boolean.parseBoolean(token.value);
        token = lexer.nextToken();
        return new JsonBoolean(value);
    }

    private JsonString parseString() {
        assertType(STRING, token);
        String value = token.value.substring(1, token.value.length() - 1);
        token = lexer.nextToken();
        return new JsonString(value);
    }

    private boolean hasType(Type expected, Token actual) {
        return actual != null && actual.type == expected;
    }

    private void assertType(Type expected, Token actual) {
        if (!hasType(expected, actual)) {
            throw new UnexpectedTokenException("Expected a " + expected.name().toLowerCase() +
                    " but got <" + actual.value + '>');
        }
    }

    private void assertNull(Token actual) {
        if (actual != null) {
            throw new UnexpectedTokenException("Unexpected token <" + actual.value + '>');
        }
    }

    public static class UnexpectedTokenException extends RuntimeException {
        public UnexpectedTokenException(String message) {
            super(message);
        }
    }

}

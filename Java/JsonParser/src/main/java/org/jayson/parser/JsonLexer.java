package org.jayson.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.jayson.parser.Token.Type.NUMBER;
import static org.jayson.parser.Token.Type.STRING;

public class JsonLexer {

    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?\\d+)?");

    private final CharIterator iterator;
    private Character curr;

    public JsonLexer(String source) {
        iterator = new CharIterator(source);
        curr = nextChar();
    }

    public boolean hasNext() {
        curr = skipAnyWhitespace();
        return curr != null;
    }

    public Token nextToken() {
        if (!hasNext())
            return null;

        switch (curr) {
            case '{':
                return parseSingle(Token.OPENING_CURLY);
            case '}':
                return parseSingle(Token.CLOSING_CURLY);
            case '[':
                return parseSingle(Token.OPENING_BRACKET);
            case ']':
                return parseSingle(Token.CLOSING_BRACKET);
            case ':':
                return parseSingle(Token.COLON);
            case ',':
                return parseSingle(Token.COMMA);
            case 't':
                return parseWord("true", Token.TRUE);
            case 'f':
                return parseWord("false", Token.FALSE);
            case 'n':
                return parseWord("null", Token.NULL);
            case '"':
                return parseString();
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return parseNumber();
            default:
                throwUnexpectedChar("Unexpected character '%s'", curr);
                return null;
        }
    }

    private Token parseWord(String expected, Token token) {
        for (char letter : expected.toCharArray()) {
            assertChar(letter, curr);
            curr = nextChar();
        }
        return token;
    }

    private Token parseString() {
        StringBuilder token = new StringBuilder();
        assertChar('"', curr);
        do {
            token.append(curr);
            curr = nextChar();
        } while (curr != null && curr != '"');
        assertChar('"', curr);
        token.append(curr);
        curr = nextChar();
        return new Token(token.toString(), STRING);
    }

    private Token parseNumber() {
        StringBuilder sb = new StringBuilder();
        while (isAnyChar("0123456789-+.eE", curr)) {
            sb.append(curr);
            curr = nextChar();
        }
        String token = sb.toString();
        if (!isValidNumber(token)) {
            curr = null;
            throwUnexpectedChar("Invalid number '%s'", token);
        }
        return new Token(token, NUMBER);
    }

    private Token parseSingle(Token constant) {
        curr = nextChar();
        return constant;
    }

    private void assertChar(Character expected, Character actual) {
        if (actual == null)
            throwEndOfSource("Expected '%c' but reached the end");
        if (actual != expected)
            throwUnexpectedChar("Expected '%c' but got '%c'", expected, actual);
    }

    private Character skipAnyWhitespace() {
        if (curr == null || isNonWhite(curr))
            return curr;
        while (iterator.hasNext()) {
            char ch = iterator.next();
            if (isNonWhite(ch))
                return ch;
        }
        return null;
    }

    private Character nextChar() {
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

    private boolean isNonWhite(char ch) {
        return ch != 0x0020 && ch != 0x000A && ch != 0x000D && ch != 0x0009;
    }

    private boolean isAnyChar(String s, Character actual) {
        return actual != null && s.indexOf(actual) != -1;
    }

    private boolean isValidNumber(String token) {
        return NUMBER_PATTERN.matcher(token).matches();
    }

    public String[] consumeStrings() {
        List<String> list = new ArrayList<>();
        while (hasNext()) {
            list.add(nextString());
        }
        return list.toArray(new String[0]);
    }

    public Token[] consumeTokens() {
        List<Token> list = new ArrayList<>();
        while (hasNext()) {
            list.add(nextToken());
        }
        return list.toArray(new Token[0]);
    }

    public String nextString() {
        Token token = nextToken();
        return token == null ? null : token.value;
    }

    private void throwUnexpectedChar(String message, Object... objects) {
        curr = null;
        message = String.format(message, objects);
        message = String.format("(%d:%d) %s", iterator.getLine(), iterator.getColumn(), message);
        throw new UnexpectedCharacterException(message);
    }

    private void throwEndOfSource(String message) {
        curr = null;
        throw new EndOfSourceException(message);
    }

    public static class EndOfSourceException extends RuntimeException {
        private EndOfSourceException(String message) {
            super(message);
        }
    }

    public static class UnexpectedCharacterException extends RuntimeException {
        private UnexpectedCharacterException(String message) {
            super(message);
        }
    }
}

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
                return parse(Token.OPENING_CURLY);
            case '}':
                return parse(Token.CLOSING_CURLY);
            case '[':
                return parse(Token.OPENING_BRACKET);
            case ']':
                return parse(Token.CLOSING_BRACKET);
            case ':':
                return parse(Token.COLON);
            case ',':
                return parse(Token.COMMA);
            case '"':
                return parseString();
            case 't':
            case 'f':
                return parseBoolean();
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
                char temp = curr;
                curr = null;
                throwUnexpectedChar("Unexpected character '%s'", temp);
                return null;
        }
    }

    private Token parseBoolean() {
        return curr == 't'
                ? parseWord("true", Token.TRUE)
                : parseWord("false", Token.FALSE);
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

    private Token parse(Token constant) {
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
        message = String.format(message, objects);
        message = String.format("(%d:%d) %s", iterator.getLine(), iterator.getColumn(), message);
        throw new UnexpectedCharacterException(message);
    }

    private void throwEndOfSource(String message) {
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

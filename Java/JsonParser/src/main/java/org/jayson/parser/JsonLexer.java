package org.jayson.parser;

import java.util.ArrayList;
import java.util.List;

public class JsonLexer {

    private final CharIterator iterator;
    private final String source;
    private boolean consumed;
    private Character c;

    public JsonLexer(String source) {
        this.source = source;
        iterator = new CharIterator(source);
        c = nextNonWhiteCharOrNull();
        consumed = c == null;
    }

    public String nextTokenAsString() {
        Token token = nextToken();
        return token == null ? null : token.value;
    }

    public Token nextToken() {
        if (c == null)
            return null;

        switch (c) {
            case '{':
                c = nextNonWhiteCharOrNull();
                return Token.OPENING_CURLY;
            case '}':
                c = nextNonWhiteCharOrNull();
                return Token.CLOSING_CURLY;
            case '[':
                c = nextNonWhiteCharOrNull();
                return Token.OPENING_BRACKET;
            case ']':
                c = nextNonWhiteCharOrNull();
                return Token.CLOSING_BRACKET;
            case ':':
                c = nextNonWhiteCharOrNull();
                return Token.COLON;
            case ',':
                c = nextNonWhiteCharOrNull();
                return Token.COMMA;
            case '"':
                return parseString();
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
            case 't':
            case 'f':
                return parseBoolean();
            default:
                unexpectedChar(c);
                break;
        }

        return null;
    }

    private Token parseBoolean() {
        if (c == 't') {
            return parseToken("true", Token.Type.BOOLEAN);
        } else {
            return parseToken("false", Token.Type.BOOLEAN);
        }
    }

    private Token parseString() {
        StringBuilder token = new StringBuilder();
        if (c != '"') {
            unexpectedChar(c);
        }
        token.append(c);
        c = nextCharOrThrow();
        while (c != '"') {
            token.append(c);
            c = nextCharOrThrow();
        }
        token.append(c);
        c = nextNonWhiteCharOrNull();
        return new Token(token.toString(), Token.Type.STRING);
    }

    private Token parseNumber() {
        StringBuilder token = new StringBuilder();
        token.append(parseInteger());
        if (c != null && c == '.') {
            token.append(c);
            c = nextCharOrThrow();
            if (!isIn("0123456789")) {
                unexpectedChar(c);
            }
            token.append(parseDigits());
        }
        return new Token(token.toString(), Token.Type.NUMBER);
    }

    private String parseInteger() {
        StringBuilder token = new StringBuilder();
        if (!isIn("123456789")) {
            unexpectedChar(c);
        }
        token.append(c);
        c = nextCharOrNull();
        token.append(parseDigits());
        return token.toString();
    }

    private String parseDigits() {
        StringBuilder tokens = new StringBuilder();
        while (c != null && isIn("0123456789")) {
            tokens.append(c);
            c = nextCharOrNull();
        }
        if (c != null && isWhitespace(c)) {
            c = nextNonWhiteCharOrNull();
        }
        return tokens.toString();
    }

    private Token parseToken(String expected, Token.Type type) {
        for (char letter : expected.toCharArray()) {
            if (c != letter)
                unexpectedChar(c);
            c = nextCharOrThrow();
        }
        return new Token(expected, type);
    }

    private char nextCharOrThrow() {
        if (!iterator.hasNext()) {
            endOfSource();
        }
        return iterator.next();
    }

    private Character nextCharOrNull() {
        if (!iterator.hasNext()) {
            return null;
        }
        return iterator.next();
    }

    private Character nextNonWhiteCharOrNull() {
        char c;
        do {
            if (!iterator.hasNext()) {
                consumed = true;
                return null;
            }
            c = iterator.next();
        } while (isWhitespace(c));
        return c;
    }

    private boolean isWhitespace(char ch) {
        return ch == 0x0020 || ch == 0x000A || ch == 0x000D || ch == 0x0009;
    }

    private boolean isIn(String s) {
        return s.indexOf(c) != -1;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public String getSource() {
        return source;
    }

    public String[] consumeTokensAsStrings() {
        List<String> list = new ArrayList<>();
        String current;
        while ((current = nextTokenAsString()) != null) {
            list.add(current);
        }
        consumed = true;
        return list.toArray(new String[0]);
    }

    public Token[] consumeTokens() {
        List<Token> list = new ArrayList<>();
        Token current;
        while ((current = nextToken()) != null) {
            list.add(current);
        }
        consumed = true;
        return list.toArray(new Token[0]);
    }

    private void unexpectedChar(char c) {
        consumed = true;
        throw new UnexpectedCharacterException(c);
    }

    private void endOfSource() {
        consumed = true;
        throw new EndOfSourceException();
    }

    static class EndOfSourceException extends RuntimeException {
        private EndOfSourceException() {
            super("Source ended prematurely");
        }
    }

    static class UnexpectedCharacterException extends RuntimeException {
        private UnexpectedCharacterException(char c) {
            super("Encountered unexpected character '" + c + "'");
        }
    }
}

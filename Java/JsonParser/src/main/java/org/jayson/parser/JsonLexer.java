package org.jayson.parser;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;

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

    public String nextString() {
        Token token = nextToken();
        return token == null ? null : token.value;
    }

    public Token nextToken() {
        if (consumed || c == null)
            return null;

        switch (c) {
            case '{':
                return parseChar(Token.OPENING_CURLY);
            case '}':
                return parseChar(Token.CLOSING_CURLY);
            case '[':
                return parseChar(Token.OPENING_BRACKET);
            case ']':
                return parseChar(Token.CLOSING_BRACKET);
            case ':':
                return parseChar(Token.COLON);
            case ',':
                return parseChar(Token.COMMA);
            case '"':
                return parseString();
            case 't':
            case 'f':
                return parseBoolean();
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
                unexpectedChar(c);
                break;
        }

        throw new IllegalStateException();
    }

    private Token parseBoolean() {
        Token token;
        if (c == 't') {
            token = parseWord("true", Token.Type.BOOLEAN);
        } else {
            token = parseWord("false", Token.Type.BOOLEAN);
        }
        return token;
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
        skipWhitespaceIfPresent();
        return tokens.toString();
    }

    private void skipWhitespaceIfPresent() {
        if (c != null && isWhitespace(c)) {
            c = nextNonWhiteCharOrNull();
        }
    }

    private Token parseChar(Token constant) {
        c = nextNonWhiteCharOrNull();
        if (c == null)
            consumed = true;
        return constant;
    }

    private Token parseWord(String expected, Token.Type type) {
        char[] word = expected.toCharArray();
        int i;
        for (i = 0; i < word.length - 1; i++) {
            if (c != word[i]) unexpectedChar(c);
            c = nextCharOrThrow();
        }
        if (c != word[i]) unexpectedChar(c);
        c = nextNonWhiteCharOrNull();
        return new Token(expected, type);
    }

    private char nextCharOrThrow() {
        if (!iterator.hasNext()) {
            consumed = true;
            endOfSource();
        }
        return iterator.next();
    }

    private Character nextCharOrNull() {
        if (!iterator.hasNext()) {
            consumed = true;
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

    public boolean hasNext() {
        return !consumed;
    }

    public String getSource() {
        return source;
    }

    public String[] consumeStrings() {
        List<String> list = new ArrayList<>();
        while (!consumed) {
            list.add(nextString());
        }
        return list.toArray(new String[0]);
    }

    public Token[] consumeTokens() {
        List<Token> list = new ArrayList<>();
        while (!consumed) {
            list.add(nextToken());
        }
        return list.toArray(new Token[0]);
    }

    private void unexpectedChar(char c) {
        consumed = true;
        throw new UnexpectedCharacterException(c, iterator.getLine(), iterator.getColumn());
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
            super(format("Encountered unexpected character '%s'", c));
        }

        private UnexpectedCharacterException(char c, int line, int column) {
            super(format("Encountered unexpected character '%s' (%d:%d)", c, line, column));
        }
    }
}

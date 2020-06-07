package org.jayson.parser;

import java.util.Objects;

public class Token {

    public static final Token OPENING_BRACKET = new Token("[", Type.OPENING_BRACKET);
    public static final Token CLOSING_BRACKET = new Token("]", Type.CLOSING_BRACKET);
    public static final Token OPENING_CURLY = new Token("{", Type.OPENING_CURLY);
    public static final Token CLOSING_CURLY = new Token("}", Type.CLOSING_CURLY);
    public static final Token COMMA = new Token(",", Type.COMMA);
    public static final Token COLON = new Token(":", Type.COLON);
    public static final Token TRUE = new Token("true", Type.BOOLEAN);
    public static final Token FALSE = new Token("false", Type.BOOLEAN);

    public final String value;
    public final Type type;

    public Token(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    enum Type {
        STRING, BOOLEAN, NUMBER,
        COMMA, COLON, TRUE, FALSE,
        OPENING_BRACKET, CLOSING_BRACKET, OPENING_CURLY, CLOSING_CURLY
    }

    @Override
    public String toString() {
        return "Token{" +
                "value=<" + value + '>' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(value, token.value) &&
                type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}

package org.jayson.parser;

public class StringEscape {

    public static String unescape(String escaped) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < escaped.length() - 1; i++) {
            result.append((escaped.charAt(i) == '\\')
                    ? toSpecial(escaped.charAt(++i))
                    : escaped.charAt(i));
        }
        return result.toString();
    }

    public static String escape(String unescaped) {
        StringBuilder result = new StringBuilder();
        result.append('"');
        for (int i = 0; i < unescaped.length(); i++) {
            char ch = unescaped.charAt(i);
            if (isSpecial(ch)) {
                result.append("\\").append(fromSpecial(ch));
            } else {
                result.append(ch);
            }
        }
        return result.append('"').toString();
    }

    private static boolean isSpecial(char c) {
        return "\"\\/\b\f\n\r\t".indexOf(c) != -1;
    }

    private static char toSpecial(char c) {
        switch (c) {
            case '"':
            case '\\':
            case '/':
                return c;
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            default:
                throw new IllegalArgumentException(String.valueOf(c));
        }
    }


    private static char fromSpecial(char c) {
        switch (c) {
            case '"':
            case '\\':
            case '/':
                return c;
            case '\b':
                return 'b';
            case '\f':
                return 'f';
            case '\n':
                return 'n';
            case '\r':
                return 'r';
            case '\t':
                return 't';
            default:
                throw new IllegalArgumentException(String.valueOf(c));
        }
    }
}

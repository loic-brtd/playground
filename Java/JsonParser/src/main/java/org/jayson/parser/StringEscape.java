package org.jayson.parser;

public class StringEscape {

    public static String unescape(String escaped) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < escaped.length() - 1; i++) {
            if (escaped.charAt(i) == '\\') {
                i++;
                if (escaped.charAt(i) == 'u') {
                    char[] hex = new char[4];
                    for (int j = 0; j < 4; j++) {
                        hex[j] = escaped.charAt(++i);
                    }
                    result.append(hexToAscii(hex));
                } else {
                    result.append(charToSpecial(escaped.charAt(i)));
                }
            } else {
                result.append(escaped.charAt(i));
            }
        }
        return result.toString();
    }

    private static char hexToAscii(char[] hex) {
        return (char) Integer.parseInt(new String(hex), 16);
    }

    public static String escape(String unescaped) {
        StringBuilder result = new StringBuilder();
        result.append('"');
        for (int i = 0; i < unescaped.length(); i++) {
            char ch = unescaped.charAt(i);
            if (isSpecial(ch)) {
                result.append('\\').append(specialToChar(ch));
            } else {
                result.append(ch);
            }
        }
        return result.append('"').toString();
    }

    private static boolean isSpecial(char c) {
        return "\"\\/\b\f\n\r\t".indexOf(c) != -1;
    }

    private static char charToSpecial(char c) {
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


    private static char specialToChar(char c) {
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

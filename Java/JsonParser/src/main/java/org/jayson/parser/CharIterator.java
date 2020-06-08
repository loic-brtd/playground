package org.jayson.parser;

import java.util.Objects;

public class CharIterator {

    private final String source;
    private int index;
    private int line;
    private int column;

    public CharIterator(String source) {
        Objects.requireNonNull(source);
        this.source = source;
        this.index = -1;
        this.line = 1;
        this.column = 0;
    }

    public boolean hasNext() {
        return index + 1 < source.length();
    }

    public Character next() {
        if (hasNext()) {
            updateLineAndColumn();
            index++;
            return source.charAt(index);
        } else {
            return null;
        }
    }

    private void updateLineAndColumn() {
        if (index > 0 && source.charAt(index) == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
    }

    public String getSource() {
        return source;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}

package org.jayson.parser;

import java.util.NoSuchElementException;
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

    public char next() {
        if (hasNext()) {
            updateLineAndColumn();
            index++;
        } else {
            throw new NoSuchElementException();
        }
        return source.charAt(index);
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

    public boolean canPeek(int offset) {
        int peekIndex = index + offset;
        return peekIndex >= 0 && peekIndex < source.length();
    }

    public char peek(int offset) {
        int peekIndex = index + offset;
        if (!canPeek(offset)) {
            throw new NoSuchElementException();
        }
        return source.charAt(peekIndex);
    }

}

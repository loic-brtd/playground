package org.jayson.parser;

import java.util.NoSuchElementException;
import java.util.Objects;

public class CharIterator {

    private final String source;
    private int index;

    public CharIterator(String source) {
        Objects.requireNonNull(source);
        this.source = source;
        this.index = -1;
    }

    public boolean hasNext() {
        return index + 1 < source.length();
    }

    public char next() {
        if (hasNext()) {
            index++;
        } else {
            throw new NoSuchElementException();
        }
        return source.charAt(index);
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

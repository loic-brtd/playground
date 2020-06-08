package org.jayson.parser;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CharIteratorTest {

    @Test
    public void testNext() {
        CharIterator iterator = new CharIterator("abc");
        assertTrue(iterator.hasNext());
        assertEquals('a', iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals('b', iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals('c', iterator.next());
        assertFalse(iterator.hasNext());
        assertNull(iterator.next());
    }

    @Test
    public void testEmptySource() {
        CharIterator iterator = new CharIterator("");
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testNullSource() {
        assertThrows(NullPointerException.class, () -> new CharIterator(null));
    }
}

package org.jayson.parser;

import org.jayson.parser.CharIterator;
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
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testPeek() {
        CharIterator iterator = new CharIterator("abc");
        assertFalse(iterator.canPeek(0));
        assertTrue(iterator.canPeek(1));
        assertTrue(iterator.canPeek(2));
        assertTrue(iterator.canPeek(3));
        assertFalse(iterator.canPeek(4));
        assertEquals('a', iterator.peek(1));
        assertEquals('b', iterator.peek(2));
        assertEquals('c', iterator.peek(3));
        iterator.next();
        iterator.next();
        assertEquals('a', iterator.peek(-1));
        assertEquals('b', iterator.peek(0));
        assertEquals('c', iterator.peek(1));
        assertThrows(NoSuchElementException.class, () -> iterator.peek(2));
        assertThrows(NoSuchElementException.class, () -> iterator.peek(-2));
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

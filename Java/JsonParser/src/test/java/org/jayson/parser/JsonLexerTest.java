package org.jayson.parser;

import org.junit.jupiter.api.Test;

import static org.jayson.parser.JsonLexer.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonLexerTest {

    @Test
    public void testEmptyObjectSpaced() {
        JsonLexer lexer = new JsonLexer("  {  } ");
        assertEquals("{", lexer.nextString());
        assertEquals("}", lexer.nextString());
        assertConsumed(lexer);
    }

    @Test
    public void testEmptyObjectNoSpace() {
        JsonLexer lexer = new JsonLexer("{}");
        assertEquals("{", lexer.nextString());
        assertEquals("}", lexer.nextString());
        assertConsumed(lexer);
    }

    @Test
    public void testSimpleObjectNoSpace() {
        JsonLexer lexer = new JsonLexer("{\"key\":\"value\"}");
        assertEquals("{", lexer.nextString());
        assertEquals("\"key\"", lexer.nextString());
        assertEquals(":", lexer.nextString());
        assertEquals("\"value\"", lexer.nextString());
        assertEquals("}", lexer.nextString());
        assertConsumed(lexer);
    }

    @Test
    public void testSimpleObjectSpaced() {
        JsonLexer lexer = new JsonLexer(" \t   {  \"key\"  \t : \"value\"   \t }   ");
        assertEquals("{", lexer.nextString());
        assertEquals("\"key\"", lexer.nextString());
        assertEquals(":", lexer.nextString());
        assertEquals("\"value\"", lexer.nextString());
        assertEquals("}", lexer.nextString());
        assertConsumed(lexer);
    }

    @Test
    public void testConsumeToArray() {
        JsonLexer lexer = new JsonLexer("{\"key\":\"value\"}");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"{", "\"key\"", ":", "\"value\"", "}"};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testStringWithSpaces() {
        JsonLexer lexer = new JsonLexer("\" string with\tspaces \"");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"\" string with\tspaces \""};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testStringWithoutEndingQuote() {
        JsonLexer lexer = new JsonLexer("\"string");
        assertThrows(EndOfSourceException.class, lexer::consumeStrings);
        assertConsumed(lexer);
    }

    @Test
    public void testStringWithoutQuotes() {
        JsonLexer lexer = new JsonLexer("string");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeStrings);
        assertConsumed(lexer);
    }

    @Test
    public void testMultipleStrings() {
        JsonLexer lexer = new JsonLexer(" \"123\"  \"hello\"  \"hi\" ");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"\"123\"", "\"hello\"", "\"hi\""};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testInteger() {
        JsonLexer lexer = new JsonLexer("  125  ");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"125"};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testMultipleIntegers() {
        JsonLexer lexer = new JsonLexer("  12 5  ");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"12", "5"};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testIntegerLeadingZero() {
        JsonLexer lexer = new JsonLexer("  0125  ");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeStrings);
        assertConsumed(lexer);
    }

    @Test
    public void testFloat() {
        JsonLexer lexer = new JsonLexer("  12.5  ");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"12.5"};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testMultipleFloats() {
        JsonLexer lexer = new JsonLexer("  12.5 3.14  ");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"12.5", "3.14"};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testFloatLeadingZero() {
        JsonLexer lexer = new JsonLexer("  012.5  ");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeStrings);
        assertConsumed(lexer);
    }

    @Test
    public void testFloatLeadingDecimalZero() {
        JsonLexer lexer = new JsonLexer("  12.005  ");
        String[] tokens = lexer.consumeStrings();
        String[] expected = {"12.005"};
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    @Test
    public void testFloatNothingAfterPoint() {
        JsonLexer lexer = new JsonLexer("  12.  ");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeStrings);
        assertConsumed(lexer);
    }

    @Test
    public void testMultipleBooleans() {
        JsonLexer lexer = new JsonLexer("  true false  true");
        assertEquals("true", lexer.nextString());
        assertEquals("false", lexer.nextString());
        assertEquals("true", lexer.nextString());
        assertConsumed(lexer);
    }

    @Test
    public void testMultipleBooleansTuckTogether() {
        JsonLexer lexer = new JsonLexer("  truefalsetrue");
        assertEquals("true", lexer.nextString());
        assertEquals("false", lexer.nextString());
        assertEquals("true", lexer.nextString());
        assertConsumed(lexer);
    }

    @Test
    public void testBigExample() {
        JsonLexer lexer = new JsonLexer(" {\n" +
                "  \"integer\": 123,\n" +
                "  \"float\": 12.3,\n" +
                "  \"array\": [12, 12.3, 8],\n" +
                "  \"object\": {},\n" +
                "  \"b_true\": true,\n" +
                "  \"b_false\": false\n" +
                "}\n");
        String[] tokens = lexer.consumeStrings();
        String[] expected = ("{ 'integer' : 123 , 'float' : 12.3 , 'array' : [ 12 , 12.3 , 8 ] , " +
                "'object' : { } , 'b_true' : true , 'b_false' : false }")
                .replaceAll("'", "\"").split(" ");
        assertArrayEquals(expected, tokens);
        assertConsumed(lexer);
    }

    private void assertConsumed(JsonLexer lexer) {
        assertNull(lexer.nextToken());
        assertTrue(lexer.isConsumed());
    }

}
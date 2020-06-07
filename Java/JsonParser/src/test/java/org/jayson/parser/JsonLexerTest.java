package org.jayson.parser;

import org.junit.jupiter.api.Test;

import static org.jayson.parser.JsonLexer.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonLexerTest {

    @Test
    public void testEmptyObjectSpaced() {
        JsonLexer lexer = new JsonLexer("  {  } ");
        assertEquals("{", lexer.nextTokenAsString());
        assertEquals("}", lexer.nextTokenAsString());
    }

    @Test
    public void testEmptyObjectNoSpace() {
        JsonLexer lexer = new JsonLexer("{}");
        assertEquals("{", lexer.nextTokenAsString());
        assertEquals("}", lexer.nextTokenAsString());
    }

    @Test
    public void testSimpleObjectNoSpace() {
        JsonLexer lexer = new JsonLexer("{\"key\":\"value\"}");
        assertEquals("{", lexer.nextTokenAsString());
        assertEquals("\"key\"", lexer.nextTokenAsString());
        assertEquals(":", lexer.nextTokenAsString());
        assertEquals("\"value\"", lexer.nextTokenAsString());
        assertEquals("}", lexer.nextTokenAsString());
    }

    @Test
    public void testSimpleObjectSpaced() {
        JsonLexer lexer = new JsonLexer(" \t   {  \"key\"  \t : \"value\"   \t }   ");
        assertEquals("{", lexer.nextTokenAsString());
        assertEquals("\"key\"", lexer.nextTokenAsString());
        assertEquals(":", lexer.nextTokenAsString());
        assertEquals("\"value\"", lexer.nextTokenAsString());
        assertEquals("}", lexer.nextTokenAsString());
    }

    @Test
    public void testConsumeToArray() {
        JsonLexer lexer = new JsonLexer("{\"key\":\"value\"}");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"{", "\"key\"", ":", "\"value\"", "}"};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testStringWithSpaces() {
        JsonLexer lexer = new JsonLexer("\" string with\tspaces \"");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"\" string with\tspaces \""};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testStringWithoutEndingQuote() {
        JsonLexer lexer = new JsonLexer("\"string");
        assertThrows(EndOfSourceException.class, lexer::consumeTokensAsStrings);
    }

    @Test
    public void testStringWithoutQuotes() {
        JsonLexer lexer = new JsonLexer("string");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeTokensAsStrings);
    }

    @Test
    public void testMultipleStrings() {
        JsonLexer lexer = new JsonLexer(" \"123\"  \"hello\"  \"hi\" ");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"\"123\"", "\"hello\"", "\"hi\""};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testInteger() {
        JsonLexer lexer = new JsonLexer("  125  ");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"125"};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testMultipleIntegers() {
        JsonLexer lexer = new JsonLexer("  12 5  ");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"12", "5"};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testIntegerLeadingZero() {
        JsonLexer lexer = new JsonLexer("  0125  ");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeTokensAsStrings);
    }

    @Test
    public void testFloat() {
        JsonLexer lexer = new JsonLexer("  12.5  ");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"12.5"};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testMultipleFloats() {
        JsonLexer lexer = new JsonLexer("  12.5 3.14  ");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"12.5", "3.14"};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testFloatLeadingZero() {
        JsonLexer lexer = new JsonLexer("  012.5  ");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeTokensAsStrings);
    }

    @Test
    public void testFloatLeadingDecimalZero() {
        JsonLexer lexer = new JsonLexer("  12.005  ");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = {"12.005"};
        assertArrayEquals(expected, tokens);
    }

    @Test
    public void testFloatNothingAfterPoint() {
        JsonLexer lexer = new JsonLexer("  12.  ");
        assertThrows(UnexpectedCharacterException.class, lexer::consumeTokensAsStrings);
    }

    @Test
    public void testBigExample() {
        JsonLexer lexer = new JsonLexer(" {" +
                "\"integer\": 123," +
                "\"float\": 12.3," +
                "\"array\": [12, 12.3, 8]," +
                "\"object\": {}," +
                "\"b_true\": true," +
                "\"b_false\": false" +
                "} ");
        String[] tokens = lexer.consumeTokensAsStrings();
        String[] expected = ("{ 'integer' : 123 , 'float' : 12.3 , 'array' : [ 12 , 12.3 , 8 ] , " +
                "'object' : { } , 'b_true' : true , 'b_false' : false }")
                .replaceAll("'", "\"").split(" ");
        assertArrayEquals(expected, tokens);
    }

}
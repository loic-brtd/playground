package org.jayson.parser;

import org.jayson.dto.Json;

public class Test {

    public static void main(String[] args) {

        // System.out.println(Arrays.toString(new JsonLexer("        {     \"hello\"  \"  :    \"world\"   }     ")
        //         .consumeToArray()));

        System.out.println(Json.parse("        {     \"hello\"   :    \"world\"   }     "));
        System.out.println(Json.parse("{}"));
        System.out.println(Json.parse("{\"first\":\"loic\",\"last\":\"bertrand\",\"boolean\":true}"));
    }

}

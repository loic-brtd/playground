package data_processing;

import java.io.FileWriter;
import java.io.IOException;

import core.PApplet;

public class TokensProcessing extends PApplet {

    public void setup() {
        noCanvas();
        noLoop();

        String[] lines = loadStrings("res/tokens");
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split("_");
            for (int j = 0; j < parts.length; j++) {
                parts[j] = parts[j].charAt(0) + parts[j].substring(1).toLowerCase();
            }
            lines[i] = join(parts, "") + "AST";
            println(lines[i]);
        }
        
        try {
            FileWriter w = new FileWriter("res/tokens_camel");
            for (String s : lines) {
                w.append(s);
                w.append("\n");
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        PApplet.run();
    }

}

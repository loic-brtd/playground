package tests;

import java.util.ArrayList;
import java.util.HashMap;

import core.PApplet;
import core.PVector;

public class Words extends PApplet {

    ArrayList<Word> words;
    float maxFreq = 0;
    float angle = 0;
    float radius = 40;
    float depth = 70;
    float step = 0.01f;
    float px, py;

    public void setup() {
        fullScreen();
        init();
    }

    public void init() {
        String[] lines = loadStrings("res/bad_romance.txt");
        String text = join(lines, " ");
        String[] tokens = text.split("[ ,-.;:!?()]");
        HashMap<String, Integer> dict = new HashMap<>();
        int count = 0;
        for (String w : tokens) {
            if (!w.isEmpty()) {
                dict.putIfAbsent(w, 0);
                dict.put(w, dict.get(w) + 1);
                count++;
            }
        }
        words = new ArrayList<>();
        for (String w : dict.keySet()) {
            int val = dict.get(w);
            float freq = (float) val / (float) count;
            words.add(new Word(w, freq));
            maxFreq = max(maxFreq, freq);
        }
        words.sort((a, b) -> a.frequency - b.frequency < 0 ? -1 : 1);
    }

    @Override
    protected void keyPressed() {
        if (key == ' ') {
            init();
        }
    }

    public void draw() {
        background(0);
        float newX = mouseX; // radius * cos(angle) + width / 2;
        float newY = mouseY; // radius * sin(angle) + height / 2;
        float dx = lerp(px, newX, 0.1f);
        float dy = lerp(py, newY, 0.1f);
        for (Word w : words) {
            w.show(maxFreq, dx, dy);
        }
        angle += step;
        px = dx;
        py = dy;
    }

    class Word {
        String value;
        float frequency;
        PVector position;

        Word(String val, float freq) {
            value = val;
            position = new PVector(random(width), random(height));
            frequency = freq;
        }

        void show(float maxFreq, float mx, float my) {
            float ts = map(frequency, 0, maxFreq, 0, height / 5);
            float fl = map(frequency, 0, maxFreq, 50, 255);
            float dx = (mx - width / 2) * frequency * depth;
            float dy = (my - height / 2) * frequency * depth;
            float x = position.x + dx;
            float y = position.y + dy;
            textSize(ts);
            fill(fl);
            noStroke();
            text(value, x, y);
        }

        @Override
        public String toString() {
            return "[Word] " + value + " " + frequency;
        }
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}

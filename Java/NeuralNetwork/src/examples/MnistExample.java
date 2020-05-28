package examples;

import core.PApplet;
import core.PImage;
import nn.NeuralNetwork;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;
import static mnist.MnistReader.*;

public class MnistExample extends PApplet {

    // String IMAGES_PATH = "src/mnist/t10k-images.idx3-ubyte";
    // String LABELS_PATH = "src/mnist/t10k-labels.idx1-ubyte";
    String IMAGES_PATH = "src/mnist/train-images.idx3-ubyte";
    String LABELS_PATH = "src/mnist/train-labels.idx1-ubyte";

    int SIZE = 28;

    List<float[]> inputs;
    List<float[]> targets;
    float unit;

    NeuralNetwork nn;
    public static final String JSON_FILE = "src/examples/mnist.json";

    @Override
    protected void setup() {
        size(300, 300);
        textAlign(CENTER, CENTER);
        textSize(150);

        inputs = getImages(IMAGES_PATH).stream()
                .map(this::formatInput)
                .collect(toList());
        targets = Arrays.stream(getLabels(LABELS_PATH))
                .mapToObj(this::formatTarget)
                .collect(toList());
        unit = floor((width * 1f) / SIZE + 1);

        // nn = new NeuralNetwork(SIZE * SIZE, 100, 10);
        // doTheTraining(200_000);
        // nn.save(JSON_FILE);
        // println("Model trained and saved");

        nn = NeuralNetwork.load(JSON_FILE);
        println("Model loaded");

        background(0);
    }

    int pmouseX = -1, pmouseY;

    @Override
    protected void draw() {
        // drawImage(inputs.get(frameCount % inputs.size()));
        if (mouseIsPressed) {
            stroke(255);
            strokeWeight(20);
            if (pmouseX != -1)
                line(pmouseX, pmouseY, mouseX, mouseY);
            pmouseX = mouseX;
            pmouseY = mouseY;
        } else {
            pmouseX = -1;
        }
    }

    @Override
    protected void mouseReleased() {
        makeAGuess();
    }

    @Override
    protected void mousePressed() {
        background(0);
    }

    @Override
    protected void keyPressed() {
        if (key == 'g' || keyCode == 'G') {
            makeAGuess();
        } else if (key == 'c' || key == 'C') {
            background(0);
        }
    }

    private void makeAGuess() {
        float[] in = canvasToInputs();
        float[] guess = nn.predict(in);
        float max = guess[0];
        int indexMax = 0;
        for (int i = 0; i < guess.length; i++) {
            if (guess[i] > max) {
                max = guess[i];
                indexMax = i;
            }
        }
        fill(0, 150, 150);
        text(indexMax, width / 2f, height / 2f);
    }

    private void drawImage(float[] img) {
        background(0);
        noStroke();
        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fill(img[index++] * 255);
                rect(j * unit, i * unit, unit, unit);
            }
        }
    }

    public void doTheTraining(int iterations) {
        for (int i = 0; i < iterations; i++) {
            int index = floor(random(inputs.size()));
            float[] in = this.inputs.get(index);
            float[] out = this.targets.get(index);
            nn.train(in, out);
        }
    }

    public float[] formatInput(int[][] a) {
        float[] res = new float[a.length * a[0].length];
        int index = 0;
        for (int[] rows : a) {
            for (int value : rows) {
                res[index++] = value / 255f;
            }
        }
        return res;
    }

    public float[] formatTarget(int label) {
        float[] res = new float[10];
        res[label] = 1;
        return res;
    }

    public float[] canvasToInputs() {
        PImage image = createImage();
        image.resize(SIZE, SIZE);
        image.loadPixels();
        float[] res = new float[image.pixels.length];
        for (int i = 0; i < image.pixels.length; i++) {
            res[i] = red(image.pixels[i]) / 255f;
        }
        return res;
    }

    public static void main(String[] args) {
        PApplet.run();
    }
}

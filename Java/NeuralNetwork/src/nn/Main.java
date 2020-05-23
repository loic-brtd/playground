package nn;

import core.PApplet;

import java.util.Arrays;
import java.util.List;

/**
 * Heavily inspired by Daniel Shiffman's playlist about creating
 * a neural network from scratch :
 * https://www.youtube.com/playlist?list=PLRqwX-V7Uu6aCibgK1PTWWu9by6XFdCfh
 */
public class Main extends PApplet {

    NeuralNetwork nn;
    List<Training> trainingData = Arrays.asList(
            new Training(new float[]{0, 0}, new float[]{0}),
            new Training(new float[]{0, 1}, new float[]{1}),
            new Training(new float[]{1, 0}, new float[]{1}),
            new Training(new float[]{1, 1}, new float[]{0})
    );

    @Override
    protected void setup() {
        noCanvas();
        nn = new NeuralNetwork(2, 2, 1);
        nn.setLearningRate(0.2f);

        for (int i = 0; i < 20_000; i++) {
            Training data = random(trainingData);
            nn.train(data.inputs, data.targets);
        }

        println(nn.feedForward(new float[]{0, 0}));
        println(nn.feedForward(new float[]{0, 1}));
        println(nn.feedForward(new float[]{1, 0}));
        println(nn.feedForward(new float[]{1, 1}));
    }

    @Override
    protected void draw() {

    }

    class Training {
        float[] inputs;
        float[] targets;

        public Training(float[] inputs, float[] targets) {
            this.inputs = inputs;
            this.targets = targets;
        }
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}

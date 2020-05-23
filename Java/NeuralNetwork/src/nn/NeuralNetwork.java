package nn;

import matrix.Matrix;

public class NeuralNetwork {

    private final int inputSize;
    private final int hiddenSize;
    private final int outputSize;

    private Matrix weightsIH;
    private Matrix weightsHO;

    private Matrix biasH;
    private Matrix biasO;

    private float learningRate = 0.2f;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;
        this.weightsIH = Matrix.ofSize(hiddenSize, inputSize).fill(Utils::randomFloat);
        this.weightsHO = Matrix.ofSize(outputSize, hiddenSize).fill(Utils::randomFloat);
        this.biasH = Matrix.ofSize(hiddenSize, 1).fill(Utils::randomFloat);
        this.biasO = Matrix.ofSize(outputSize, 1).fill(Utils::randomFloat);
    }

    public float[] feedForward(float[] inputArray) {
        Matrix inputs = Matrix.fromArray(inputArray);

        Matrix hidden = weightsIH
                .mul(inputs)
                .add(biasH)
                .map(Activation::sigmoid);

        Matrix outputs = weightsHO
                .mul(hidden)
                .add(biasO)
                .map(Activation::sigmoid);

        return outputs.toFlatArray();
    }

    public void train(float[] inputArray, float[] targetArray) {
        Matrix inputs = Matrix.fromArray(inputArray);
        Matrix hidden = weightsIH
                .mul(inputs)
                .add(biasH)
                .map(Activation::sigmoid);
        Matrix outputs = weightsHO
                .mul(hidden)
                .add(biasO)
                .map(Activation::sigmoid);

        Matrix targets = Matrix.fromArray(targetArray);
        Matrix outputErrors = targets.sub(outputs);
        Matrix gradients = outputs
                .map(NeuralNetwork::dsigmoid)
                .hadamard(outputErrors)
                .scale(learningRate);
        Matrix deltaHO = gradients.mul(hidden.t());
        weightsHO = weightsHO.add(deltaHO);
        biasO = biasO.add(gradients);

        Matrix hiddenErrors = weightsHO.t().mul(outputErrors);
        Matrix hiddenGradient = hidden
                .map(NeuralNetwork::dsigmoid)
                .hadamard(hiddenErrors)
                .scale(learningRate);
        Matrix deltaIH = hiddenGradient.mul(inputs.t());
        weightsIH = weightsIH.add(deltaIH);
        biasH = biasH.add(hiddenGradient);
    }

    private static float dsigmoid(float y) {
        return y * (1 - y);
    }

    public float getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
    }
}

package nn;

public class Activation {

    public static float sigmoid(float x) {
        return (float) (1.0 / (1.0 + Math.exp(-x)));
    }

}

package matrix;

import java.util.Arrays;
import java.util.Random;

import static matrix.Matrix.*;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();

        Matrix a = fromArray(new float[][]{
                {4, 2, 1},
                {6, 8, 3}
        });
        Matrix b = fromArray(new float[][]{
                {7, 3, 2},
                {9, 6, 7},
                {2, 4, 1}
        });
        Matrix c = fromArray(new float[]{4, 5, 6});
        a.print();
        b.print();

        a.t().print();
        a.scale(5).print();
        a.add(a).print();
        a.sub(a).print();
        a.mul(b).print();

        ofSize(3, 3).fill(rand::nextFloat).print();

        fromArray(new float[]{1, 2, 3}).print();

        System.out.println(Arrays.deepToString(a.toArray()));
        System.out.println(Arrays.deepToString(c.toArray()));
    }

}

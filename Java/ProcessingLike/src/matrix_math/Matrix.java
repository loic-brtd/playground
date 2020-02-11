package matrix_math;

import java.util.function.Consumer;
import java.util.function.Function;

public class Matrix {

    protected float[][] array;
    protected int cols, rows;

    protected Matrix(float[][] array) {
        this.array = array;
        cols = this.array[0].length;
        rows = this.array.length;
    }

    public static Matrix of(float[][] array) {
        return new Matrix(array);
    }

    public static Matrix identity(int size) {
        final float[][] res = new float[size][size];
        for (int n = 0; n < size; n++) {
            res[n][n] = 1;
        }
        return new Matrix(res);
    }

    public static Matrix prod(Matrix a, Matrix b) {
        if (a.cols != b.rows) {
            throw new IllegalArgumentException("Columns of 'a' must match rows of 'b'");
        }
        final float[][] res = new float[a.rows][b.cols];
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                for (int k = 0; k < a.cols; k++) {
                    res[i][j] += a.array[i][k] * b.array[k][j];
                }
            }
        }
        return new Matrix(res);
    }

    public static Vector prod(Matrix a, Vector b) {
        return prod(a, (Matrix) b).toVector();
    }

    public Matrix copy() {
        float[][] copy = new float[rows][cols];
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                copy[j][i] = array[j][i];
            }
        }
        return new Matrix(copy);
    }

    public Matrix map(Function<Float, Float> function) {
        Matrix copy = this.copy();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                copy.array[j][i] = function.apply(array[j][i]);
            }
        }
        return copy;
    }

    public float get(int col, int row) {
        return array[row][col];
    }

    public Matrix forEach(Consumer<Float> consumer) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                consumer.accept(array[j][i]);
            }
        }
        return this;
    }

    public Matrix mult(float value) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                array[j][i] *= value;
            }
        }
        return this;
    }

    public Matrix add(float value) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                array[j][i] += value;
            }
        }
        return this;
    }

    public Matrix add(Matrix other) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                array[j][i] += other.array[j][i];
            }
        }
        return this;
    }

    public Matrix sub(float value) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                array[j][i] -= value;
            }
        }
        return this;
    }

    public Matrix div(float value) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                array[j][i] /= value;
            }
        }
        return this;
    }

    public Vector toVector() {
        if (cols != 1) {
            throw new IllegalArgumentException("There must be only one column to convert to a Vector");
        }
        final float[] values = new float[array.length];
        for (int j = 0; j < values.length; j++) {
            values[j] = array[j][0];
        }
        return Vector.of(values);
    }

    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("rows x cols: " + rows + " x " + cols + System.lineSeparator());
        for (int j = 0; j < rows; j++) {
            builder.append(j == 0 ? "[[" : " [");
            for (int i = 0; i < cols; i++) {
                builder.append(" " + array[j][i]);
                if (i != cols - 1) {
                    builder.append(",");
                }
            }
            builder.append(j == rows - 1
                    ? " ]]" + System.lineSeparator()
                    : " ]" + System.lineSeparator());
        }
        return builder.toString();
    }

}

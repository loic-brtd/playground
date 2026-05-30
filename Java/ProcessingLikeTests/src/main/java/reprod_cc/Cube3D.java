package reprod_cc;

import core.PApplet;
import matrix_math.Matrix;
import matrix_math.Vector;

public class Cube3D extends PApplet {

  float angle = 0;
  Vector[] points = new Vector[8];
  // Matrix projection = Matrix.of(new float[][] {
  // { 1, 0, 0 },
  // { 0, 1, 0 },
  // { 0, 0, 0 }
  // });

  @Override
  protected void setup() {
    size(600, 500);

    float size = 0.5f;

    points[0] = Vector.of(-size, -size, -size);
    points[1] = Vector.of(size, -size, -size);
    points[2] = Vector.of(size, size, -size);
    points[3] = Vector.of(-size, size, -size);
    points[4] = Vector.of(-size, -size, size);
    points[5] = Vector.of(size, -size, size);
    points[6] = Vector.of(size, size, size);
    points[7] = Vector.of(-size, size, size);
  }

  @Override
  protected void draw() {
    background(0);
    translate(width / 2, height / 2);

    Matrix rotationX = Matrix.of(new float[][] {
      { 1, 0, 0 },
      { 0, cos(angle), -sin(angle) },
      { 0, sin(angle), cos(angle) }
    });

    Matrix rotationY = Matrix.of(new float[][] {
      { cos(angle), 0, -sin(angle) },
      { 0, 1, 0 },
      { sin(angle), 0, cos(angle) }
    });

    Matrix rotationZ = Matrix.of(new float[][] {
      { cos(angle), -sin(angle), 0 },
      { sin(angle), cos(angle), 0 },
      { 0, 0, 1 }
    });

    Vector[] projected = new Vector[8];

    for (int i = 0; i < points.length; i++) {
      Vector v = points[i];
      Vector rotated = Matrix.prod(rotationY, v);
      rotated = Matrix.prod(rotationX, rotated);
      rotated = Matrix.prod(rotationZ, rotated);

      float z = 1 / (2 - rotated.z());
      Matrix weakPerspective = Matrix.of(new float[][] {
        { z, 0, 0 },
        { 0, z, 0 },
        { 0, 0, 0 }
      });

      Vector projected2d = Matrix.prod(weakPerspective, rotated).mult(200);
      projected[i] = projected2d;

      stroke(255, 100);
      strokeWeight(20);
      noFill();
      point(projected2d.x(), projected2d.y());
    }

    connect(0, 1, projected);
    connect(1, 2, projected);
    connect(2, 3, projected);
    connect(3, 0, projected);
    connect(4, 5, projected);
    connect(5, 6, projected);
    connect(6, 7, projected);
    connect(7, 4, projected);
    connect(1, 5, projected);
    connect(2, 6, projected);
    connect(3, 7, projected);
    connect(4, 0, projected);

    angle += 0.02;
  }

  public void connect(int i, int j, Vector[] points) {
    Vector a = points[i];
    Vector b = points[j];
    strokeWeight(3);
    stroke(255);
    line(a.x(), a.y(), b.x(), b.y());
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}

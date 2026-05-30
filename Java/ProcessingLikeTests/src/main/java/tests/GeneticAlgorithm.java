package tests;

import java.util.ArrayList;
import java.util.Arrays;

import components.Slider;
import core.PApplet;
import core.PVector;

public class GeneticAlgorithm extends PApplet {

  Slider mutationProba;
  Slider skip;
  Slider hitDamages;

  int LIFE = 500;
  int gen = 1;
  int lifespan = 0;
  float maxFitness = 0;
  Fish[] fishes = new Fish[1000];
  Obstacle obstacle;
  Target target;

  public void setup() {
    size(700, 500);

    createP("mutation :").color(255);
    mutationProba = createSlider(0, 1, 0.01f, 0.001f);
    createP("skip :").color(255);
    skip = createSlider(0, 200, 1);
    createP("hitDamages :").color(255);
    hitDamages = createSlider(0, 1, 0, 0.001f);

    obstacle = new Obstacle(width / 2, height / 2, 50);
    target = new Target(width - 40, height - 40, 20);

    for (int i = 0; i < fishes.length; i++) {
      fishes[i] = new Fish();
    }

    textAlign(CENTER, CENTER);
    textSize(30);
  }

  public void draw() {
    background(0);
    obstacle.show();
    target.show();
    for (Fish f : fishes) {
      f.show();
    }
    fill(255);
    text("max fitness : " + maxFitness, width / 2, height - 120);
    text("lifespan : " + lifespan, width / 2, height - 80);
    text("generation : " + gen, width / 2, height - 40);

    for (int n = 0; n < skip.value(); n++) {
      for (Fish f : fishes) {
        f.update();
        if (f.hits(obstacle) || f.hitsSides()) {
          f.hit = true;
        }
      }
      if (lifespan < LIFE - 1) {
        lifespan++;
      } else {
        gen++;
        // Probability list
        ArrayList<Fish> prob = new ArrayList<>();
        // Fitness7
        float max = fishes[0].fitness;
        float maxDist = PVector.dist(createVector(40, 40), target.pos);
        for (Fish f : fishes) {
          f.fitness = map(PVector.dist(f.pos, target.pos), maxDist, 0, 0, 1);
          if (f.hit) {
            f.fitness *= hitDamages.value();
          }
          if (f.fitness > max) {
            max = f.fitness;
          }
          maxFitness = max;
          int occurences = floor(f.fitness * 100);
          for (int i = 0; i < occurences; i++) {
            prob.add(f);
          }
        }
        if (prob.isEmpty()) {
          prob.addAll(Arrays.asList(fishes));
        }
        // New generation
        for (int i = 0; i < fishes.length; i++) {
          Fish a, b;
          do {
            a = random(prob);
            b = random(prob);
          } while (a == b);
          fishes[i] = new Fish(a, b);
        }
        lifespan = 0;
      }
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

  class Fish extends Circle {
    float fitness = 0;
    boolean hit = false;
    PVector[] dna = new PVector[LIFE];

    Fish() {
      super(30, 30, 5, color(100, 100, 255));
      for (int i = 0; i < dna.length; i++) {
        dna[i] = PVector.random2D().mult(10);
      }
    }

    Fish(Fish a, Fish b) {
      this();
      // Crossover
      for (int i = 0; i < dna.length; i++) {
        dna[i] = (random() < 0.5 ? a.dna[i] : b.dna[i]).copy();
        // Mutation
        if (random() < mutationProba.value()) {
          dna[i].add(PVector.random2D().mult(3));
        }
      }
    }

    void update() {
      if (!hit) {
        fitness = pos.x / width;
        pos.add(dna[lifespan]);
      }
    }

  }

  class Obstacle extends Circle {
    public Obstacle(int x, int y, int r) {
      super(x, y, r, color(255, 100, 100));
    }
  }

  class Target extends Circle {
    public Target(int x, int y, int r) {
      super(x, y, r, color(100, 255, 100));
    }
  }

  class Circle {
    PVector pos;
    int radius;
    int col;

    Circle(int x, int y, int r, int c) {
      pos = createVector(x, y);
      radius = r;
      col = c;
    }

    boolean hits(Circle other) {
      return PVector.dist(pos, other.pos) < radius + other.radius;
    }

    boolean hitsSides() {
      return pos.x - radius < 0 || pos.x + radius > width || pos.y - radius < 0 || pos.y + radius > height;
    }

    void show() {
      noStroke();
      fill(col);
      circle(pos.x, pos.y, radius * 2);
    }
  }

}

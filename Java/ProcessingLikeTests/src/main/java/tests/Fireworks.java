package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import core.PApplet;
import core.PVector;

public class Fireworks extends PApplet {

    // final static File ASCENSION = new File("res/whistle.wav");
    // final static File EXPLOSION = new File("res/explosion.wav");
    final PVector g = new PVector(0, .6f);
    List<Firework> fireworks;

    @Override
    protected void setup() {
        fullScreen();
        frameRate(30);
        noStroke();
        fireworks = new ArrayList<>();
    }

    @Override
    protected void draw() {
        background(0);
        
        if (random() < .05) {
            double r = random();
            fireworks.add(new Firework(r < .33 ? MICKEY : r < .66 ? CIRCLE : HEART));
        }

        for (int i = fireworks.size() - 1; i >= 0; i--) {
            Firework f = fireworks.get(i);
            f.update();
            if (f.done) {
                fireworks.remove(i);
            }
        }
    }

    public static void playSound(File file) {
        try {
            AudioInputStream audioIn = null;
            audioIn = AudioSystem.getAudioInputStream(file);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.err.println(file);
            e.printStackTrace();
        }
    }

    class Firework {

        List<Particle> particles = new ArrayList<>();
        Particle seed;
        boolean exploded = false;
        boolean done = false;
        Shape shape;

        public Firework(Shape shape) {
            int color = color(random(255), random(255), random(255));
            seed = new Particle(random(width), height, true, color);
            seed.applyForce(new PVector(0, -random(20, 30)));
            particles.add(seed);
            this.shape = shape;
        }

        void update() {
            for (Particle p : particles) {
                p.applyForce(g);
                p.update();
                if (p != seed && p.done()) {
                    done = true;
                }
            }
            if (!exploded && seed.done()) {
                explode();
            }
        }

        void explode() {
            exploded = true;
            particles.remove(seed);
            particles.addAll(shape.createParticle(seed));
            seed = null;
            // playSound(EXPLOSION);
        }

    }

    @FunctionalInterface
    public interface Shape {
        List<Particle> createParticle(Particle seed);
    }

    final Shape HEART = seed -> {
        List<Particle> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Particle p = new Particle(seed.pos.x, seed.pos.y, false, seed.color);
            float t = random(TWO_PI);
            float x = 16 * sin(t) * sin(t) * sin(t);
            float y = 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t);
            PVector force = new PVector(x, -y).mult(random(.6f));
            p.applyForce(force);
            list.add(p);
        }
        return list;
    };

    final Shape CIRCLE = seed -> {
        List<Particle> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Particle p = new Particle(seed.pos.x, seed.pos.y, false, seed.color);
            PVector force = PVector.random2D().mult(random(8.0f));
            p.applyForce(force);
            list.add(p);
        }
        return list;
    };

    final Shape MICKEY = seed -> {
        List<Particle> list = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            Particle p = new Particle(seed.pos.x - 100, seed.pos.y - 50, false, seed.color);
            PVector force = PVector.random2D().mult(random(5.0f));
            p.applyForce(force);
            list.add(p);
        }
        for (int i = 0; i < 80; i++) {
            Particle p = new Particle(seed.pos.x + 100, seed.pos.y - 50, false, seed.color);
            PVector force = PVector.random2D().mult(random(5.0f));
            p.applyForce(force);
            list.add(p);
        }
        for (int i = 0; i < 150; i++) {
            Particle p = new Particle(seed.pos.x, seed.pos.y + 50, false, seed.color);
            PVector force = PVector.random2D().mult(random(8.0f));
            p.applyForce(force);
            list.add(p);
        }
        return list;
    };

    class Particle {
        PVector pos = new PVector();
        PVector vel = new PVector();
        PVector acc = new PVector();
        int lifespan = 255;
        boolean isSeed;
        int color;

        Particle(float x, float y, boolean isSeed, int c) {
            pos.x = x;
            pos.y = y;
            this.isSeed = isSeed;
            color = c;
            if (isSeed) {
                // playSound(ASCENSION);
            }
        }

        void update() {
            fill(color, lifespan);
            circle(pos.x, pos.y, 8);
            pos.add(vel);
            vel.add(acc);
            acc.mult(0);
            lifespan -= 8;
        }

        boolean done() {
            return lifespan <= 0;
        }

        void applyForce(PVector v) {
            acc.add(v);
        }
    }

    public static void main(String[] args) {
        new Fireworks().start();
    }

}

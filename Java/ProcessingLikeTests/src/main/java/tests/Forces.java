package tests;

import java.util.ArrayList;
import java.util.List;

import core.PApplet;
import core.PGraphics;
import core.PVector;

public class Forces extends PApplet {

    Particle[] pts = {
            new Particle(100, 100), // 0
            new Particle(300, 280), // 1
            new Particle(500, 100), // 2
            new Particle(300, 460) // 3
    };

    PVector gravity = createVector(0, 0.9f);

    PGraphics g;

    @Override
    protected void setup() {
        size(600, 700);
        textSize(20);
        textAlign(CENTER, CENTER);
        
        g = createGraphics(width, height);

        pts[0].fixed = true;
        // pts[1].fixed = true;
        pts[2].fixed = true;
        // pts[3].fixed = true;

    }

    @Override
    protected void draw() {
        background(0);
        
        if (dragged != null && mouseIsPressed) {
            dragged.acc = createVector();
            dragged.vel = createVector();
            dragged.pos = createVector(mouseX, mouseY);
        }

        float factor = 0.005f;

        pts[0].attracts(pts[1], factor);
        pts[1].attracts(pts[0], factor);
        pts[2].attracts(pts[1], factor);
        pts[1].attracts(pts[2], factor);
        pts[3].attracts(pts[1], factor);
        pts[1].attracts(pts[3], factor);

        for (int i = 0; i < pts.length; i++) {
            Particle p = pts[i];
            p.applyForce(gravity);
            p.update();
            p.show();
            p.reset();
            fill(255);
            text(i, p.pos.x, p.pos.y - 25);
        }

        // strokeWeight(1);
        // join(pts[0], pts[1]);
        // join(pts[1], pts[2]);
        // join(pts[1], pts[3]);

        if (drawing) {
            g.strokeWeight(3);
            g.stroke(255);
            g.point(pts[3].pos.x, pts[3].pos.y);
        }

        image(g, 0, 0);

    }

    Particle dragged = null;

    boolean playing = true;
    boolean drawing = false;

    @Override
    protected void keyPressed() {
        if (key == ' ') {
            if (playing) {
                noLoop();
                playing = false;
            } else {
                loop();
                playing = true;
            }
        } else if (key == 'D' || key == 'd') {
            drawing = !drawing;
        } else if (key == 'C' || key == 'c') {
            g.clear();
        }
    }

    @Override
    protected void mousePressed() {
        PVector m = createVector(mouseX, mouseY);
        for (Particle p : pts) {
            if (PVector.dist(p.pos, m) < 50) {
                dragged = p;
                return;
            }
        }
        dragged = null;
    }

    void join(Particle a, Particle b) {
        stroke(255);
        line(a.pos.x, a.pos.y, b.pos.x, b.pos.y);
    }

    class Particle {
        PVector pos;
        PVector vel;
        PVector acc;
        boolean fixed;

        List<PVector> forces = new ArrayList<PVector>();

        public Particle(int x, int y) {
            pos = createVector(x, y);
            vel = createVector();
            acc = createVector();
        }

        void update() {
            vel.add(acc);
            vel.mult(0.99f);
            if (!fixed)
                pos.add(vel);
        }

        void applyForce(PVector f) {
            acc.add(f);
            forces.add(f);
        }

        void attracts(Particle other, float factor) {
            PVector f = PVector.sub(this.pos, other.pos).mult(factor);
            other.applyForce(f);
        }

        void reset() {
            acc.mult(0);
            forces.clear();
        }

        void show() {

            for (PVector f : forces) {
                stroke(255, 255, 0);
                strokeWeight(2);
                float factor = 80f;
                push();
                translate(pos.x, pos.y);
                line(0, 0, f.x * factor, f.y * factor);
                translate(f.x * factor, f.y * factor);
                rotate(f.heading() + PI / 2);
                line(0, 0, -4, 4);
                line(0, 0, 4, 4);
                pop();
            }
            
            noStroke();
            if (fixed)
                fill(255);
            else
                fill(255, 50, 100);
            circle(pos.x, pos.y, 20);
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", (int) pos.x, (int) pos.y);
        }

    }

    public static void main(String[] args) {
        PApplet.run();
    }
}

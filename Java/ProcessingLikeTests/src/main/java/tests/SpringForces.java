package tests;

import core.PApplet;
import core.PVector;

public class SpringForces extends PApplet {

  Spring spring;

  public void setup() {
    size(600, 400);
    spring = new Spring(width / 2, 0, height / 2);
  }

  public void draw() {
    background(51);
    spring.update();
    spring.show();
  }

  class Spring {
    PVector anchor;
    PVector restPos;
    float restLen;
    Ball ball;

    Spring(float x, float y, float len) {
      anchor = createVector(x, y);
      restLen = len;
      restPos = anchor.copy().add(0, restLen);
      ball = new Ball(restPos.copy(), 20);
    }

    void update() {
      PVector springForce = PVector
        .sub(restPos, ball.pos)
        .mult(0.01f);
      ball.applyForce(springForce);
      ball.vel.mult(0.99f);
      ball.followMouse();
      ball.update();
    }

    void show() {
      noFill();
      stroke(255);
      strokeWeight(2);
      line(anchor.x, anchor.y, ball.pos.x, ball.pos.y);
      ball.show();
    }
  }

  class Ball {
    PVector pos, vel, acc;
    float radius;
    boolean dragged = false;

    Ball(PVector pos, float r) {
      this.pos = pos;
      this.acc = createVector();
      this.vel = createVector();
      radius = r;
    }

    void show() {
      if (dragged) {
        fill(255, 0, 0);
      } else {
        fill(255, 50, 50);
      }
      noStroke();
      circle(pos.x, pos.y, radius * 2);
    }

    void set(float x, float y) {
      pos.set(x, y);
    }

    boolean includes(float x, float y) {
      return dist(x, y, pos.x, pos.y) < radius;
    }
    
    void applyForce(PVector f) {
      acc.add(f);
    }
    
    void update() {
      vel.add(acc);
      pos.add(vel);
      acc.mult(0);
    }

    void followMouse() {
      if (mouseIsPressed) {
        if (includes(mouseX, mouseY)) {
          dragged = true;
        }
      } else {
        dragged = false;
      }
      if (dragged) {
        pos.set(mouseX, mouseY);
        vel.mult(0);
        acc.mult(0);
      }
    }
  }

  public static void main(String[] args) {
    PApplet.run();
  }

}

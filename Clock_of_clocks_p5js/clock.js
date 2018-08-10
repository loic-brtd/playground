// Loïc Bertrand
// Clock of clocks with p5.js
// Inspired by this video : https://www.youtube.com/watch?v=ExkVIQ60ClM

class Clock {

  constructor(x, y) {
    if (!x && !y) {
      x = 0;
      y = 0;
    }
    this.corner = createVector(x, y);
    this.hourAngle = 0;
    this.minAngle = PI;
    this.hSize = scl * .42;
    this.mSize = scl * .37;
    this.cx = this.corner.x + scl / 2;
    this.cy = this.corner.y + scl / 2;
  }

  show(ctx) {
    if (!ctx) {
      ctx = window;
    }
    ctx.fill(darkTheme ? 0 : 255);
    ctx.noStroke();
    ctx.ellipse(this.corner.x, this.corner.y, scl, scl);
    ctx.stroke(darkTheme ? 255 : 0);
    ctx.strokeWeight(scl / 10);
    ctx.line(
      this.cx,
      this.cy,
      this.cx + this.hSize * cos(this.hourAngle),
      this.cy + this.hSize * sin(this.hourAngle));
    ctx.line(
      this.cx,
      this.cy,
      this.cx + this.mSize * cos(this.minAngle),
      this.cy + this.mSize * sin(this.minAngle));
  }

  set(h, m, amount) {
    if (!amount) {
      amount = 1;
    }
    this.hourAngle = lerp(this.hourAngle, h * HALF_PI, amount);
    this.minAngle = lerp(this.minAngle, m * HALF_PI, amount);
  }

}

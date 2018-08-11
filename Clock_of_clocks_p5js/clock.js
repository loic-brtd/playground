// Loïc Bertrand
// Clock of clocks with p5.js
// Inspired by this video : https://www.youtube.com/watch?v=ExkVIQ60ClM

class Clock {

  constructor(x = 0, y = 0) {
    this.corner = createVector(x, y);
    this.hourAngle = 0;
    this.minAngle = PI;
    this.hSize = scl * .42;
    this.mSize = scl * .37;
    this.cx = this.corner.x + scl / 2;
    this.cy = this.corner.y + scl / 2;
  }

  show() {
    fill(darkTheme ? 0 : 255);
    noStroke();
    ellipse(this.corner.x, this.corner.y, scl, scl);
    stroke(darkTheme ? 255 : 0);
    strokeWeight(scl / 10);
    line(
      this.cx,
      this.cy,
      this.cx + this.hSize * cos(this.hourAngle),
      this.cy + this.hSize * sin(this.hourAngle));
    line(
      this.cx,
      this.cy,
      this.cx + this.mSize * cos(this.minAngle),
      this.cy + this.mSize * sin(this.minAngle));
  }

  set(h, m, amount = 1) {
    this.hourAngle = lerp(this.hourAngle, h * HALF_PI, amount);
    this.minAngle = lerp(this.minAngle, m * HALF_PI, amount);
  }

}

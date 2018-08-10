// Loïc Bertrand
// Clock of clocks with p5.js
// Inspired by this video : https://www.youtube.com/watch?v=ExkVIQ60ClM

class DigitalClock {

  constructor(x, y) {
    if (!x && !y) {
      x = 0;
      y = 0;
    }
    // Time digits
    this.digits = [];
    this.digits.push(new Digit(x + scl * 0, y));
    this.digits.push(new Digit(x + scl * 5, y));
    this.digits.push(new Digit(x + scl * 12, y));
    this.digits.push(new Digit(x + scl * 17, y));
    this.digits.push(new Digit(x + scl * 24, y));
    this.digits.push(new Digit(x + scl * 29, y));

    // Columns
    this.columns = [];
    this.columns.push(new Column(x + scl * 10, y));
    this.columns.push(new Column(x + scl * 22, y));
  }

  update(amount) {
    if (!amount) {
      amount = 1;
    }
    const d = new Date();
    const h = d.getHours();
    const m = d.getMinutes();
    const s = d.getSeconds();
    this.digits[0].set(floor(h / 10), amount);
    this.digits[1].set(h % 10, amount);
    this.digits[2].set(floor(m / 10), amount);
    this.digits[3].set(m % 10, amount);
    this.digits[4].set(floor(s / 10), amount);
    this.digits[5].set(s % 10, amount);
  }

  show() {
    for (let d of this.digits) {
      d.show();
    }
    for (let c of this.columns) {
      c.show();
    }
  }

}

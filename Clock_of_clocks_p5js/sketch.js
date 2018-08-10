// Loïc Bertrand
// Clock of clocks with p5.js
// Inspired by this video : https://www.youtube.com/watch?v=ExkVIQ60ClM

const smoothness = 0.1;

let scl;
let clock;
let darkTheme;

function setup() {
  scl = windowWidth / 40;
  createCanvas(scl * 34, scl * 6).parent('canvas-holder');
  ellipseMode(CORNER);
  clock = new DigitalClock(0, 0);
  darkTheme = false;
  const checkbox = document.querySelector('#theme');
  checkbox.addEventListener('change', toggleDarkTheme);
}

function draw() {
  background(darkTheme ? '#111' : '#eee');
  clock.show();
  clock.update(smoothness);
}

function toggleDarkTheme() {
  darkTheme = !darkTheme;
  document.body.style.backgroundColor = darkTheme ? '#111' : '#eee';
}

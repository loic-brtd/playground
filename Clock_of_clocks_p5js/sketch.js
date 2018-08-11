// Loïc Bertrand
// Clock of clocks with p5.js
// Inspired by this video : https://www.youtube.com/watch?v=ExkVIQ60ClM

const smoothness = 0.2;

let scl;
let clock;
let darkTheme;

function setup() {
  scl = windowWidth / 40;
  createCanvas(scl * 34, scl * 6).parent('canvas-holder');
  frameRate(30);
  ellipseMode(CORNER);
  clock = new DigitalClock(0, 0);
  const checkbox = document.querySelector('#theme');
  checkbox.addEventListener('change', toggleDarkTheme);
  darkTheme = false;
  toggleDarkTheme();
}

function draw() {
  clear();
  clock.show();
  clock.update(smoothness);
}

function toggleDarkTheme() {
  darkTheme = !darkTheme;
  document.body.style.backgroundColor = darkTheme ? '#111' : '#eee';
  const metaThemeColor = document.querySelector("meta[name=theme-color]");
  metaThemeColor.setAttribute("content", darkTheme ? '#111' : '#eee');
}

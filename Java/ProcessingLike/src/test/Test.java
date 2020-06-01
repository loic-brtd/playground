package test;

import core.PApplet;

public class Test extends PApplet {

    @Override
    protected void setup() {
        size(400, 400);
        noCanvas();
        new Thread(() -> {
            sleep(3000);
            System.out.println("before = " + frameRate);
            changeFPS(2);
            System.out.println("after = " + frameRate);
            sleep(3000);
            System.out.println("before = " + frameRate);
            changeFPS(20);
            System.out.println("after = " + frameRate);
        }).start();
    }

    private void changeFPS(float fps) {
        System.out.println("fps = " + fps);
        frameRate(fps);
    }

    @Override
    protected void draw() {
        println(frameCount);
    }

    @Override
    protected void keyPressed() {
        if (keyCode == SPACE) {
            float fps = random(1, 20);
            changeFPS(fps);
        }
        if (key == 'n') noLoop();
        if (key == 'l') loop();
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}

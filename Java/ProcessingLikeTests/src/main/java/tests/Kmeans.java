package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.PApplet;

public class Kmeans extends PApplet {

    final int[] colors = new int[] {
            0xFFFF0000,
            0xFF00FF00,
            0xFF0000FF,
            0xFFFFFF00,
            0xFF00FFFF,
            0xFFFF00FF,
            0xFF888800
    };
    List<Point> points;
    List<Point> barycentres;
    int K = 3;
    int n = 1000;
    boolean step = false;

    public void setup() {
        size(700, 400);
        noLoop();

        // Création aléatoire des points
        // points = new ArrayList<>();
        // for (int i = 0; i < n; i++) {
        // float x = random(0, width);
        // float y = random(0, height);
        // points.add(new Point(x, y));
        // }
        clusters3();
        n = points.size();

        // Affectation aléatoire des barycentres initiaux
        List<Integer> ms = new ArrayList<>();
        barycentres = new ArrayList<>();
        // Pour chaque classe
        for (int i = 0; i < K; i++) {
            int u = -1;
            do {
                u = floor(random(0, n));
            } while (ms.contains(u));
            ms.add(u);
            points.get(u).k = i;
            barycentres.add(points.get(u).cpy());
        }

        affectationDesPoints();
    }

    public float intertie() {
        float totale = 0;
        for (int k = 0; k < K; k++) {
            Point g = barycentres.get(k);
            float classe = 0;
            for (int i = 0; i < n; i++) {
                Point p = points.get(i);
                if (p.k == k) {
                    float d = dist(p.x, p.y, g.x, g.y);
                    classe += d * d;
                }
            }
            totale += classe;
        }
        return totale;
    }

    public void draw() {

        if (step) {
            calculDesBarycentres();
            affectationDesPoints();
        }

        background(0);
        for (Point p : points) {
            p.show();
        }
        for (Point c : barycentres) {
            stroke(255);
            strokeWeight(2);
            noFill();
            circle(c.x, c.y, 10);
        }
        step = true;

        println("inertie:", intertie());

    }

    public void mousePressed() {
        redraw();

        // println("new Point(" + mouseX + ", " + mouseY + "),");
    }

    public void calculDesBarycentres() {
        // Pour chaque classe on recalcule le barycentre
        for (int k = 0; k < K; k++) {
            Point mean = null;
            int count = 0;
            for (int i = 0; i < n; i++) {
                Point p = points.get(i);
                if (p.k == k) {
                    if (mean == null) {
                        mean = new Point(p.x, p.y);
                    } else {
                        mean.x += p.x;
                        mean.y += p.y;
                    }
                    count++;
                }
            }
            mean.x /= count;
            mean.y /= count;
            Point c = barycentres.get(k);
            c.x = mean.x;
            c.y = mean.y;
        }
    }

    public boolean affectationDesPoints() {
        boolean changed = false;
        // Pour chaque point
        for (int i = 0; i < n; i++) {
            Point p = points.get(i);
            int nearestClass = -1;
            float nearestDist = Integer.MAX_VALUE;
            for (int k = 0; k < K; k++) {
                Point g = barycentres.get(k);
                float d = dist(p.x, p.y, g.x, g.y);
                if (d < nearestDist) {
                    nearestDist = d;
                    nearestClass = k;
                }
            }
            if (p.k != nearestClass) {
                changed = true;
            }
            p.k = nearestClass;
        }
        return changed;
    }

    class Point {
        float x, y;
        int k = -1;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        void show() {
            if (k == -1) {
                stroke(20);
            } else {
                stroke(colors[k]);
            }
            strokeWeight(8);
            point(x, y);
        }

        Point cpy() {
            Point p = new Point(x, y);
            p.k = k;
            return p;
        }
    }

    public void clusters3() {
        points = Arrays.asList(
                new Point(109, 51),
                new Point(91, 34),
                new Point(93, 93),
                new Point(154, 87),
                new Point(191, 52),
                new Point(209, 111),
                new Point(210, 109),
                new Point(167, 34),
                new Point(163, 191),
                new Point(43, 88),
                new Point(19, 182),
                new Point(85, 249),
                new Point(198, 134),
                new Point(280, 82),
                new Point(229, 181),
                new Point(126, 183),
                new Point(212, 48),
                new Point(304, 131),
                new Point(189, 162),
                new Point(173, 73),
                new Point(215, 171),
                new Point(126, 170),
                new Point(125, 64),
                new Point(224, 104),
                new Point(562, 47),
                new Point(476, 74),
                new Point(503, 145),
                new Point(463, 25),
                new Point(528, 146),
                new Point(355, 150),
                new Point(430, 210),
                new Point(548, 207),
                new Point(584, 147),
                new Point(533, 159),
                new Point(609, 92),
                new Point(482, 181),
                new Point(533, 65),
                new Point(620, 135),
                new Point(526, 147),
                new Point(571, 64),
                new Point(578, 231),
                new Point(519, 239),
                new Point(241, 250),
                new Point(347, 266),
                new Point(348, 256),
                new Point(219, 335),
                new Point(282, 336),
                new Point(322, 184),
                new Point(303, 246),
                new Point(287, 324),
                new Point(179, 290),
                new Point(176, 360),
                new Point(307, 254),
                new Point(200, 275),
                new Point(362, 302),
                new Point(368, 278),
                new Point(322, 303),
                new Point(330, 284),
                new Point(390, 272),
                new Point(240, 341),
                new Point(396, 336),
                new Point(467, 341),
                new Point(379, 294),
                new Point(315, 213));
    }

    public static void main(String[] args) {
        PApplet.run();
    }

}

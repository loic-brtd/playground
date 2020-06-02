package com.picasso.util;

import java.awt.*;

public class Points {

    private Points() {
    }

    public static Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    public static Point sub(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }
}

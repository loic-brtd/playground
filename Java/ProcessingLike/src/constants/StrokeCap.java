package constants;

import java.awt.BasicStroke;

public enum StrokeCap {

    ROUND(BasicStroke.CAP_ROUND),
    SQUARE(BasicStroke.CAP_BUTT),
    PROJECT(BasicStroke.CAP_SQUARE);

    private int value;

    private StrokeCap(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

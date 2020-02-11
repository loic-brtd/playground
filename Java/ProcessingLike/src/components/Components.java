package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public interface Components {

    Font FONT = new Font("Monospaced", Font.PLAIN, 20);
    Border BORDER = new LineBorder(Color.GRAY, 1);
    Border PADDING = new EmptyBorder(0, 6, 0, 6);

}

package design;

import java.awt.Color;

public class DarkPalette implements Palette {

    @Override
    public Color background() {
        return new Color(3, 3, 3);
    }

    @Override
    public Color menu() {
        return new Color(26, 26, 27);
    }

    @Override
    public Color separator() {
        return new Color(52, 53, 54);
    }

    @Override
    public Color heading() {
        return new Color(129, 131, 132);
    }

    @Override
    public Color text() {
        return Color.WHITE;
    }

    @Override
    public Color button() {
        return new Color(215, 218, 220);
    }
}

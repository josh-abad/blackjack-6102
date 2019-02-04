package design;

import java.awt.Color;

public class LightPalette implements Palette {

    @Override
    public Color background() {
        return new Color(218, 224, 230);
    }

    @Override
    public Color menu() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color separator() {
        return new Color(237, 239, 241);
    }

    @Override
    public Color heading() {
        return new Color(124, 124, 124);
    }

    @Override
    public Color text() {
        return new Color(28, 28, 28);
    }

    @Override
    public Color button() {
        return new Color(0, 121, 211);
    }
}

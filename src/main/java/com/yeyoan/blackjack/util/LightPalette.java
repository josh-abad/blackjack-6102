package com.yeyoan.blackjack.util;

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
        return new Color(185, 179, 179);
    }

    @Override
    public Color heading() {
        return new Color(119, 98, 95);
    }

    @Override
    public Color text() {
        return new Color(43, 43, 43);
    }

    @Override
    public Color button() {
        return new LightPalette().red();
    }

    @Override
    public Color altButton() {
        return new LightPalette().menu();
    }
}

package com.yeyoan.blackjack.util;

import java.awt.Color;

/**
 * A color palette for the blackjack GUI.
 * @author Joshua Abad
 */
public interface Palette {
    
    Color background();
    Color menu();
    Color separator();
    Color heading();
    Color text();
    Color button();
    Color altButton();

    default Color table() { return new Color(0, 102, 51); }
    default Color tableDark() { return table().darker(); }
    default Color tableDarker() { return tableDark().darker(); }
    default Color tableDarkest() { return tableDarker().darker(); }
    default Color tableLight() { return table().brighter(); }
    default Color green() { return new Color(42, 193, 88); }
    default Color red() { return new Color(201, 63, 63); }
}
package design;

import java.awt.Color;

/**
 * A color palette for the blackjack GUI.
 * @author Joshua Abad
 */
public class Palette {
    
    public static final Color BLACK = Color.BLACK;
    public static final Color TABLE = new Color(0, 102, 51);
    public static final Color TABLE_DARK = TABLE.darker();
    public static final Color TABLE_DARKER = TABLE_DARK.darker();
    public static final Color TABLE_DARKEST = TABLE_DARKER.darker();
    public static final Color TABLE_LIGHT = TABLE.brighter();
    public static final Color TEXT = Color.WHITE;

    private Palette() {
        // Do not instantiate
    }
}

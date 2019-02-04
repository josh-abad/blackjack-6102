package design;

import java.awt.Color;

/**
 * A color palette for the blackjack GUI.
 * @author Joshua Abad
 */
public class Palette {
    
    public static final Color BLACK = new Color(26, 26, 27);
    public static final Color DARK_GREY = new Color(52, 53, 54);
    public static final Color LIGHT_GREY = new Color(129, 131, 132);
    public static final Color TABLE = new Color(0, 102, 51);
    public static final Color TABLE_DARK = TABLE.darker();
    public static final Color TABLE_DARKER = TABLE_DARK.darker();
    public static final Color TABLE_DARKEST = TABLE_DARKER.darker();
    public static final Color TABLE_LIGHT = TABLE.brighter();
    public static final Color TEXT = Color.WHITE;
    public static final Color GREEN = new Color(42, 193, 88);
    public static final Color RED = new Color(201, 63, 63);
    public static final Color BUTTON = new Color(215, 218, 220);

    // Suppress default constructor for noninstantiability
    private Palette() {
        throw new AssertionError();
    }
}

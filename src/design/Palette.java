package design;

import java.awt.Color;

/**
 * A color palette for the blackjack GUI.
 * @author Joshua Abad
 */
public class Palette {
    
    public static final Color BLACK = new Color(22, 22, 22);
    public static final Color TABLE = new Color(0, 102, 51);
    public static final Color TABLE_DARK = TABLE.darker();
    public static final Color TABLE_DARKER = TABLE_DARK.darker();
    public static final Color TABLE_DARKEST = TABLE_DARKER.darker();
    public static final Color TABLE_LIGHT = TABLE.brighter();
    public static final Color TEXT = Color.WHITE;
    public static final Color GREEN = new Color(42, 193, 88);
    public static final Color RED = new Color(201, 63, 63);

    // Suppress default constructor for noninstantiability
    private Palette() {
        throw new AssertionError();
    }
}

package design;

import java.awt.Font;

/**
 * A font for streamlined use.
 * @author Joshua Abad
 */
public class DefaultFont {

    /**
     * Creates a new {@code DefaultFont} with the specified font name.
     * @param name the font name
     */
    public DefaultFont(String name) {
        this.name = name;
    }

    /**
     * Returns a regular-styled font with the specified size.
     * @param size the point size of the font
     * @return the font
     */
    public Font generateFont(int size) {
        return generateFont(size, Font.PLAIN);
    }
    
    /**
     * Returns a font with the specified size and style.
     * @param size the point size of the font
     * @param style the style of the font
     * @return the font
     */
    public Font generateFont(int size, int style) {
        return new Font(name, style, size);
    }
    
    private final String name;
}
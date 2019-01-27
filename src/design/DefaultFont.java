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
     * Returns a font with the specified size.
     * 
     * <p>If the size is less than or equal to 12, the font style is bold.
     * Larger font sizes have a plain style.
     * 
     * @param size the point size of the font
     * @return the font
     */
    public Font generateFont(int size) {
        int style = (size < 12) ? 1 : 0;
        return new Font(name, style, size);
    }
    
    private final String name;
}
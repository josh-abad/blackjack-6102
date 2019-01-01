package blackjack;

import java.awt.Font;

public class DefaultFont {
    
    private final String name;

    public DefaultFont(String name) {
        this.name = name;
    }

    /**
     * Returns a font with the specified size. If the size is less than
     * or equal to 12, the font style is bold, else plain.
     * @param size the point size of the font
     * @return the font
     */
    public Font generateFont(int size) {
        int style = (size <= 12) ? 1 : 0;
        return new Font(name, style, size);
    }
}
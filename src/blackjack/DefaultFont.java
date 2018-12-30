package blackjack;

import java.awt.Font;

public class DefaultFont {
    
    private String name;

    public DefaultFont(String name) {
        this.name = name;
    }

    public Font generateFont(int size) {
        int style = (size <= 12) ? 1 : 0;
        return new Font(name, style, size);
    }
}

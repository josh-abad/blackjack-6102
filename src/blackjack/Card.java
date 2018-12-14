package blackjack;

import javax.swing.ImageIcon;

public class Card {

    private int value;
    final private String suite;
    protected ImageIcon icon;

    public Card(int value, String suite) {
        if (value > 1 && value <= 10) {
            this.value = value;
        }
        this.suite = suite;
        if (value != 0) {
            this.icon = new ImageIcon(getClass().getResource(
                "/blackjack/cards/" + value + suite + ".png"
            ));
        }
    }

    @Override
    public String toString() {
        return this.value + " of " + this.suite;
    }

    public String getSuite() {
        return this.suite;
    }

    public int getValue() {
        return this.value;
    }
}
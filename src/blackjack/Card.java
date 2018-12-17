package blackjack;

import javax.swing.ImageIcon;

public class Card {

    private int value;
    final private String suite;
    protected ImageIcon frontIcon;
    protected ImageIcon backIcon;

    public Card(int value, String suite) {
        if (value > 1 && value <= 10) {
            this.value = value;
        }
        this.suite = suite;

        // Images from https://github.com/htdebeer/SVG-cards
        if (value != 0) {
            this.frontIcon = ImageResizer.getScaledImage(
                new ImageIcon(getClass().getResource(
                    "/blackjack/cards/" + suite + "/" + value + ".png"
                )
            ), 100);
            this.backIcon = ImageResizer.getScaledImage(
                new ImageIcon(getClass().getResource(
                    "/blackjack/cards/" + suite + "/Back.png"
                )
            ), 100);
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
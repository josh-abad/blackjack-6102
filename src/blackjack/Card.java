package blackjack;

import javax.swing.ImageIcon;

public class Card {

    private int value;
    private final String suit;
    private final ImageIcon frontIcon;
    private final ImageIcon backIcon;

    public Card(int value, String suit) {
        if (value >= 1 && value <= 10) {
            this.value = value;
        } else if (value > 10 && value <= 13) {
            this.value = 10;
        }
        this.suit = suit;

        // Images from https://github.com/htdebeer/SVG-cards
        this.frontIcon = ImageResizer.getScaledImage(
            new ImageIcon(getClass().getResource(
                "/blackjack/images/cards/" + suit + "/" + value + ".png"
            )), 
            100
        );
        this.backIcon = ImageResizer.getScaledImage(
            new ImageIcon(getClass().getResource(
                "/blackjack/images/cards/" + suit + "/Back.png"
            )), 
            100
        );
    }

    @Override
    public String toString() {
        return this.value + " of " + this.suit;
    }

    public String getSuit() {
        return this.suit;
    }

    public int getValue() {
        return this.value;
    }

    public ImageIcon getFrontIcon() {
        return this.frontIcon;
    }

    public ImageIcon getBackIcon() {
        return this.backIcon;
    }
}
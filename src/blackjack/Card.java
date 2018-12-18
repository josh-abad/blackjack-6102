package blackjack;

import javax.swing.ImageIcon;

public class Card {

    private int value;
    final private String suit;
    protected ImageIcon frontIcon;
    private ImageIcon backIcon;

    public Card(int value, String suit) {
        if (value > 1 && value <= 10) {
            this.value = value;
        }
        this.suit = suit;

        // Images from https://github.com/htdebeer/SVG-cards
        if (value != 0) {
            this.frontIcon = ImageResizer.getScaledImage(
                new ImageIcon(getClass().getResource(
                    "/blackjack/withshadow/" + suit + "/" + value + ".png"
                )), 
                100
            );
            this.backIcon = ImageResizer.getScaledImage(
                new ImageIcon(getClass().getResource(
                    "/blackjack/withshadow/" + suit + "/Back.png"
                )), 
                100
            );
        }
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

    public ImageIcon getBackIcon() {
        return this.backIcon;
    }
}
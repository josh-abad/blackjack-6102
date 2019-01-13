package blackjack;

import blackjack.design.ImageResizer;
import java.net.URL;
import javax.swing.ImageIcon;

public class Card {

    public Card(int value, Suit suit) {
        if (value >= 1 && value <= 10) {
            this.value = value;
        } else if (value > 10 && value <= 13) {
            this.value = 10;
        } else {
            this.value = 0;
        }
        this.suit = suit;

        // Images from https://github.com/htdebeer/SVG-cards
        String path = "/blackjack/images/cards/" + suit + "/" + value + ".png";
        URL location = Card.class.getResource(path);
        ImageIcon icon = new ImageIcon(location);
        this.frontIcon = ImageResizer.getScaledImage(icon, 100);

        path = "/blackjack/images/cards/" + suit + "/Back.png";
        location = Card.class.getResource(path);
        icon = new ImageIcon(location);
        this.backIcon = ImageResizer.getScaledImage(icon, 100);
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public ImageIcon getFrontIcon() {
        return frontIcon;
    }

    public ImageIcon getBackIcon() {
        return backIcon;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }

    private final int value;
    private final Suit suit;
    private final ImageIcon frontIcon;
    private final ImageIcon backIcon;
}
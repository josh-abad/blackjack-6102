package blackjack;

import javax.swing.ImageIcon;

public class Ace extends Card {

    public Ace(String suite) {
        super(0, suite);

        // Images from https://github.com/htdebeer/SVG-cards
        this.frontIcon = ImageResizer.getScaledImage(
            new ImageIcon(getClass().getResource(
                "/blackjack/cards/" + suite + "/Ace.png"
            )
        ), 100);
        this.backIcon = ImageResizer.getScaledImage(
            new ImageIcon(getClass().getResource(
                "/blackjack/cards/" + suite + "/Back.png"
            )
        ), 100);
    }

    @Override
    public String toString() {
        return "Ace of " + this.getSuite();
    }
}
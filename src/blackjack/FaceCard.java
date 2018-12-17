package blackjack;

import javax.swing.ImageIcon;

public class FaceCard extends Card {

    private final String face;

    public String getFace() {
        return face;
    }

    public FaceCard(String face, String suite) {
        super(10, suite);
        this.face = face;

        // Images from https://github.com/htdebeer/SVG-cards
        this.frontIcon = ImageResizer.getScaledImage(
            new ImageIcon(getClass().getResource(
                "/blackjack/cards/" + suite + "/" + face + ".png"
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
        return this.face + " of " + this.getSuite();
    }
}


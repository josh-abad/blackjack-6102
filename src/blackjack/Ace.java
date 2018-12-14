package blackjack;

import javax.swing.ImageIcon;

public class Ace extends Card {

    public Ace(String suite) {
        super(0, suite);
        this.icon = new ImageIcon(getClass().getResource(
            "/blackjack/cards/Ace" + suite + ".png"
        ));
    }

    @Override
    public String toString() {
        return "Ace of " + this.getSuite();
    }
}


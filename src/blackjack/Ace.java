package blackjack;

public class Ace extends Card {

    public Ace(String suite) {
        super(0, suite);
    }

    @Override
    public String toString() {
        return "Ace of " + this.getSuite();
    }
}


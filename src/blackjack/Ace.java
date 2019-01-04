package blackjack;

public class Ace extends Card {

    public Ace(String suite) {
        super(1, suite);
    }

    @Override
    public String toString() {
        return "Ace of " + getSuit();
    }
}
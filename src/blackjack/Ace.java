package blackjack;

public class Ace extends Card {

    public Ace(Suit suit) {
        super(1, suit);
    }

    @Override
    public String toString() {
        return "Ace of " + getSuit();
    }
}
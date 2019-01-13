package blackjack;

public class Card {

    public Card(int value, Suit suit) {
        this.value = (value >= 1 && value <= 10) ? value : 0;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }

    private final int value;
    private final Suit suit;
}
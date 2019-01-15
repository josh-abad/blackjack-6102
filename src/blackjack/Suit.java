package blackjack;

public enum Suit {
    
    DIAMONDS("Diamonds"),
    CLUBS("Clubs"),
    SPADES("Spades"),
    HEARTS("Hearts");

    private final String suit;

    Suit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return suit;
    }
}

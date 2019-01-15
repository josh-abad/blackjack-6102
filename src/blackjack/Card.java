package blackjack;

/**
 * Abstract card object 
 * @author Joshua Abad
 */
abstract public class Card {

    public Card(int rank, Suit suit) {
        if (rank < 1 || rank > 10) { 
            throw new IllegalArgumentException("Rank invalid");
        }
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Return the rank of this {@code Card}.
     * @return this {@code Card}'s rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Returns the {@link Suit} of this {@code Card}.
     * @return this {@code Card}'s {@link Suit}
     */
    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    private final int rank;
    private final Suit suit;
}
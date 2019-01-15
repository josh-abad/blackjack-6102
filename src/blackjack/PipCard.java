package blackjack;

/**
 * Standard Card object with the rank explicitly displayed.
 * @author Joshua Abad
 */
public class PipCard extends Card {
    
    /**
     * Creates a new {@code PipCard} with the specified rank and {@link Suit}.
     * @param rank the rank for the {@code PipCard}
     * @param suit the {@link Suit} for the {@code PipCard}
     */
    public PipCard(int rank, Suit suit) {
        super(rank, suit);
        if (rank < 2 || rank > 10) {
            throw new IllegalArgumentException("Rank invalid");
        }
    }
}

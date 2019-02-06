package com.yeyoan.playingcards;

/**
 * An ordinary ranked playing card.
 * @author Joshua Abad
 */
public class PipCard extends Card {
    
    /**
     * Creates a new {@code PipCard} with the specified rank and suit.
     * 
     * <p>In a standard 52-card deck, pip cards are cards that depict their rank
     * through the number of small symbols, called pips, on the front side of
     * the card.
     * 
     * @param   rank possible rank values are from 2-to-10
     * @param   suit possible suit values are: {@code SPADES}, {@code HEARTS},
     *          {@code CLUBS} and {@code DIAMONDS}
     * @throws  IllegalArgumentException if rank is below 2 or over 10
     */
    public PipCard(int rank, Suit suit) {
        super(rank, suit);
        if (rank < 2 || rank > 10) {
            throw new IllegalArgumentException("Rank invalid");
        }
    }
}

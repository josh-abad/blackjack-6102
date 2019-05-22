package com.yeyoan.blackjack.playingcards;

/**
 * An abstract playing card.
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
     * Returns the rank of this card.
     * @return the rank of this card
     */
    public int getRank() {
        return rank;
    }

    /**
     * Returns the suit of this card.
     * @return the suit of this card
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
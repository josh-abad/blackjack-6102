package com.yeyoan.blackjack.playingcards;

/**
 * An ace playing card.
 * @author yeyoa
 */
public class Ace extends Card {

    /**
     * Creates a new {@code Ace} with the specified suit.
     * 
     * <p>An ace has a value of 1, however, it may also be counted as 11 in a 
     * game of blackjack.
     * 
     * @param   suit possible suit values are: {@code SPADES}, {@code HEARTS},
     *          {@code CLUBS} and {@code DIAMONDS}
     */
    public Ace(Suit suit) {
        super(1, suit);
    }

    @Override
    public String toString() {
        return "Ace of " + getSuit();
    }
}
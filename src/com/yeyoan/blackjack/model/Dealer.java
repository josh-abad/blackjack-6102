package com.yeyoan.blackjack.model;

/**
 * A blackjack dealer.
 * @author Joshua Abad
 */
public class Dealer extends Player {

    public Dealer() {
        super("Dealer");
    }

    /**
     * Determines if this dealer has a soft 17. 
     * 
     * <p>A hand that contains an ace counted as 11 is a soft hand. If the hand 
     * is soft and has a value of 17, it is a soft 17.
     * 
     * @return true if this dealer has a soft 17
     */
    public boolean hasSoft17() {
        return getHandValue() == 17 && hasSoftHand();
    }
}
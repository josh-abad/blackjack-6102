package blackjack;

public class Dealer extends Player {

    /**
     * Determines if this {@code Dealer} has a soft 17. A hand that contains an
     * {@code Ace} counted as 11 is a soft hand. If the hand is soft and has a
     * value of 17, it is a soft 17.
     * @return true if the hand has a value of 17 and they have an {@code Ace} 
     * counted as 11
     */
    public boolean hasSoft17() {
        return getHandValue() == 17 && hasSoftHand();
    }
}
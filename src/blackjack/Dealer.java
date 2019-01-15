package blackjack;

public class Dealer extends Player {

    /**
     * Checks if this {@code Dealer} has a soft 17.
     * @return true if this {@code Dealer}'s hand value is 17 and they have an {@link Ace} 
     */
    public boolean hasSoft17() {
        return hasAce() && getHandValue() == 17;
    }
}
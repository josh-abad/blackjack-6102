package blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Player object
 * @author Joshua Abad
 */
abstract public class Player {

    public Player() {
        this.hand = new ArrayList<>();
    }

    /**
     * Add a {@link Card} to this {@code Player}'s hand.
     * @param card {@link Card} that will be added
     */
    public void hit(Card card) {
        hand.add(card);
    }

    /**
     * Removes every {@link Card} from this {@code Player}'s hand and adds 
     * them to a {@link Deck}.
     * @param deck the {@link Deck} where every {@link Card} will be added to
     */
    public void resetHand(Deck deck) {
        deck.add((List<Card>) hand);
        hand.clear();
    }

    /**
     * Checks if this {@code Player}'s hand has not gone past the limit.
     * @return true if this {@code Player}'s hand is not greater than 21
     */
    public boolean isBelowLimit() {
        return getHandValue() <= 21;
    }

    /**
     * Counts the value of this {@code Player}'s hand. An {@link Ace} is 
     * counted as 11 unless the hand will exceed 21.
     * @return the total value of this {@code Player}'s hand
     */
    public int getHandValue() {
        int total = 0;
        for (Card card : hand) {
            total += card.getRank();
        }

        if (hasAce() && total < 12) {
            total += 10;
        }

        return total;
    }

    /**
     * Checks if this {@code Player}'s hand is a Blackjack.
     * @return true if this {@code Player} has an {@link Ace} and a ten value {@link Card}
     */
    public boolean hasBlackjack() {
        if (hand.size() == 2 && hasAce()) {
            return hand.get(0).getRank() == 10 || hand.get(1).getRank() == 10;
        }
        return false;
    }

    /**
     * Checks if this {@code Player} has an {@link Ace} in their hand.
     * @return true if an {@link Ace} is found
     */
    public boolean hasAce() {
        return hand.stream().anyMatch((card) -> (card instanceof Ace));
    }

    /**
     * Determines if this {@code Player} has a soft hand. Any hand that has an 
     * {@code Ace} that is counted as 11 is a soft hand.
     * @return true if the hand is soft
     */
    public boolean hasSoftHand() {
        if (hasAce()) {
            int total = 0;
            for (Card card : hand) {
                total += card.getRank();
            }
            return total < 12;
        }
        return false;
    }

    /**
     * Returns this {@code Player}'s hand.
     * @return this {@code Player}'s hand
     */
    public List<Card> getHand() {
        return hand;
    }

    private final List<Card> hand;
}
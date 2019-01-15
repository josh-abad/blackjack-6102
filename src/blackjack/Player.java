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
     * Removes every {@link Card} from this {@code Player}'s hand and adds them to a {@link Deck}.
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
     * Counts the value of this {@code Player}'s hand.
     * <P>An {@link Ace} is counted as 11 unless the hand will exceed 21.
     * @return the total value of this {@code Player}'s hand
     */
    public int getHandValue() {
        List<Card> aces = new ArrayList<>();
        int total = 0;

        // If there are any aces in the hand, count them later
        for (Card card : hand) {
            if (card instanceof Ace) {
                aces.add(card);
            } else {
                total += card.getRank();
            }
        }

        // Adds 1 to the total if the hand will exceed 21, 11 otherwise
        for (int i = 0, len = aces.size(); i < len; i++) {
            total += (total + 11 > 21) ? 1 : 11;
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
     * Returns this {@code Player}'s hand.
     * @return this {@code Player}'s hand
     */
    public List<Card> getHand() {
        return hand;
    }

    private final List<Card> hand;
}
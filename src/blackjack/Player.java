package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {

    final private List<Card> hand;

    public Player() {
        this.hand = new ArrayList<>();
    }

    /**
     * Add a card to the player's hand
     * @param card Card object that will be added
     */
    public void hit(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    /**
     * Removes all cards from the player's hand and returns them to a deck
     * @param deck the deck where the cards will be replaced in
     */
    public void resetHand(Deck deck) {
        deck.add((ArrayList) hand);
        hand.clear();
    }

    @Override
    public String toString() {
        return "Player";
    }

    public boolean isBelowLimit() {
        return getHandValue() <= 21;
    }

    /**
     * Counts the value of the player's hand
     * @return the total value of the player's hand
     */
    public int getHandValue() {
        List<Card> aces = new ArrayList<>();
        int total = 0;

        // If there are any aces in the hand, count them later
        for (Card card : hand) {
            if (card instanceof Ace) {
                aces.add(card);
            } else {
                total += card.getValue();
            }
        }

        // Adds 1 to the total if the hand will exceed 21, 11 otherwise
        for (Card ace : aces) {
            total += (total + 11 > 21) ? 1 : 11;
        }

        return total;
    }

    /**
     * Check if the player's hand is a blackjack
     * @return  true if player has an ace and another ten-valued-card
     */
    public boolean hasBlackjack() {
        if (hand.size() == 2) {
            for (Card card : hand) {
                if (card.getValue() == 10) {
                    return hasAce();
                }
            }
        }
        return false;
    }

    public boolean hasAce() {
        return hand.stream().anyMatch((card) -> (card instanceof Ace));
    }
}
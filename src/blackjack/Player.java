package blackjack;

import java.util.ArrayList;

public class Player {

    final private ArrayList<Card> hand;

    public Player() {
        this.hand = new ArrayList<>();
    }

    /**
     * Add a card to the player's hand
     * @param card Card object that will be added
     */
    public void hit(Card card) {
        this.hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    /**
     * Removes all cards from the player's hand and returns them to a deck
     * @param deck the deck where the cards will be replaced in
     */
    public void resetHand(Deck deck) {
        deck.add(this.hand);
        this.hand.clear();
    }

    @Override
    public String toString() {
        return "Player";
    }

    public void speak(String message) {
        System.out.print("Player: " + message);
    }

    public boolean isBelowLimit() {
        return this.stand() <= 21;
    }

    public void printHand() {
        System.out.println("Your hand:\n");
        this.hand.forEach((card) -> {
            System.out.println("    " + card);
        });
    }

    /**
     * Counts the value of the player's hand
     * @return the total value of the player's hand
     */
    public int stand() {
        ArrayList<Card> aces = new ArrayList<>();
        int total = 0;

        // If there are any aces in the hand, count them later
        for (Card card : this.hand) {
            if (card instanceof Ace) {
                aces.add(card);
            } else {
                total += card.getValue();
            }
        }

        // Adds 1 to the total if the hand will exceed 21, 11 otherwise
        for (Card ace : aces) {
            total += total + 11 > 21 ? 1 : 11;
        }

        return total;
    }

    /**
     * Check if the player's hand is a blackjack
     *
     * @return  true if player has an ace and another ten-valued-card
     */
    public boolean hasBlackjack() {
        if (this.hand.size() == 2) {
            for (Card card : this.hand) {
                if (card.getValue() == 10) {
                    return hasAce();
                }
            }
        }
        return false;
    }

    public boolean hasAce() {
        return this.hand.stream().anyMatch((card) -> (card instanceof Ace));
    }
}
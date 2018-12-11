package blackjack;

import java.util.ArrayList;

public class Player {

    final private ArrayList<Card> hand;
    private int chips;

    public Player() {
        this.hand = new ArrayList<>();
        this.chips = 1000;
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

    public void addChips(int amount) {
        this.chips += amount;
    }

    public int getChips() {
        return this.chips;
    }
    
    /**
     * Bets the specified amount of chips
     * @param amount the amount of chips to be removed from the player
     * @return the specified amount of chips
     */
    public int bet(int amount) {
        if (amount <= this.chips) {
            this.chips -= amount;
        } else {
            amount = this.chips;
            this.chips = 0;
        }
        return amount;
    }

    @Override
    public String toString() {
        return "You";
    }

    public void speak(String message) {
        System.out.print("Player: " + message);
    }

    public boolean isBelowLimit() {
        return this.stand() <= 21;
    }

    public void printHand() {
        System.out.println("Your hand:\n");
        for (Card card : this.hand) {
            System.out.println("    " + card);
        }
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
            Card ace = null;
            Card tenValue = null;
            for (Card card : this.hand) {
                if (card.getValue() == 10) {
                    tenValue = card;
                }

                if (card instanceof Ace) {
                    ace = card;
                }
            }

            return ace != null && tenValue != null;
        }
        return false;
    }

    public boolean hasAce() {
        for (Card card : this.hand) {
            if (card instanceof Ace) {
                return true;
            }
        }
        return false;
    }
}


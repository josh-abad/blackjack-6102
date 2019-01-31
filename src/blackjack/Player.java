package blackjack;

import playingcards.Card;
import playingcards.Ace;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract player.
 * @author Joshua Abad
 */
abstract public class Player {

    public Player() {
        this.hand = new ArrayList<>();
    }

    /**
     * Adds a card to this player's hand.
     * 
     * <p>A hit is when a player takes another card and adds it to their hand.
     * 
     * @param card the card
     */
    public void hit(Card card) {
        hand.add(card);
    }

    /**
     * Discards this player's hand to a discard deck.
     * 
     * <p>This method removes every card from this player's hand and set them
     * aside.
     * 
     * @param discardDeck the discardDeck where the hand will be returned to
     */
    public void discardHand(List<Card> discardDeck) {
        hand.forEach(discardDeck::add);
        hand.clear();
    }

    /**
     * Checks if this player's hand has not bust.
     * 
     * <p>In blackjack, a bust means that the value of the hand went over 21. A
     * player loses when they bust.
     * 
     * @return true if this player's hand has not bust
     */
    public boolean isBelowLimit() {
        return getHandValue() <= 21;
    }

    /**
     * Counts the maximum value of this player's hand.
     * 
     * <p>Initially, any existing ace is counted as 1 regardless if an 11 will
     * bust the hand. After counting each card, 10 will be added to the total
     * provided that an ace was found and that it won't bust the hand.
     * 
     * @return the total value of this player's hand
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
     * Determines if this player has blackjack.
     * 
     * <p>A blackjack is defined as two cards totaling 21. These two cards are 
     * a ten-value card, such as a 10, king, queen or jack, and an ace. This is
     * also known as a <b>natural 21</b>.
     * 
     * @return true if this player has blackjack
     */
    public boolean hasBlackjack() {
        if (hand.size() == 2 && hasAce()) {
            return hand.get(0).getRank() == 10 || hand.get(1).getRank() == 10;
        }
        return false;
    }

    /**
     * Determines if this player has an ace in their hand.
     * @return true if an ace is found
     */
    public boolean hasAce() {
        return hand.stream().anyMatch((card) -> (card instanceof Ace));
    }

    /**
     * Determines if this player has a soft hand. 
     * 
     * <p>Any hand that has an ace that is counted as 11 is a soft hand.
     * 
     * @return true if this player has a soft hand
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
     * Returns this player's hand.
     * @return this player's hand
     */
    public List<Card> getHand() {
        return hand;
    }

    private final List<Card> hand;
}
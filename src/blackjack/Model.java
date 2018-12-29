package blackjack;

import java.util.ArrayList;

public class Model {
    
    private UserPlayer player;
    private Dealer dealer;
    private Deck deck;
    public final int MINIMUM_BET;
    public final int[] BET_VALUES = {5, 10, 25, 50, 100};

    public Model() {
        player = new UserPlayer(1000);
        dealer = new Dealer();
        deck = new Deck();
        MINIMUM_BET = 25;
    }

    public UserPlayer getPlayer() {
        return player;
    }

    public ArrayList<Card> getPlayerHand() {
        return player.getHand();
    }

    public int getPlayerHandValue() {
        return player.getHandValue();
    }

    public double getBet() {
        return player.getBet();
    }

    /**
     * Check if the player has not yet placed a bet
     * @return true if bet is 0
     */
    public boolean betIsEmpty() {
        return player.getBet() == 0;
    }

    /**
     * Increase the player's existing bet
     * @param amount to increase bet by
     */
    public void addBet(double amount) {
        player.addBet(amount);
    }

    public double getChips() {
        return player.getChips();
    }

    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Pays the player a certain amount of chips depending on the Payout type
     * @param type a Payout enum with values REGULAR, BLACKJACK, and HALF
     */
    public void givePayout(Payout type) {
        switch (type) {
            case BLACKJACK:
                player.addChips(player.getBet() + (player.getBet() * 1.5));
                break;
            case REGULAR:
                player.addChips(player.getBet() * 2);
                break;
            case HALF:
                player.addChips(player.getBet() / 2);
                break;
        }
        resetBet();
    }

    public ArrayList<Card> getDealerHand() {
        return dealer.getHand();
    }

    public int getDealerHandValue() {
        return dealer.getHandValue();
    }

    /**
     * Get the first card in the Dealer's hand
     * @return the first card
     */
    public Card getHoleCard() {
        return dealer.getHand().get(0);
    }

    public Deck getDeck() {
        return deck;
    }

    /**
     * Draws a card from the deck
     * @return a card
     */
    public Card drawCard() {
        return deck.drawCard();
    }

    /**
     * Replaces the player's existing bet with a new one
     * @param amount the new bet
     */
    public void setBet(double amount) {
        player.setBet(amount);
    }

    /**
     * Doubles the player's existing bet if there is sufficient chips
     */
    public void doubleBet() {
        player.doubleBet();
    }

    /**
     * Sets the player's existing bet to 0
     */
    public void resetBet() {
        player.setBet(0);
    }

    /**
     * Player adds a card to their hand
     * @param card the card to be added
     */
    public void playerHit(Card card) {
        player.hit(card);
    }

    /**
     * Dealer adds a card to their hand
     * @param card the card to be added
     */
    public void dealerHit(Card card) {
        dealer.hit(card);
    }

    /**
     * Shuffles the deck
     */
    public void shuffleDeck() {
        deck.shuffle();
    }

    /**
     * The Dealer draws a card if they have a Soft 17 
     * or a hand value less than or equal to 16
     */
    public void dealerTurn() {
        while (dealer.hasSoft17() || dealer.getHandValue() <= 16) {
            Card card = deck.drawCard();
            dealer.hit(card);
        }
    }

    /**
     * Helper method to check if the player and Dealer are not above 21
     * @return true if the hand values of both player and Dealer aren't under 21 
     */
    private boolean bothBelowLimit() {
        return player.isBelowLimit() && dealer.isBelowLimit();
    }

    /**
     * Helper method to check if the player and Dealer both have Blackjack
     * @return true if both hands have a Blackjack
     */
    private boolean isDoubleBlackjack() {
        if (player.hasBlackjack() ^ dealer.hasBlackjack()) {
            return false;
        }
        return player.hasBlackjack() && dealer.hasBlackjack();
    }

    /**
     * Helper method to check if neither player nor Dealer has Blackjack 
     * and both have equal hand values
     * @return true if the player and Dealer have equal hand values 
     */
    private boolean isEqualHand() {
        return (!player.hasBlackjack() && !dealer.hasBlackjack())
                && (player.getHandValue() == dealer.getHandValue());
    }

    /**
     * Check if the game is a push for tie
     * @return true if either there is a double Blackjack or equal hand values
     */
    public boolean isTie() {
        if (bothBelowLimit()) {
            return (isDoubleBlackjack()) || (isEqualHand());
        }
        return false;
    }

    public boolean playerWon() {
        if (bothBelowLimit()) {
            return player.getHandValue() > dealer.getHandValue();
        }
        return player.isBelowLimit();
    }

    public boolean dealerWon() {
        if (bothBelowLimit()) {
            return dealer.getHandValue() > player.getHandValue();
        }
        return dealer.isBelowLimit();
    }

    public void returnBet() {
        player.addChips(player.getBet());
        player.setBet(0);
    }

    public void playerGetPayout() {
        double payout = player.getBet();
        if (player.hasBlackjack()) {
            payout += player.getBet() * 1.5;
        } else {
            payout *= 2;
        }
        player.addChips(payout);
        player.setBet(0);
    }

    public void playerLoseBet() {
        player.setBet(0);
    }

    public boolean playerIsOutOfChips() {
        return player.getChips() < MINIMUM_BET;
    }

    public boolean playerCanDoubleDown() {
        return player.getBet() <= player.getChips();
    }

    public boolean betSufficient() {
        return player.getBet() >= MINIMUM_BET;
    }

    public boolean playerWentOver() {
        return !player.isBelowLimit();
    }
}

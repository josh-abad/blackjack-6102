package blackjack;

import javax.swing.ImageIcon;

public class Model {
    
    /**
     * The required amount of chips for the PLAYER to be able to play
     */
    public final int MINIMUM_BET;

    /**
     * The possible options for the PLAYER when they are playing
     */
    public final String[] PLAY_OPTIONS;

    /**
     * The possible choices for the PLAYER regarding their hand
     */
    public final String[] HAND_OPTIONS;

    /**
     * The possible number of chips a PLAYER may bet at a time
     */
    public final int[] BET_VALUES;

    public Model() {
        PLAYER = new UserPlayer(1000);
        DEALER = new Dealer();
        DECK = new Deck();

        MINIMUM_BET = 25;

        PLAY_OPTIONS = new String[] {
            "Hit",
            "Stand",
            "Double Down",
            "Surrender"
        };

        HAND_OPTIONS = new String[] {
            "Deal",
            "Next Hand",
            "Quit Game"
        };

        BET_VALUES = new int[] {
            5,
            10,
            25,
            50,
            100
        };
    }

    /**
     * Increase the PLAYER's existing bet
     * @param amount to increase bet by
     */
    public void bet(double amount) {
        PLAYER.addBet(amount);
    }

    /**
     * Checks if the PLAYER's current bet has reached the minimum bet
     * @return true if PLAYER's bet is greater than or equal to the minimum bet
     */
    public boolean betIsSufficient() {
        return PLAYER.getBet() >= MINIMUM_BET;
    }

    /**
     * Check if the PLAYER has enough chips to double their bet
     * @return true if PLAYER's current bet is less than or equal to their chips
     */
    public boolean canDoubleDown() {
        return PLAYER.getBet() <= PLAYER.getChips();
    }

    /**
     * Check if the DEALER has an Ace and another card with a value of 10
     * @return true if the DEALER has Blackjack
     */
    public boolean dealerHasBlackjack() {
        return DEALER.hasBlackjack();
    }

    /**
     * DEALER adds a card to their hand
     * @param card the card to be added
     */
    public void dealerHit(Card card) {
        DEALER.hit(card);
    }

    /**
     * The DEALER draws a card if they have a Soft 17 
     * or a hand value less than or equal to 16
     */
    public void dealerTurn() {
        while (DEALER.hasSoft17() || DEALER.getHandValue() <= 16) {
            Card card = DECK.drawCard();
            DEALER.hit(card);
        }
    }

    /**
     * Doubles the PLAYER's existing bet if there is sufficient chips
     */
    public void doubleBet() {
        PLAYER.doubleBet();
    }

    /**
     * Draws a card from the DECK
     * @return a card
     */
    public Card drawCard() {
        return DECK.drawCard();
    }

    /**
     * Pays the PLAYER a certain amount of chips depending on the Payout type
     * @param type possible values are: REGULAR, BLACKJACK, and HALF
     */
    public void givePayout(Payout type) {
        switch (type) {
            case BLACKJACK:
                PLAYER.addChips(PLAYER.getBet() + (PLAYER.getBet() * 1.5));
                break;
            case REGULAR:
                PLAYER.addChips(PLAYER.getBet() * 2);
                break;
            case HALF:
                PLAYER.addChips(PLAYER.getBet() / 2);
                break;
        }
        resetBet();
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

    /**
     * Removes the chips from the PLAYER's bet
     */
    public void loseBet() {
        PLAYER.setBet(0);
    }

    /**
     * Checks if the DEALER has a greater hand value than the PLAYER
     * if they are both below limit. If the PLAYER is past limit,
     * check if the DEALER is below limit
     * @return true if the DEALER won
     */
    public boolean lost() {
        if (bothBelowLimit()) {
            return DEALER.getHandValue() > PLAYER.getHandValue();
        }
        return DEALER.isBelowLimit();
    }

    /**
     * Check if the PLAYER still has enough chips to play
     * @return true if PLAYER's chips are less than the minimum bet
     */
    public boolean outOfChips() {
        return PLAYER.getChips() < MINIMUM_BET;
    }

    /**
     * Check if the PLAYER has an Ace and another card with a value of 10
     * @return true if the PLAYER has Blackjack
     */
    public boolean playerHasBlackjack() {
        return PLAYER.hasBlackjack();
    }

    /**
     * Player adds a card to their hand
     * @param card the card to be added
     */
    public void playerHit(Card card) {
        PLAYER.hit(card);
    }

    /**
     * Sets the PLAYER's existing bet to 0
     */
    public void resetBet() {
        PLAYER.setBet(0);
    }

    /**
     * Removes all cards from the PLAYER and DEALER's hands 
     * and places them back onto the DECK
     */
    public void resetHand() {
        PLAYER.resetHand(DECK);
        DEALER.resetHand(DECK);
    }

    /**
     * Removes the chips from the PLAYER's bet and adds them back to their chips 
     */
    public void returnBet() {
        PLAYER.addChips(PLAYER.getBet());
        PLAYER.setBet(0);
    }

    /**
     * Shuffles the DECK
     */
    public void shuffleDeck() {
        DECK.shuffle();
    }

    /**
     * Check if the PLAYER's hand value is greater than 21
     * @return true if PLAYER's hand value is greater than 21
     */
    public boolean wentOver() {
        return !PLAYER.isBelowLimit();
    }

    /**
     * Checks if the PLAYER has a greater hand value than the DEALER
     * if they are both below limit. If the DEALER is past limit,
     * check if the PLAYER is below limit
     * @return true if the PLAYER won
     */
    public boolean won() {
        if (bothBelowLimit()) {
            return PLAYER.getHandValue() > DEALER.getHandValue();
        }
        return PLAYER.isBelowLimit();
    }

    public ImageIcon getBackHoleCard() {
        return DEALER.getHand().get(0).getBackIcon();
    }

    public double getBet() {
        return PLAYER.getBet();
    }

    public double getChips() {
        return PLAYER.getChips();
    }

    public ImageIcon[] getDealerCardImages() {
        int len = DEALER.getHand().size();
        ImageIcon[] cardImages = new ImageIcon[len];
        for (int i = 0; i < len; i++) {
            cardImages[i] = DEALER.getHand().get(i).getFrontIcon();
        }
        return cardImages;
    }

    public int getDealerHandValue() {
        return DEALER.getHandValue();
    }

    public ImageIcon getFrontHoleCard() {
        return DEALER.getHand().get(0).getFrontIcon();
    }

    public ImageIcon[] getPlayerCardImages() {
        int len = PLAYER.getHand().size();
        ImageIcon[] cardImages = new ImageIcon[len];
        for (int i = 0; i < len; i++) {
            cardImages[i] = PLAYER.getHand().get(i).getFrontIcon();
        }
        return cardImages;
    }

    public Card getPlayerFirstCard() {
        return PLAYER.getHand().get(0);
    }

    public int getPlayerHandValue() {
        return PLAYER.getHandValue();
    }

    public Card getPlayerSecondCard() {
        return PLAYER.getHand().get(1);
    }

    /**
     * Helper method to check if the PLAYER and DEALER are not above 21
     * @return true if the hand values of both PLAYER and DEALER aren't under 21 
     */
    private boolean bothBelowLimit() {
        return PLAYER.isBelowLimit() && DEALER.isBelowLimit();
    }

    /**
     * Helper method to check if the PLAYER and DEALER both have Blackjack
     * @return true if both hands have a Blackjack
     */
    private boolean isDoubleBlackjack() {
        if (PLAYER.hasBlackjack() ^ DEALER.hasBlackjack()) {
            return false;
        }
        return PLAYER.hasBlackjack() && DEALER.hasBlackjack();
    }

    /**
     * Helper method to check if neither PLAYER nor DEALER has Blackjack 
     * and both have equal hand values
     * @return true if the PLAYER and DEALER have equal hand values 
     */
    private boolean isEqualHand() {
        return (!PLAYER.hasBlackjack() && !DEALER.hasBlackjack())
                && (PLAYER.getHandValue() == DEALER.getHandValue());
    }

    private final UserPlayer PLAYER;
    private final Dealer DEALER;
    private final Deck DECK;
}
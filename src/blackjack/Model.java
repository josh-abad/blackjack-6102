package blackjack;

import playingcards.Deck;
import playingcards.Card;

public class Model {
    
    /**
     * The required amount of chips for the player to be able to play.
     */
    public static final int MINIMUM_BET = 25;

    public Model() {
        player = new BlackjackPlayer();
        dealer = new Dealer();
        deck = new Deck(6);
    }

    /**
     * Returns the possible options for the player when they are playing.
     * @return the play options
     */
    public static final String[] playOptions() {
        return PLAY_OPTIONS.clone();
    }

    /**
     * Returns the possible choices for the player regarding their hand.
     * @return the hand options
     */
    public static final String[] handOptions() {
        return HAND_OPTIONS.clone();
    }

    /**
     * Returns the possible number of chips a {@code Player} may bet at a time.
     * @return the bet values 
     */
    public static final int[] betValues() {
        return BET_VALUES.clone();
    }

    /**
     * Increases the player's existing bet.
     * @param amount to increase bet by
     */
    public void bet(double amount) {
        player.addBet(amount);
    }

    /**
     * Checks if the player's current bet has reached the minimum bet.
     * @return true if player's bet has reached the minimum bet
     */
    public boolean betIsSufficient() {
        return player.getBet() >= MINIMUM_BET;
    }

    /**
     * Checks if the player has enough chips to double their bet.
     * @return true if player's current bet is less than or equal to their chips
     */
    public boolean canDoubleDown() {
        return player.getBet() <= player.getChips();
    }

    /**
     * Determines if the dealer has blackjack.
     * @return true if the dealer has blackjack
     */
    public boolean dealerHasBlackjack() {
        return dealer.hasBlackjack();
    }

    /**
     * Adds a card to the dealer's hand.
     * @param card the card
     */
    public void dealerHit(Card card) {
        dealer.hit(card);
    }

    /**
     * Performs a dealer's turn in blackjack.
     */
    public void dealerTurn() {
        while (dealer.hasSoft17() || dealer.getHandValue() <= 16) {
            Card card = deck.drawCard();
            dealer.hit(card);
        }
    }

    /**
     * This method doubles the existing bet if there is sufficient chips.
     */
    public void doubleBet() {
        player.doubleBet();
    }

    /**
     * This method draws a card from the deck.
     * @return a card
     */
    public Card drawCard() {
        return deck.drawCard();
    }

    /**
     * This method gives the player the chips specified by the payout.  
     * 
     * <p>A regular payout pays the player the amount of their bet. A
     * blackjack payout pays the player 3-to-2, or 1.5 times their bet. A half
     * payout returns half of the player's bet.
     * 
     * @param   type possible values are: {@code REGULAR}, {@code BLACKJACK}, 
     *          and {@code HALF}
     */
    public void givePayout(Payout type) {
        player.addChips(type.pay(player.getBet()));
        resetBet();
    }

    /**
     * Determines if the player has a soft hand.
     * @return true if the player has a soft hand
     */
    public boolean isSoft() {
        return player.hasSoftHand();
    }

    /**
     * This method determines if there is a push for a tie. 
     * 
     * <p>A push is when the player and the dealer have the same hand value. 
     * Neither players win in a push. 
     * 
     * @return true if the player and the dealer have the same hand value
     */
    public boolean isTie() {
        if (bothBelowLimit(player, dealer)) {
            return isEqualHand(player, dealer);
        }
        return false;
    }

    /**
     * This method removes the chips from the player's bet.
     */
    public void clearBet() {
        player.setBet(0);
    }

    /**
     * This method determines if the dealer has won over the player.
     * @return true if the dealer won
     */
    public boolean playerLost() {
        return won(dealer, player);
    }

    /**
     * Determines if the player has enough chips to continue playing.
     * @return true if the chips are less than the minimum bet
     */
    public boolean outOfChips() {
        return player.getChips() < MINIMUM_BET;
    }

    /**
     * This method determines if the player has blackjack.
     * 
     * <p>A blackjack is defined as two cards totaling 21. These two cards are 
     * a ten-value card, such as a 10, king, queen or jack, and an ace. This is
     * also known as a <b>natural 21</b>.
     * 
     * @return true if the player has blackjack
     */
    public boolean playerHasBlackjack() {
        return player.hasBlackjack();
    }

    /**
     * The player adds a card to their hand.
     * @param card the card to be added
     */
    public void playerHit(Card card) {
        player.hit(card);
    }

    /**
     * This method sets the existing bet to 0.
     */
    public void resetBet() {
        player.setBet(0);
    }

    /**
     * This method resets the player and dealer's hands.
     */
    public void resetHand() {
        player.resetHand(deck);
        dealer.resetHand(deck);
    }

    /**
     * This method returns the player's bet.
     */
    public void returnBet() {
        player.addChips(player.getBet());
        player.setBet(0);
    }

    /**
     * This method shuffles the deck.
     */
    public void shuffleDeck() {
        deck.shuffle();
    }

    /**
     * This method determines if the player's hand has busted.
     * @return true if player's hand has busted
     */
    public boolean wentOver() {
        return !player.isBelowLimit();
    }

    /**
     * This method determines if the player has won.
     * @return true if the {@code player} has won
     */
    public boolean playerWon() {
        return won(player, dealer);
    }

    public double getBet() {
        return player.getBet();
    }

    public double getChips() {
        return player.getChips();
    }

    public String[] getDealerCardNames() {
        int handSize = dealer.getHand().size();
        String[] cardNames = new String[handSize];
        for (int i = 0; i < handSize; i++) {
            cardNames[i] = dealer.getHand().get(i).toString();
        }
        return cardNames;
    }

    public int getDealerHandValue() {
        return dealer.getHandValue();
    }

    public String getHoleCardName() {
        return dealer.getHand().get(0).toString();
    }

    public String[] getPlayerCardNames() {
        int handSize = player.getHand().size();
        String[] cardNames = new String[handSize];
        for (int i = 0; i < handSize; i++) {
            cardNames[i] = player.getHand().get(i).toString();
        }
        return cardNames;
    }

    public String getFirstCard() {
        return player.getHand().get(0).toString();
    }

    public int getPlayerHandValue() {
        return player.getHandValue();
    }

    public String getSecondCard() {
        return player.getHand().get(1).toString();
    }

    private boolean bothBelowLimit(Player player, Player opponent) {
        return player.isBelowLimit() && opponent.isBelowLimit();
    }

    private boolean isDoubleBlackjack(Player player, Player opponent) {
        return player.hasBlackjack() && opponent.hasBlackjack();
    }

    private boolean isEqualHand(Player player, Player opponent) {
        if (player.getHandValue() == 21 && opponent.getHandValue() == 21) {
            return isDoubleBlackjack(player, opponent) 
                    || noOneHasBlackjack(player, opponent);
        }
        return player.getHandValue() == opponent.getHandValue();
    }

    private boolean noOneHasBlackjack(Player player, Player opponent) {
        return !player.hasBlackjack() && !opponent.hasBlackjack();
    }

    public boolean won(Player player, Player opponent) {
        if (player.hasBlackjack()) {
            return !opponent.hasBlackjack();
        }
        if (bothBelowLimit(player, opponent)) {
            return player.getHandValue() > opponent.getHandValue();
        }
        return player.isBelowLimit();
    }

    private final BlackjackPlayer player;
    private final Dealer dealer;
    private final Deck deck;
    private static final String[] PLAY_OPTIONS = {
        "Hit", "Stand", "Double Down", "Surrender"
    };
    private static final String[] HAND_OPTIONS = {
        "Deal", "Next Hand", "Quit Game"
    };
    private static final int[] BET_VALUES = {5, 10, 25, 50, 100};
}
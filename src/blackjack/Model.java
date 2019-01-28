package blackjack;

import java.util.ArrayList;
import java.util.List;
import playingcards.Card;
import playingcards.CardContainer;
import playingcards.Shoe;

public class Model {
    
    
    /**
     * The initial amount of chips the player has at the start of the game
     */
    public static final int BANKROLL = 1000;

    /**
     * The required amount of chips for the player to be able to play.
     */
    public static final int MINIMUM_BET = 25;

    public Model() {
        player = new BlackjackPlayer(BANKROLL);
        dealer = new Dealer();
        shoe = new Shoe(NUMBER_OF_DECKS);
        discardDeck = new ArrayList<>();
        shoe.shuffle();
    }

    /**
     * Returns the possible choices for the player when they are playing.
     * @return the choices
     */
    public static final String[] choices() {
        return CHOICES.clone();
    }

    /**
     * Returns the possible options for the player regarding the game.
     * @return the options
     */
    public static final String[] options() {
        return OPTIONS.clone();
    }

    /**
     * Returns the possible types of chips a player may bet.
     * 
     * <p>Every int element in the private array {@code CHIPS} should be
     * displayed on the screen. A note about the order of the elements in the
     * array: the last element must be the smallest value. This is to keep the
     * rightmost insets intact as a chip option get disabled.
     * 
     * @return the chips
     */
    public static final int[] chips() {
        return CHIPS.clone();
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
        return player.getBet() <= player.getBankroll();
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
            if (shoe.isEmpty()) {
                return;
            }
            Card card = shoe.drawCard();
            dealer.hit(card);
            updateRunningCount(card.getRank());
        }
    }

    /**
     * Returns the number of decks in the shoe.
     * @return the number of decks
     */
    public int deckCount() {
        if (shoe.getClass() == Shoe.class) {
            Shoe s = (Shoe) shoe;
            return s.deckCount();
        }
        return 1;
    }

    /**
     * This method doubles the existing bet if there is sufficient chips.
     */
    public void doubleBet() {
        player.doubleBet();
    }

    /**
     * This method draws a card from the shoe.
     * @return a card
     */
    public Card drawCard() {
        return shoe.drawCard();
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
    public boolean playerHasSoftHand() {
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
     * Determines if there are any remaining cards in the shoe.
     * @return true if there are any remaining cards in the shoe
     */
    public boolean shoeIsEmpty() {
        return shoe.isEmpty();
    }

    /**
     * Determines if the remaining cards in the deck is sufficient.
     * 
     * <p>This remaining cards are considered sufficient if the current size of 
     * the shoe is more than one-fourth of the original size. This is to ensure
     * that the shoe will always contain cards without having to reshuffle every
     * time.
     * 
     * @return true if the remaining cards in the shoe is sufficient
     */
    public boolean shoeIsSufficient() {
        return shoe.size() > ((NUMBER_OF_DECKS * 52) * 0.25);
    }

    /**
     * This method determines if the dealer has won over the player.
     * @return true if the dealer won
     */
    public boolean playerLost() {
        return won(dealer, player);
    }

    /**
     * Returns an action based on the basic strategy for blackjack.
     * @return an action
     */
    public BasicStrategy.Action basicStrategy() {
        boolean softHand = player.hasSoftHand();
        int p = player.getHandValue();
        int d = dealer.getHand().get(1).getRank();
        return BasicStrategy.generate(softHand, p, d);
    }

    /**
     * Add a card to the running count.
     * @param rank the card's value
     */
    public void updateRunningCount(int rank) {
        if (rank >= 2 && rank <= 6) {
            runningCount++;
        } else if (rank == 1 || rank == 10) {
            runningCount--;
        }
    }

    /**
     * Return the running count.
     * @return the running count
     */
    public int getRunningCount() {
        return runningCount;
    }

    /**
     * Resets the running count to 0.
     */
    public void resetRunningCount() {
        runningCount = 0;
    }

    /**
     * Returns the true count of cards played in the shoe.
     * 
     * <p>The true count is calculated by dividing the running count by the
     * number of decks remaining.
     * 
     * @return the true count 
     */
    public int getTrueCount() {
        if (shoe.getClass() == Shoe.class) {
            Shoe s = (Shoe) this.shoe;
            if (s.deckCount() > 0) {
                return Math.round(runningCount / s.deckCount());
            }
        }
        return runningCount;
    }

    /**
     * Determines if the player has enough chips to continue playing.
     * @return true if the chips are less than the minimum bet
     */
    public boolean outOfChips() {
        return player.getBankroll() < MINIMUM_BET;
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
     * Adds a card to the player's  hand.
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
        player.discardHand(discardDeck);
        dealer.discardHand(discardDeck);
    }

    public void restartGame() {
        player.addBet(player.getBankroll());
        player.setBet(0);
        player.addChips(BANKROLL);
        player.discardHand(discardDeck);
        dealer.discardHand(discardDeck);
        shoe.reset(discardDeck);
        shoe.shuffle();
        runningCount = 0;
    }

    /**
     * This method returns the player's bet.
     */
    public void returnBet() {
        player.addChips(player.getBet());
        player.setBet(0);
    }

    /**
     * Returns all the discarded cards back to the shoe.
     */
    public void shuffleDeck() {
        shoe.reset(discardDeck);
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
     * @return true if the player has won
     */
    public boolean playerWon() {
        return won(player, dealer);
    }

    public double playerBet() {
        return player.getBet();
    }

    public double playerChips() {
        return player.getBankroll();
    }

    public String[] dealerCardNames() {
        int handSize = dealer.getHand().size();
        String[] cardNames = new String[handSize];
        for (int i = 0; i < handSize; i++) {
            cardNames[i] = dealer.getHand().get(i).toString();
        }
        return cardNames;
    }

    public int dealerFrontCard() {
        int value = dealer.getHand().get(1).getRank();
        if (value == 1) {
            return 11;
        }
        return value;
    }

    public int dealerHandValue() {
        return dealer.getHandValue();
    }

    public boolean dealerHasSoftHand() {
        return dealer.hasSoftHand();
    }

    public Card holeCard() {
        return dealer.getHand().get(0);
    }

    public String[] playerCardNames() {
        int handSize = player.getHand().size();
        String[] cardNames = new String[handSize];
        for (int i = 0; i < handSize; i++) {
            cardNames[i] = player.getHand().get(i).toString();
        }
        return cardNames;
    }

    public String[] initialCards() {
        String[] firstTwoCards = new String[2];
        firstTwoCards[0] = player.getHand().get(0).toString();
        firstTwoCards[1] = player.getHand().get(1).toString();
        return firstTwoCards;
    }

    public int playerHandValue() {
        return player.getHandValue();
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

    private boolean won(Player player, Player opponent) {
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
    private final CardContainer shoe;
    private final List<Card> discardDeck;
    private int runningCount;
    private static final int NUMBER_OF_DECKS = 4;
    private static final String[] CHOICES = {
        "Hit", "Double Down", "Surrender", "Stand"
    };
    private static final String[] OPTIONS = {
        "Deal", "Next Hand", "Hint", "New Game", "Quit Game"
    };
    private static final int[] CHIPS = {100, 50, 25, 10, 5};
}
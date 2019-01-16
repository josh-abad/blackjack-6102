package blackjack;

public class Model {
    
    /**
     * The required amount of chips for the {@code Player} to be able to play.
     */
    public static final int MINIMUM_BET = 25;

    public Model() {
        player = new BlackjackPlayer();
        dealer = new Dealer();
        deck = new Deck(6);
    }

    /**
     * The possible options for the {@code Player} when they are playing.
     * @return a {@code String} array containing the play options
     */
    public static final String[] playOptions() {
        return PLAY_OPTIONS.clone();
    }

    /**
     * The possible choices for the {@code Player} regarding their hand.
     * @return a {@code String} array containing the hand options
     */
    public static final String[] handOptions() {
        return HAND_OPTIONS.clone();
    }

    /**
     * The possible number of chips a {@code Player} may bet at a time.
     * @return an {@code int} array containing the bet values 
     */
    public static final int[] betValues() {
        return BET_VALUES.clone();
    }

    /**
     * Increases the {@code Player}'s existing bet.
     * @param amount to increase bet by
     */
    public void bet(double amount) {
        player.addBet(amount);
    }

    /**
     * Checks if the {@code Player}'s current bet has reached the {@code MINIMUM_BET}.
     * @return true if {@code Player}'s bet is greater than or equal to the {@code MINIMUM_BET}
     */
    public boolean betIsSufficient() {
        return player.getBet() >= MINIMUM_BET;
    }

    /**
     * Check if the {@code Player} has enough chips to double their bet.
     * @return true if {@code Player}'s current bet is less than or equal to their chips
     */
    public boolean canDoubleDown() {
        return player.getBet() <= player.getChips();
    }

    /**
     * Check if the {@code Dealer} has an {@code Ace} and another {@code Card} 
     * with a value of 10.
     * @return true if the {@code Dealer} has Blackjack
     */
    public boolean dealerHasBlackjack() {
        return dealer.hasBlackjack();
    }

    /**
     * {@code Dealer} adds a {@code Card} to their hand.
     * @param card the {@code Card} to be added
     */
    public void dealerHit(Card card) {
        dealer.hit(card);
    }

    /**
     * The {@code Dealer} hits as long as their hand is a soft 17 or less than 
     * a hard 17.
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
     * This method draws a {@code Card} from the {@code Deck}.
     * @return a {@code Card}
     */
    public Card drawCard() {
        return deck.drawCard();
    }

    /**
     * This method gives the {@code Player} the chips specified by the 
     * {@code Payout}.  
     * @param type possible values are: {@code REGULAR}, {@code BLACKJACK}, and 
     * {@code HALF}
     */
    public void givePayout(Payout type) {
        player.addChips(type.getPayout(player.getBet()));
        resetBet();
    }

    public boolean isSoft() {
        return player.hasSoftHand();
    }

    /**
     * This method determines if there is a push for a tie. A push is when the 
     * {@code BlackjackPlayer} and the {@code Dealer} have the same hand value.
     * Neither players win in a push. 
     * @return true if the {@code BlackjackPlayer} and the {@code Dealer} have
     * the same hand value
     */
    public boolean isTie() {
        if (bothBelowLimit(player, dealer)) {
            return isEqualHand(player, dealer);
        }
        return false;
    }

    /**
     * This method removes the chips from the {@code Player}'s bet.
     */
    public void clearBet() {
        player.setBet(0);
    }

    /**
     * This method determines if the {@code dealer} has won over the 
     * {@code player}.
     * @return true if the {@code dealer} won
     */
    public boolean playerLost() {
        return won(dealer, player);
    }

    /**
     * This method determines if the {@code player} has enough chips to 
     * continue playing.
     * @return true if the chips are less than the {@code MINIMUM_BET}
     */
    public boolean outOfChips() {
        return player.getChips() < MINIMUM_BET;
    }

    /**
     * This method determines if the {@code player} has an {@code Ace} and 
     * another {@code Card} with a value of 10.
     * @return true if the {@code player} has Blackjack
     */
    public boolean playerHasBlackjack() {
        return player.hasBlackjack();
    }

    /**
     * The {@code player} adds a {@code Card} to their hand.
     * @param card the {@code Card} to be added
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
     * This method removes every {@code Card} from each hand and places them 
     * back in the {@code deck}.
     */
    public void resetHand() {
        player.resetHand(deck);
        dealer.resetHand(deck);
    }

    /**
     * This method removes the chips from the {@code player}'s bet and adds 
     * them back to their chips.
     */
    public void returnBet() {
        player.addChips(player.getBet());
        player.setBet(0);
    }

    /**
     * This method shuffles the {@code deck}.
     */
    public void shuffleDeck() {
        deck.shuffle();
    }

    /**
     * This method determines if the {@code player}'s hand value is greater 
     * than 21.
     * @return true if {@code Player}'s hand value is greater than 21
     */
    public boolean wentOver() {
        return !player.isBelowLimit();
    }

    /**
     * This method determines if the {@code player} has won over the 
     * {@code dealer}.
     * @return true if the {@code player} won
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

    /**
     * Helper method to check if the {@code Player} and {@code Dealer} 
     * are not above 21.
     * @return true if the hand values of both {@code Player} and {@code Dealer} aren't under 21 
     */
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
package blackjack;

public class Model {
    
    /**
     * The required amount of chips for the {@code Player} to be able to play.
     */
    public final int MINIMUM_BET;

    /**
     * The possible options for the {@code Player} when they are playing.
     */
    public final String[] PLAY_OPTIONS;

    /**
     * The possible choices for the {@code Player} regarding their hand.
     */
    public final String[] HAND_OPTIONS;

    /**
     * The possible number of chips a {@code Player} may bet at a time.
     */
    public final int[] BET_VALUES;

    public Model() {
        player = new BlackjackPlayer();
        dealer = new Dealer();
        deck = new Deck();

        MINIMUM_BET = 0;

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
     * The {@code Dealer} draws a {@code Card} if they have a soft 17 
     * or a hand value less than or equal to 16.
     */
    public void dealerTurn() {
        while (dealer.hasSoft17() || dealer.getHandValue() <= 16) {
            Card card = deck.drawCard();
            dealer.hit(card);
        }
    }

    /**
     * Doubles the {@code Player}'s existing bet if there is sufficient chips.
     */
    public void doubleBet() {
        player.doubleBet();
    }

    /**
     * Draws a {@code Card} from the {@code Deck}.
     * @return a {@code Card}
     */
    public Card drawCard() {
        return deck.drawCard();
    }

    /**
     * Pays the {@code Player} the chips specified by the {@code Payout}. 
     * @param type possible values are: {@code REGULAR}, {@code BLACKJACK}, and {@code HALF}
     */
    public void givePayout(Payout type) {
        player.addChips(type.getPayout(player.getBet()));
        resetBet();
    }

    /**
     * Check if the game is a push for tie.
     * @return true if either there is a double Blackjack or equal hand values
     */
    public boolean isTie() {
        if (bothBelowLimit()) {
            return isEqualHand();
        }
        return false;
    }

    /**
     * Removes the chips from the {@code Player}'s bet.
     */
    public void loseBet() {
        player.setBet(0);
    }

    /**
     * Checks if the {@code Dealer} has a greater hand value than 
     * the {@code Player} if they are both below limit. If the {@code Player} 
     * is past limit, check if the {@code Dealer} is below limit.
     * @return true if the {@code Dealer} won
     */
    public boolean lost() {
        if (dealerHasBlackjack()) {
            return !playerHasBlackjack();
        }

        if (bothBelowLimit()) {
            return dealer.getHandValue() > player.getHandValue();
        }

        return dealer.isBelowLimit();
    }

    /**
     * Check if the {@code Player} still has enough chips to play.
     * @return true if {@code Player}'s chips are less than the minimum bet
     */
    public boolean outOfChips() {
        return player.getChips() < MINIMUM_BET;
    }

    /**
     * Check if the {@code Player} has an {@code Ace} and another 
     * {@code Card} with a value of 10.
     * @return true if the {@code Player} has Blackjack
     */
    public boolean playerHasBlackjack() {
        return player.hasBlackjack();
    }

    /**
     * {@code Player} adds a {@code Card} to their hand.
     * @param card the {@code Card} to be added
     */
    public void playerHit(Card card) {
        player.hit(card);
    }

    /**
     * Sets the {@code Player}'s existing bet to 0.
     */
    public void resetBet() {
        player.setBet(0);
    }

    /**
     * Removes all {@code Card} from the {@code Player} and {@code Dealer}'s 
     * hands and places them back onto the {@code Deck}.
     */
    public void resetHand() {
        player.resetHand(deck);
        dealer.resetHand(deck);
    }

    /**
     * Removes the chips from the {@code Player}'s bet and adds them back 
     * to their chips.
     */
    public void returnBet() {
        player.addChips(player.getBet());
        player.setBet(0);
    }

    /**
     * Shuffles the {@code Deck}.
     */
    public void shuffleDeck() {
        deck.shuffle();
    }

    /**
     * Check if the {@code Player}'s hand value is greater than 21.
     * @return true if {@code Player}'s hand value is greater than 21
     */
    public boolean wentOver() {
        return !player.isBelowLimit();
    }

    /**
     * Checks if the {@code Player} has a greater hand value than the 
     * {@code Dealer} if they are both below limit. If the {@code Dealer} 
     * is past limit, check if the {@code Player} is below limit.
     * @return true if the {@code Player} won
     */
    public boolean won() {
        if (player.hasBlackjack()) {
            return !dealer.hasBlackjack();
        }
        if (bothBelowLimit()) {
            return player.getHandValue() > dealer.getHandValue();
        }
        return player.isBelowLimit();
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
    private boolean bothBelowLimit() {
        return player.isBelowLimit() && dealer.isBelowLimit();
    }

    /**
     * Helper method to check if the {@code Player} and {@code Dealer} both 
     * have Blackjack.
     * @return true if both hands have a Blackjack
     */
    private boolean isDoubleBlackjack() {
        return player.hasBlackjack() && dealer.hasBlackjack();
    }

    /**
     * Helper method to check if neither {@code Player} nor {@code Dealer} has 
     * Blackjack and both have equal hand values.
     * @return true if the {@code Player} and {@code Dealer} have equal hand values 
     */
    private boolean isEqualHand() {
        if (player.getHandValue() == 21 && dealer.getHandValue() == 21) {
            return isDoubleBlackjack() || noOneHasBlackjack();
        }
        return player.getHandValue() == dealer.getHandValue();
    }

    private boolean noOneHasBlackjack() {
        return !playerHasBlackjack() && !dealerHasBlackjack();
    }

    private final BlackjackPlayer player;
    private final Dealer dealer;
    private final Deck deck;
}
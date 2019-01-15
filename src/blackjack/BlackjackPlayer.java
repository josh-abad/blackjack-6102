package blackjack;

public class BlackjackPlayer extends Player {

    /**
     * Creates a new {@code BlackjackPlayer} with the specified chips.
     * @param chips the desired amount of chips
     */
    public BlackjackPlayer(double chips) {
        super();
        this.chips = chips;
        this.bet = 0;
    }    

    /**
     * Creates a new {@code BlackjackPlayer} with 1,000 chips.
     */
    public BlackjackPlayer() {
        this(1000);
    }    

    /**
     * Adds to the existing bet of this {@code BlackjackPlayer}.
     * @param amount the desired bet
     */
    public void addBet(double amount) {
        if (amount <= chips) {
            chips -= amount;
        } else {
            amount = chips;
            chips = 0;
        }
        bet += amount;
    }

    /**
     * Adds onto this {@code BlackjackPlayer}'s amount of chips 
     * @param amount the amount to be added 
     */
    public void addChips(double amount) {
        chips += amount;
    }

    /**
     * Doubles this {@code BlackjackPlayer}'s current bet
     */
    public void doubleBet() {
        if (bet <= chips) {
            addBet(bet);
        }
    }

    /**
     * Bets the specified amount of chips
     * @param amount the amount of chips to be removed from this {@code BlackjackPlayer}
     */
    public void setBet(double amount) {
        if (amount <= chips) {
            chips -= amount;
        } else {
            amount = chips;
            chips = 0;
        }
        bet = amount;
    }

    /**
     * Returns this {@code BlackjackPlayer}'s chips
     * @return this {@code BlackjackPlayer}'s chips
     */
    public double getChips() {
        return chips;
    }

    /**
     * Returns this {@code BlackjackPlayer}'s bet
     * @return this {@code BlackjackPlayer}'s bet
     */
    public double getBet() {
        return bet;
    }

    private double chips;
    private double bet; 
}
package blackjack;

/**
 * A blackjack player with an ability to place bets.
 * @author Joshua Abad
 */
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
     * Adds to the existing bet of this player.
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
     * Adds onto this player's amount of chips.
     * @param amount the desired amount
     */
    public void addChips(double amount) {
        chips += amount;
    }

    /**
     * Doubles this player's current bet.
     */
    public void doubleBet() {
        if (bet <= chips) {
            addBet(bet);
        }
    }

    /**
     * Bets the specified amount of chips.
     * @param amount the desired amount of chips
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
     * Returns this player's chips
     * @return this player's chips
     */
    public double getChips() {
        return chips;
    }

    /**
     * Returns this player's bet
     * @return this player's bet
     */
    public double getBet() {
        return bet;
    }

    private double chips;
    private double bet; 
}
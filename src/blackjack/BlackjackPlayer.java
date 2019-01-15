package blackjack;

public class UserPlayer extends Player {

    public UserPlayer(double chips) {
        super();
        this.chips = chips;
        this.bet = 0;
    }    

    public void addBet(double amount) {
        if (amount <= chips) {
            chips -= amount;
        } else {
            amount = chips;
            chips = 0;
        }
        bet += amount;
    }

    public void addChips(double amount) {
        chips += amount;
    }

    public void doubleBet() {
        if (bet <= chips) {
            addBet(bet);
        }
    }

    /**
     * Bets the specified amount of chips
     * @param amount the amount of chips to be removed from the player
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

    public double getChips() {
        return chips;
    }

    public double getBet() {
        return bet;
    }

    private double chips;
    private double bet; 
}
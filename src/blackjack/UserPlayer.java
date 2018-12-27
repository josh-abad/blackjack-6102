package blackjack;

public class UserPlayer extends Player {

    private double chips;
    private double bet; 

    public UserPlayer(double chips) {
        super();
        this.chips = chips;
        this.bet = 0;
    }    

    public void doubleBet() {
        if (this.bet <= this.chips) {
            this.chips -= this.bet;
            this.bet *= 2;
        }
    }

    public double getBet() {
        return this.bet;
    }

    /**
     * Bets the specified amount of chips
     * @param amount the amount of chips to be removed from the player
     */
    public void setBet(double amount) {
        if (amount <= this.chips) {
            this.chips -= amount;
        } else {
            amount = this.chips;
            this.chips = 0;
        }
        this.bet = amount;
    }

    public void addBet(double amount) {
        if (amount <= this.chips) {
            this.chips -= amount;
        } else {
            amount = this.chips;
            this.chips = 0;
        }
        this.bet += amount;
    }

    public void addChips(double amount) {
        this.chips += amount;
    }

    public double getChips() {
        return this.chips;
    }
}
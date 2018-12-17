/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author yeyoa
 */
public class UserPlayer extends Player {

    private int chips;
    private int bet; 

    public UserPlayer() {
        super();
        this.chips = 1000;
        this.bet = 0;
    }    

    public int getBet() {
        return this.bet;
    }

    /**
     * Bets the specified amount of chips
     * @param amount the amount of chips to be removed from the player
     */
    public void setBet(int amount) {
        if (amount <= this.chips) {
            this.chips -= amount;
        } else {
            amount = this.chips;
            this.chips = 0;
        }
        this.bet = amount;
    }

    public void addChips(int amount) {
        this.chips += amount;
    }

    public int getChips() {
        return this.chips;
    }
}

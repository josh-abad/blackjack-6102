package com.yeyoan.blackjack.model;

/**
 * A blackjack player with an ability to place bets.
 * @author Joshua Abad
 */
public class BlackjackPlayer extends Player {

    /**
     * Creates a new {@code BlackjackPlayer} with the specified name and
     * amount of chips.
     * @param name the player's name
     * @param bankroll the starting amount of chips
     */
    public BlackjackPlayer(String name, double bankroll) {
        super(name);
        this.bankroll = bankroll;
        this.bet = 0;
    }    

    /**
     * Creates a new {@code BlackjackPlayer} with 1,000 chips and the specified
     * name.
     * @param name the player's name
     */
    public BlackjackPlayer(String name) {
        this(name, 1000);
    }    

    /**
     * Creates a new {@code BlackjackPlayer} named Player with the specified
     * amount of chips.
     * @param bankroll the starting amount of chips
     */
    public BlackjackPlayer(double bankroll) {
        this("Player", bankroll);
    }    

    /**
     * Creates a new {@code BlackjackPlayer} named Player with 1,000 chips.
     */
    public BlackjackPlayer() {
        this("Player", 1000);
    }    

    /**
     * Adds to the existing bet of this player.
     * @param amount the desired bet
     */
    public void addBet(double amount) {
        if (amount <= bankroll) {
            bankroll -= amount;
        } else {
            amount = bankroll;
            bankroll = 0;
        }
        bet += amount;
    }

    /**
     * Adds to this player's bankroll.
     * @param amount the desired amount
     */
    public void addChips(double amount) {
        bankroll += amount;
    }

    /**
     * Doubles this player's current bet.
     */
    public void doubleBet() {
        if (bet <= bankroll) {
            addBet(bet);
        }
    }

    /**
     * Bets the specified amount of chips.
     * @param amount the desired amount of chips
     */
    public void setBet(double amount) {
        if (amount <= bankroll) {
            bankroll -= amount;
        } else {
            amount = bankroll;
            bankroll = 0;
        }
        bet = amount;
    }

    /**
     * Returns this player's bankroll
     * @return this player's bankroll
     */
    public double getBankroll() {
        return bankroll;
    }

    /**
     * Returns this player's bet
     * @return this player's bet
     */
    public double getBet() {
        return bet;
    }

    private double bankroll;
    private double bet; 
}
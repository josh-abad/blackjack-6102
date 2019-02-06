package com.yeyoan.blackjack.view;

import com.yeyoan.util.Format;

public class Message {

    public static String bothOver() {
        return "You both went over.";
    }

    public static String deal(String firstCard, String secondCard) {
        return "Your first two cards are " + firstCard
             + " and " + secondCard + ".";
    }

    public static String deal(String[] initialCards) {
        if (initialCards.length < 2) {
            return "";
        }
        return deal(initialCards[0], initialCards[1]);
    }

    public static String dealerBlackjack(double bet) {
        return "The Dealer got Blackjack. " + Message.playerLost(bet);
    }

    public static String deckIsEmpty() {
        return "The deck is empty. The Dealer will reshuffle in the next hand";
    }

    public static String doubleDown(double bet, String card) {
        String chips = Format.currency(bet);
        return "You double your bet to " + chips + " Chips " 
             + "and draw " + card + ".";
    }

    public static String hint(String action) {
        String[] templates = {
            "You should " + action.toLowerCase() + ".",
            "Your best option is to " + action.toLowerCase() + ".",
            action + " is your best option.",
            "The cards dictate that you should " + action.toLowerCase() + "." 
        };
        return templates[(int) (Math.random() * templates.length)];
    }

    public static String hit(String card) {
        return "You draw " + card + ".";
    }
    
    public static String minimumBet(int amount) {
        return "You need to bet a minimum of " + amount + " chips to play.";
    }

    public static String nextHand() {
        return "Place a bet.";
    }

    public static String newGame() {
        return "Are you sure you want to start a new game? "
             + "You will lose your current winnings.";
    }

    public static String outOfChips() {
        return "You're out of chips.";
    }

    public static String playerBlackjack(double bet) {
        return "You got Blackjack! " + Message.playerWon(bet * 1.5);
    }

    public static String playerLost(double bet) {
        if (bet == 0) {
            return "You lost.";
        }
        String chips = Format.currency(bet);
        return "You lost " + chips + " chips.";
    }

    public static String playerWon(double bet) {
        if (bet == 0) {
            return "You won!";
        }
        String chips = Format.currency(bet);
        return "You won " + chips + " Chips!";
    }

    public static String quit() {
        return "Are you sure you want to quit? "
             + "You will lose your current winnings.";
    }

    public static String reshuffle() {
        return "Deck is reshuffled.";
    }

    public static String surrender(double bet) {
        String message = "You surrendered";
        String chips = Format.currency(bet / 2);
        message += (bet > 0) ? " and got back " + chips + " Chips." : ".";
        return message;
    }

    public static String tie() {
        return "You pushed for a tie.";
    }

    public static String welcome() {
        return "Welcome to Blackjack! Place a bet.";
    }

    // Suppress default constructor for noninstantiability
    private Message() { 
        throw new AssertionError();
    }
}

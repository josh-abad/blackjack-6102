package blackjack;

import blackjack.design.Format;

public class Message {

    public static String bothOver() {
        return "You both went over.";
    }

    public static String deal(String firstCard, String secondCard) {
        return "Your first two cards are " + firstCard 
                + " and " + secondCard + ".";
    }

    public static String dealerBlackjack(double bet) {
        return "The Dealer got Blackjack. " + Message.playerLost(bet);
    }

    public static String doubleDown(double bet, Card card) {
        String chips = Format.currency(bet);
        return "You double your bet to " + chips + " Chips " 
                + "and draw " + card + ".";
    }

    public static String hit(Card card) {
        return "You draw " + card + ".";
    }
    
    public static String minimumBet(int amount) {
        return "You need to bet a minimum of " + amount + " chips to play.";
    }

    public static String nextHand() {
        return "Place a bet.";
    }

    public static String outOfChips() {
        return "You're out of chips.";
    }

    public static String playerBlackjack(double bet) {
        return "You got Blackjack! " + Message.playerWon(bet * 1.5);
    }

    public static String playerLost(double bet) {
        String chips = Format.currency(bet);
        return "You lost " + chips + " chips.";
    }

    public static String playerWon(double bet) {
        String chips = Format.currency(bet);
        return "You won " + chips + " Chips!";
    }

    public static String surrender(double bet) {
        String chips = Format.currency(bet / 2);
        return "You surrendered and got back " + chips + " Chips.";
    }

    public static String tie() {
        return "You pushed for a tie.";
    }

    private Message() { 
        // Do not instantiate
    }
}

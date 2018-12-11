package blackjack;

import java.util.Scanner;

/*
 
 _     _            _    _            _    
| |   | |          | |  (_)          | |   
| |__ | | __ _  ___| | ___  __ _  ___| | __
| '_ \| |/ _` |/ __| |/ / |/ _` |/ __| |/ /
| |_) | | (_| | (__|   <| | (_| | (__|   < 
|_.__/|_|\__,_|\___|_|\_\ |\__,_|\___|_|\_\
                       _/ |                
                      |__/    

*/

public class Game {

    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final Scanner reader;
    private int pot;

    public Game() {
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
        this.reader = new Scanner(System.in);
    }

    public void start() {
        int round = 1;
        while (player.getChips() > 0 && dealer.getChips() > 0) {
            if (round != 1) {
                clearScreen(3000);
            }
            String answer;
            System.out.println("ROUND " + round);
            System.out.println("\nYou have " + player.getChips() + " chips.");

            // Betting
            while (true) {
                try {
                    System.out.print("How much will you bet? ");
                    int amount = Integer.parseInt(reader.nextLine());
                    pot += player.bet(amount);
                    pot += dealer.bet(amount);
                    System.out.println("\nThe pot is " + this.pot + '.');
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("\nPlease enter a valid amount.\n");
                }
            }

            // The player and the dealer get two cards each at the start.
            System.out.print("Your initial two cards are ");
            for (int i = 0; i < 2; i++) {
                Card playerCard = deck.getCard();
                Card dealerCard = deck.getCard();

                if (i == 1) {
                    System.out.print(" and ");
                }
                System.out.print(playerCard);

                player.hit(playerCard);
                dealer.hit(dealerCard);
            }
            System.out.println(".");

            System.out.println("The dealer's exposed card is " + 
                    dealer.getHand().get(0) + '.');

            do {
                deck.shuffle();
                System.out.println();

                player.printHand();
                System.out.print("\nHit? ");
                answer = reader.nextLine();

                if (answer.equals("yes")) {
                    Card card = deck.getCard();
                    System.out.println("\nYou drew " + card + '.');
                    player.hit(card);
                }   
            } while (answer.equals("yes") && player.isBelowLimit());

            // The dealer's cards are shown.
            System.out.println("The dealer's hole card is " + 
                    dealer.getHand().get(1) + '.');
            if (dealer.hasAce() && dealer.stand() == 17 || dealer.stand() <= 16) {
                Card card = deck.getCard();
                sleep(1500);
                System.out.println("\nThe dealer drew " + card + '.');
                dealer.hit(card);
            }

            sleep(3000);
            clearScreen(3000);
            final int playerHand = player.stand();
            final int dealerHand = dealer.stand();
            System.out.println("Your final hand is " + playerHand + '.');
            System.out.println("The dealer's final hand is " + dealerHand + '.');

            Player winner = null;
            if (player.isBelowLimit() && dealer.isBelowLimit()) {
                if (playerHand == dealerHand) {
                    System.out.println("\nThis round is a draw.\n");
                    player.addChips(pot / 2);
                    dealer.addChips(pot / 2);
                    pot = 0;
                } else {
                    winner = playerHand > dealerHand ? player : dealer;
                }
            } else if (playerHand > 21 ^ dealerHand > 21){
                winner = playerHand <= 21 ? player : dealer;
            } else {
                System.out.println("\nYou both lost this round.\n");
            }

            if (winner != null) {
                if (winner instanceof Dealer) {
                    if (dealer.hasBlackjack()) {
                        System.out.println("\nThe dealer got blackjack.\n");
                    } else {
                        System.out.println("\nThe " + winner + " won this round.\n");
                    }
                    dealer.addChips(pot);
                    pot = 0;
                } else {
                    if (player.hasBlackjack()) {
                        System.out.println("\n" + winner + " got blackjack.\n");
                    } else {
                        System.out.println("\n" + winner + " won this round.\n");
                    }
                    player.addChips(pot);
                    pot = 0;
                }
            }

            player.resetHand(this.deck);
            dealer.resetHand(this.deck);
            round++;
        }

        if (player.getChips() > 0) {
            System.out.println("You won " + player.getChips() + " chips.");
        } else {
            System.out.println("You are out of chips.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    private static void clearScreen(int milliseconds) {
        sleep(milliseconds);

        for (int i = 0; i < 50; i++) {
            System.out.println(); 
        }
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) { }
    }
}
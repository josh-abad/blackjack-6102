package com.yeyoan.blackjack.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;

import com.jfoenix.effects.JFXDepthManager;
import com.yeyoan.blackjack.playingcards.Card;
import com.yeyoan.blackjack.playingcards.CardContainer;
import com.yeyoan.blackjack.playingcards.Shoe;
import com.yeyoan.blackjack.view.Message;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Model {
    
    /**
     * The initial amount of chips the player has at the start of the game
     */
    public static final int BANKROLL = 1000;

    public Model() {
        player = new BlackjackPlayer();
        dealer = new Dealer();
        discardDeck = new ArrayList<>();
        String name = "Player";
        player = new BlackjackPlayer(name);
        int deckAmount = 4;
        minimumBet = 25;
        stand17 = false;
        shoe = new Shoe(deckAmount);
        shoe.shuffle();
    }

    public enum Result {
        TIE(bet -> Message.tie()),
        PLAYER_WIN(bet -> Message.playerWon(bet)),
        PLAYER_BLACKJACK(bet -> Message.playerBlackjack(bet)),
        DEALER_WIN(bet -> Message.playerLost(bet)),
        DEALER_BLACKJACK(bet -> Message.dealerBlackjack(bet)),
        BOTH_OVER(bet -> Message.bothOver());

        Result(DoubleFunction<String> message) {
            this.message = message;
        }

        public String getMessage(double bet) {
            return message.apply(bet);
        }

        private final DoubleFunction<String> message;
    }

    public Result getResult() {
        if (isTie()) {
            returnBet();
            return Result.TIE;
        } else if (playerWon()) {
            if (playerHasBlackjack()) {
                givePayout(Payout.BLACKJACK);
                return Result.PLAYER_BLACKJACK;
            } else {
                givePayout(Payout.REGULAR);
                return Result.PLAYER_WIN;
            }
        } else if (playerLost()) {
            resetBet();
            return dealer.hasBlackjack()
                ? Result.DEALER_BLACKJACK
                : Result.DEALER_WIN;
        } else {
            return Result.BOTH_OVER;
        }
    }

    public ImageView getCardImage(Card card) {
        String value = null;
        try {
            Integer.parseInt(card.toString().substring(0, 1));
            value = Integer.toString(card.getRank());
        } catch (NumberFormatException ex) {
            value = card.toString().substring(0, 1);
        }
        String fileName = "card" + card.getSuit() + value + ".png";

        Image image = new Image(Model.class.getResource("/Cards/" + fileName).toExternalForm());

        ImageView cardImage = new ImageView(image);

        cardImage.setPreserveRatio(true);
        cardImage.setFitWidth(100.0);
        // JFXDepthManager.setDepth(cardImage, player.getHand().size());
        // cardImage.getStyleClass().add("depth-high");
        cardImage.setStyle(
            "-fx-effect:dropshadow(three-pass-box,rgba(0,0,0,0.5),20,0,0,1);"
        );

        return cardImage; 
    }

    public ImageView getCardBack() {
        Image image = new Image(Model.class.getResource("/Cards/cardBack_red4.png").toExternalForm());
        ImageView cardBack = new ImageView(image);
        cardBack.setPreserveRatio(true);
        cardBack.setFitWidth(100.0);
        JFXDepthManager.setDepth(cardBack, player.getHand().size());

        return cardBack;
    }

    /**
     * Returns the possible choices for the player when they are playing.
     * @return the choices
     */
    public static final String[] choices() {
        return CHOICES.clone();
    }

    /**
     * Returns the possible options for the player regarding the game.
     * @return the options
     */
    public static final String[] options() {
        return OPTIONS.clone();
    }

    /**
     * Returns the possible types of chips a player may bet.
     * 
     * <p>Every int element in the private array {@code CHIPS} should be
     * displayed on the screen. A note about the order of the elements in the
     * array: the last element must be the smallest value. This is to keep the
     * rightmost insets intact as a chip option get disabled.
     * 
     * @return the chips
     */
    public static final int[] chips() {
        return CHIPS.clone();
    }

    public static final String[] chipItems() {
        return Arrays.stream(chips())
            .mapToObj(String::valueOf)
            .toArray(String[]::new);
    }

    /**
     * Increases the player's existing bet.
     * @param amount to increase bet by
     */
    public void bet(double amount) {
        player.addBet(amount);
    }

    public boolean betIsEmpty() {
        return player.getBet() == 0;
    }

    /**
     * Checks if the player's current bet has reached the minimum bet.
     * @return true if player's bet has reached the minimum bet
     */
    public boolean betIsSufficient() {
        return player.getBet() >= minimumBet;
    }

    /**
     * Checks if the player has enough chips to double their bet.
     * @return true if player's current bet is less than or equal to their chips
     */
    public boolean canDoubleDown() {
        return player.getBet() <= player.getBankroll();
    }

    /**
     * Determines if the dealer has blackjack.
     * @return true if the dealer has blackjack
     */
    public boolean dealerHasBlackjack() {
        return dealer.hasBlackjack();
    }

    /**
     * Adds a card to the dealer's hand.
     * @param card the card
     */
    public void dealerHit(Card card) {
        dealer.hit(card);
    }

    /**
     * Performs a dealer's turn in blackjack.
     * 
     * <p>In blackjack, the dealer hits until their hand value is greater than
     * 16. The dealer may also hit a soft 17 in some games, depending on the
     * casino rules.
     */
    public int dealerTurn() {
        int numberOfCards = 0;
        while (dealer.hasSoft17() || dealer.getHandValue() <= 16) {
            if ((dealer.hasSoft17() && stand17) || shoe.isEmpty()) {
                break;
            }
            Card card = shoe.drawCard();
            dealer.hit(card);
            numberOfCards++;
            updateRunningCount(card.getRank());
        }
        return numberOfCards;
    }

    public boolean dealerHasSoft17() {
        return dealer.hasSoft17();
    }

    /**
     * Returns the number of decks in the shoe.
     * @return the number of decks
     */
    public int dCount() {
        if (shoe instanceof Shoe) {
            Shoe s = (Shoe) shoe;
            return s.deckCount();
        }
        return 1;
    }

    /**
     * This method doubles the existing bet if there is sufficient chips.
     */
    public void doubleBet() {
        player.doubleBet();
    }

    /**
     * This method draws a card from the shoe.
     * @return a card
     */
    public Card drawCard() {
        return shoe.drawCard();
    }

    /**
     * This method gives the player the chips specified by the payout.  
     * 
     * <p>A regular payout pays the player the amount of their bet. A
     * blackjack payout pays the player 3-to-2, or 1.5 times their bet. A half
     * payout returns half of the player's bet.
     * 
     * @param   type possible values are: {@code REGULAR}, {@code BLACKJACK}, 
     *          and {@code HALF}
     */
    public void givePayout(Payout type) {
        player.addChips(type.pay(player.getBet()));
        resetBet();
    }

    public void loadSettings(Object[] settings) {
        String name = (String) settings[0];
        if (name.isEmpty()) {
            player = new BlackjackPlayer();
        } else {
            player = new BlackjackPlayer(name);
        }
        int deckAmount = (int) settings[1];
        minimumBet = (int) settings[2];
        stand17 = (int) settings[3] == 1;
        shoe = new Shoe(deckAmount);
        shoe.shuffle();
    }

    /**
     * Determines if the player has a soft hand.
     * @return true if the player has a soft hand
     */
    public boolean playerHasSoftHand() {
        return player.hasSoftHand();
    }

    /**
     * This method determines if there is a push for a tie. 
     * 
     * <p>A push is when the player and the dealer have the same hand value. 
     * Neither players win in a push. 
     * 
     * @return true if the player and the dealer have the same hand value
     */
    public boolean isTie() {
        if (bothBelowLimit(player, dealer)) {
            return isEqualHand(player, dealer);
        }
        return false;
    }

    /**
     * Determines if there are any remaining cards in the shoe.
     * @return true if there are any remaining cards in the shoe
     */
    public boolean shoeIsEmpty() {
        return shoe.isEmpty();
    }

    /**
     * Determines if the remaining cards in the deck is sufficient.
     * 
     * <p>This remaining cards are considered sufficient if the current size of 
     * the shoe is more than one-fourth of the original size. This is to ensure
     * that the shoe will always contain cards without having to reshuffle every
     * time.
     * 
     * @return true if the remaining cards in the shoe is sufficient
     */
    public boolean shoeIsSufficient() {
        return shoe.size() > ((NUMBER_OF_DECKS * 52) * 0.25);
    }

    /**
     * This method determines if the dealer has won over the player.
     * @return true if the dealer won
     */
    public boolean playerLost() {
        return won(dealer, player);
    }

    /**
     * Returns an action based on the basic strategy for blackjack.
     * @return an action
     */
    public BasicStrategy.Action basicStrategy() {
        boolean softHand = player.hasSoftHand();
        int p = player.getHandValue();
        int d = dealer.getHand().get(1).getRank();
        return BasicStrategy.generate(!stand17, softHand, p, d);
    }

    /**
     * Add a card to the running count.
     * @param rank the card's value
     */
    public void updateRunningCount(int rank) {
        if (rank >= 2 && rank <= 6) {
            runningCount++;
        } else if (rank == 1 || rank == 10) {
            runningCount--;
        }
    }

    /**
     * Return the running count.
     * @return the running count
     */
    public int getRunningCount() {
        return runningCount;
    }

    /**
     * Resets the running count to 0.
     */
    public void resetRunningCount() {
        runningCount = 0;
    }

    /**
     * Returns the true count of cards played in the shoe.
     * 
     * <p>The true count is calculated by dividing the running count by the
     * number of decks remaining.
     * 
     * @return the true count 
     */
    public int tCount() {
        if (shoe.getClass() == Shoe.class) {
            Shoe s = (Shoe) this.shoe;
            if (s.deckCount() > 0) {
                return Math.round(runningCount / s.deckCount());
            }
        }
        return runningCount;
    }

    /**
     * The required amount of chips for the player to be able to play.
     * @return the minimum bet 
     */
    public int minimumBet() {
        return minimumBet;
    }

    /**
     * Determines if the player has enough chips to continue playing.
     * @return true if the chips are less than the minimum bet
     */
    public boolean outOfChips() {
        return player.getBankroll() < minimumBet;
    }

    /**
     * This method determines if the player has blackjack.
     * 
     * <p>A blackjack is defined as two cards totaling 21. These two cards are 
     * a ten-value card, such as a 10, king, queen or jack, and an ace. This is
     * also known as a <b>natural 21</b>.
     * 
     * @return true if the player has blackjack
     */
    public boolean playerHasBlackjack() {
        return player.hasBlackjack();
    }

    /**
     * Adds a card to the player's  hand.
     * @param card the card to be added
     */
    public void playerHit(Card card) {
        player.hit(card);
    }

    /**
     * This method sets the existing bet to 0.
     */
    public void resetBet() {
        player.setBet(0);
    }

    /**
     * This method resets the player and dealer's hands.
     */
    public void resetHand() {
        player.discardHand(discardDeck);
        dealer.discardHand(discardDeck);
    }

    public void restartGame() {
        // player = new BlackjackPlayer(BANKROLL);
        dealer = new Dealer();
        discardDeck = new ArrayList<>();
        runningCount = 0;
    }

    /**
     * This method returns the player's bet.
     */
    public void returnBet() {
        player.addChips(player.getBet());
        player.setBet(0);
    }

    /**
     * Returns all the discarded cards back to the shoe.
     */
    public void shuffleDeck() {
        shoe.reset(discardDeck);
    }

    /**
     * This method determines if the player's hand has busted.
     * @return true if player's hand has busted
     */
    public boolean wentOver() {
        return !player.isBelowLimit();
    }

    /**
     * This method determines if the player has won.
     * @return true if the player has won
     */
    public boolean playerWon() {
        return won(player, dealer);
    }

    public double playerBet() {
        return player.getBet();
    }

    public double playerChips() {
        return player.getBankroll();
    }

    public String guessDealerHandValue() {
        int visible = dealerFrontCard();
        if (visible == 11) {
            return "12-21";
        }
        return visible + "-" + (visible + 11);
    }

    public String[] dealerCardNames() {
        int handSize = dealer.getHand().size();
        String[] cardNames = new String[handSize];
        for (int i = 0; i < handSize; i++) {
            cardNames[i] = dealer.getHand().get(i).toString();
        }
        return cardNames;
    }

    public int dealerFrontCard() {
        int value = dealer.getHand().get(0).getRank();
        return value == 1 ? 11 : value;
    }

    public int dealerHandValue() {
        return dealer.getHandValue();
    }

    public boolean dealerHasSoftHand() {
        return dealer.hasSoftHand();
    }

    public Card holeCard() {
        return dealer.getHand().get(1);
    }

    public String[] playerCardNames() {
        return player.getHand().stream().map(card -> {
            return card.toString();
        }).toArray(String[]::new);
    }

    public String[] initialCards() {
        String[] firstTwoCards = new String[2];
        firstTwoCards[0] = player.getHand().get(0).toString();
        firstTwoCards[1] = player.getHand().get(1).toString();
        return firstTwoCards;
    }

    public int playerHandValue() {
        return player.getHandValue();
    }

    public String playerName() {
        return player + "";
    }

    private boolean bothBelowLimit(Player player, Player opponent) {
        return player.isBelowLimit() && opponent.isBelowLimit();
    }

    private boolean isDoubleBlackjack(Player player, Player opponent) {
        return player.hasBlackjack() && opponent.hasBlackjack();
    }

    private boolean isEqualHand(Player player, Player opponent) {
        if (player.getHandValue() == 21 && opponent.getHandValue() == 21) {
            return isDoubleBlackjack(player, opponent) 
                    || noOneHasBlackjack(player, opponent);
        }
        return player.getHandValue() == opponent.getHandValue();
    }

    private boolean noOneHasBlackjack(Player player, Player opponent) {
        return !player.hasBlackjack() && !opponent.hasBlackjack();
    }

    private boolean won(Player player, Player opponent) {
        if (player.hasBlackjack()) {
            return !opponent.hasBlackjack();
        }
        if (bothBelowLimit(player, opponent)) {
            return player.getHandValue() > opponent.getHandValue();
        }
        return player.isBelowLimit();
    }

    private BlackjackPlayer player;
    private Dealer dealer;
    private CardContainer shoe;
    private List<Card> discardDeck;
    private int minimumBet;
    private int runningCount;
    private static final int NUMBER_OF_DECKS = 4;
    private static final String[] CHOICES = {
        "Hit", "Double Down", "Surrender", "Stand"
    };
    private static final String[] OPTIONS = {
        "Deal", "Next Hand", "Hint", "New Game", "Quit Game"
    };
    private static final int[] CHIPS = {5, 10, 25, 50, 100};
    private boolean stand17;
}
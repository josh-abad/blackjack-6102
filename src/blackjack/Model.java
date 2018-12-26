package blackjack;

import java.util.ArrayList;

public class Model {
    
    private UserPlayer player;
    private Dealer dealer;
    private Deck deck;
    public final int MINIMUM_BET;
    public final int[] BET_VALUES = {5, 10, 25, 50, 100};

    public Model() {
        player = new UserPlayer();
        dealer = new Dealer();
        deck = new Deck();
        MINIMUM_BET = 25;
    }

    public UserPlayer getPlayer() {
        return player;
    }

    public ArrayList<Card> getPlayerHand() {
        return player.getHand();
    }

    public int getPlayerHandValue() {
        return player.getHandValue();
    }

    public double getBet() {
        return player.getBet();
    }

    public boolean betIsEmpty() {
        return player.getBet() == 0;
    }

    public void addBet(double amount) {
        player.addBet(amount);
    }

    public double getChips() {
        return player.getChips();
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void givePayout(Payout type) {
        switch (type) {
            case BLACKJACK:
                player.addChips(player.getBet() + (player.getBet() * 1.5));
                break;
            case REGULAR:
                player.addChips(player.getBet() * 2);
                break;
            case HALF:
                player.addChips(player.getBet() / 2);
                break;
        }
        resetBet();
    }

    public ArrayList<Card> getDealerHand() {
        return dealer.getHand();
    }

    public int getDealerHandValue() {
        return dealer.getHandValue();
    }

    public Card getHoleCard() {
        return dealer.getHand().get(0);
    }

    public Deck getDeck() {
        return deck;
    }

    public Card drawCard() {
        return deck.drawCard();
    }

    public void setBet(double amount) {
        player.setBet(amount);
    }

    public void doubleBet() {
        player.doubleBet();
    }

    public void resetBet() {
        player.setBet(0);
    }

    public void playerHit(Card card) {
        player.hit(card);
    }

    public void dealerHit(Card card) {
        dealer.hit(card);
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public void dealerTurn() {
        if (dealer.hasSoft17() || dealer.getHandValue() <= 16) {
            Card card = deck.drawCard();
            dealer.hit(card);
        }
    }

    private boolean bothBelowLimit() {
        return player.isBelowLimit() && dealer.isBelowLimit();
    }

    private boolean isDoubleBlackjack() {
        return player.hasBlackjack() && dealer.hasBlackjack();
    }

    private boolean isEqualHand() {
        return !isDoubleBlackjack() 
                && player.getHandValue() == dealer.getHandValue();
    }

    public boolean isTie() {
        return (isDoubleBlackjack()) || (isEqualHand());
    }

    public boolean playerWon() {
        if (bothBelowLimit()) {
            return player.getHandValue() > dealer.getHandValue();
        }
        return player.isBelowLimit();
    }

    public boolean dealerWon() {
        if (bothBelowLimit()) {
            return dealer.getHandValue() > player.getHandValue();
        }
        return dealer.isBelowLimit();
    }

    public void returnBet() {
        player.addChips(player.getBet());
        player.setBet(0);
    }

    public void playerGetPayout() {
        double payout = player.getBet();
        if (player.hasBlackjack()) {
            payout += player.getBet() * 1.5;
        } else {
            payout *= 2;
        }
        player.addChips(payout);
        player.setBet(0);
    }

    public void playerLoseBet() {
        player.setBet(0);
    }

    public boolean playerIsOutOfChips() {
        return player.getChips() < MINIMUM_BET;
    }

    public boolean playerCanDoubleDown() {
        return player.getBet() * 2 <= player.getChips();
    }

    public boolean betSufficient() {
        return player.getBet() >= MINIMUM_BET;
    }

    public boolean playerWentOver() {
        return !player.isBelowLimit();
    }
}

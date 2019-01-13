package blackjack;

public class Dealer extends Player {

    public boolean hasSoft17() {
        return hasAce() && getHandValue() == 17;
    }
}
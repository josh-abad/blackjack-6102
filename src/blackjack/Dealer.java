package blackjack;

public class Dealer extends Player {

    @Override
    public String toString() {
        return "Dealer";
    }

    public boolean hasSoft17() {
        return hasAce() && getHandValue() == 17;
    }
}
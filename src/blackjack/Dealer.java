package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends Player {

    /**
     * Determines if this {@code Dealer} has a soft 17. A hand that contains an
     * {@code Ace} counted as 11 is a soft hand. If the hand is soft and has a
     * value of 17, it is a soft 17.
     * @return true if the hand has a value of 17 and they have an {@code Ace} 
     * counted as 11
     */
    public boolean hasSoft17() {
        if (getHandValue() == 17 && hasAce()) {
            int total = 0;
            for (Card card : getHand()) {
                total += (card instanceof Ace) ? 0 : card.getRank();
            }
            return total + 11 == 17 || total == 0;
        }
        return false;
    }
}
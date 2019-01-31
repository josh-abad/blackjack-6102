package playingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe implements CardContainer {

    /**
     * Creates a new {@code Shoe} with the specified number of decks.
     * 
     * <p>A shoe is a device used to hold multiple decks of playing cards. The
     * show allows for more games to be played by reducing time between
     * shuffles. In blackjack, using multiple decks of cards can increase the
     * house edge.
     * 
     * @param decks the number of decks
     * @throws NegativeArraySizeException if {@code decks} is negative
     */
    public Shoe(int decks) {
        if (decks < 0) throw new NegativeArraySizeException();
        shoe = new ArrayList<>();
        Deck deck = new Deck();
        for (int i = 0; i < decks; i++) {
            shoe.addAll(deck.getCards());
        }
    }

    public int deckCount() {
        return shoe.size() / 52;
    }

    /**
     * Returns a card drawn from this shoe.
     * @return a card
     */
    @Override
    public Card drawCard() {
        int topCard = shoe.size() - 1;
        if (!shoe.isEmpty()) {
            Card pickedCard = shoe.get(topCard);
            shoe.remove(topCard);
            return pickedCard;
        }
        return null;
    }

    /**
     * Determines if there are no more cards left in this shoe.
     * @return true if there are no more cards left in this shoe
     */
    @Override
    public boolean isEmpty() {
        return shoe.isEmpty();
    }

    /**
     * Resets this shoe with cards that were discarded.
     * @param discarded hand that will be added
     */
    @Override
    public void reset(List<Card> discarded) {
        discarded.forEach(shoe::add);
        discarded.clear();
        shuffle();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(shoe);
    }

    @Override
    public int size() {
        return shoe.size();
    }
    
    private final List<Card> shoe;
}

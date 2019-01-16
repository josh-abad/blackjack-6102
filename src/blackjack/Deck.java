package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    
    /**
     * Creates a new {@code Deck} with the standard 52 cards.
     */
    public Deck() {
        this(1);
    }

    public Deck(int amount) {
        deck = new ArrayList<>(); 
        for (int i = 0; i < amount; i++) {
            for (Suit suit : Suit.values()) {
                for (int rank = 2; rank <= 10; rank++) {
                    deck.add(new PipCard(rank, suit));
                }
                for (Face face : Face.values()) {
                    deck.add(new FaceCard(face, suit));
                }
                deck.add(new Ace(suit));
            }
        }
    }

    /**
     * Adds a hand to this {@code Deck}
     * @param hand hand that will be added
     */
    public void add(List<Card> hand) {
        hand.forEach(deck::add);
    }

    /**
     * Draws a {@link Card} from this {@code Deck}
     * @return a {@link Card}
     */
    public Card drawCard() {
        int index = (int) (Math.random() * deck.size());
        Card pickedCard = deck.get(index);
        deck.remove(index);
        return pickedCard;
    }

    /**
     * Shuffles this {@code Deck}
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }
    
    private final List<Card> deck;
}
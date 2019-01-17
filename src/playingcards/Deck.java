package playingcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A deck of cards.
 * @author Joshua Abad
 */
public class Deck {
    
    /**
     * Creates a new {@code Deck} with the standard 52 playing cards.
     * 
     * <p>It includes thirteen ranks in each of the four suits: clubs, diamonds,
     * hearts and spades. Each suit includes an ace, a king, queen and jack, and
     * ranks two through ten.
     */
    public Deck() {
        this(1);
    }

    /**
     * Creates a new {@code Deck} with the specified amount of standard decks.
     * @param amount the amount of standard decks
     */
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
     * Adds a hand to this deck.
     * @param hand hand that will be added
     */
    public void add(List<Card> hand) {
        hand.forEach(deck::add);
    }

    /**
     * Returns a card drawn from this deck.
     * @return a card
     */
    public Card drawCard() {
        int index = (int) (Math.random() * deck.size());
        Card pickedCard = deck.get(index);
        deck.remove(index);
        return pickedCard;
    }

    /**
     * Shuffles this deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }
    
    private final List<Card> deck;
}
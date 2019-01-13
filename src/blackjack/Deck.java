package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    
    public Deck() {
        deck = new ArrayList<>(); 

        for (Suit suit : Suit.values()) {
            for (int value = 2; value <= 10; value++) {
                deck.add(new Card(value, suit));
            }
            for (Face face : Face.values()) {
                deck.add(new FaceCard(face, suit));
            }
            deck.add(new Ace(suit));
        }
    }

    public void add(List<Card> hand) {
        hand.forEach(deck::add);
    }

    public Card drawCard() {
        int index = (int) (Math.random() * deck.size());
        Card pickedCard = deck.get(index);
        deck.remove(index);
        return pickedCard;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }
    
    private final List<Card> deck;
}
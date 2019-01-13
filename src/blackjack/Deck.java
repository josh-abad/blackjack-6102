package blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
    
    public Deck() {
        deck = new ArrayList<>(); 

        Arrays.asList(Suit.values()).forEach((suit) -> {
            for (int value = 1; value <= 13; value++) {
                if (value == 1) {
                    deck.add(new Ace(suit));
                } else if (value > 10) {
                    deck.add(new FaceCard(value, suit));
                } else {
                    deck.add(new Card(value, suit));
                }
            }
        });
        for (Suit suit : Suit.values()) {
            for (int value = 1; value <= 13; value++) {
                if (value == 1) {
                    deck.add(new Ace(suit));
                } else if (value > 10) {
                    deck.add(new FaceCard(value, suit));
                } else {
                    deck.add(new Card(value, suit));
                }
            }
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
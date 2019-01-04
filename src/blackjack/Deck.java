package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    
    public Deck() {
        deck = new ArrayList<>(); 

        final String VALID_SUITS[] = {
            "Diamonds", "Clubs", "Spades", "Hearts"
        };
        for (String suit : VALID_SUITS) {
            for (int i = 1; i <= 13; i++) {
                if (i == 1) {
                    deck.add(new Ace(suit));
                } else if (i > 10) {
                    deck.add(new FaceCard(i, suit));
                } else {
                    deck.add(new Card(i, suit));
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
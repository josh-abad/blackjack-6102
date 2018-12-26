package blackjack;

import java.util.ArrayList;

public class Deck {
    
    private final ArrayList<Card> deck;
    
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

    public Card drawCard() {
        int index = (int) (Math.random() * this.deck.size());
        Card pickedCard = this.deck.get(index);
        this.deck.remove(index);
        return pickedCard;
    }

    public void add(ArrayList<Card> hand) {
        hand.forEach((card) -> {
            this.deck.add(card);
        });
    }

    public void shuffle() {
        this.deck.stream().map((card) -> card).forEachOrdered((firstCard) -> {
            int index = (int) (Math.random() * this.deck.size());
            Card secondCard = this.deck.get(index);
            this.deck.set(index, firstCard);
            this.deck.set(this.deck.indexOf(firstCard), secondCard);
        });
    }
}
package blackjack;

import java.util.ArrayList;

public class Deck {
    
    private final ArrayList<Card> deck;
    
    public Deck() {
        deck = new ArrayList<>(); 
        final String VALID_VALUES[] = {
            "Ace", "2", "3", "4", "5", "6", "7", 
            "8", "9", "10", "Jack", "Queen", "King"
        };
        final String VALID_SUITS[] = {
            "Diamonds", "Clubs", "Spades", "Hearts"
        };
        for (String suit : VALID_SUITS) {
            for (String value : VALID_VALUES) {
                if (value.equals("Ace")) {
                    deck.add(new Ace(suit));
                } else if (value.chars().allMatch(Character::isDigit)) {
                    deck.add(new Card(Integer.parseInt(value), suit));
                } else {
                    deck.add(new FaceCard(value, suit));
                }
            }
        }
    }

    public Card getCard() {
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
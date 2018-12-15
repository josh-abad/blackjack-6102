package blackjack;

import java.util.ArrayList;

public class Deck {
    
    private final ArrayList<Card> deck;
    
    public Deck() {
        deck = new ArrayList<Card>() {
            {
                // Ace 🂡
                add(new Ace("Diamonds"));                     // Ace of ♦
                add(new Ace("Hearts"));                       // Ace of ♥
                add(new Ace("Spades"));                       // Ace of ♠
                add(new Ace("Clubs"));                        // Ace of ♣

                // 2 🂢
                add(new Card(2, "Diamonds"));                 // 2 of ♦
                add(new Card(2, "Hearts"));                   // 2 of ♥
                add(new Card(2, "Spades"));                   // 2 of ♠
                add(new Card(2, "Clubs"));                    // 2 of ♣

                // 3 🂣
                add(new Card(3, "Diamonds"));                 // 3 of ♦
                add(new Card(3, "Hearts"));                   // 3 of ♥
                add(new Card(3, "Spades"));                   // 3 of ♠
                add(new Card(3, "Clubs"));                    // 3 of ♣

                // 4 🂤
                add(new Card(4, "Diamonds"));                 // 4 of ♦
                add(new Card(4, "Hearts"));                   // 4 of ♥
                add(new Card(4, "Spades"));                   // 4 of ♠
                add(new Card(4, "Clubs"));                    // 4 of ♣

                // 5 🂥
                add(new Card(5, "Diamonds"));                 // 5 of ♦
                add(new Card(5, "Hearts"));                   // 5 of ♥
                add(new Card(5, "Spades"));                   // 5 of ♠
                add(new Card(5, "Clubs"));                    // 5 of ♣

                // 6 🂦
                add(new Card(6, "Diamonds"));                 // 6 of ♦
                add(new Card(6, "Hearts"));                   // 6 of ♥
                add(new Card(6, "Spades"));                   // 6 of ♠
                add(new Card(6, "Clubs"));                    // 6 of ♣

                // 7 🂧
                add(new Card(7, "Diamonds"));                 // 7 of ♦
                add(new Card(7, "Hearts"));                   // 7 of ♥
                add(new Card(7, "Spades"));                   // 7 of ♠
                add(new Card(7, "Clubs"));                    // 7 of ♣

                // 8 🂨
                add(new Card(8, "Diamonds"));                 // 8 of ♦
                add(new Card(8, "Hearts"));                   // 8 of ♥
                add(new Card(8, "Spades"));                   // 8 of ♠
                add(new Card(8, "Clubs"));                    // 8 of ♣

                // 9 🂩
                add(new Card(9, "Diamonds"));                 // 9 of ♦
                add(new Card(9, "Hearts"));                   // 9 of ♥
                add(new Card(9, "Spades"));                   // 9 of ♠
                add(new Card(9, "Clubs"));                    // 9 of ♣

                // 10 🂪
                add(new Card(10, "Diamonds"));                // 10 of ♦
                add(new Card(10, "Hearts"));                  // 10 of ♥
                add(new Card(10, "Spades"));                  // 10 of ♠
                add(new Card(10, "Clubs"));                   // 10 of ♣

                // Jack 🂫
                add(new FaceCard("Jack", "Diamonds"));        // Jack of ♦
                add(new FaceCard("Jack", "Hearts"));          // Jack of ♥
                add(new FaceCard("Jack", "Spades"));          // Jack of ♠
                add(new FaceCard("Jack", "Clubs"));           // Jack of ♣

                // Queen 🂭
                add(new FaceCard("Queen", "Diamonds"));       // Queen of ♦
                add(new FaceCard("Queen", "Hearts"));         // Queen of ♥
                add(new FaceCard("Queen", "Spades"));         // Queen of ♠
                add(new FaceCard("Queen", "Clubs"));          // Queen of ♣

                // King 🂬
                add(new FaceCard("King", "Diamonds"));        // King of ♦
                add(new FaceCard("King", "Hearts"));          // King of ♥
                add(new FaceCard("King", "Spades"));          // King of ♠
                add(new FaceCard("King", "Clubs"));           // King of ♣
            }
            // final String VALID_VALUES = {"Ace"
        };
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


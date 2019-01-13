package blackjack;

public class FaceCard extends Card {

    public FaceCard(Face face, Suit suit) {
        super(10, suit);
        this.face = face;
    }

    public Face getFace() {
        return face;
    }

    @Override
    public String toString() {
        return face + " of " + getSuit();
    }

    private final Face face;
}
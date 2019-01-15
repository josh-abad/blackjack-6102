package blackjack;

/**
 * Face Cards are valued at 10 and are identified by their {@link Face}
 * @author Joshua Abad
 */
public class FaceCard extends Card {

    /**
     * FaceCard Constructor
     * @param face this card's {@link Face}
     * @param suit this card's {@link Suit}
     */
    public FaceCard(Face face, Suit suit) {
        super(10, suit);
        this.face = face;
    }

    /**
     * Return the {@link Face} of this card
     * @return this card's {@link Face}
     */
    public Face getFace() {
        return face;
    }

    @Override
    public String toString() {
        return face + " of " + getSuit();
    }

    private final Face face;
}
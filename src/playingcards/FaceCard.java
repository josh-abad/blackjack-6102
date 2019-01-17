package playingcards;

/**
 * A ten-value playing card denoting a person.
 * @author Joshua Abad
 */
public class FaceCard extends Card {

    /**
     * Creates a new {@code FaceCard} with the specified face and suit.
     * 
     * <p>In a standard 52-card deck, face cards are cards that depict a person
     * instead of symbols on the front side of the card. Every face cards has 
     * a value of 10.
     * 
     * @param   face possible face values are: {@code KING}, {@code QUEEN}, 
     *          {@code JACK}
     * @param   suit possible suit values are: {@code SPADES}, {@code HEARTS},
     *          {@code CLUBS} and {@code DIAMONDS}
     */
    public FaceCard(Face face, Suit suit) {
        super(10, suit);
        this.face = face;
    }

    /**
     * Returns the face of this card.
     * @return the face of this card
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
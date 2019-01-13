package blackjack;

import java.util.HashMap;
import java.util.Map;

public class FaceCard extends Card {

    public FaceCard(int value, Suit suit) {
        super(value, suit);

        Map<Integer, String> faces = new HashMap<>();
        faces.put(11, "Jack");
        faces.put(12, "Queen");
        faces.put(13, "King");
         
        this.face = faces.get(value);
    }

    public String getFace() {
        return face;
    }

    @Override
    public String toString() {
        return face + " of " + getSuit();
    }

    private final String face;
}
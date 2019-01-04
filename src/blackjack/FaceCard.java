package blackjack;

import java.util.HashMap;
import java.util.Map;

public class FaceCard extends Card {

    private final String face;

    public String getFace() {
        return face;
    }

    public FaceCard(int value, String suite) {
        super(value, suite);

        Map<Integer, String> faces = new HashMap<>();
        faces.put(11, "Jack");
        faces.put(12, "Queen");
        faces.put(13, "King");
         
        this.face = faces.get(value);
    }

    @Override
    public String toString() {
        return face + " of " + getSuit();
    }
}
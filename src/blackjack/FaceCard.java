package blackjack;

import java.util.HashMap;

public class FaceCard extends Card {

    private final String face;

    public String getFace() {
        return face;
    }

    public FaceCard(int value, String suite) {
        super(value, suite);

        HashMap<Integer, String> faces = new HashMap<Integer, String>() {
            {
                put(11, "Jack");
                put(12, "Queen");
                put(13, "King");
            }
        }; 
        this.face = faces.get(value);
    }

    @Override
    public String toString() {
        return this.face + " of " + this.getSuit();
    }
}
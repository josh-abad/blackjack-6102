package blackjack;

public class FaceCard extends Card {

    private final String face;

    public String getFace() {
        return face;
    }

    public FaceCard(String face, String suite) {
        super(10, suite);
        this.face = face;
    }

    @Override
    public String toString() {
        return this.face + " of " + this.getSuite();
    }
}


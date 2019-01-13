package blackjack;

public enum Face {
    
    KING("King"),
    JACK("Jack"),
    QUEEN("Queen");

    private String face;

    Face(String face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return face;
    }
}

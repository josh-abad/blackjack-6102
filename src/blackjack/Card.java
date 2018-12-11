package blackjack;

public class Card {

    private int value;
    final private String suite;

    public Card(int value, String suite) {
        if (value > 1 && value <= 10) {
            this.value = value;
        }
        this.suite = suite;
    }

    @Override
    public String toString() {
        return this.value + " of " + this.suite;
    }

    public String getSuite() {
        return this.suite;
    }

    public int getValue() {
        return this.value;
    }
}


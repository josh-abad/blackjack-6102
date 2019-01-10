package blackjack;

public enum Payout {

    REGULAR, 
    BLACKJACK, 
    HALF;

    public double getPayout(double bet) {
        double payout = 0;
        switch (this) {
            case REGULAR: 
                payout = regular(bet);
                break;
            case BLACKJACK: 
                payout = blackjack(bet);
                break;
            case HALF: 
                payout = half(bet);
                break;
        }
        return payout;
    }

    private double regular(double bet) {
        return bet * 2;
    }

    private double blackjack(double bet) {
        return bet + (bet * 1.5);
    }

    private double half(double bet) {
        return bet / 2;
    }
}
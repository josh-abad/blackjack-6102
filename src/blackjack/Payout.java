package blackjack;

public interface Payout {
    
    public static final Payout BLACKJACK = (bet) -> bet + (bet * 1.5);
    public static final Payout REGULAR = (bet) -> bet * 2;
    public static final Payout HALF = (bet) -> bet / 2;

    double getPayout(double bet);
}

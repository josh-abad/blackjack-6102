package com.yeyoan.blackjack.model;

/**
 * Basic strategy for blackjack.
 * @author yeyoa
 */
public class BasicStrategy {

    public enum Action {

        H("Hit"),
        S("Stand"),
        DH("Double Down"),
        DS("Double Down"),
        RS("Surrender"),
        RH("Surrender"),
        NONE("N/A");

        private final String s;

        Action(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return s;
        }

        private static Action hardS17(int p, int d) {
            switch (p) {
                case 4: case 5: case 6: case 7: case 8: return H;
                case 9: return (d >= 3 && d <= 6) ? DH : H;
                case 10: return (d >= 2 && d <= 9) ? DH : H;
                case 11: return (d >= 2 && d <= 10) ? DH : H;
                case 12: return (d >= 4 && d <= 6) ? S : H;
                case 13: case 14: return (d >= 2 && d <= 6) ? S : H;
                case 15:
                    if (d >= 2 && d <= 6) return S;
                    else if ((d >= 7 && d <= 9) || d == 1) return H;
                    else return RH;
                case 16:
                    if (d >= 2 && d <= 6) return S;
                    else if (d == 7 || d == 8) return H;
                    else return RH;
                case 17: case 18: case 19: case 20: case 21: return S;
                default: return NONE;
            }
        }

        private static Action softS17(int p, int d) {
            switch (p) {
                case 12: return hardH17(p, d);
                case 13: case 14: return (d == 5 || d == 6) ? DH : H;
                case 15: case 16: return (d >= 4 && d <= 6) ? DH : H;
                case 17: return (d >= 3 && d <= 6) ? DH : H;
                case 18:
                    if (d >= 3 && d <= 6) return DS;
                    else if (d == 7 || d == 8 || d == 2) return S;
                    else return H;
                case 19: case 20: case 21: return S;
                default: return NONE;
            }
        }

        private static Action hardH17(int p, int d) {
            switch (p) {
                case 4: case 5: case 6: case 7: case 8: return H;
                case 9: return (d >= 3 && d <= 6) ? DH : H;
                case 10: return (d >= 2 && d <= 9) ? DH : H;
                case 11: return DH;
                case 12: return (d >= 4 && d <= 6) ? S : H;
                case 13: case 14: return (d >= 2 && d <= 6) ? S : H;
                case 15:
                    if (d >= 2 && d <= 6) return S;
                    else if (d >= 7 && d <= 9) return H;
                    else return RH;
                case 16:
                    if (d >= 2 && d <= 6) return S;
                    else if (d == 7 || d == 8) return H;
                    else return RH;
                case 17: return (d != 1) ? S : RS;
                case 18: case 19: case 20: case 21: return S;
                default: return NONE;
            }
        }

        private static Action softH17(int p, int d) {
            switch (p) {
                case 12: return hardH17(p, d);
                case 13: case 14: return (d == 5 || d == 6) ? DH : H;
                case 15: case 16: return (d >= 4 && d <= 6) ? DH : H;
                case 17: return (d >= 3 && d <= 6) ? DH : H;
                case 18:
                    if (d >= 2 && d <= 6) return DH;
                    else if (d == 7 || d == 8) return S;
                    else return H;
                case 19: return (d == 6) ? DS : S;
                case 20: case 21: return S;
                default: return NONE;
            }
        }
    }

    /**
     * Returns the action for the player to take based on basic strategy.
     * 
     * <p>The mathematically correct way to play blackjack, called basic
     * strategy, can cut the dealer's edge to 0.5%.
     * 
     * @param h17 if the dealer hits a soft 17
     * @param soft if the player has a soft hand
     * @param p the player's hand value
     * @param d the dealer's front facing card
     * @return the action
     */
    public static Action generate(boolean h17, boolean soft, int p, int d) {
        if (h17) {
            return (soft) ? Action.softH17(p, d) : Action.hardH17(p, d);
        }
        return (soft) ? Action.softS17(p, d) : Action.hardS17(p, d);
    }

    // Suppress default constructor for noninstantiability
    private BasicStrategy() {
        throw new AssertionError();
    }
}

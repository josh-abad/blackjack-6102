/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author yeyoa
 */
public class Dealer extends Player {

    @Override
    public String toString() {
        return "dealer";
    }

    @Override
    public void printHand() {
        System.out.println("The dealer's hand:\n");
        for (Card card : this.getHand()) {
            System.out.println("    " + card);
        }
    }

    public boolean hasSoft17() {
        return hasAce() && stand() == 17;
    }
}

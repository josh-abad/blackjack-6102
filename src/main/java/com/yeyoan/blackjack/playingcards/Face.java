package com.yeyoan.blackjack.playingcards;

/**
 * A face displayed on a face card.
 * @author yeyoa
 */
public enum Face {
    
    KING("King"),
    JACK("Jack"),
    QUEEN("Queen");

    private final String face;

    Face(String face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return face;
    }
}

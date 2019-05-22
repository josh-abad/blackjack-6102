package com.yeyoan.blackjack.playingcards;

import java.util.List;

public interface CardContainer {
    
    Card drawCard();
    boolean isEmpty();
    void reset(List<Card> discarded);
    void shuffle();
    int size();
}

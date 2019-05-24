package com.yeyoan.blackjack.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatsPaneController {

    public void updateStats(double bet, double chips, int tCount, int dCount) {
        trueCountLabel.setText(String.valueOf(tCount));
        deckCountLabel.setText(String.valueOf(dCount));
        playerBet.setText(String.valueOf(bet));
        playerChips.setText(String.valueOf(chips));
    }

    @FXML
    private Label deckCountLabel;    

    @FXML
    private Label trueCountLabel;    

    @FXML
    private Label playerBet;    

    @FXML
    private Label playerChips;    
}
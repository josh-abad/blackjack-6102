package com.yeyoan.blackjack.controller;

import com.yeyoan.blackjack.util.Format;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlayerInfoCardController {

    @FXML  
    private Text name;

    @FXML
    private Text handValue;

    @FXML
    private HandIndicatorController handIndicatorController;

    @FXML
    private Text playerChips;

    public void setName(String name) {
        this.name.setText(name);
    }

    public void clearHandValue() {
        handIndicatorController.setText("?");
        handValue.setText("No cards");
    }

    public void updateHandValue(int value, boolean softHand) {
        handIndicatorController.setText(softHand ? "S" : "H");
        handValue.setText(String.valueOf(value));
    }

    public void updatePlayerChips(double chips) {
        playerChips.setText(Format.currency(chips)
            + " chip" + (chips != 1 ? "s" : " "));
    }
}
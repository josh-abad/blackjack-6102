package com.yeyoan.blackjack.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DealerInfoCardController {

    @FXML  
    private Text name;

    @FXML
    private Text handValue;

    @FXML
    private HandIndicatorController handIndicatorController;

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

    public void updatePartialHandValue(int value) {
        handIndicatorController.setText("?");
        handValue.setText((value == 11) ? "12-21" : value + "-" + (value + 11));
    }
}
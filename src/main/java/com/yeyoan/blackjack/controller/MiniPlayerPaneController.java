package com.yeyoan.blackjack.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MiniPlayerPaneController {

    @FXML  
    private Text name;

    @FXML
    private Text handValue;

    public void setName(String name) {
        this.name.setText(name);
    }

    public void clearHandValue() {
        handValue.setText("No cards");
    }

    public void updateHandValue(int value, boolean softHand) {
        handValue.setText((softHand ? "Soft" : "Hard") + " " + value);
    }

    public void updatePartialHandValue(int value) {
        handValue.setText((value == 11) ? "12-21" : value + "-" + (value + 11));
    }
}
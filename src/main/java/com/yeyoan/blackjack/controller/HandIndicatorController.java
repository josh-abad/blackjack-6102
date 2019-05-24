package com.yeyoan.blackjack.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HandIndicatorController {

    @FXML  
    private Text indicatorText;

    public void setText(String text) {
        indicatorText.setText(text);
    }
}
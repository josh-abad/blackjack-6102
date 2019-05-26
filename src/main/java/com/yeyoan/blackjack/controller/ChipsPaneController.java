package com.yeyoan.blackjack.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.IntConsumer;

import com.jfoenix.controls.JFXButton;
import com.yeyoan.blackjack.model.Model;
import com.yeyoan.blackjack.util.Format;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ChipsPaneController implements Initializable {

    @FXML
    private HBox chipsBox;

    @FXML
    private Text playerBet;

    private List<JFXButton> chips;

    public void disableAllChips(boolean value) {
        chips.forEach(chip -> chip.setDisable(value));
    }

    public void updateChips(double amount) {
        chips.stream()
            .filter(chip -> Integer.parseInt(chip.getText()) > amount)
            .forEach(chip -> chip.setDisable(true));
    }

    public void updateBet(double bet) {
        playerBet.setText(Format.currency(bet));
    }
    
    public void setChipAction(IntConsumer chipAction) {
        chips.forEach(chip -> {
            chip.setOnAction(event -> {
                chipAction.accept(Integer.parseInt(chip.getText()));
            });
        });
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        chips = new ArrayList<>();
        Arrays.stream(Model.chips())
            .mapToObj(Integer::toString)
            .map(JFXButton::new)
            .forEach(button -> {
                chips.add(button);
                chipsBox.getChildren().add(button);
            });
    }
}
package com.yeyoan.blackjack.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.yeyoan.blackjack.model.Model;
import com.yeyoan.blackjack.model.Payout;
import com.yeyoan.blackjack.playingcards.Card;
import com.yeyoan.blackjack.view.Message;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Controller implements Initializable {

    @FXML
    private HBox playerSide;

    @FXML private Text playerHandValue;
    @FXML private Text dealerHandValue;

    @FXML
    private HBox dealerSide;

    @FXML
    private BorderPane root;

    @FXML private Label deckCountLabel;
    @FXML private Label trueCountLabel;

    @FXML private JFXButton hitButton;
    @FXML private JFXButton doubleDownButton;
    @FXML private JFXButton surrenderButton;
    @FXML private JFXButton standButton;

    @FXML private JFXButton hintButton;
    @FXML private JFXButton nextHandButton;

    private JFXSnackbar snackbar;

    Model model;

    @FXML
    protected void handleHitAction() {
        Card card = model.drawCard();
        model.playerHit(card);
        model.updateRunningCount(card.getRank());
        deckCountLabel.setText(model.deckCount() + "");
        trueCountLabel.setText(model.getTrueCount() + "");
        snackbar.enqueue(new SnackbarEvent(new JFXSnackbarLayout(Message.hit(card + ""))));
        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        surrenderButton.setDisable(true);
        if (model.wentOver() || model.shoeIsEmpty()) {
            hitButton.setDisable(true);
            doubleDownButton.setDisable(true);
            surrenderButton.setDisable(true);
            hintButton.setDisable(true);
            if (model.shoeIsEmpty()) {
                snackbar.enqueue(new SnackbarEvent(new JFXSnackbarLayout(Message.deckIsEmpty())));
            }
        }
        playerSide.getChildren().add(model.getCardImage(card));
    }

    @FXML
    protected void handleDoubleDownAction() {
        model.doubleBet();
        Card card = model.drawCard();
        model.playerHit(card);
        model.updateRunningCount(card.getRank());
        deckCountLabel.setText(model.deckCount() + "");
        trueCountLabel.setText(model.getTrueCount() + "");
        snackbar.enqueue(new SnackbarEvent(new JFXSnackbarLayout(Message.doubleDown(model.playerBet(), card + ""))));
        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        // TODO: do this
        // view.updateStats(model.playerChips(), model.playerBet());
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        hintButton.setDisable(true);
        playerSide.getChildren().add(model.getCardImage(card));
    }

    @FXML
    protected void handleSurrenderAction() {
        snackbar.enqueue(new SnackbarEvent(new JFXSnackbarLayout(Message.surrender(model.playerBet()))));
        model.givePayout(Payout.HALF);
        model.resetBet();
        // view.revealHoleCard(model.holeCard());
        // model.updateRunningCount(model.holeCard().getRank());
        trueCountLabel.setText(model.getTrueCount() + "");
        dealerHandValue.setText((model.dealerHasSoftHand() ? "Soft" : "Hard") + " " + model.dealerHandValue());
        // view.updateStats(model.playerChips(), model.playerBet());
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        standButton.setDisable(true);
        nextHandButton.setDisable(false);
        hintButton.setDisable(true);
    }

    @FXML
    protected void handleQuitAction() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        snackbar = new JFXSnackbar(root);
        model = new Model();
    }
}
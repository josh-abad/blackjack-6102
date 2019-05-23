package com.yeyoan.blackjack.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Controller implements Initializable {

    @FXML
    private HBox playerSide;

    @FXML private Text playerHandValue;
    @FXML private Text dealerHandValue;

    @FXML
    private HBox dealerSide;

    @FXML
    private StackPane root;

    @FXML private Label deckCountLabel;
    @FXML private Label trueCountLabel;

    @FXML private JFXButton hitButton;
    @FXML private JFXButton doubleDownButton;
    @FXML private JFXButton surrenderButton;
    @FXML private JFXButton standButton;

    @FXML private JFXButton hintButton;
    @FXML private JFXButton dealButton;
    @FXML private JFXButton nextHandButton;

    private JFXSnackbar snackbar;
    @FXML private JFXDialog dialog;
    @FXML private DialogController dialogController;

    private Model model;

    @FXML
    protected void handleHitAction() {
        Card card = model.drawCard();
        model.playerHit(card);
        model.updateRunningCount(card.getRank());
        deckCountLabel.setText(model.deckCount() + "");
        trueCountLabel.setText(model.getTrueCount() + "");
        displayMessage(Message.hit(card + ""));
        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        surrenderButton.setDisable(true);
        if (model.wentOver() || model.shoeIsEmpty()) {
            hitButton.setDisable(true);
            doubleDownButton.setDisable(true);
            surrenderButton.setDisable(true);
            hintButton.setDisable(true);
            if (model.shoeIsEmpty()) {
                displayMessage(Message.deckIsEmpty());
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
        displayMessage(Message.doubleDown(model.playerBet(), card + ""));
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
        displayMessage(Message.surrender(model.playerBet()));
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
    protected void handleStandAction() {
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        standButton.setDisable(true);
        hintButton.setDisable(true);
        dealerSide.getChildren().remove(1);
        dealerSide.getChildren().add(model.getCardImage(model.holeCard()));

        while (model.dealerHasSoft17() || model.dealerHandValue() <= 16) {
            if (model.shoeIsEmpty()) {
                break;
            }
            Card card = model.drawCard();
            model.dealerHit(card);
            dealerSide.getChildren().add(model.getCardImage(card));
            model.updateRunningCount(card.getRank());
        }

        deckCountLabel.setText(model.deckCount() + "");
        trueCountLabel.setText(model.getTrueCount() + "");

        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        dealerHandValue.setText((model.dealerHasSoftHand() ? "Soft" : "Hard") + " " + model.dealerHandValue());

        String message;
        if (model.isTie()) {
            displayMessage(Message.tie());
            model.returnBet();
        } else if (model.playerWon()) {
            if (model.playerHasBlackjack()) {
                message = Message.playerBlackjack(model.playerBet());
                model.givePayout(Payout.BLACKJACK);
            } else {
                message = Message.playerWon(model.playerBet());
                model.givePayout(Payout.REGULAR);
            }
            displayMessage(message);
        } else if (model.playerLost()) {
            message = (model.dealerHasBlackjack()) ?
                    Message.dealerBlackjack(model.playerBet()) :
                    Message.playerLost(model.playerBet());
            displayMessage(message);
            model.resetBet();
        } else {
            displayMessage(Message.bothOver());
        }

        if (model.outOfChips()) {
            displayMessage(Message.outOfChips());
        } else {
            nextHandButton.setDisable(false);
        }
    
        // view.updateStats(model.playerChips(), model.playerBet()); 
    }

    @FXML
    protected void handleDealAction() {
        // view.disableAllChips();
        hitButton.setDisable(false);
        doubleDownButton.setDisable(false);
        surrenderButton.setDisable(false);
        standButton.setDisable(false);
        hintButton.setDisable(false);
        dealButton.setDisable(true);

        if (model.betIsEmpty() || !model.canDoubleDown()) {
            doubleDownButton.setDisable(true);
        }
    
        for (int i = 0; i < 2; i++) {
            Card playerCard = model.drawCard();
            Card dealerCard = model.drawCard();
            model.playerHit(playerCard);
            model.updateRunningCount(playerCard.getRank());
            model.dealerHit(dealerCard);
            playerSide.getChildren().add(model.getCardImage(playerCard));
            if (i != 0) {
                model.updateRunningCount(dealerCard.getRank());
                dealerSide.getChildren().add(model.getCardBack());
            } else {
                dealerSide.getChildren().add(model.getCardImage(dealerCard));
            }
        }
        trueCountLabel.setText(model.getTrueCount() + "");
        displayMessage(Message.deal(model.initialCards()));

        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        // view.updateDealerHandValue(model.dealerFrontCard());
        // view.hideHoleCard();
        deckCountLabel.setText(model.deckCount() + "");
    }

    @FXML
    protected void handleNextHandAction() {
        displayMessage(Message.nextHand());
        playerSide.getChildren().clear();
        dealerSide.getChildren().clear();

        if (!model.shoeIsSufficient()) {
            model.shuffleDeck();
            model.resetRunningCount();
            displayMessage(Message.reshuffle());
        }

        playerHandValue.setText("No cards");
        dealerHandValue.setText("No cards");
        nextHandButton.setDisable(true);
        hintButton.setDisable(true);
        model.resetHand();

        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        standButton.setDisable(true);
        if (model.betIsSufficient()) {
            dealButton.setDisable(false);
        } else {
            displayBetDialog();
            dealButton.setDisable(true);
        }

        // view.enableAllChips();
        // view.updateChips(model.playerChips(), Model.chips());
        // view.updateStats(model.playerChips(), model.playerBet());
        deckCountLabel.setText(model.deckCount() + "");
    }

    @FXML
    protected void handleQuitAction() {
        Platform.exit();
    }

    private void displayMessage(String message) {
        snackbar.enqueue(
            new SnackbarEvent(
                new JFXSnackbarLayout(message)
            )
        );
    }

    private void displayBetDialog() {
        dialog.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        snackbar = new JFXSnackbar(root);
        model = new Model();
    }
}
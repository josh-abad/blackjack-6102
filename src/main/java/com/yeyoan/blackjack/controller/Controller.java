package com.yeyoan.blackjack.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.IntConsumer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.yeyoan.blackjack.model.Model;
import com.yeyoan.blackjack.model.Model.Result;
import com.yeyoan.blackjack.model.Payout;
import com.yeyoan.blackjack.playingcards.Card;
import com.yeyoan.blackjack.view.Animation;
import com.yeyoan.blackjack.view.Message;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class Controller implements Initializable {

    @FXML
    private StackPane root;

    @FXML private HBox playerSide;
    @FXML private HBox dealerSide;

    @FXML
    private DealerInfoCardController dealerInfoCardController;

    @FXML
    private PlayerInfoCardController playerInfoCardController;

    // @FXML
    // private StatsPaneController statsPaneController;

    @FXML
    private ChipsPaneController chipsPaneController;

    @FXML private JFXButton hitButton;
    @FXML private JFXButton doubleDownButton;
    @FXML private JFXButton surrenderButton;
    @FXML private JFXButton standButton;

    // @FXML private JFXButton hintButton;
    @FXML private JFXButton dealButton;
    @FXML private JFXButton nextHandButton;

    private JFXSnackbar snackbar;

    private Model model;

    @FXML
    protected void handleHitAction() {
        Card card = model.drawCard();
        model.playerHit(card);
        model.updateRunningCount(card.getRank());
        playerInfoCardController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        standButton.setDisable(true);

        Animation.enter(model.getCardImage(card), playerSide, () -> {
            hitButton.setDisable(false);
            doubleDownButton.setDisable(false);
            standButton.setDisable(false);
            displayMessage(Message.hit(card + ""));
            if (model.wentOver() || model.shoeIsEmpty()) {
                hitButton.setDisable(true);
                doubleDownButton.setDisable(true);
                surrenderButton.setDisable(true);
                // hintButton.setDisable(true);
                if (model.shoeIsEmpty()) {
                    displayMessage(Message.deckIsEmpty());
                }
            }
        });
    }

    @FXML
    protected void handleDoubleDownAction() {
        model.doubleBet();
        Card card = model.drawCard();
        model.playerHit(card);
        model.updateRunningCount(card.getRank());
        playerInfoCardController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
        playerInfoCardController.updatePlayerChips(model.playerChips());
        chipsPaneController.updateBet(model.playerBet());
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        // hintButton.setDisable(true);
        Animation.enter(model.getCardImage(card), playerSide, () -> {
            displayMessage(Message.doubleDown(model.playerBet(), card + ""));
        });
    }

    @FXML
    protected void handleSurrenderAction() {
        displayMessage(Message.surrender(model.playerBet()));
        model.givePayout(Payout.HALF);
        model.resetBet();
        dealerSide.getChildren().remove(1);
        ImageView holeCard = model.getCardImage(model.holeCard());
        dealerSide.getChildren().add(holeCard);
        Animation.lower(holeCard, 19);
        model.updateRunningCount(model.holeCard().getRank());
        chipsPaneController.updateBet(model.playerBet());
        playerInfoCardController.updatePlayerChips(model.playerChips());
        disableAllChoices(true);
        nextHandButton.setDisable(false);
        // hintButton.setDisable(true);
    }

    private void dealerTurn() {
        dealerInfoCardController.updateHandValue(model.dealerHandValue(), model.dealerHasSoftHand());
        if (model.dealerHasSoft17() || model.dealerHandValue() <= 16) {
            if (!model.shoeIsEmpty()) {
                Card card = model.drawCard();
                model.dealerHit(card);
                Animation.enter(model.getCardImage(card), dealerSide, () -> dealerTurn());
            }
        } else {
            displayResult();
        }
    }

    private void displayResult() {
        Result result = model.getResult();
        displayMessage(result.getMessage(model.playerBet()));
        model.finishRound(result);

        if (model.outOfChips()) {
            displayMessage(Message.outOfChips());
        } else {
            nextHandButton.setDisable(false);
        }
        chipsPaneController.updateBet(model.playerBet());
        playerInfoCardController.updatePlayerChips(model.playerChips());
    }

    @FXML
    protected void handleStandAction() {
        disableAllChoices(true);
        // hintButton.setDisable(true);
        dealerSide.getChildren().remove(1);
        ImageView holeCard = model.getCardImage(model.holeCard());
        dealerSide.getChildren().add(holeCard);
        Animation.lower(holeCard, 19);
        dealerTurn();
        playerInfoCardController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
    }

    @FXML
    protected void handleDealAction() {
        chipsPaneController.disableAllChips(true);
        disableAllChoices(false);
        // hintButton.setDisable(false);
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
            Animation.enter(model.getCardImage(playerCard), playerSide);
            if (i != 0) {
                model.updateRunningCount(dealerCard.getRank());
                Animation.enter(model.getCardBack(), dealerSide);
            } else {
                Animation.enter(model.getCardImage(dealerCard), dealerSide);
            }
        }

        playerInfoCardController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
        dealerInfoCardController.updatePartialHandValue(model.dealerFrontCard());
        chipsPaneController.updateBet(model.playerBet());
        playerInfoCardController.updatePlayerChips(model.playerChips());
    }

    @FXML
    protected void handleNextHandAction() {
        Animation.leave(0, playerSide, () -> {
            displayMessage(Message.nextHand());
            playerSide.getChildren().clear();
        });
        Animation.leave(0, dealerSide, () -> {
            dealerSide.getChildren().clear();
        });

        if (!model.shoeIsSufficient()) {
            model.shuffleDeck();
            model.resetRunningCount();
            displayMessage(Message.reshuffle());
        }

        playerInfoCardController.clearHandValue();
        dealerInfoCardController.clearHandValue();
        nextHandButton.setDisable(true);
        // hintButton.setDisable(true);
        model.resetHand();

        disableAllChoices(true);
        if (model.betIsSufficient()) {
            dealButton.setDisable(false);
        } else {
            dealButton.setDisable(true);
        }

        chipsPaneController.disableAllChips(false);

        chipsPaneController.updateChips(model.playerChips());
        chipsPaneController.updateBet(model.playerBet());
        playerInfoCardController.updatePlayerChips(model.playerChips());
    }

    @FXML
    protected void handleQuitAction() {
        Platform.exit();
    }

    private void disableAllChoices(boolean value) {
        hitButton.setDisable(value);
        doubleDownButton.setDisable(value);
        surrenderButton.setDisable(value);
        standButton.setDisable(value);
    }

    private void displayMessage(String message) {
        snackbar.enqueue(new SnackbarEvent(
            new JFXSnackbarLayout(message)
        ));
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        snackbar = new JFXSnackbar(root);
        model = new Model();
        chipsPaneController.updateBet(model.playerBet());
        playerInfoCardController.updatePlayerChips(model.playerChips());
        IntConsumer chipAction = value -> {
            model.bet(value);
            if (model.betIsSufficient()) {
                dealButton.setDisable(false);
            }
            chipsPaneController.updateBet(model.playerBet());
            playerInfoCardController.updatePlayerChips(model.playerChips());
            chipsPaneController.updateChips(model.playerChips());
        };
        chipsPaneController.setChipAction(chipAction);
        dealerInfoCardController.setName("Dealer");
        playerInfoCardController.setName(model.playerName());
    }
}
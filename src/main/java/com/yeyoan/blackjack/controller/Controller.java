package com.yeyoan.blackjack.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.yeyoan.blackjack.model.Model;
import com.yeyoan.blackjack.model.Payout;
import com.yeyoan.blackjack.playingcards.Card;
import com.yeyoan.blackjack.view.Message;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Controller implements Initializable {

    @FXML
    private StackPane root;

    @FXML private HBox playerSide;
    @FXML private HBox dealerSide;

    @FXML private Text playerHandValue;
    @FXML private Text dealerHandValue;

    @FXML private Label deckCountLabel;
    @FXML private Label trueCountLabel;
    @FXML private Label playerBet;
    @FXML private Label playerChips;

    @FXML private HBox chipsPane;

    @FXML private JFXButton hitButton;
    @FXML private JFXButton doubleDownButton;
    @FXML private JFXButton surrenderButton;
    @FXML private JFXButton standButton;

    @FXML private JFXButton hintButton;
    @FXML private JFXButton dealButton;
    @FXML private JFXButton nextHandButton;

    private JFXSnackbar snackbar;

    private Model model;
    private List<JFXButton> chips;

    private void createTransition(Node node, HBox parent, EventHandler<ActionEvent> event) {
        MoveTo moveTo = new MoveTo(1000, 68);
        LineTo lineTo = new LineTo(50, 68);

        Path path = new Path(moveTo, lineTo);

        PathTransition transition = new PathTransition(Duration.millis(1000), path, node);
        transition.setOnFinished(event);
        transition.play();

        parent.getChildren().add(node);
    }

    @FXML
    protected void handleHitAction() {
        Card card = model.drawCard();
        model.playerHit(card);
        model.updateRunningCount(card.getRank());
        deckCountLabel.setText(model.dCount() + "");
        trueCountLabel.setText(model.tCount() + "");
        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        surrenderButton.setDisable(true);

        createTransition(model.getCardImage(card), playerSide, event -> {
            displayMessage(Message.hit(card + ""));
            if (model.wentOver() || model.shoeIsEmpty()) {
                hitButton.setDisable(true);
                doubleDownButton.setDisable(true);
                surrenderButton.setDisable(true);
                hintButton.setDisable(true);
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
        deckCountLabel.setText(model.dCount() + "");
        trueCountLabel.setText(model.tCount() + "");
        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        hintButton.setDisable(true);
        createTransition(model.getCardImage(card), playerSide, event -> {
            displayMessage(Message.doubleDown(model.playerBet(), card + ""));
        });
    }

    @FXML
    protected void handleSurrenderAction() {
        displayMessage(Message.surrender(model.playerBet()));
        model.givePayout(Payout.HALF);
        model.resetBet();
        dealerSide.getChildren().remove(1);
        dealerSide.getChildren().add(model.getCardImage(model.holeCard()));
        model.updateRunningCount(model.holeCard().getRank());
        updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
        disableAllChoices(true);
        nextHandButton.setDisable(false);
        hintButton.setDisable(true);
    }

    private void dealerTurn() {
        dealerHandValue.setText((model.dealerHasSoftHand() ? "Soft" : "Hard") + " " + model.dealerHandValue());
        if (model.dealerHasSoft17() || model.dealerHandValue() <= 16) {
                if (!model.shoeIsEmpty()) {
                Card card = model.drawCard();
                model.dealerHit(card);
                createTransition(model.getCardImage(card), dealerSide, event -> {
                    dealerTurn();
                });
            }
        }
        displayResult();
    }

    private void displayResult() {
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
        updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
    }

    @FXML
    protected void handleStandAction() {
        disableAllChoices(true);
        hintButton.setDisable(true);
        dealerSide.getChildren().remove(1);
        dealerSide.getChildren().add(model.getCardImage(model.holeCard()));

        dealerTurn();

        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());

        // String message;
        // if (model.isTie()) {
        //     displayMessage(Message.tie());
        //     model.returnBet();
        // } else if (model.playerWon()) {
        //     if (model.playerHasBlackjack()) {
        //         message = Message.playerBlackjack(model.playerBet());
        //         model.givePayout(Payout.BLACKJACK);
        //     } else {
        //         message = Message.playerWon(model.playerBet());
        //         model.givePayout(Payout.REGULAR);
        //     }
        //     displayMessage(message);
        // } else if (model.playerLost()) {
        //     message = (model.dealerHasBlackjack()) ?
        //             Message.dealerBlackjack(model.playerBet()) :
        //             Message.playerLost(model.playerBet());
        //     displayMessage(message);
        //     model.resetBet();
        // } else {
        //     displayMessage(Message.bothOver());
        // }

    }

    @FXML
    protected void handleDealAction() {
        disableAllChips(chips, true);
        disableAllChoices(false);
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
            createTransition(model.getCardImage(playerCard), playerSide, event -> {

            });
            if (i != 0) {
                model.updateRunningCount(dealerCard.getRank());
                createTransition(model.getCardBack(), dealerSide, event -> {
                
                });
            } else {
                createTransition(model.getCardImage(dealerCard), dealerSide, event -> {
                
                });
            }
        }
        trueCountLabel.setText(model.tCount() + "");
        displayMessage(Message.deal(model.initialCards()));

        playerHandValue.setText((model.playerHasSoftHand() ? "Soft" : "Hard") + " " + model.playerHandValue());
        dealerHandValue.setText(model.guessDealerHandValue());
        deckCountLabel.setText(model.dCount() + "");
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

        disableAllChoices(true);
        if (model.betIsSufficient()) {
            dealButton.setDisable(false);
        } else {
            dealButton.setDisable(true);
        }

        disableAllChips(chips, false);

        updateChips(model.playerChips(), chips);
        updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
        deckCountLabel.setText(model.dCount() + "");
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

    private void disableAllChips(List<JFXButton> chips, boolean value) {
        chips.forEach(chip -> chip.setDisable(value));
    }

    private void displayMessage(String message) {
        snackbar.enqueue(
            new SnackbarEvent(
                new JFXSnackbarLayout(message)
            )
        );
    }

    private void updateChips(double amount, List<JFXButton> chips) {
        chips.stream()
            .filter(chip -> Integer.parseInt(chip.getText()) > amount)
            .forEach(chip -> chip.setDisable(true));
    }

    private void updateStats(double bet, double chips, int tCount, int dCount) {
        playerBet.setText(String.valueOf(bet));
        playerChips.setText(String.valueOf(chips));
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        snackbar = new JFXSnackbar(root);
        model = new Model();
        playerChips.setText(model.playerChips() + "");
        chips = new ArrayList<>();
        Arrays.stream(Model.chips())
            .mapToObj(Integer::toString)
            .map(JFXButton::new)
            .forEach(button -> {
                chips.add(button);
                chipsPane.getChildren().add(button);
            });
        chips.forEach(chip -> {
            chip.setOnAction(event -> {
                model.bet(Integer.parseInt(chip.getText()));
                if (model.betIsSufficient()) {
                    dealButton.setDisable(false);
                }
                updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount());
                updateChips(model.playerChips(), chips);
            });
        });
    }
}
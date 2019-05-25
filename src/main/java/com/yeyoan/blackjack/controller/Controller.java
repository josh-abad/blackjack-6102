package com.yeyoan.blackjack.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.IntConsumer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.yeyoan.blackjack.model.Model;
import com.yeyoan.blackjack.model.Payout;
import com.yeyoan.blackjack.playingcards.Card;
import com.yeyoan.blackjack.view.Message;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Controller implements Initializable {

    @FXML
    private StackPane root;

    @FXML private HBox playerSide;
    @FXML private HBox dealerSide;

    @FXML
    private MiniPlayerPaneController dealerMiniPaneController;

    @FXML
    private MiniPlayerPaneController playerMiniPaneController;

    @FXML
    private StatsPaneController statsPaneController;

    @FXML
    private ChipsPaneController chipsPaneController;

    @FXML private JFXButton hitButton;
    @FXML private JFXButton doubleDownButton;
    @FXML private JFXButton surrenderButton;
    @FXML private JFXButton standButton;

    @FXML private JFXButton hintButton;
    @FXML private JFXButton dealButton;
    @FXML private JFXButton nextHandButton;

    private JFXSnackbar snackbar;

    private Model model;

    private void createTransition(Node node, HBox parent, Runnable action) {
        Path path = new Path(new MoveTo(1000, 68), new LineTo(50, 68));
        PathTransition transition = new PathTransition(Duration.millis(1000), path, node);
        transition.setOnFinished(event -> {
            animationTest(node, 19);
            action.run();
        });
        transition.play();
        parent.getChildren().add(node);
    }

    private void animationTest(Node node, int radius) {
        if (radius < 5) {
            return;
        }

        String depth = "-fx-effect: "
            + "dropshadow(three-pass-box, "
            + "rgba(0, 0, 0, 0.5), "
            + radius
            + ", 0, 0, 1);";

        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5),
            new KeyValue(node.styleProperty(), depth)
        ));

        timeline.setOnFinished(next -> {
            animationTest(node, radius - 1);
        });

        timeline.play();
    }

    @FXML
    protected void handleHitAction() {
        Card card = model.drawCard();
        model.playerHit(card);
        model.updateRunningCount(card.getRank());
        playerMiniPaneController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        standButton.setDisable(true);

        createTransition(model.getCardImage(card), playerSide, () -> {
            hitButton.setDisable(false);
            doubleDownButton.setDisable(false);
            standButton.setDisable(false);
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
        playerMiniPaneController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
        statsPaneController.updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
        hitButton.setDisable(true);
        doubleDownButton.setDisable(true);
        surrenderButton.setDisable(true);
        hintButton.setDisable(true);
        createTransition(model.getCardImage(card), playerSide, () -> {
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
        animationTest(holeCard, 19);
        model.updateRunningCount(model.holeCard().getRank());
        statsPaneController.updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
        disableAllChoices(true);
        nextHandButton.setDisable(false);
        hintButton.setDisable(true);
    }

    private void dealerTurn() {
        dealerMiniPaneController.updateHandValue(model.dealerHandValue(), model.dealerHasSoftHand());
        if (model.dealerHasSoft17() || model.dealerHandValue() <= 16) {
            if (!model.shoeIsEmpty()) {
                Card card = model.drawCard();
                model.dealerHit(card);
                createTransition(model.getCardImage(card), dealerSide, () -> {
                    dealerTurn();
                });
            }
        } else {
            displayResult();
        }
    }

    private void displayResult() {
        displayMessage(model.getResult().getMessage(model.playerBet()));

        if (model.outOfChips()) {
            displayMessage(Message.outOfChips());
        } else {
            nextHandButton.setDisable(false);
        }
        statsPaneController.updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
    }

    @FXML
    protected void handleStandAction() {
        disableAllChoices(true);
        hintButton.setDisable(true);
        dealerSide.getChildren().remove(1);
        ImageView holeCard = model.getCardImage(model.holeCard());
        dealerSide.getChildren().add(holeCard);
        animationTest(holeCard, 19);
        dealerTurn();
        playerMiniPaneController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
    }

    @FXML
    protected void handleDealAction() {
        chipsPaneController.disableAllChips(true);
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
            createTransition(model.getCardImage(playerCard), playerSide, () -> { });
            if (i != 0) {
                model.updateRunningCount(dealerCard.getRank());
                createTransition(model.getCardBack(), dealerSide, () -> { });
            } else {
                createTransition(model.getCardImage(dealerCard), dealerSide, () -> { });
            }
        }

        playerMiniPaneController.updateHandValue(model.playerHandValue(), model.playerHasSoftHand());
        dealerMiniPaneController.updatePartialHandValue(model.dealerFrontCard());
        statsPaneController.updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
    }

    private void createLeaveTransition(int i, HBox parent, Runnable action) {
        if (i == parent.getChildren().size()) {
            return;
        }
        Path path = new Path(new MoveTo(50, 68), new LineTo(-1000, 68));
        Node node = parent.getChildren().get(i);
        PathTransition transition = new PathTransition(Duration.millis(1000), path, node);
        if (i == parent.getChildren().size()-1) {
            transition.setOnFinished(event -> {
                action.run();
            });
        }
        transition.play();
        createLeaveTransition(i + 1, parent, action);
    }

    @FXML
    protected void handleNextHandAction() {
        createLeaveTransition(0, playerSide, () -> {
            displayMessage(Message.nextHand());
            playerSide.getChildren().clear();
        });
        createLeaveTransition(0, dealerSide, () -> {
            dealerSide.getChildren().clear();
        });

        if (!model.shoeIsSufficient()) {
            model.shuffleDeck();
            model.resetRunningCount();
            displayMessage(Message.reshuffle());
        }

        playerMiniPaneController.clearHandValue();
        dealerMiniPaneController.clearHandValue();
        nextHandButton.setDisable(true);
        hintButton.setDisable(true);
        model.resetHand();

        disableAllChoices(true);
        if (model.betIsSufficient()) {
            dealButton.setDisable(false);
        } else {
            dealButton.setDisable(true);
        }

        chipsPaneController.disableAllChips(false);

        chipsPaneController.updateChips(model.playerChips());
        statsPaneController.updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
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
        statsPaneController.updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
        IntConsumer chipAction = value -> {
            model.bet(value);
            if (model.betIsSufficient()) {
                dealButton.setDisable(false);
            }
            statsPaneController.updateStats(model.playerBet(), model.playerChips(), model.tCount(), model.dCount()); 
            chipsPaneController.updateChips(model.playerChips());
        };
        chipsPaneController.setChipAction(chipAction);
        dealerMiniPaneController.setName("Dealer");
        playerMiniPaneController.setName(model.playerName());
    }
}
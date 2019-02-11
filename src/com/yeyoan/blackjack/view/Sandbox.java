package com.yeyoan.blackjack.view;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Sandbox extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.chips = new HashMap<>();
        this.choices = new HashMap<>();
        this.options = new HashMap<>();

        stage.setTitle("Blackjack");
        stage.setMaximized(true);

        BorderPane rootNode = new BorderPane();
        rootNode.setId("pane");
        Scene myScene = new Scene(rootNode, 300, 200);
        myScene.getStylesheets().add(Sandbox.class.getResource("dark.css").toExternalForm());

        stage.setScene(myScene);

        GridPane topPane = new GridPane();
        ImageView logo = createImageView("/com/yeyoan/blackjack/resources/default_logo_s.png", 150);

        Label chips = new Label("1000");
        chips.setGraphic(createImageView("/com/yeyoan/blackjack/resources/chip.png", 36));
        chips.setId("chips");

        // topPane.setId("front_panel");

        GridPane messagePane = createMessagePane();

        GridPane.setHgrow(logo, Priority.ALWAYS);
        GridPane.setValignment(logo, VPos.CENTER);
        GridPane.setMargin(logo, new Insets(10, 0, 10, 10));

        GridPane.setHalignment(messagePane, HPos.CENTER);
        GridPane.setValignment(messagePane, VPos.CENTER);
        GridPane.setFillHeight(messagePane, Boolean.FALSE);
        // GridPane.setMargin(messagePane, new Insets(10, 0, 0, 0));

        GridPane.setHalignment(chips, HPos.RIGHT);
        GridPane.setValignment(chips, VPos.CENTER);
        GridPane.setHgrow(chips, Priority.ALWAYS);
        GridPane.setMargin(chips, new Insets(0, 10, 0, 0));

        topPane.add(logo, 0, 0);
        topPane.add(messagePane, 1, 0);
        topPane.add(chips, 2, 0);

        GridPane tablePane = new GridPane();
        GridPane optionsPane = new GridPane();

        rootNode.setTop(topPane);
        rootNode.setCenter(tablePane);
        rootNode.setBottom(optionsPane);
        // optionsPane.setId("front_panel");

        GridPane statsPane = createStatPane();
        GridPane chipsPane = createMenuPane("CHIPS", new String[] {"5", "10", "25", "50", "100"}, this.chips, false);
        GridPane choicePane = createMenuPane("CHOICES", new String[] {"Hit", "Double Down", "Surrender", "Stand"}, this.choices, false);
        GridPane optionPane = createMenuPane("OPTIONS", new String[] {"New Game", "Quit Game"}, this.options, false);

        GridPane.setMargin(statsPane, new Insets(10, 0, 10, 10));
        GridPane.setHgrow(statsPane, Priority.ALWAYS);
        GridPane.setFillWidth(statsPane, Boolean.FALSE);
        GridPane.setFillHeight(statsPane, Boolean.FALSE);
        optionsPane.add(statsPane, 0, 0);

        GridPane.setMargin(chipsPane, new Insets(10, 0, 10, 0));
        GridPane.setHgrow(chipsPane, Priority.ALWAYS);
        GridPane.setFillWidth(chipsPane, Boolean.FALSE);
        // optionsPane.add(chipsPane, 1, 0);

        GridPane.setMargin(choicePane, new Insets(10, 0, 10, 0));
        GridPane.setHgrow(choicePane, Priority.ALWAYS);
        GridPane.setFillWidth(choicePane, Boolean.FALSE);
        optionsPane.add(choicePane, 1, 0);
        switchPane(optionsPane, chipsPane, choicePane);

        GridPane.setMargin(optionPane, new Insets(10, 10, 10, 0));
        optionsPane.add(optionPane, 2, 0);

        Label dealerHandValue = new Label("Dealer");
        FlowPane dealerPanel = new FlowPane(20.0f, 0.0f);
        Label playerHandValue = new Label("Player");
        FlowPane playerPanel = new FlowPane(20.0f, 0.0f);

        GridPane.setConstraints(dealerHandValue, 0, 0);
        GridPane.setConstraints(dealerPanel, 0, 1);
        GridPane.setConstraints(playerHandValue, 0, 2);
        GridPane.setConstraints(playerPanel, 0, 3);

        GridPane.setMargin(dealerHandValue, new Insets(20, 0, 20, 10));
        GridPane.setMargin(dealerPanel, new Insets(0, 0, 20, 10));
        GridPane.setMargin(playerHandValue, new Insets(20, 0, 20, 10));
        GridPane.setMargin(playerPanel, new Insets(0, 0, 20, 10));

        GridPane.setHgrow(dealerPanel, Priority.ALWAYS);
        GridPane.setHgrow(playerPanel, Priority.ALWAYS);

        tablePane.getChildren().addAll(dealerHandValue, dealerPanel, playerHandValue, playerPanel);

        for (int i = 0; i < 10; i++) {
            ImageView emptyPlayerCard = createImageView("/com/yeyoan/blackjack/resources/blank.png", 115);
            ImageView emptyDealerCard = createImageView("/com/yeyoan/blackjack/resources/blank.png", 115);
            emptyDealerCard.setOpacity(0.5f);
            emptyPlayerCard.setOpacity(0.5f);
            playerPanel.getChildren().add(emptyPlayerCard);
            dealerPanel.getChildren().add(emptyDealerCard);
        }

        stage.show();
    }

    private void switchPane(GridPane pane, Node first, Node second) {
        if (pane.getChildren().contains(first)) {
            pane.getChildren().remove(first);
            pane.add(second, 1, 0);
        } else {
            pane.getChildren().remove(second);
            pane.add(first, 1, 0);
        }
    }

    private static ImageView createImageView(String path, double width) {
        Image image = new Image(Sandbox.class.getResourceAsStream(path));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    
    private static GridPane createStatPane() {
        GridPane statPane = new GridPane();
        Label deckCount = new Label("4");
        Label trueCount = new Label("0");
        Label bet = new Label("25");

        deckCount.setGraphic(createImageView("/com/yeyoan/blackjack/resources/dark/deck.png", 36));
        trueCount.setGraphic(createImageView("/com/yeyoan/blackjack/resources/dark/card_count.png", 36));
        bet.setGraphic(createImageView("/com/yeyoan/blackjack/resources/dark/bet.png", 36));

        GridPane.setConstraints(deckCount, 0, 0);
        GridPane.setConstraints(trueCount, 1, 0);
        GridPane.setConstraints(bet, 2, 0);

        GridPane.setMargin(deckCount, new Insets(10, 40, 10, 10));
        GridPane.setMargin(trueCount, new Insets(10, 40, 10, 0));
        GridPane.setMargin(bet, new Insets(10, 10, 10, 0));

        deckCount.setId("chips");
        trueCount.setId("chips");
        bet.setId("chips");

        statPane.getChildren().addAll(deckCount, trueCount, bet);
        return statPane;
    }

    private static GridPane createMenuPane(String name,
                                            String[] options,
                                            Map<String, Button> map,
                                            boolean hasIcon) {
        GridPane menuPane = new GridPane();

        Text header = new Text(name);
        GridPane.setColumnSpan(header, options.length);
        GridPane.setMargin(header, new Insets(10, 10, 2, 10));
        menuPane.add(header, 0, 0);

        Separator separator = new Separator();
        GridPane.setColumnSpan(separator, options.length);
        GridPane.setMargin(separator, new Insets(0, 10, 10, 10));
        menuPane.add(separator, 0, 1);

        for (int i = 0; i < options.length; i++) {
            Button button = new Button(options[i]);
            GridPane.setColumnSpan(button, 1);
            GridPane.setFillWidth(button, Boolean.FALSE);
            int right = (i == options.length - 1) ? 10 : 0;
            GridPane.setMargin(button, new Insets(0, right, 10, 10));

            if (hasIcon) {
                String path = options[i] + ".png";
                // setIcon(button, path, 16);
            }

            menuPane.add(button, i, 2);
            map.put(options[i], button);
        }

        header.setId("header");
        menuPane.setId("front_panel");

        return menuPane;
    }

    private static GridPane createMessagePane() {
        GridPane messagePane = new GridPane();

        Text header = new Text("MESSAGE");
        Separator separator = new Separator();
        Text message = new Text("Welcome to Blackjack! Place a bet.");

        header.setFontSmoothingType(FontSmoothingType.LCD);
        message.setFontSmoothingType(FontSmoothingType.LCD);

        messagePane.setId("front_panel");
        header.setId("header");
        message.setId("text");

        GridPane.setConstraints(header, 0, 0);
        GridPane.setConstraints(separator, 0, 1);
        GridPane.setConstraints(message, 0, 2);

        GridPane.setMargin(header, new Insets(10, 10, 2, 10));
        GridPane.setMargin(separator, new Insets(0, 10, 10, 10));
        GridPane.setMargin(message, new Insets(0, 10, 10, 10));

        messagePane.getChildren().addAll(header, separator, message);

        return messagePane;
    }

    private Map<String, Button> chips;
    private Map<String, Button> choices;
    private Map<String, Button> options;
} 

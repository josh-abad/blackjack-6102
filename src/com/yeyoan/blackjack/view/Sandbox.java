package com.yeyoan.blackjack.view;

import com.yeyoan.util.Format;
import java.util.Arrays;
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

    public static final String RESOURCE_PATH = "/com/yeyoan/blackjack/resources/";
    public static final String MODE = "dark";
    public static final int CARD_SIZE = 115;
    public static final String CARD_STYLE = "classic";
    
    public Sandbox(Str)

    public static void main(String[] args) {
        launch(args);
    }

    public void display() {
        launch(new String[0]);
    }

    /**
     * Removes all card images on the screen.
     * 
     * <p>This method <b>doesn't</b> actually remove every image on the screen,
     * but rather it replaces the images with a blank image that has the same 
     * size as the card images. This is to keep the table border stretched along
     * the length of the screen even if no cards are displayed.
     */
    public void clearCards() {
        String path = RESOURCE_PATH + "blank.png";
        try {
            Image image = new Image(View.class.getResourceAsStream(path));
            resetImages(image, playerHand);
            resetImages(image, dealerHand);
        } catch (NullPointerException ex) {
            resetImages(null, playerHand);
            resetImages(null, dealerHand);
            System.err.println("Could not find " + path);
        }
    }

    /**
     * Displays a message on the screen with a message bubble icon.
     * @param message the message
     */
    public void displayMessage(String message) {
        displayMessage("MESSAGE", message, "message.png");
    }

    /**
     * Displays a message on the screen with the specified icon.
     * @param header the type of message
     * @param message the message
     * @param filename the filename of the icon
     */
    public void displayMessage(String header, String message, String filename) {
        messagePane.setVisible(true);
        messageHeader.setText(header);
        messageText.setText(message);
        double size = messageHeader.getFont().getSize();
        messageHeader.setGraphic(createImageView(RESOURCE_PATH + MODE + "/" + filename + ".png", size));
    }

    public GridPane initBetOptions(int[] options) {
        chipsPane = new GridPane();
        String[] stringOptions = new String[options.length];
        for (int i = 0; i < stringOptions.length; i++) {
            stringOptions[i] = String.valueOf(options[i]);
        }
        return createMenuPane("CHIPS", stringOptions, chips, true);
    }

    public void initPlayOptions(String[] options) {
        initOptions("CHOICES",          // Name of the panel, i.e. the header
                    options,            // The text on the buttons
                    this.playOptions,   // The buttons
                    playOptionsPanel,   // The panel containing the buttons
                    false);             // If the text has an icon next to it
    }

    public void initHandOptions(String[] options) {
        initOptions("OPTIONS", options, this.handOptions, handOptionsPanel, false);
    }

    /**
     * Updates the card images on the dealer's side of the screen.
     * @param cardNames the names of the cards
     */
    public void updateDealerCards(String[] cardNames) {
        updateImages(cardNames, dealerHand);
    }

    /**
     * Updates the number of decks displayed on screen.
     * @param count the number of decks
     */
    public void updateDeckCount(int count) {
        deckCountLabel.setText(count + "");
    }

    /**
     * Updates the card images on the player's side of the screen.
     * @param cardNames the names of the cards
     */
    public void updatePlayerCards(String[] cardNames) {
        updateImages(cardNames, playerHand);
    }

    /**
     * Updates the amount of chips and the current bet of the player.
     * @param chips the player's chips
     * @param bet the player's bet
     */
    public void updateStats(double chips, double bet) {
        chipsLabel.setText(Format.currency(chips));
        betLabel.setText(Format.currency(bet));
    }

    /**
     * Updates the true count of cards displayed on the screen.
     * @param count the true count
     */
    public void updateTrueCount(int count) {
        trueCountLabel.setText(count + "");
    }

    private void resetImages(Image image, ImageView[] hand) {
        Arrays.asList(hand).forEach((imageView) -> {
            imageView.setImage(image);
        });
    }

    private void updateImages(String[] cardNames, ImageView[] images) {
        for (int i = 0; i < cardNames.length; i++) {
            String[] comp = cardNames[i].split(" ");
            String value = CARD_STYLE + "_" + comp[0].toLowerCase();
            String suit = comp[2].toLowerCase();
            String path = suit + "/" + value + ".png";
            Image image = new Image(Sandbox.class.getResourceAsStream(RESOURCE_PATH + path));
            images[i].setImage(image);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.chips = new HashMap<>();
        this.choices = new HashMap<>();
        this.options = new HashMap<>();
        this.playerHand = new ImageView[10];
        this.dealerHand = new ImageView[10];

        stage.setTitle("Blackjack");
        stage.setMaximized(true);

        BorderPane rootNode = new BorderPane();
        rootNode.setId("pane");
        Scene myScene = new Scene(rootNode, 300, 200);
        myScene.getStylesheets().add(Sandbox.class.getResource(MODE + ".css").toExternalForm());

        stage.setScene(myScene);

        GridPane topPane = new GridPane();
        ImageView logo = createImageView(RESOURCE_PATH + "default_logo_s.png", 150);

        chipsLabel = new Label("1000");
        chipsLabel.setGraphic(createImageView(RESOURCE_PATH + "chip.png", 36));
        chipsLabel.setId("chips");

        // topPane.setId("front_panel");

        messagePane = createMessagePane();

        GridPane.setHgrow(logo, Priority.ALWAYS);
        GridPane.setValignment(logo, VPos.CENTER);
        GridPane.setMargin(logo, new Insets(10, 0, 10, 10));

        GridPane.setHalignment(messagePane, HPos.CENTER);
        GridPane.setValignment(messagePane, VPos.CENTER);
        GridPane.setFillHeight(messagePane, Boolean.FALSE);
        // GridPane.setMargin(messagePane, new Insets(10, 0, 0, 0));

        GridPane.setHalignment(chipsLabel, HPos.RIGHT);
        GridPane.setValignment(chipsLabel, VPos.CENTER);
        GridPane.setHgrow(chipsLabel, Priority.ALWAYS);
        GridPane.setMargin(chipsLabel, new Insets(0, 10, 0, 0));

        topPane.add(logo, 0, 0);
        topPane.add(messagePane, 1, 0);
        topPane.add(chipsLabel, 2, 0);

        GridPane tablePane = new GridPane();
        GridPane optionsPane = new GridPane();

        rootNode.setTop(topPane);
        rootNode.setCenter(tablePane);
        rootNode.setBottom(optionsPane);
        // optionsPane.setId("front_panel");

        GridPane statsPane = createStatPane();
        chipsPane = createMenuPane("CHIPS", new String[] {"5", "10", "25", "50", "100"}, this.chips, false);
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
            ImageView emptyPlayerCard = createImageView(RESOURCE_PATH + "blank.png", 115);
            ImageView emptyDealerCard = createImageView(RESOURCE_PATH + "blank.png", 115);
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
    
    private GridPane createStatPane() {
        GridPane statPane = new GridPane();
        deckCountLabel = new Label("4");
        trueCountLabel = new Label("0");
        betLabel = new Label("25");

        deckCountLabel.setGraphic(createImageView(RESOURCE_PATH + MODE + "/deck.png", 36));
        trueCountLabel.setGraphic(createImageView(RESOURCE_PATH + MODE + "/card_count.png", 36));
        betLabel.setGraphic(createImageView(RESOURCE_PATH + MODE + "/bet.png", 36));

        GridPane.setConstraints(deckCountLabel, 0, 0);
        GridPane.setConstraints(trueCountLabel, 1, 0);
        GridPane.setConstraints(betLabel, 2, 0);

        GridPane.setMargin(deckCountLabel, new Insets(10, 40, 10, 10));
        GridPane.setMargin(trueCountLabel, new Insets(10, 40, 10, 0));
        GridPane.setMargin(betLabel, new Insets(10, 10, 10, 0));

        deckCountLabel.setId("chips");
        trueCountLabel.setId("chips");
        betLabel.setId("chips");

        statPane.getChildren().addAll(deckCountLabel, trueCountLabel, betLabel);
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

    private GridPane createMessagePane() {
        GridPane messagePane = new GridPane();

        messageHeader = new Label("MESSAGE");
        Separator separator = new Separator();
        messageText = new Text("Welcome to Blackjack! Place a bet.");

        messageText.setFontSmoothingType(FontSmoothingType.LCD);

        messagePane.setId("front_panel");
        messageHeader.setId("header");
        messageText.setId("text");

        GridPane.setConstraints(messageHeader, 0, 0);
        GridPane.setConstraints(separator, 0, 1);
        GridPane.setConstraints(messageText, 0, 2);

        GridPane.setMargin(messageHeader, new Insets(10, 10, 2, 10));
        GridPane.setMargin(separator, new Insets(0, 10, 10, 10));
        GridPane.setMargin(messageText, new Insets(0, 10, 10, 10));

        messagePane.getChildren().addAll(messageHeader, separator, messageText);

        return messagePane;
    }

    private Map<String, Button> chips;
    private Map<String, Button> choices;
    private Map<String, Button> options;
    private ImageView[] playerHand;
    private ImageView[] dealerHand;
    private Label chipsLabel;
    private Label betLabel;
    private Label trueCountLabel;
    private Label deckCountLabel;
    private GridPane messagePane;
    private Label messageHeader;
    private Text messageText;
    private GridPane chipsPane;
} 

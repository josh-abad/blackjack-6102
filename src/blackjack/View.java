package blackjack;

import design.Format;
import design.DefaultFont;
import design.Palette;
import design.ImageResizer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class View {

    public View() {
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 725);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        backgroundPanel = new Table();

        String path;

        path = "/images/logo2.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            frame.setIconImage(icon.getImage());
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }

        font = new DefaultFont("Segoe UI");

        // Styling for the option pane
        UIManager.put("OptionPane.messageFont", font.generateFont(14));
        UIManager.put("OptionPane.background", Palette.BLACK);
        UIManager.put("OptionPane.messageForeground", Palette.TEXT);
        UIManager.put("Panel.background", Palette.BLACK);
        UIManager.put("OptionPane.buttonFont", font.generateFont(12));
        UIManager.put("Button.foreground", Palette.TEXT);
        UIManager.put("Button.background", Palette.BLACK);

        startPanel = new JPanel();
        topPanel = new JPanel();
        titleLabel = new JLabel();

        setIcon(titleLabel, "/images/default_logo.png", 150);

        messageLabel = new JLabel();
        chipsLabel = new JLabel();

        settingsPanel = new SettingsPanel(font);

        tablePanel = new JPanel();
        dealerHandValueLabel = new JLabel();        
        dealerPanel = new JPanel();
        dealerHand = new JLabel[10];
        playerHandValueLabel = new JLabel();
        playerPanel = new JPanel();
        playerHand = new JLabel[10];

        optionsPanel = new JPanel();
        currentBetPanel = new JPanel();
        deckCountLabel = new JLabel();
        trueCountLabel = new JLabel();
        currentBetValueLabel = new JLabel();
        betOptionsPanel = new JPanel();
        betOptions = new HashMap<>();
        playOptionsPanel = new JPanel();
        playOptions = new HashMap<>();
        handOptionsPanel = new JPanel();
        handOptions = new HashMap<>();

        topPanel.setBackground(Palette.TABLE_DARK);
        messageLabel.setForeground(Palette.TEXT);
        messageLabel.setFont(font.generateFont(18));

        chipsLabel.setForeground(Palette.TEXT);
        chipsLabel.setFont(font.generateFont(36));

        setIcon(chipsLabel, "/images/chip.png", 36);
        setIcon(deckCountLabel, "/images/deck.png", 30);
        setIcon(trueCountLabel, "/images/card_count.png", 30);
        setIcon(currentBetValueLabel, "/images/bet.png", 30);

        tablePanel.setBackground(Palette.TABLE);
        dealerHandValueLabel.setForeground(Palette.TEXT);
        dealerPanel.setBackground(Palette.TABLE);
        playerHandValueLabel.setForeground(Palette.TEXT);
        playerPanel.setBackground(Palette.TABLE);
        dealerHandValueLabel.setFont(font.generateFont(16));
        playerHandValueLabel.setFont(font.generateFont(16));

        optionsPanel.setBackground(Palette.BLACK);
        currentBetPanel.setBackground(Palette.BLACK);
        betOptionsPanel.setBackground(Palette.BLACK);
        playOptionsPanel.setBackground(Palette.BLACK);
        handOptionsPanel.setBackground(Palette.BLACK);
        deckCountLabel.setForeground(Palette.TEXT);
        trueCountLabel.setForeground(Palette.TEXT);
        currentBetValueLabel.setForeground(Palette.TEXT);
        deckCountLabel.setFont(font.generateFont(30));
        trueCountLabel.setFont(font.generateFont(30));
        currentBetValueLabel.setFont(font.generateFont(30));

        frame.add(backgroundPanel, BorderLayout.CENTER);

        backgroundPanel.setLayout(new BorderLayout());

        layoutStartPanel();
        layoutTopPanel();
        layoutTablePanel();

        dealerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));

        /*
        Images are displayed using ten labels each for the player and dealer.
        I used ten as I don't know how to dynamically add labels to screen
        without ruining everything so, unless the player or dealer gets ten aces
        straight, ten labels should suffice.
        */
        for (int i = 0; i < dealerHand.length; i++) {
            JLabel dealerCard = new JLabel();
            JLabel playerCard = new JLabel();
            dealerHand[i] = dealerCard;
            playerHand[i] = playerCard;
            dealerPanel.add(dealerCard);
            playerPanel.add(playerCard);
        }

        layoutOptionsPanel();

        // Fallback for when the background texture fails to load
        if (backgroundPanel.getBackground() != Palette.TABLE) {
            startPanel.setOpaque(false);
            playerPanel.setOpaque(false);
            dealerPanel.setOpaque(false);
            topPanel.setOpaque(false);
            tablePanel.setOpaque(false);
            optionsPanel.setOpaque(false);
            currentBetPanel.setOpaque(false);
        }
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
        String path = "/images/blank.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            resetImages(ImageResizer.getScaledImage(icon, cardSize),
                        playerHand);
            resetImages(ImageResizer.getScaledImage(icon, cardSize),
                        dealerHand);
        } catch (NullPointerException ex) {
            resetImages(null, playerHand);
            resetImages(null, dealerHand);
            System.err.println("Could not find " + path);
        }
    }

    /**
     * Removes any message displayed on the screen.
     */
    public void clearMessage() {
        messageLabel.setText("");
        messageLabel.setIcon(null);
    }

    /**
     * Displays the frame.
     */
    public void displayFrame() {
        frame.setVisible(true);
        SwingUtilities.invokeLater(View::new);
    }

    public void displaySettings() {
        backgroundPanel.removeAll();
        backgroundPanel.add(startPanel);
        backgroundPanel.repaint();
    }

    public void displayTable() {
        backgroundPanel.removeAll();
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(tablePanel, BorderLayout.CENTER);
        backgroundPanel.add(optionsPanel, BorderLayout.SOUTH);
        backgroundPanel.repaint();
    }

    /**
     * Displays a message on the screen with a message bubble icon.
     * @param message the message
     */
    public void displayMessage(String message) {
        displayMessage(message, "/images/message.png");
    }

    /**
     * Displays a message on the screen with the specified icon.
     * @param message the message
     */
    public void displayMessage(String message, String path) {
        messageLabel.setText(message);
        int size = messageLabel.getFont().getSize();
        setIcon(messageLabel, path, size);
    }

    public boolean prompt(String message, String title) {
        
        int option = JOptionPane.showConfirmDialog(frame,
                                                   message,
                                                   title,
                                                   JOptionPane.YES_NO_OPTION);
        return option == 0;
    }

    /**
     * Places the front side of the dealer's hole card down.
     * 
     * <p>In blackjack, the dealer will hide one of his two cards after the
     * initial deal. This hidden card is called the hole card.
     */
    public void hideHoleCard() {
        String path = "/images/" + cardStyle + "_back.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            dealerHand[0].setIcon(ImageResizer.getScaledImage(icon, cardSize));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }
    }

    public boolean isChoiceEnabled(String choice) {
        return playOptions.get(choice).isEnabled();
    }

    /**
     * Flips over the dealer's hole card.
     * 
     * <p>The dealer's hole card is usually revealed after the player has
     * finished their turn.
     * 
     * @param holeCardName the name of the hole card
     */
    public void revealHoleCard(String holeCardName) {
        JLabel label = dealerHand[0];
        String[] comp = holeCardName.split(" ");
        String value = comp[0], suit = comp[2];
        String path;
        path = "/images/" + cardStyle + "/" + suit + "/" + value + ".png";
        setIcon(label, path, cardSize);
    }

    /**
     * Flips over the dealer's hole card.
     * 
     * <p>The dealer's hole card is usually revealed after the player has
     * finished their turn.
     * 
     * @param holeCardName the name of the hole card
     */
    public void revealHoleCard(Object holeCardName) {
        revealHoleCard(holeCardName.toString());
    }

    /**
     * Displays all bet options.
     */
    public void enableAllChips() {
        betOptionsPanel.setVisible(true);
        betOptions.values().forEach((betOption) -> {
            betOption.setEnabled(true);
            betOption.setVisible(true);
        });
    }

    /**
     * Displays all play options.
     */
    public void enableAllChoices() {
        playOptionsPanel.setVisible(true);
        playOptions.values().forEach((playOption) -> {
            playOption.setEnabled(true);
            playOption.setVisible(true);
        });
    }

    /**
     * Removes all bet options from display.
     */
    public void disableAllChips() {
        betOptionsPanel.setVisible(false);
        betOptions.values().forEach((betOption) -> {
            betOption.setEnabled(false);
            betOption.setVisible(false);
        });
    }

    /**
     * Removes all play options from the display.
     */
    public void disableAllChoices() {
        playOptionsPanel.setVisible(false);
        playOptions.values().forEach((playOption) -> {
            playOption.setEnabled(false);
            playOption.setVisible(false);
        });
    }

    /**
     * Updates the bet options according to the player's remaining chips.
     * @param chips the player's remaining chips
     * @param betValues the possible bet options
     */
    public void updateChips(double chips, int[] betValues) {
        for (int i = 0, len = betValues.length; i < len; i++) {
            String betValue = String.valueOf(betValues[i]);
            if (chips < betValues[i]) {
                betOptions.get(betValue).setEnabled(false);
                betOptions.get(betValue).setVisible(false);
            } else {
                betOptions.get(betValue).setEnabled(true);
                betOptions.get(betValue).setVisible(true);
            }
        }
    }

    /**
     * Updates the card images on the dealer's side of the screen.
     * @param cardNames the names of the cards
     */
    public void updateDealerCards(String[] cardNames) {
        updateImages(cardNames, dealerHand);
    }

    /**
     * Updates the maximum value of the dealer's hand.
     * @param dealerHandValue the dealer's hand value
     */
    public void updateDealerHandValue(int dealerHandValue) {
        dealerHandValueLabel.setText(dealerHandValue + " — Dealer");

        String path = "/images/question.png";
        int size = dealerHandValueLabel.getFont().getSize();
        setIcon(dealerHandValueLabel, path, size);
    }

    public void updateDealerHandValue(int dealerHandValue, boolean isSoft) {
        dealerHandValueLabel.setText(dealerHandValue + " — Dealer");

        String path = "/images/";
        path += (isSoft) ? "soft.png" : "hard.png";
        int size = dealerHandValueLabel.getFont().getSize();
        setIcon(dealerHandValueLabel, path, size);
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
     * Updates the true count of cards displayed on the screen.
     * @param count the true count
     */
    public void updateTrueCount(int count) {
        trueCountLabel.setText(count + "");
    }

    /**
     * Clears the player and dealer's hand value.
     */
    public void resetHandValue() {
        playerHandValueLabel.setText(" ");
        playerHandValueLabel.setIcon(null);
        dealerHandValueLabel.setText(" ");
        dealerHandValueLabel.setIcon(null);
    }

    /**
     * Updates the maximum value and the type of the player's hand.
     * 
     * @param   playerHandValue the player's hand value
     * @param   isSoft if true, an S indicating a soft hand will be displayed 
     *          next to the value, else an H for a hard hand
     */
    public void updatePlayerHandValue(int playerHandValue, boolean isSoft) {
        playerHandValueLabel.setText(playerHandValue + " — Player");

        String path = "/images/";
        path += (isSoft) ? "soft.png" : "hard.png";
        int size = playerHandValueLabel.getFont().getSize();
        setIcon(playerHandValueLabel, path, size);
    }

    /**
     * Updates the amount of chips and the current bet of the player.
     * @param chips the player's chips
     * @param bet the player's bet
     */
    public void updateStats(double chips, double bet) {
        chipsLabel.setText(Format.currency(chips));
        currentBetValueLabel.setText(Format.currency(bet));
    }

    /**
     * Returns a collection of bet options.
     * 
     * <p>This method exists so that each button can have an action
     * event without having to write code every time a new button is added.
     * 
     * @return the bet options 
     */
    public Collection<JButton> getBetOptions() {
        return betOptions.values();
    }

    public int[] getSettings() {
        SettingsPanel settings = (SettingsPanel) settingsPanel;
        return settings.getSettings();
    }

    public void initBetOptions(int[] options) {
        String[] stringOptions = new String[options.length];
        for (int i = 0; i < stringOptions.length; i++) {
            stringOptions[i] = String.valueOf(options[i]);
        }
        initOptions(stringOptions, betOptions, betOptionsPanel, true);
    }

    public void initPlayOptions(String[] options) {
        initOptions(options, this.playOptions, playOptionsPanel, false);
    }

    public void initHandOptions(String[] options) {
        initOptions(options, this.handOptions, handOptionsPanel, false);
    }

    /**
     * Adds an ActionListener to a button. 
     * 
     * <p>This methods checks if the key is an option button or a choice button.
     * Options include: {@code Deal}, {@code Next Hand}, {@code Hint},
     * {@code New Game} and {@code Quit Game}. Choices include: {@code Hit},
     * {@code Double Down}, {@code Surrender} and {@code Stand}.
     * 
     * @param key the name of the button 
     * @param l the ActionListener
     */
    public void initButtonActionListener(String key, ActionListener l) {
        if (handOptions.containsKey(key)) {
            handOptions.get(key).addActionListener(l);
        } else if (playOptions.containsKey(key)) {
            playOptions.get(key).addActionListener(l);
        } else if (key.equals("Play")) {
            settingsPanel.initPlayActionListener(l);
        } else {
            System.err.println("Invalid key: " + key);
        }
    }

    /**
     * Disables and removes a button from the screen. 
     * 
     * <p>This methods checks if the key is an option button or a choice button.
     * Options include: {@code Deal}, {@code Next Hand}, {@code Hint},
     * {@code New Game} and {@code Quit Game}. Choices include: {@code Hit},
     * {@code Double Down}, {@code Surrender} and {@code Stand}.
     * 
     * @param key the name of the button 
     */
    public void disableButton(String key) {
        if (handOptions.containsKey(key)) {
            handOptions.get(key).setEnabled(false);
            handOptions.get(key).setVisible(false);
        } else if (playOptions.containsKey(key)) {
            playOptions.get(key).setEnabled(false);
            playOptions.get(key).setVisible(false);
        } else {
            System.err.println("Invalid key: " + key);
        }
    }

    /**
     * Enables and displays a button to the screen. 
     * 
     * <p>This methods checks if the key is an option button or a choice button.
     * Options include: {@code Deal}, {@code Next Hand}, {@code Hint},
     * {@code New Game} and {@code Quit Game}. Choices include: {@code Hit},
     * {@code Double Down}, {@code Surrender} and {@code Stand}.
     * 
     * @param key the name of the button 
     */
    public void enableButton(String key) {
        if (handOptions.containsKey(key)) {
            handOptions.get(key).setEnabled(true);
            handOptions.get(key).setVisible(true);
        } else if (playOptions.containsKey(key)) {
            playOptions.get(key).setEnabled(true);
            playOptions.get(key).setVisible(true);
        } else {
            System.err.println("Invalid key: " + key);
        }
    }

    private void initOptions(String[] options,
                             Map<String, JButton> map,
                             JPanel panel,
                             boolean hasIcon)
    {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < options.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            int right = (i == options.length - 1) ? 10 : 0;
            gbc.insets = new Insets(10, 10, 10, right);
            JButton option = new JButton(options[i]);
            option.setForeground(Palette.TEXT);
            option.setBackground(Palette.BLACK);
            option.setFont(font.generateFont(16));

            if (hasIcon) {
                String path = "/images/" + options[i] + ".png";
                setIcon(option, path, 16);
            }

            map.put(options[i], option);
            panel.add(option, gbc);
        }
    }

    private void layoutStartPanel() {
        startPanel.setLayout(new GridBagLayout());
        GridBagConstraints placeholderConstraints = new GridBagConstraints();
        placeholderConstraints.gridx = 0;
        placeholderConstraints.gridy = 0;
        placeholderConstraints.anchor = GridBagConstraints.CENTER;
        startPanel.add(settingsPanel, placeholderConstraints);
    }

    private void layoutTopPanel() {
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 20, 10, 0);
        topPanel.add(titleLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        topPanel.add(messageLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 20);
        topPanel.add(chipsLabel, gbc);
    }

    private void layoutTablePanel() {
        tablePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        tablePanel.add(dealerHandValueLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.ipadx = 100;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 40, 0);
        tablePanel.add(dealerPanel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        tablePanel.add(playerHandValueLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.ipadx = 100;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 10, 0);
        tablePanel.add(playerPanel, gbc);
    }

    private void layoutOptionsPanel() {
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 0, 10, 0);
        optionsPanel.add(currentBetPanel, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(betOptionsPanel, gbc);
        optionsPanel.add(playOptionsPanel, gbc);

        gbc.gridx++;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 0, 10, 20);
        optionsPanel.add(handOptionsPanel, gbc);

        currentBetPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
        currentBetPanel.add(deckCountLabel);
        currentBetPanel.add(trueCountLabel);
        currentBetPanel.add(currentBetValueLabel);
    }

    private void resetImages(ImageIcon image, JLabel[] playerHand) {
        Arrays.asList(playerHand).forEach((label) -> {
            label.setIcon(image);
        });
    }

    private void setIcon(AbstractButton label, String path, int size) {
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            label.setIcon(ImageResizer.getScaledImage(icon, size));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }
    }

    private void setIcon(JLabel label, String path, int size) {
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            label.setIcon(ImageResizer.getScaledImage(icon, size));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }
    }

    private void updateImages(String[] cardNames, JLabel[] labels) {
        for (int i = 0; i < cardNames.length; i++) {
            String[] comp = cardNames[i].split(" ");
            String value = comp[0], suit = comp[2];
            String path;
            path = "/images/" + cardStyle + "/" + suit + "/" + value + ".png";
            setIcon(labels[i], path, cardSize);
        }
    }

    private final JFrame frame;
    private final JPanel startPanel;
    private final JPanel backgroundPanel;
    private final JPanel topPanel;
    private final JLabel titleLabel;
    private final JLabel messageLabel;
    private final JLabel chipsLabel;
    private final JPanel tablePanel;
    private final SettingsPanel settingsPanel;
    private final JLabel dealerHandValueLabel;
    private final JPanel dealerPanel;
    private final JLabel[] dealerHand;
    private final JLabel playerHandValueLabel;
    private final JPanel playerPanel;
    private final JLabel[] playerHand;
    private final JPanel optionsPanel;
    private final JPanel currentBetPanel;
    private final JLabel deckCountLabel;
    private final JLabel trueCountLabel;
    private final JLabel currentBetValueLabel;
    private final JPanel betOptionsPanel;
    private final Map<String, JButton> betOptions;
    private final JPanel playOptionsPanel;
    private final Map<String, JButton> playOptions;
    private final JPanel handOptionsPanel;
    private final Map<String, JButton> handOptions;
    private final DefaultFont font;
    
    private final String cardStyle = "classic";
    private final int cardSize = 115;
}
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
        frame.setSize(800, 725);
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

        UIManager.put("OptionPane.messageFont", font.generateFont(14));
        UIManager.put("OptionPane.background", Palette.BLACK);
        UIManager.put("OptionPane.messageForeground", Palette.TEXT);
        UIManager.put("Panel.background", Palette.BLACK);
        UIManager.put("OptionPane.buttonFont", font.generateFont(12));
        UIManager.put("Button.foreground", Palette.TEXT);
        UIManager.put("Button.background", Palette.BLACK);

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

        JPanel placeholder = new JPanel();
        placeholder.setOpaque(false);
        placeholder.setLayout(new GridBagLayout());
        GridBagConstraints placeholderConstraints = new GridBagConstraints();
        placeholderConstraints.gridx = 0;
        placeholderConstraints.gridy = 0;
        placeholderConstraints.weightx = 10.0;
        placeholderConstraints.weighty = 10.0;
        placeholderConstraints.anchor = GridBagConstraints.CENTER;
        placeholder.add(settingsPanel, placeholderConstraints);

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(placeholder, BorderLayout.CENTER);
        // backgroundPanel.add(tablePanel, BorderLayout.CENTER);
        backgroundPanel.add(optionsPanel, BorderLayout.SOUTH);

        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints topPanelConstraints = new GridBagConstraints();

        topPanelConstraints.gridx = 0;
        topPanelConstraints.gridy = 0;
        topPanelConstraints.anchor = GridBagConstraints.LINE_START;
        topPanelConstraints.weightx = 1.0;
        topPanelConstraints.insets = new Insets(10, 40, 10, 0);
        topPanel.add(titleLabel, topPanelConstraints);

        topPanelConstraints.gridx = 1;
        topPanelConstraints.anchor = GridBagConstraints.CENTER;
        topPanelConstraints.weightx = 1.0;
        topPanelConstraints.insets = new Insets(0, 0, 0, 0);
        topPanel.add(messageLabel, topPanelConstraints);

        topPanelConstraints.gridx = 2;
        topPanelConstraints.anchor = GridBagConstraints.LINE_END;
        topPanelConstraints.weightx = 1.0;
        topPanelConstraints.insets = new Insets(0, 0, 0, 40);
        topPanel.add(chipsLabel, topPanelConstraints);

        dealerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));

        tablePanel.setLayout(new GridBagLayout());
        GridBagConstraints tablePanelConstraints = new GridBagConstraints();
        tablePanelConstraints.gridx = 0;
        tablePanelConstraints.gridy = 0;
        tablePanelConstraints.gridwidth = 1;
        tablePanelConstraints.insets = new Insets(0, 20, 0, 0);
        tablePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        tablePanel.add(dealerHandValueLabel, tablePanelConstraints);

        tablePanelConstraints.gridy = 1;
        tablePanelConstraints.gridwidth = 2;
        tablePanelConstraints.ipadx = 100;
        tablePanelConstraints.weightx = 1.0;
        tablePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        tablePanelConstraints.insets = new Insets(0, 0, 40, 0);
        tablePanel.add(dealerPanel, tablePanelConstraints);

        tablePanelConstraints.gridy = 2;
        tablePanelConstraints.gridwidth = 1;
        tablePanelConstraints.ipadx = 0;
        tablePanelConstraints.weightx = 0;
        tablePanelConstraints.insets = new Insets(0, 20, 0, 0);
        tablePanel.add(playerHandValueLabel, tablePanelConstraints);

        tablePanelConstraints.gridy = 3;
        tablePanelConstraints.gridwidth = 2;
        tablePanelConstraints.ipadx = 100;
        tablePanelConstraints.weightx = 1.0;
        tablePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        tablePanelConstraints.insets = new Insets(0, 0, 10, 0);
        tablePanel.add(playerPanel, tablePanelConstraints);

        for (int i = 0; i < dealerHand.length; i++) {
            JLabel dealerCard = new JLabel();
            JLabel playerCard = new JLabel();
            dealerHand[i] = dealerCard;
            playerHand[i] = playerCard;
            dealerPanel.add(dealerCard);
            playerPanel.add(playerCard);
        }

        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints optionsPanelConstraints = new GridBagConstraints();

        optionsPanelConstraints.gridx = 0;
        optionsPanelConstraints.gridy = 0;
        optionsPanelConstraints.weightx = 1.0;
        optionsPanelConstraints.weighty = 1.0;
        optionsPanelConstraints.anchor = GridBagConstraints.LINE_START;
        optionsPanelConstraints.insets = new Insets(10, 0, 10, 0);
        optionsPanel.add(currentBetPanel, optionsPanelConstraints);

        optionsPanelConstraints.gridx = 1;
        optionsPanelConstraints.weightx = 0;
        optionsPanelConstraints.weighty = 0;
        optionsPanelConstraints.anchor = GridBagConstraints.CENTER;
        optionsPanel.add(betOptionsPanel, optionsPanelConstraints);
        optionsPanel.add(playOptionsPanel, optionsPanelConstraints);

        optionsPanelConstraints.gridx = 2;
        optionsPanelConstraints.weightx = 1.0;
        optionsPanelConstraints.anchor = GridBagConstraints.LINE_END;
        optionsPanelConstraints.insets = new Insets(10, 0, 10, 40);
        optionsPanel.add(handOptionsPanel, optionsPanelConstraints);

        currentBetPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 0));
        currentBetPanel.add(deckCountLabel);
        currentBetPanel.add(trueCountLabel);
        currentBetPanel.add(currentBetValueLabel);

        betOptionsPanel.setLayout(new GridBagLayout());
        playOptionsPanel.setLayout(new GridBagLayout());
        handOptionsPanel.setLayout(new GridBagLayout());
        if (backgroundPanel.getBackground() != Palette.TABLE) {
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
    }

    /**
     * Displays the frame.
     */
    public void displayFrame() {
        frame.setVisible(true);
        SwingUtilities.invokeLater(View::new);
    }

    public void displaySettings() {
        tablePanel.setVisible(false);
    }

    public void displayTable() {
        backgroundPanel.remove(1);
        backgroundPanel.add(tablePanel, BorderLayout.CENTER);
        tablePanel.setVisible(true);
    }

    /**
     * Displays a message on the screen.
     * @param message the message
     */
    public void displayMessage(String message) {
        messageLabel.setText(message);
    }

    public boolean prompt(String message, String title) {
        
        int option = JOptionPane.showConfirmDialog(frame,
                                                   message,
                                                   title,
                                                   JOptionPane.YES_NO_OPTION);
        return option == 0;
    }

    @Deprecated
    /**
     * Displays a border around the where the player and dealer's cards are.
     */
    public void displayTableBorder() {
        Border border = new LineBorder(Color.WHITE, 4);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        playerPanel.setBorder(new CompoundBorder(margin, border));
        dealerPanel.setBorder(new CompoundBorder(margin, border));
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
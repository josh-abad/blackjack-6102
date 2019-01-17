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
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class View {

    public View() {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 725);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        String path;

        path = "/images/logo2.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            frame.setIconImage(icon.getImage());
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }

        font = new DefaultFont("Segoe UI");

        topPanel = new JPanel();
        titleLabel = new JLabel();

        path = "/images/logo1.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            titleLabel.setIcon(ImageResizer.getScaledImage(icon, 150));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }

        messagePanel = new JPanel();
        messageLabel = new JLabel("Welcome to Blackjack! Place a bet.");
        chipsLabel = new JLabel();

        tablePanel = new JPanel();
        dealerHandValueLabel = new JLabel();        
        dealerPanel = new JPanel();
        dealerHand = new JLabel[10];
        playerHandValueLabel = new JLabel();
        playerPanel = new JPanel();
        playerHand = new JLabel[10];

        optionsPanel = new JPanel();
        currentBetPanel = new JPanel();
        currentBetLabel = new JLabel("Bet:");
        currentBetValueLabel = new JLabel();
        betOptionsPanel = new JPanel();
        betOptionsLabel = new JLabel("Chips");
        betOptions = new HashMap<>();
        playOptionsPanel = new JPanel();
        playOptionsLabel = new JLabel("Choices");
        playOptions = new HashMap<>();
        handOptionsPanel = new JPanel();
        handOptionsLabel = new JLabel("Options");
        handOptions = new HashMap<>();

        topPanel.setBackground(Palette.TABLE);
        messagePanel.setBackground(Palette.TABLE_DARK);
        messageLabel.setForeground(Palette.TEXT);
        messageLabel.setFont(font.generateFont(16));

        chipsLabel.setForeground(Palette.TEXT);
        chipsLabel.setFont(font.generateFont(36));

        path = "/images/chip.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            chipsLabel.setIcon(ImageResizer.getScaledImage(icon, 36));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }

        tablePanel.setBackground(Palette.TABLE);
        dealerHandValueLabel.setForeground(Palette.TEXT);
        dealerPanel.setBackground(Palette.TABLE);
        playerHandValueLabel.setForeground(Palette.TEXT);
        playerPanel.setBackground(Palette.TABLE);
        dealerHandValueLabel.setFont(font.generateFont(16));
        playerHandValueLabel.setFont(font.generateFont(16));

        optionsPanel.setBackground(Palette.TABLE_DARK);
        currentBetPanel.setBackground(Palette.TABLE_DARK);
        betOptionsPanel.setBackground(Palette.TABLE_DARKER);
        playOptionsPanel.setBackground(Palette.TABLE_DARKER);
        handOptionsPanel.setBackground(Palette.TABLE_DARKER);
        currentBetLabel.setForeground(Palette.TEXT);
        currentBetValueLabel.setForeground(Palette.TEXT);
        betOptionsLabel.setForeground(Palette.TEXT);
        playOptionsLabel.setForeground(Palette.TEXT);
        handOptionsLabel.setForeground(Palette.TEXT);
        currentBetLabel.setFont(font.generateFont(18));
        currentBetValueLabel.setFont(font.generateFont(18));
        betOptionsLabel.setFont(font.generateFont(12));
        playOptionsLabel.setFont(font.generateFont(12));
        handOptionsLabel.setFont(font.generateFont(12));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(optionsPanel, BorderLayout.SOUTH);

        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints topPanelConstraints = new GridBagConstraints();

        topPanelConstraints.gridx = 0;
        topPanelConstraints.gridy = 0;
        topPanelConstraints.gridheight = 2;
        topPanelConstraints.fill = GridBagConstraints.VERTICAL;
        topPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        topPanelConstraints.weightx = 1.0;
        topPanelConstraints.insets = new Insets(10, 10, 0, 0);
        topPanel.add(titleLabel, topPanelConstraints);

        topPanelConstraints.gridx = 1;
        topPanelConstraints.gridy = 0;
        topPanelConstraints.gridheight = 1;
        topPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        topPanelConstraints.anchor = GridBagConstraints.PAGE_START;
        topPanelConstraints.weightx = 0;
        topPanelConstraints.weighty = 1.0;
        topPanelConstraints.insets = new Insets(0, 0, 10, 10);
        topPanel.add(messagePanel, topPanelConstraints);

        topPanelConstraints.gridx = 2;
        topPanelConstraints.gridy = 0;
        topPanelConstraints.gridheight = 2;
        topPanelConstraints.fill = GridBagConstraints.VERTICAL;
        topPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
        topPanelConstraints.weightx = 1.0;
        topPanelConstraints.weighty = 0;
        topPanelConstraints.insets = new Insets(0, 0, 0, 20);
        topPanel.add(chipsLabel, topPanelConstraints);

        messagePanel.add(messageLabel);

        dealerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 25, 25));
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 25, 25));

        tablePanel.setLayout(new GridBagLayout());
        GridBagConstraints tablePanelConstraints = new GridBagConstraints();
        tablePanelConstraints.gridx = 0;
        tablePanelConstraints.gridy = 0;
        tablePanelConstraints.gridwidth = 1;
        tablePanelConstraints.insets = new Insets(10, 10, 0, 0);
        tablePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        tablePanel.add(dealerHandValueLabel, tablePanelConstraints);

        tablePanelConstraints.gridy = 1;
        tablePanelConstraints.gridwidth = 2;
        tablePanelConstraints.ipadx = 100;
        tablePanelConstraints.weightx = 1.0;
        tablePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        tablePanelConstraints.insets = new Insets(0, 0, 10, 0);
        tablePanel.add(dealerPanel, tablePanelConstraints);

        tablePanelConstraints.gridy = 2;
        tablePanelConstraints.gridwidth = 1;
        tablePanelConstraints.ipadx = 0;
        tablePanelConstraints.weightx = 0;
        tablePanelConstraints.insets = new Insets(10, 10, 0, 0);
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

        optionsPanel.add(currentBetPanel);
        optionsPanel.add(betOptionsPanel);
        optionsPanel.add(playOptionsPanel);
        optionsPanel.add(handOptionsPanel);

        currentBetPanel.add(currentBetLabel);
        currentBetPanel.add(currentBetValueLabel);

        betOptionsPanel.setLayout(new GridBagLayout());
        playOptionsPanel.setLayout(new GridBagLayout());
        handOptionsPanel.setLayout(new GridBagLayout());
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
            resetImages(ImageResizer.getScaledImage(icon, 100), playerHand);
            resetImages(ImageResizer.getScaledImage(icon, 100), dealerHand);
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
        messagePanel.setOpaque(false);
    }

    /**
     * Displays the frame.
     */
    public void displayFrame() {
        frame.setVisible(true);
        SwingUtilities.invokeLater(View::new);
    }

    /**
     * Displays a message on the screen with the specified background color.
     * @param message the message
     * @param bg the desired background color
     */
    public void displayMessage(String message, Color bg) {
        messagePanel.setVisible(true);
        messagePanel.setOpaque(true);
        messagePanel.setBackground(bg);
        messageLabel.setText(message);
        messageLabel.setForeground(bg.equals(Color.GREEN)
                ? Palette.BLACK : Palette.TEXT);
    }

    /**
     * Displays a message on the screen with a dark green background.
     * @param message the message
     */
    public void displayMessage(String message) {
        displayMessage(message, Palette.TABLE_DARK);
    }

    /**
     * Displays a border around the where the player and dealer's cards are.
     */
    public void displayTableBorder() {
        Border border = new LineBorder(Palette.TABLE_LIGHT, 5);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        playerPanel.setBorder(new CompoundBorder(margin, border));
        dealerPanel.setBorder(new CompoundBorder(margin, border));
    }

    /**
     * Places the front side of the dealer's hole card down.
     * 
     * <p>In blackjack, the dealer will hide one of his two cards after the
     * initial deal. This hidden card is called the hole card.
     * 
     * @param holeCardName the name of the hole card
     */
    public void hideHoleCard(String holeCardName) {
        String suit = holeCardName.split(" ")[2];
        String path = "/images/cards/" + suit + "/Back.png";
        URL location = View.class.getResource(path);
        try {
            ImageIcon icon = new ImageIcon(location);
            dealerHand[0].setIcon(ImageResizer.getScaledImage(icon, 100));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }
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
        String[] comp = holeCardName.split(" ");
        String value = comp[0];
        String suit = comp[2];
        String path = "/images/cards/" + suit + "/" + value + ".png";
        URL location = View.class.getResource(path);
        try {
            ImageIcon icon = new ImageIcon(location);
            dealerHand[0].setIcon(ImageResizer.getScaledImage(icon, 100));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }
    }

    /**
     * Displays all bet options.
     */
    public void enableAllChips() {
        betOptionsPanel.setVisible(true);
        betOptions.values().forEach((betOption) -> {
            betOption.setEnabled(true);
        });
    }

    /**
     * Displays all play options.
     */
    public void enableAllChoices() {
        playOptionsPanel.setVisible(true);
        playOptions.values().forEach((playOption) -> {
            playOption.setEnabled(true);
        });
    }

    /**
     * Enables a hand option.
     * 
     * @param   option possible strings are: {@code "Deal"}, {@code "Next Hand"} 
     *          and {@code "Quit Game"}
     */
    public void enableOption(String option) {
        handOptions.get(option).setEnabled(true);
    }

    /**
     * Enables a play option.
     * 
     * @param   option possible strings are: {@code "Hit"}, {@code "Stand"},
     *          {@code "Double Down"} and {@code "Surrender"}
     */
    public void enablePlayOption(String option) {
        playOptions.get(option).setEnabled(true);
    }

    /**
     * Removes all bet options from display.
     */
    public void disableAllChips() {
        betOptionsPanel.setVisible(false);
        betOptions.values().forEach((betOption) -> {
            betOption.setEnabled(false);
        });
    }

    /**
     * Removes all play options from the display.
     */
    public void disableAllChoices() {
        playOptionsPanel.setVisible(false);
        playOptions.values().forEach((playOption) -> {
            playOption.setEnabled(false);
        });
    }

    /**
     * Disables a hand option.
     * 
     * @param   option possible strings are: {@code "Deal"}, {@code "Next Hand"} 
     *          and {@code "Quit Game"}
     */
    public void disableOption(String option) {
        if (handOptions.containsKey(option)) {
            handOptions.get(option).setEnabled(false);
        }
    }

    /**
     * Disables a play option.
     * 
     * @param   option possible strings are: {@code "Hit"}, {@code "Stand"},
     *          {@code "Double Down"} and {@code "Surrender"}
     */
    public void disableChoice(String option) {
        if (playOptions.containsKey(option)) {
            playOptions.get(option).setEnabled(false);
        }
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
            } else if (!betOptions.get(betValue).isEnabled()) {
                betOptions.get(betValue).setEnabled(true);
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
        dealerHandValueLabel.setAlignmentX(SwingConstants.LEADING);
    }

    /**
     * Updates the card images on the player's side of the screen.
     * @param cardNames the names of the cards
     */
    public void updatePlayerCards(String[] cardNames) {
        updateImages(cardNames, playerHand);
    }

    /**
     * Clears the player and dealer's hand value.
     */
    public void resetHandValue() {
        playerHandValueLabel.setText("Player");
        if (playerHandValueLabel.getIcon() != null) {
            playerHandValueLabel.setIcon(null);
        }
        dealerHandValueLabel.setText("Dealer");
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
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            int size = playerHandValueLabel.getFont().getSize();
            icon = ImageResizer.getScaledImage(icon, size);
            playerHandValueLabel.setIcon(icon);
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }

        dealerHandValueLabel.setText("Dealer");
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

    public Collection<JButton> getBetOptions() {
        return betOptions.values();
    }

    public void initBetOptions(int[] options) {
        initOptionLabel(betOptionsPanel, betOptionsLabel, options.length);
        String[] stringOptions = new String[options.length];
        for (int i = 0; i < stringOptions.length; i++) {
            stringOptions[i] = String.valueOf(options[i]);
        }
        initOptions(stringOptions, betOptions, betOptionsPanel, true);
    }

    public void initPlayOptions(String[] options) {
        initOptionLabel(playOptionsPanel, playOptionsLabel, options.length);
        initOptions(options, this.playOptions, playOptionsPanel, false);
    }

    public void initHandOptions(String[] options) {
        initOptionLabel(handOptionsPanel, handOptionsLabel, options.length);
        initOptions(options, this.handOptions, handOptionsPanel, false);
    }

    public void initHitActionListener(ActionListener l) {
        playOptions.get("Hit").addActionListener(l);
    }

    public void initStandActionListener(ActionListener l) {
        playOptions.get("Stand").addActionListener(l);
    }

    public void initDoubleDownActionListener(ActionListener l) {
        playOptions.get("Double Down").addActionListener(l);
    }

    public void initSurrenderActionListener(ActionListener l) {
        playOptions.get("Surrender").addActionListener(l);
    }

    public void initDealActionListener(ActionListener l) {
        handOptions.get("Deal").addActionListener(l);
    }

    public void initNextHandActionListener(ActionListener l) {
        handOptions.get("Next Hand").addActionListener(l);
    }

    public void initQuitGameActionListener(ActionListener l) {
        handOptions.get("Quit Game").addActionListener(l);
    }

    private void initOptionLabel(JPanel panel, JLabel label, int gridWidth) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = gridWidth;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(label, gbc);
    }

    private void initOptions(String[] options,
                             Map<String, JButton> map,
                             JPanel panel,
                             boolean hasIcon)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < options.length; i++) {
            gbc.gridx = i;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            int right = (i == options.length - 1) ? 5 : 0;
            gbc.insets = new Insets(0, 5, 5, right);
            JButton option = new JButton(options[i]);
            option.setForeground(Palette.TEXT);
            option.setBackground(Palette.TABLE_DARKEST);
            option.setFont(font.generateFont(14));

            if (hasIcon) {
                String path = "/images/" + options[i] + ".png";
                try {
                    URL location = View.class.getResource(path);
                    ImageIcon icon = new ImageIcon(location);
                    option.setIcon(ImageResizer.getScaledImage(icon, 14));
                } catch (NullPointerException ex) {
                    System.err.println("Could not find " + path);
                }
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

    private void updateImages(String[] cardNames, JLabel[] labels) {
        for (int i = 0; i < cardNames.length; i++) {
            String[] comp = cardNames[i].split(" ");
            String value = comp[0];
            String suit = comp[2];
            String path = "/images/cards/" + suit + "/" + value + ".png";
            try {
                URL location = View.class.getResource(path);
                ImageIcon icon = new ImageIcon(location);
                labels[i].setIcon(ImageResizer.getScaledImage(icon, 100));
            } catch (NullPointerException ex) {
                System.err.println("Could not find " + path);
            }
        }
    }
    
    private final JFrame frame;
    private final JPanel topPanel;
    private final JLabel titleLabel;
    private final JPanel messagePanel;
    private final JLabel messageLabel;
    private final JLabel chipsLabel;
    private final JPanel tablePanel;
    private final JLabel dealerHandValueLabel;
    private final JPanel dealerPanel;
    private final JLabel[] dealerHand;
    private final JLabel playerHandValueLabel;
    private final JPanel playerPanel;
    private final JLabel[] playerHand;
    private final JPanel optionsPanel;
    private final JPanel currentBetPanel;
    private final JLabel currentBetLabel;
    private final JLabel currentBetValueLabel;
    private final JPanel betOptionsPanel;
    private final JLabel betOptionsLabel;
    private final Map<String, JButton> betOptions;
    private final JPanel playOptionsPanel;
    private final JLabel playOptionsLabel;
    private final Map<String, JButton> playOptions;
    private final JPanel handOptionsPanel;
    private final JLabel handOptionsLabel;
    private final Map<String, JButton> handOptions;
    private final DefaultFont font;
}
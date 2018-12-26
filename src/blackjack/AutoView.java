/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author yeyoa
 */
public class AutoView extends javax.swing.JFrame {

    private final JPanel currentBetPanel;
    private final JPanel betOptionsPanel;
    private final JPanel playOptionsPanel;
    private final JPanel handOptionsPanel;
    private final JLabel currentBetLabel;
    private final JLabel currentBetValueLabel;
    private final ArrayList<JLabel> dealerHand;
    private final ArrayList<JLabel> playerHand;
    private JButton[] betOptions;
    private final HashMap<String, JButton> playOptions;
    private final HashMap<String, JButton> handOptions;

    private final String FONT_NAME;

    /**
     * Creates new form AutoView
     */
    public AutoView() {
        initComponents();

        getContentPane().setBackground(new Color(0, 102, 51));

        FONT_NAME = "Segoe UI";

        currentBetPanel = new JPanel();
        betOptionsPanel = new JPanel();
        playOptionsPanel = new JPanel();
        handOptionsPanel = new JPanel();
        titleLabel.setIcon(ImageResizer.getScaledImage(
                new ImageIcon(getClass().getResource(
                        "/blackjack/images/logo1.png"
                )),
                150
        ));
        messageLabel.setText("Welcome to Blackjack");
        currentBetLabel = new JLabel("Bet:");
        currentBetValueLabel = new JLabel();

        // final int WIDTH = 10;
        // final int LENGTH = 50;
        // playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, WIDTH, LENGTH));
        // dealerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, WIDTH, LENGTH));
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            playerHand.add(new JLabel());
            dealerHand.add(new JLabel());
            playerPanel.add(playerHand.get(i));
            dealerPanel.add(dealerHand.get(i));
        }

        tablePanel.setBackground(new Color(0, 102, 51));
        optionsPanel.setBackground(tablePanel.getBackground().darker());
        betOptionsPanel.setBackground(optionsPanel.getBackground().darker());
        playOptionsPanel.setBackground(optionsPanel.getBackground().darker());
        handOptionsPanel.setBackground(optionsPanel.getBackground().darker());

        playOptions = new HashMap<>();
        for (int i = 0, len = 4; i < len; i++) {
            String[] keys = {"Hit", "Stand", "Double Down", "Surrender"};
            playOptions.put(keys[i], new JButton(keys[i]));
            playOptions.get(keys[i]).setForeground(Color.WHITE);
            playOptions.get(keys[i]).setBackground(
                    playOptionsPanel.getBackground().darker()
            );
            playOptions.get(keys[i]).setFont(new Font(FONT_NAME, 0, 14));
            playOptionsPanel.add(playOptions.get(keys[i]));
        }

        handOptions = new HashMap<>();
        for (int i = 0, len = 3; i < len; i++) {
            String[] keys = {"Deal", "Next Hand", "Quit Game"};
            handOptions.put(keys[i], new JButton(keys[i]));
            handOptions.get(keys[i]).setForeground(Color.WHITE);
            handOptions.get(keys[i]).setBackground(
                    handOptionsPanel.getBackground().darker()
            );
            handOptions.get(keys[i]).setFont(new Font(FONT_NAME, 0, 14));
            handOptionsPanel.add(handOptions.get(keys[i]));
        }

        // frame.getContentPane().add(BorderLayout.NORTH, topPanel);
        // frame.getContentPane().add(BorderLayout.CENTER, tablePanel);
        // frame.getContentPane().add(BorderLayout.SOUTH, optionsPanel);

        chipsLabel.setForeground(Color.WHITE);
        chipsLabel.setFont(new Font(FONT_NAME, 0, 36));
        chipsLabel.setIcon(ImageResizer.getScaledImage(
                new ImageIcon(getClass().getResource(
                        "/blackjack/images/chip.png"
                )),
                chipsLabel.getFont().getSize()
        ));

        topPanel.setBackground(tablePanel.getBackground());
        // topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        // topPanel.add(titleLabel);
        // topPanel.add(Box.createHorizontalGlue());
        // topPanel.add(messagePanel);
        // topPanel.add(messageLabel);
        // topPanel.add(Box.createHorizontalGlue());
        // topPanel.add(chipsLabel);
        // topPanel.setSize(topPanel.getWidth(), topPanel.getHeight()+250);


        // tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        // tablePanel.add(dealerPanel);
        // tablePanel.add(playerPanel);

        messagePanel.setBackground(tablePanel.getBackground().darker());
        // messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        // messagePanel.add(messageLabel);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font(FONT_NAME, 0, 16));

        dealerPanel.setBackground(tablePanel.getBackground());
        playerPanel.setBackground(tablePanel.getBackground());
        // playerPanel.setSize(new Dimension(frame.getContentPane().getWidth() - 20, 80));
        playerPanel.setBorder(BorderFactory.createLineBorder(
                tablePanel.getBackground().brighter(),
                4
        ));
        dealerPanel.setBorder(BorderFactory.createLineBorder(
                tablePanel.getBackground().brighter(),
                4
        ));

        optionsPanel.add(currentBetPanel);
        optionsPanel.add(betOptionsPanel);
        optionsPanel.add(playOptionsPanel);
        optionsPanel.add(handOptionsPanel);

        currentBetLabel.setForeground(Color.WHITE);
        currentBetLabel.setFont(new Font(FONT_NAME, 0, 18));
        currentBetValueLabel.setForeground(Color.WHITE);
        currentBetValueLabel.setFont(new Font(FONT_NAME, 0, 18));

        currentBetPanel.setBackground(optionsPanel.getBackground().darker());
        currentBetPanel.add(currentBetLabel);
        currentBetPanel.add(currentBetValueLabel);

        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        chipsLabel = new javax.swing.JLabel();
        messagePanel = new javax.swing.JPanel();
        messageLabel = new javax.swing.JLabel();
        tablePanel = new javax.swing.JPanel();
        dealerPanel = new javax.swing.JPanel();
        playerPanel = new javax.swing.JPanel();
        optionsPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        topPanel.setBackground(tablePanel.getBackground());

        chipsLabel.setText("jLabel2");

        messageLabel.setText("jLabel1");

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(messageLabel)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(messageLabel)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                .addComponent(chipsLabel)
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(chipsLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(topPanelLayout.createSequentialGroup()
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablePanel.setBackground(new java.awt.Color(0, 102, 51));

        javax.swing.GroupLayout dealerPanelLayout = new javax.swing.GroupLayout(dealerPanel);
        dealerPanel.setLayout(dealerPanelLayout);
        dealerPanelLayout.setHorizontalGroup(
            dealerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        dealerPanelLayout.setVerticalGroup(
            dealerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 77, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout playerPanelLayout = new javax.swing.GroupLayout(playerPanel);
        playerPanel.setLayout(playerPanelLayout);
        playerPanelLayout.setHorizontalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        playerPanelLayout.setVerticalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 77, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dealerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dealerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(playerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dealerPanel, playerPanel});

        optionsPanel.setBackground(tablePanel.getBackground().darker());

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(optionsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void updateBetOptions(double chips) {
        final int[] BET_VALUES = {5, 10, 25, 50, 100};
        for (int i = 0, len = BET_VALUES.length; i < len; i++) {
            if (chips < BET_VALUES[i]) {
                betOptions[i].setEnabled(false);
            } else if (!betOptions[i].isEnabled()) {
                betOptions[i].setEnabled(true);
            }
        }
    }

    private void updateImages(ArrayList<Card> hand, ArrayList<JLabel> labels) {
        try {
            int i = 0;
            for (Card card : hand) {
                labels.get(i).setIcon(card.getFrontIcon());
                i++;
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.getMessage();
            disablePlayOption("Hit");
        }
    }

    public void updateCards(ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
        updateImages(playerHand, this.playerHand);
        updateImages(dealerHand, this.dealerHand);
    }

    public void updateStats(double chips, double bet) {
        chipsLabel.setText(Format.currency(chips));
        currentBetValueLabel.setText(Format.currency(bet));
    }

    public void initBetOptions(int[] betValues) {
        betOptions = new JButton[betValues.length];
        for (int i = 0; i < betOptions.length; i++) {
            betOptions[i] = new JButton(String.valueOf(betValues[i]));
            betOptions[i].setIcon(ImageResizer.getScaledImage(
                    new ImageIcon(getClass().getResource(
                            "/blackjack/images/" + betValues[i] + ".png"
                    )),
                    betOptions[i].getFont().getSize()
            ));
            betOptions[i].setForeground(Color.WHITE);
            betOptions[i].setBackground(
                    betOptionsPanel.getBackground().darker()
            );
            betOptions[i].setFont(new Font(FONT_NAME, 0, 14));
            betOptionsPanel.add(betOptions[i]);
        }
    }

    public void enablePlayOption(String option) {
        playOptions.get(option).setEnabled(true);
    }

    public void disablePlayOption(String option) {
        playOptions.get(option).setEnabled(false);
    }

    public void enableHandOption(String option) {
        handOptions.get(option).setEnabled(true);
    }

    public void disableHandOption(String option) {
        if (handOptions.containsKey(option)) {
            handOptions.get(option).setEnabled(false);
        }
    }

    public void enableAllPlayOptions() {
        playOptionsPanel.setVisible(true);
    }

    public void disableAllPlayOptions() {
        playOptionsPanel.setVisible(false);
    }

    public void enableAllBetOptions() {
        betOptionsPanel.setVisible(true);
    }

    public void disableAllBetOptions() {
        betOptionsPanel.setVisible(false);
    }

    public void displayFrame() {
        setVisible(true);
    }

    public void displayMessage(String message, Color color) {
        if (!messagePanel.isVisible()) {
            messagePanel.setVisible(true);
        }
        if (!messagePanel.isOpaque()) {
            messagePanel.setOpaque(true);
        }
        if (!color.equals(messagePanel.getBackground())) {
            messagePanel.setBackground(color);
        }
        if (!message.equals(messageLabel.getText())) {
            messageLabel.setText(message);
        }
        messageLabel.setForeground(color.equals(Color.green)
                ? Color.black : Color.white);
    }

    public void displayMessage(String message) {
        displayMessage(message, tablePanel.getBackground().darker());
    }

    public void hideHoleCard(Card card) {
        dealerHand.get(0).setIcon(card.getBackIcon());
    }

    public void revealHoleCard(Card card) {
        dealerHand.get(0).setIcon(card.getFrontIcon());
    }

    private void resetImages(ArrayList<JLabel> playerHand) {
        playerHand.forEach((card) -> {
            card.setIcon(null);
        });
    }

    public void clearCards() {
        resetImages(playerHand);
        resetImages(dealerHand);
    }

    public JButton[] getBetOptions() {
        return betOptions;
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chipsLabel;
    private javax.swing.JPanel dealerPanel;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel playerPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}

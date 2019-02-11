package com.yeyoan.blackjack.view;

import com.yeyoan.util.ImageResizer;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SettingsPanel extends JPanel {

    public SettingsPanel(ImageIcon logo) {
        initPanel(logo);
    }

    public Object[] getSettings() {
        Object[] settings = {
            name(),
            deckAmount(),
            minimumBet(),
            dealerBehavior(),
        };
        return settings;
    }

    private String name() {
        return nameTextField.getText();
    }

    private int dealerBehavior() {
        return (standRadioButton.isSelected()) ? STAND : HIT;
    }

    private int deckAmount() {
        return (int) deckSpinner.getValue();
    }

    private int minimumBet() {
        return (int) betSpinner.getValue();
    }

    private void initPanel(ImageIcon logo) {
        menuItemsPanel = new JPanel();
        logoLabel = new JLabel();
        playerLabel = new JLabel("PLAYER");
        nameLabel = new JLabel("Name");
        houseRulesLabel = new JLabel("HOUSE RULES");
        deckAmountLabel = new JLabel("Number of decks");
        minimumBetLabel = new JLabel("Minimum bet");
        dealerBehaviorLabel = new JLabel("DEALER BEHAVIOR");
        standDescriptionLabel = new JLabel("The dealer stands on a soft 17");
        hitDescriptionLabel = new JLabel("The dealer hits a soft 17");
        nameTextField = new JTextField();
        deckSpinner = new JSpinner();
        betSpinner = new JSpinner();
        standRadioButton = new JRadioButton("S17");
        hitRadioButton = new JRadioButton("H17");
        dealerBehaviorGroup = new ButtonGroup();
        displayHandValue = new JCheckBox("Show hand value");
        playButton = new JButton("PLAY");

        logoLabel.setIcon(ImageResizer.getScaledImage(logo, 150));

        hitRadioButton.setSelected(true);
        dealerBehaviorGroup.add(standRadioButton);
        dealerBehaviorGroup.add(hitRadioButton);

        displayHandValue.setSelected(true);

        deckSpinner.setModel(new SpinnerNumberModel(4, 1, 8, 1));
        betSpinner.setModel(new SpinnerNumberModel(25, 0, 1000, 5));
        int len = deckSpinner.getEditor().getComponentCount();
        for (int i = 0; i < len; i++) {
            Component dc = deckSpinner.getEditor().getComponent(i);
            dc.setBackground(View.PALETTE.menu());
            dc.setForeground(View.PALETTE.text());

            Component bc = betSpinner.getEditor().getComponent(i);
            bc.setBackground(View.PALETTE.menu());
            bc.setForeground(View.PALETTE.text());
        }

        playerLabel.setFont(View.FONT.generate(12, Font.BOLD));
        nameLabel.setFont(View.FONT.generate(14));
        houseRulesLabel.setFont(View.FONT.generate(12, Font.BOLD));
        deckAmountLabel.setFont(View.FONT.generate(14));
        minimumBetLabel.setFont(View.FONT.generate(14));
        nameTextField.setFont(View.FONT.generate(14));
        deckSpinner.setFont(View.FONT.generate(14));
        betSpinner.setFont(View.FONT.generate(14));
        dealerBehaviorLabel.setFont(View.FONT.generate(12, Font.BOLD));
        standDescriptionLabel.setFont(View.FONT.generate(12));
        hitDescriptionLabel.setFont(View.FONT.generate(12));
        standRadioButton.setFont(View.FONT.generate(14));
        hitRadioButton.setFont(View.FONT.generate(14));
        displayHandValue.setFont(View.FONT.generate(14));
        playButton.setFont(View.FONT.generate(14, Font.BOLD));

        playerLabel.setForeground(View.PALETTE.heading());
        nameLabel.setForeground(View.PALETTE.text());
        houseRulesLabel.setForeground(View.PALETTE.heading());
        deckAmountLabel.setForeground(View.PALETTE.text());
        minimumBetLabel.setForeground(View.PALETTE.text());
        dealerBehaviorLabel.setForeground(View.PALETTE.heading());
        standDescriptionLabel.setForeground(View.PALETTE.heading());
        hitDescriptionLabel.setForeground(View.PALETTE.heading());
        nameTextField.setForeground(View.PALETTE.text());
        standRadioButton.setForeground(View.PALETTE.text());
        hitRadioButton.setForeground(View.PALETTE.text());
        displayHandValue.setForeground(View.PALETTE.text());
        playButton.setForeground(View.PALETTE.menu());

        EmptyBorder eb = new EmptyBorder(1, 1, 1, 1);
        LineBorder lb = new LineBorder(View.PALETTE.separator());
        nameTextField.setBorder(new CompoundBorder(lb, eb));
        deckSpinner.setBorder(eb);
        betSpinner.setBorder(eb);

        nameTextField.setBackground(View.PALETTE.menu());
        deckSpinner.setBackground(View.PALETTE.separator());
        betSpinner.setBackground(View.PALETTE.separator());
        menuItemsPanel.setBackground(View.PALETTE.menu());
        standRadioButton.setBackground(View.PALETTE.menu());
        hitRadioButton.setBackground(View.PALETTE.menu());
        displayHandValue.setBackground(View.PALETTE.menu());
        playButton.setBackground(View.PALETTE.button());
        menuItemsPanel.setBorder(new LineBorder(View.PALETTE.separator()));
        setOpaque(false);

        menuItemsPanel.setLayout(new GridBagLayout());
        setLayout(new GridBagLayout());

        GridBagConstraints outerGBC = new GridBagConstraints();

        outerGBC.gridx = 0;
        outerGBC.gridy = 0;
        outerGBC.insets = new Insets(10, 10, 10, 10);
        add(logoLabel, outerGBC);

        outerGBC.gridy++;
        outerGBC.insets = new Insets(0, 0, 0, 0);
        add(menuItemsPanel, outerGBC);

        GridBagConstraints innerGBC = new GridBagConstraints();

        innerGBC.gridx = 0;
        innerGBC.gridy = 0;
        innerGBC.gridwidth = 2;
        innerGBC.insets = new Insets(10, 10, 2, 10);
        innerGBC.fill = GridBagConstraints.HORIZONTAL;
        menuItemsPanel.add(playerLabel, innerGBC);

        innerGBC.gridy++;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(createSeparator(View.PALETTE.separator()), innerGBC);

        innerGBC.gridy++;
        innerGBC.gridwidth = 1;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(nameLabel, innerGBC);

        innerGBC.gridx++;
        innerGBC.insets = new Insets(0, 0, 10, 10);
        menuItemsPanel.add(nameTextField, innerGBC);

        innerGBC.gridx = 0;
        innerGBC.gridy++;
        innerGBC.gridwidth = 2;
        innerGBC.insets = new Insets(10, 10, 2, 10);
        menuItemsPanel.add(houseRulesLabel, innerGBC);

        innerGBC.gridy++;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(createSeparator(View.PALETTE.separator()), innerGBC);

        innerGBC.gridy++;
        innerGBC.gridwidth = 1;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(deckAmountLabel, innerGBC);

        innerGBC.gridx++;
        innerGBC.insets = new Insets(10, 0, 10, 10);
        menuItemsPanel.add(deckSpinner, innerGBC);

        innerGBC.gridx = 0;
        innerGBC.gridy++;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(minimumBetLabel, innerGBC);

        innerGBC.gridx++;
        innerGBC.insets = new Insets(0, 0, 10, 10);
        menuItemsPanel.add(betSpinner, innerGBC);

        innerGBC.gridx = 0;
        innerGBC.gridy++;
        innerGBC.gridwidth = 2;
        innerGBC.insets = new Insets(10, 10, 2, 10);
        menuItemsPanel.add(dealerBehaviorLabel, innerGBC);

        innerGBC.gridy++;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(createSeparator(View.PALETTE.separator()), innerGBC);

        innerGBC.gridy++;
        innerGBC.gridwidth = 1;
        innerGBC.insets = new Insets(0, 10, 0, 10);
        menuItemsPanel.add(hitRadioButton, innerGBC);

        innerGBC.gridy++;
        innerGBC.gridwidth = 2;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(hitDescriptionLabel, innerGBC);

        innerGBC.gridy++;
        innerGBC.gridwidth = 1;
        innerGBC.insets = new Insets(10, 10, 0, 10);
        menuItemsPanel.add(standRadioButton, innerGBC);

        innerGBC.gridy++;
        innerGBC.gridwidth = 2;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(standDescriptionLabel, innerGBC);

        innerGBC.gridy++;
        innerGBC.fill = GridBagConstraints.NONE;
        innerGBC.insets = new Insets(10, 10, 10, 10);
        menuItemsPanel.add(playButton, innerGBC);
    }

    private JSeparator createSeparator(Color fg) {
        JSeparator separator = new JSeparator();
        separator.setForeground(fg);
        separator.setBackground(fg);
        return separator;
    }

    public void initPlayActionListener(ActionListener l) {
        playButton.addActionListener(l);
    }

    private JPanel menuItemsPanel;
    private JLabel logoLabel;
    private JLabel playerLabel;
    private JLabel nameLabel;
    private JLabel houseRulesLabel;
    private JLabel deckAmountLabel;
    private JLabel minimumBetLabel;
    private JLabel dealerBehaviorLabel;
    private JLabel standDescriptionLabel;
    private JLabel hitDescriptionLabel;
    private JTextField nameTextField;
    private JSpinner deckSpinner;
    private JSpinner betSpinner;
    private JRadioButton standRadioButton;
    private JRadioButton hitRadioButton;
    private ButtonGroup dealerBehaviorGroup;
    private JCheckBox displayHandValue;
    private JButton playButton;
    private final static int HIT = 0;
    private final static int STAND = 1;
}

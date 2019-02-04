package blackjack;

import design.DefaultFont;
import design.ImageResizer;
import design.Palette;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class SettingsPanel extends JPanel {

    public SettingsPanel(DefaultFont font) {
        initPanel(font);
    }

    public int[] getSettings() {
        int[] settings = {
            deckAmount(),
            minimumBet(),
            dealerBehavior(),
            // handValueVisible()
        };
        return settings;
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

    private int handValueVisible() {
        return (displayHandValue.isSelected()) ? 1 : 0;
    }

    private void initPanel(DefaultFont font) {
        menuItemsPanel = new JPanel();
        logoLabel = new JLabel();
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
        playButton = new JButton("Play");

        String path = "/images/default_logo.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            logoLabel.setIcon(ImageResizer.getScaledImage(icon, 150));
        } catch (NullPointerException ex) {
            System.err.println("Could not find " + path);
        }

        hitRadioButton.setSelected(true);
        dealerBehaviorGroup.add(standRadioButton);
        dealerBehaviorGroup.add(hitRadioButton);

        displayHandValue.setSelected(true);

        deckSpinner.setModel(new SpinnerNumberModel(4, 1, 8, 1));
        betSpinner.setModel(new SpinnerNumberModel(25, 0, 1000, 5));
        int len = deckSpinner.getEditor().getComponentCount();
        for (int i = 0; i < len; i++) {
            Component dc = deckSpinner.getEditor().getComponent(i);
            dc.setBackground(Palette.BLACK);
            dc.setForeground(Palette.TEXT);

            Component bc = betSpinner.getEditor().getComponent(i);
            bc.setBackground(Palette.BLACK);
            bc.setForeground(Palette.TEXT);
        }

        nameLabel.setFont(font.generate(14));
        houseRulesLabel.setFont(font.generate(12, Font.BOLD));
        deckAmountLabel.setFont(font.generate(14));
        minimumBetLabel.setFont(font.generate(14));
        nameTextField.setFont(font.generate(14));
        deckSpinner.setFont(font.generate(14));
        betSpinner.setFont(font.generate(14));
        dealerBehaviorLabel.setFont(font.generate(12, Font.BOLD));
        standDescriptionLabel.setFont(font.generate(12));
        hitDescriptionLabel.setFont(font.generate(12));
        standRadioButton.setFont(font.generate(14));
        hitRadioButton.setFont(font.generate(14));
        displayHandValue.setFont(font.generate(14));
        playButton.setFont(font.generate(14));

        nameLabel.setForeground(Palette.TEXT);
        houseRulesLabel.setForeground(Palette.LIGHT_GREY);
        deckAmountLabel.setForeground(Palette.TEXT);
        minimumBetLabel.setForeground(Palette.TEXT);
        dealerBehaviorLabel.setForeground(Palette.LIGHT_GREY);
        standDescriptionLabel.setForeground(Palette.LIGHT_GREY);
        hitDescriptionLabel.setForeground(Palette.LIGHT_GREY);
        standRadioButton.setForeground(Palette.TEXT);
        hitRadioButton.setForeground(Palette.TEXT);
        displayHandValue.setForeground(Palette.TEXT);
        playButton.setForeground(Palette.BLACK);

        deckSpinner.setBorder(new EmptyBorder(1, 1, 1, 1));
        betSpinner.setBorder(new EmptyBorder(1, 1, 1, 1));

        deckSpinner.setBackground(Palette.DARK_GREY);
        betSpinner.setBackground(Palette.DARK_GREY);
        menuItemsPanel.setBackground(Palette.BLACK);
        standRadioButton.setBackground(Palette.BLACK);
        hitRadioButton.setBackground(Palette.BLACK);
        displayHandValue.setBackground(Palette.BLACK);
        playButton.setBackground(Palette.BUTTON);
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
        menuItemsPanel.add(houseRulesLabel, innerGBC);

        innerGBC.gridy++;
        innerGBC.insets = new Insets(0, 10, 10, 10);
        menuItemsPanel.add(createSeparator(Palette.DARK_GREY), innerGBC);

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
        menuItemsPanel.add(createSeparator(Palette.DARK_GREY), innerGBC);

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

        /*
        innerGBC.gridy++;
        innerGBC.insets = new Insets(0, 10, 20, 10);
        menuItemsPanel.add(displayHandValue, innerGBC);
        */

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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 400);
        frame.setLocationRelativeTo(null);
        frame.add(new SettingsPanel(new DefaultFont("Segoe UI")));
        frame.setVisible(true);
    }

    public void initPlayActionListener(ActionListener l) {
        playButton.addActionListener(l);
    }

    private JPanel menuItemsPanel;
    private JLabel logoLabel;
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

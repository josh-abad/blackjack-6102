package blackjack;

import design.DefaultFont;
import design.Palette;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SettingsPanel extends JPanel {

    public SettingsPanel(DefaultFont font) {
        initPanel(font);
    }

    public String getDealerBehavior() {
        return (standRadioButton.isSelected()) ? "Stand" : "Hit";
    }

    public int getDeckAmount() {
        return (int) deckAmountSpinner.getValue();
    }

    public int getMinimumBet() {
        return (int) minimumBetSpinner.getValue();
    }

    public boolean handValueVisible() {
        return displayHandValue.isSelected();
    }

    private void initPanel(DefaultFont font) {
        this.font = font;
        settingsLabel = new JLabel("Settings");
        deckAmountLabel = new JLabel("Number of decks");
        minimumBetLabel = new JLabel("Minimum bet");
        dealerBehaviorLabel = new JLabel("Dealer behavior for soft 17");
        deckAmountSpinner = new JSpinner();
        minimumBetSpinner = new JSpinner();
        standRadioButton = new JRadioButton("Stand");
        hitRadioButton = new JRadioButton("Hit");
        dealerBehaviorGroup = new ButtonGroup();
        displayHandValue = new JCheckBox("Show hand value");
        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save");

        hitRadioButton.setSelected(true);
        dealerBehaviorGroup.add(standRadioButton);
        dealerBehaviorGroup.add(hitRadioButton);

        displayHandValue.setSelected(true);

        deckAmountSpinner.setModel(new SpinnerNumberModel(4, 1, 8, 1));
        minimumBetSpinner.setModel(new SpinnerNumberModel(25, 0, 1000, 5));

        settingsLabel.setFont(font.generateFont(16));
        deckAmountLabel.setFont(font.generateFont(12));
        deckAmountSpinner.setFont(font.generateFont(12));
        minimumBetLabel.setFont(font.generateFont(12));
        minimumBetSpinner.setFont(font.generateFont(12));
        dealerBehaviorLabel.setFont(font.generateFont(12));
        standRadioButton.setFont(font.generateFont(12));
        hitRadioButton.setFont(font.generateFont(12));
        displayHandValue.setFont(font.generateFont(12));
        cancelButton.setFont(font.generateFont(12));
        saveButton.setFont(font.generateFont(12));

        settingsLabel.setForeground(Palette.TEXT);
        deckAmountLabel.setForeground(Palette.TEXT);
        deckAmountSpinner.setForeground(Palette.TEXT);
        minimumBetLabel.setForeground(Palette.TEXT);
        minimumBetSpinner.setForeground(Palette.TEXT);
        dealerBehaviorLabel.setForeground(Palette.TEXT);
        standRadioButton.setForeground(Palette.TEXT);
        hitRadioButton.setForeground(Palette.TEXT);
        displayHandValue.setForeground(Palette.TEXT);
        cancelButton.setForeground(Palette.TEXT);
        saveButton.setForeground(Palette.TEXT);

        standRadioButton.setBackground(Palette.BLACK);
        hitRadioButton.setBackground(Palette.BLACK);
        displayHandValue.setBackground(Palette.BLACK);
        cancelButton.setBackground(Palette.BLACK);
        saveButton.setBackground(Palette.BLACK);
        setBackground(Palette.BLACK);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(settingsLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(deckAmountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(deckAmountSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(minimumBetLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(minimumBetSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(dealerBehaviorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(hitRadioButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(standRadioButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(displayHandValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(cancelButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(saveButton, gbc);

        saveButton.addActionListener((ActionEvent e) -> {
            System.out.println("Dealer behavior: " + getDealerBehavior());
            System.out.println("Deck amount: " + getDeckAmount());
            System.out.println("Minimum bet: " + getMinimumBet());
            String hv = (handValueVisible()) ? "Visible" : "Hidden";
            System.out.println("Hand value: " + hv);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel settings = new SettingsPanel(new DefaultFont("Segoe UI"));

        frame.add(settings);
        frame.setVisible(true);
    }
    
    private JLabel settingsLabel;
    private JLabel deckAmountLabel;
    private JLabel minimumBetLabel;
    private JLabel dealerBehaviorLabel;
    private JSpinner deckAmountSpinner;
    private JSpinner minimumBetSpinner;
    private JRadioButton standRadioButton;
    private JRadioButton hitRadioButton;
    private ButtonGroup dealerBehaviorGroup;
    private JCheckBox displayHandValue;
    private JButton cancelButton;
    private JButton saveButton;
    private DefaultFont font;
}

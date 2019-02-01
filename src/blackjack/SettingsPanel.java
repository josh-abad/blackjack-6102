package blackjack;

import design.DefaultFont;
import design.ImageResizer;
import design.Palette;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SettingsPanel extends JPanel {

    public SettingsPanel(DefaultFont font) {
        initPanel(font);
    }

    public int[] getSettings() {
        int[] settings = {
            deckAmount(),
            minimumBet(),
            dealerBehavior(),
            handValueVisible()
        };
        return settings;
    }

    private int dealerBehavior() {
        return (standRadioButton.isSelected()) ? STAND : HIT;
    }

    private int deckAmount() {
        return (int) deckAmountSpinner.getValue();
    }

    private int minimumBet() {
        return (int) minimumBetSpinner.getValue();
    }

    private int handValueVisible() {
        return (displayHandValue.isSelected()) ? 1 : 0;
    }

    private void initPanel(DefaultFont font) {
        logoLabel = new JLabel();
        deckAmountLabel = new JLabel("Number of decks");
        minimumBetLabel = new JLabel("Minimum bet");
        dealerBehaviorLabel = new JLabel("Dealer behavior for soft 17");
        deckAmountSpinner = new JSpinner();
        minimumBetSpinner = new JSpinner();
        standRadioButton = new JRadioButton("Stand");
        hitRadioButton = new JRadioButton("Hit");
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

        deckAmountSpinner.setModel(new SpinnerNumberModel(4, 1, 8, 1));
        minimumBetSpinner.setModel(new SpinnerNumberModel(25, 0, 1000, 5));

        deckAmountLabel.setFont(font.generateFont(16));
        deckAmountSpinner.setFont(font.generateFont(16));
        minimumBetLabel.setFont(font.generateFont(16));
        minimumBetSpinner.setFont(font.generateFont(16));
        dealerBehaviorLabel.setFont(font.generateFont(16));
        standRadioButton.setFont(font.generateFont(16));
        hitRadioButton.setFont(font.generateFont(16));
        displayHandValue.setFont(font.generateFont(16));
        playButton.setFont(font.generateFont(16));

        deckAmountLabel.setForeground(Palette.TEXT);
        deckAmountSpinner.setForeground(Palette.TEXT);
        minimumBetLabel.setForeground(Palette.TEXT);
        minimumBetSpinner.setForeground(Palette.TEXT);
        dealerBehaviorLabel.setForeground(Palette.TEXT);
        standRadioButton.setForeground(Palette.TEXT);
        hitRadioButton.setForeground(Palette.TEXT);
        displayHandValue.setForeground(Palette.TEXT);
        playButton.setForeground(Palette.TEXT);

        standRadioButton.setBackground(Palette.BLACK);
        hitRadioButton.setBackground(Palette.BLACK);
        displayHandValue.setBackground(Palette.BLACK);
        playButton.setBackground(Palette.BLACK);
        setBackground(Palette.BLACK);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(40, 40, 40, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(logoLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(deckAmountLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(deckAmountSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(minimumBetLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(minimumBetSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(dealerBehaviorLabel, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(hitRadioButton, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(standRadioButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 20, 10);
        add(displayHandValue, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(playButton, gbc);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.add(new SettingsPanel(new DefaultFont("Segoe UI")));
        frame.setVisible(true);
    }

    public void initPlayActionListener(ActionListener l) {
        playButton.addActionListener(l);
    }

    private JLabel logoLabel;
    private JLabel deckAmountLabel;
    private JLabel minimumBetLabel;
    private JLabel dealerBehaviorLabel;
    private JSpinner deckAmountSpinner;
    private JSpinner minimumBetSpinner;
    private JRadioButton standRadioButton;
    private JRadioButton hitRadioButton;
    private ButtonGroup dealerBehaviorGroup;
    private JCheckBox displayHandValue;
    private JButton playButton;
    private final static int HIT = 0;
    private final static int STAND = 1;
}

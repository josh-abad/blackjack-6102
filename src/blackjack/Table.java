package blackjack;

import design.Palette;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Table extends JPanel {
    
    private Image texture;

    public Table() {
        initTable();
    }

    private void initTable() {
        loadTexture();

        int w = (texture != null) ? texture.getWidth(this) : 1600;
        int h = (texture != null) ? texture.getHeight(this) : 900;
        setPreferredSize(new Dimension(w, h));        
    }

    private void loadTexture() {
        String path = "/images/table.png";
        try {
            ImageIcon icon = new ImageIcon(View.class.getResource(path));
            texture = icon.getImage();
        } catch (NullPointerException ex) {
            System.out.println("Could not find " + path);
            setBackground(Palette.TABLE);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(texture, 0, 0, this);
    }
}

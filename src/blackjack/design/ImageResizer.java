package blackjack.design;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageResizer {

    public static ImageIcon getScaledImage(ImageIcon icon, int scale) {
        Image image = icon.getImage();
        Image scaledImg = image.getScaledInstance(
            scale, -scale, Image.SCALE_SMOOTH
        );
        
        return new ImageIcon(scaledImg);
    }
    
    private ImageResizer() {
        // Do not instantiate
    }
}
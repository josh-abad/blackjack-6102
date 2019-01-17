package design;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * An ImageIcon resize tool.
 * @author Joshua Abad
 */
public class ImageResizer {

    /**
     * Returns a scaled instance of an ImageIcon.
     * @param icon the ImageIcon
     * @param scale the desired scale
     * @return a scaled instance of an ImageIcon
     */
    public static ImageIcon getScaledImage(ImageIcon icon, int scale) {
        Image image = icon.getImage();
        Image scaledImg = image.getScaledInstance(
            scale, -scale, Image.SCALE_SMOOTH
        );
        
        return new ImageIcon(scaledImg);
    }
    
    // Suppress default constructor for noninstantiability
    private ImageResizer() {
        throw new AssertionError();
    }
}
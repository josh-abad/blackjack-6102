/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author yeyoa
 */
public class ImageResizer {
    
    public static ImageIcon getScaledImage(ImageIcon icon, int scale) {
        Image image = icon.getImage();
        Image scaledImg = image.getScaledInstance(
            scale, -scale, Image.SCALE_SMOOTH
        );
        
        return new ImageIcon(scaledImg);
    }
}

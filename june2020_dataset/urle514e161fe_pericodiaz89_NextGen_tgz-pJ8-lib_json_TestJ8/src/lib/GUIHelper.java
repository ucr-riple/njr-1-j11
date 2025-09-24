package lib;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author RodrigoLaptop
 */
public class GUIHelper {

    public static void errorMessage(Component component, String error) {
        JOptionPane.showMessageDialog(component, error, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public static void setIconOnFrame(JFrame frame, String dir) {
        try {
            InputStream imgStream = frame.getClass().getResourceAsStream(dir);
            BufferedImage bi = ImageIO.read(imgStream);
            ImageIcon myImg = new ImageIcon(bi);
            frame.setIconImage(myImg.getImage());
        } catch (Exception e) {
            //do nothing
        }
    }

    public static void setGridLines(JTable table) {
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setGridColor(Color.lightGray);
    }
}

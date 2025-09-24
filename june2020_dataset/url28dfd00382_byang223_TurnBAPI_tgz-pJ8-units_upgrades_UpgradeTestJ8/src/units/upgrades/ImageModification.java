package units.upgrades;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import serialization.SBufferedImage;
import achiever.Achiever;
import attribute.AttributeExperience;
import attribute.AttributeImage;

import com.golden.gamedev.util.ImageUtil;

/**
 * Concrete example of UnitAttributeModification that changes
 * the unit's image
 * @author Alex Mariakakis
 *
 */
public class ImageModification extends UnitAttributeModification {

	private String image;
	
	public ImageModification(UnitUpgradable unit, int cost, String picture) {
	    super(unit, cost, "Image");
	    image = picture;
    }
	
	// Decorator method
	@Override
    public void modify() {
		SBufferedImage imgS = new SBufferedImage();
		try {
            BufferedImage img = ImageIO.read(new File(image));
            img = ImageUtil.resize(img, 50, 50);
            imgS.setImage(img);
            
            ((AttributeImage) getAttribute()).setNewImage(imgS);
            
		} catch (IOException e) {
		    e.printStackTrace();
        }
		
    }

    @Override
    public boolean checkCost() {
        Achiever owner = getOwner();
        if ((Integer) ((AttributeExperience) owner.getAttribute("Experience"))
                .getData() >= getUpgradeCost()) {
            return true;
        }
        return false;
    }

	@Override
    public void applyCost() {
        Achiever owner = getOwner();
        AttributeExperience exp = (AttributeExperience) owner.getAttribute("Experience");
    }

}

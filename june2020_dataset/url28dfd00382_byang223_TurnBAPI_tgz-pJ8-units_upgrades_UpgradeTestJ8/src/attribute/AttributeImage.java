package attribute;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import serialization.SBufferedImage;
import units.Unit;

import com.golden.gamedev.util.ImageUtil;

public class AttributeImage extends
        Attribute<SBufferedImage, SBufferedImage> {

    protected SBufferedImage myImage;
    protected Unit myUnit;

    public AttributeImage(Unit unit, String imageFilePath) {
        super(unit);

        myUnit = unit;
        myImage = new SBufferedImage();
        try {
            BufferedImage img = ImageIO.read(new File(imageFilePath));
            img = ImageUtil.resize(img, 50, 50);
            myImage.setImage(img);

        } catch (IOException e) {
        }
        myUnit.setImage(myImage.getImage());
    }

    // is this needed with ImageModification?
    public void setNewImage(SBufferedImage img) {
        myImage = img;
        myUnit.setImage(myImage.getImage());
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public String name() {
        return "Image";
    }

    @Override
    public void augmentDataTemplate(SBufferedImage dataElement) {
        super.setData(dataElement);
        myUnit.setImage(super.getData().getImage());
    }

}

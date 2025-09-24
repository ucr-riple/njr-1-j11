package units.interactions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import modes.models.GameModel;
import serialization.SBufferedImage;
import units.Unit;
import units.upgrades.ImageModification;
import units.upgrades.UpgradeNode;

public class ButtonEvolve extends InteractionUnitButton {

	@Override
    public SBufferedImage buttonImage() {
		SBufferedImage imgS = new SBufferedImage();
		try {
			BufferedImage img = ImageIO.read(new File("resources/buttons/evolvebutton.png"));
			imgS.setImage(img);
		} catch (IOException e) {
		}
		return imgS;
    }

	@Override
    public void performButton(GameModel gameModel) {
		Unit myUnit = gameModel.getSelectedUnit();
        UpgradeNode node = new UpgradeNode(new ImageModification(myUnit, 20, "resources/unit images/Raichu.png"));
        node.setAvailable(true);
        node.modify();
    }

	@Override
    public String toString() {
	    return "Evolve";
    }

	@Override
    public void refresh() {
    }

}

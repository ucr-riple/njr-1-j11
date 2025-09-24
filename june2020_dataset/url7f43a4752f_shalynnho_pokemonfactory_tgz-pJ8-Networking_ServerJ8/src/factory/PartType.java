package factory;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.Serializable;

import Utils.Constants;
import Utils.StringUtil;

public class PartType implements Serializable, FactoryData {
	private static final long serialVersionUID = 1;
	private String name = "";
	private final String id;
	private int partNum;
	private float badChance = 0;
	private String description = "";
	private String imagePath;
	private boolean invisible;

	// private Image image;

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	/**
	 * 
	 * @param s
	 *            - a number indicating part type
	 */
	public PartType(String s) {
		name = s;
		this.id = StringUtil.md5(name);
		invisible = false;
		// setImage();
	}

	public PartType(String s, int num, String desc) {
		name = s;
		partNum = num;
		description = desc;
		this.id = StringUtil.md5(name);
		imagePath = name;
		invisible = false;
	}

	public PartType(String s, int num, String desc, float chance) {
		name = s;
		partNum = num;
		description = desc;
		badChance = chance;
		imagePath = name;
		this.id = StringUtil.md5(name);
		invisible = false;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setPartNum(int partNum) {
		this.partNum = partNum;
	}

	public int getPartNum() {
		return partNum;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public float getBadChance() {
		return badChance;
	}

	public void setBadChance(float badChance) {
		this.badChance = badChance;
	}

	@Override
	public String getID() {
		return id;
	}

	public Image getImage() {
		return Toolkit.getDefaultToolkit().getImage(Constants.PART_IMAGE_PATH + imagePath + ".png");
	}

	// Added this for testing --Neetu
	public Image getPokeballImage() {
		return Toolkit.getDefaultToolkit().getImage(Constants.BALL_IMAGE + imagePath + ".png");
	}

	public Image getBinImage() {
		return Toolkit.getDefaultToolkit().getImage(Constants.BIN_IMAGE_PATH + imagePath + ".png");
	}

	public Image getBadImage() {
		return Toolkit.getDefaultToolkit().getImage(Constants.BAD_PART_IMAGE_PATH + imagePath + ".png");
	}

	public Image getBadPokeballImage() {
		return Toolkit.getDefaultToolkit().getImage(Constants.BALL_IMAGE + "bad" + ".png");
	}

	public boolean equals(PartType pt) {
		if (pt != null) {
			return this.id.equals(pt.getID());
		} else {
			return false;
		}
	}

	public void setImagePath(String newImagePath) {
		imagePath = newImagePath;
	}

	public String getImagePath() {
		return imagePath;
	}
}

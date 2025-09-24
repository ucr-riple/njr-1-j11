package in.mustafaak.izuna.meta;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * @author Mustafa
 */
@Root(name = "Path")
public class WavePath {
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public void setMidX(int midX) {
		this.midX = midX;
	}

	public void setMidY(int midY) {
		this.midY = midY;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Element
	private int duration;

	@Element(required = false)
	private int endX;
	@Element(required = false)
	private int endY;
	@Element(required = false)
	private int midX;
	@Element(required = false)
	private int midY;
	@Element(required = false)
	private int startX;
	@Element(required = false)
	private int startY;
	@Attribute
	private String type;

	/**
	 * 
	 * @return
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * 
	 * @return
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * 
	 * @return
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * 
	 * @return
	 */
	public int getMidX() {
		return midX;
	}

	/**
	 * 
	 * @return
	 */
	public int getMidY() {
		return midY;
	}

	/**
	 * 
	 * @return
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * 
	 * @return
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}
}
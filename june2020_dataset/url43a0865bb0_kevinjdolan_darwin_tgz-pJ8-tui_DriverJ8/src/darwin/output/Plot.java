package darwin.output;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;

public class Plot {

	private List<Point2D.Double> points;
	private Color pointColor;
	private Color connectColor;
	private boolean connect;
	private boolean circle;
	private boolean fill;
	private int size;
	
	public Plot(List<Point2D.Double> xy) {
		this.points = xy;
		pointColor = new Color(255, 0, 0);
		connectColor = new Color(255,0, 0);
		connect = false;
		circle = true;
		fill = true;
		size = 4;
	}

	/**
	 * @return the xy
	 */
	public List<Point2D.Double> getPoints() {
		return points;
	}

	/**
	 * @param xy the xy to set
	 */
	public void setPoints(List<Point2D.Double> xy) {
		this.points = xy;
	}

	/**
	 * @return the pointColor
	 */
	public Color getPointColor() {
		return pointColor;
	}

	/**
	 * @return the connectColor
	 */
	public Color getConnectColor() {
		return connectColor;
	}
	
	/**
	 * @param pointColor default red
	 */
	public void setPointColor(Color pointColor) {
		this.pointColor = pointColor;
	}

	/**
	 * @param connectColor default red
	 */
	public void setConnectColor(Color connectColor) {
		this.connectColor = connectColor;
	}
	
	/**
	 * @return the connect
	 */
	public boolean isConnect() {
		return connect;
	}

	/**
	 * @param connect default false
	 */
	public void setConnect(boolean connect) {
		this.connect = connect;
	}

	/**
	 * @return the circle
	 */
	public boolean isCircle() {
		return circle;
	}

	/**
	 * @param circle default true
	 */
	public void setCircle(boolean circle) {
		this.circle = circle;
	}

	/**
	 * @return the fill
	 */
	public boolean isFill() {
		return fill;
	}

	/**
	 * @param fill default true
	 */
	public void setFill(boolean fill) {
		this.fill = fill;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size default 4
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}

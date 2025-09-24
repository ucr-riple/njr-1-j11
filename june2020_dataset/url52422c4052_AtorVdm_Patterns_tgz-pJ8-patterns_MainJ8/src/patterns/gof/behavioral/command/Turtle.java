package patterns.gof.behavioral.command;

import java.awt.Point;

public class Turtle {
	private Point position = new Point(0, 0);
	
	public void moveLeft() {
		CommandClient.addOutput("turtle is moving left");
		position.x--;
	}
	
	public void moveRight() {
		CommandClient.addOutput("turtle is moving right");
		position.x++;
	}
	
	public void moveForward() {
		CommandClient.addOutput("turtle is moving forward");
		position.y++;
	}
	
	public void moveBackward() {
		CommandClient.addOutput("turtle is moving backward");
		position.y--;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
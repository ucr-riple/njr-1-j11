package patterns.gof.structural.bridge;

public class Rectangle extends Shape {
	public Rectangle(Color color) { 
		super(color);  
	}  

	@Override
	public void colorIt() {
		BridgeClient.addOutput("rectangle filled with " + getColor().fillColor() + " color");
	}
}

package patterns.gof.structural.bridge;

public class Circle extends Shape {
	public Circle(Color color) { 
		super(color);  
	}  

	@Override
	public void colorIt() {
		BridgeClient.addOutput("circle filled with " + getColor().fillColor() + " color");
	}
}

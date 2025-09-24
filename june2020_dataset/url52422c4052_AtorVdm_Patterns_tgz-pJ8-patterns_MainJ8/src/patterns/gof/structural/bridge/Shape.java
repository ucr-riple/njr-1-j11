package patterns.gof.structural.bridge;

public abstract class Shape {
	private Color color;  
	
    Shape(Color color) {  
        this.color = color;  
    }
    
    abstract public void colorIt();
    
    public Color getColor() {
    	return color;
    }
}
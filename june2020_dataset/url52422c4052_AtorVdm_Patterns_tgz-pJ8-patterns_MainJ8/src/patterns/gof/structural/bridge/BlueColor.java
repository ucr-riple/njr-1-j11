package patterns.gof.structural.bridge;

public class BlueColor implements Color {
	@Override
	public String fillColor() {
		return "blue";
	}
}
package patterns.gof.structural.bridge;

public class RedColor implements Color {
	@Override
	public String fillColor() {
		return "red";
	}
}
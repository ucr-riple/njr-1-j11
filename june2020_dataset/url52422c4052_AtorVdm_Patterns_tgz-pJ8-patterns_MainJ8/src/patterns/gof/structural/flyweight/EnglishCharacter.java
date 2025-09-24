package patterns.gof.structural.flyweight;

public abstract class EnglishCharacter {
	private char symbol;
	private int width;
	private int height;
 
	public void printCharacter() {
		FlyweightClient.addOutput(symbol + " " + width + " " + height);
	}

	public void setSymbol(char simbol) {
		this.symbol = simbol;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
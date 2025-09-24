package basic.structure.interfacee;

public class Parrot implements Whistle {
	
	private String place;
 
	public Parrot(String place) {
		this.place = place;
	}
 
	public void whistle() {
		System.out.printf("Parrot: %s!%n", place);
	}
	

}

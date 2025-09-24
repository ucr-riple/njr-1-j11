package basic.structure.interfacee;

public class Human implements Whistle{

    private String name;
    
    public Human(String name) {
        this.name = name;
    }

	@Override
	public void whistle() {
		System.out.printf("Human: %s!%n", name);
		
	}
 
    /*public void whistle() {
        System.out.printf("Human: %s!%n", name);
    }*/
    

}

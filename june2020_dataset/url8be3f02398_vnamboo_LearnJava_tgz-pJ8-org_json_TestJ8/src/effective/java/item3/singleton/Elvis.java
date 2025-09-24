package effective.java.item3.singleton;

public enum Elvis {
	INSTANCE;
	
	public void leaveTheBuilding(){
		System.out.println("I am leaving the building");
	}
	
	public static void main(String[] args) {
		Elvis.INSTANCE.leaveTheBuilding();
	}
}

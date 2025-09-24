package basic.structure.interfacee;

public class Main {

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
            int n = (int) (Math.random() * 10) % 2;
            switch (n) {
                case 0:
                	doWhistle(new Human("Shoooooo"));
                    break;
                case 1:
                	doWhistle(new Parrot("Shoooooo"));
                	break;
            }
        }

	}
	
	public static void doWhistle(Whistle request) {
        request.whistle();
    }
}

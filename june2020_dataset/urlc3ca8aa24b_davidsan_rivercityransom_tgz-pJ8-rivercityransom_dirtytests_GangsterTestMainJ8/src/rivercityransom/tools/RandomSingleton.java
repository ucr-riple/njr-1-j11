package rivercityransom.tools;

import java.util.Random;

public class RandomSingleton {
	private static RandomSingleton instance;
	private Random rnd;

	private RandomSingleton() {
		rnd = new Random(42);
	}

	public static RandomSingleton getInstance() {
		if (instance == null) {
			instance = new RandomSingleton();
		}
		return instance;
	}

	public double nextDouble() {
		return rnd.nextDouble();
	}

	public int nextInt(int n) {
		return rnd.nextInt(n);
	}
}
package nz.net.initial3d.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nz.net.initial3d.util.IntPool;
import nz.net.initial3d.util.SynchronizedIntPool;

public class IntPoolTest {

	public static void main(String[] args) {

		IntPool pool = new SynchronizedIntPool(0, 1024);

		List<Integer> ids = new ArrayList<Integer>();

		for (int i = 0; i < 100; i++) {
			ids.add(pool.alloc());
		}

		System.out.println("After alloc:");
		System.out.println(pool);

		Random ran = new Random();
		while (!ids.isEmpty()) {
			int id = ids.remove(ran.nextInt(ids.size()));
			pool.free(id);
			if (ids.size() == 50) {
				break;
			}
		}

		System.out.println("After partial free:");
		System.out.println(pool);

		for (int i = 0; i < 10; i++) {
			System.out.println("Next id: " + pool.alloc());
		}
		
		System.out.println("At end:");
		System.out.println(pool);
	}

}

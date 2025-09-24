package nz.net.initial3d.renderer;

import static nz.net.initial3d.renderer.Util.*;
import static nz.net.initial3d.renderer.Type.*;

import java.util.Random;

import sun.misc.Unsafe;

public class Test {

	public static void main(String[] args) {

		final long TEST_LENGTH = 1000000000L;

		final Random ran = new Random();

		final int c = ran.nextInt();

		long nano_start0 = System.nanoTime();

		int test0 = ran.nextInt();
		for (long i = 0; i < TEST_LENGTH; i++) {
			test0 ^= colorMul(test0, c);
		}

		System.out.println(test0);

		long nanos0 = System.nanoTime() - nano_start0;
		System.out.println(nanos0);

		// ----------

		long nano_start1 = System.nanoTime();

		int test1 = ran.nextInt();
		for (long i = 0; i < TEST_LENGTH; i++) {
			test1 ^= fastColorAdd(test1, c);
		}

		System.out.println(test1);

		long nanos1 = System.nanoTime() - nano_start1;
		System.out.println(nanos1);

	}

}

package nz.net.initial3d.test;

import java.util.Random;

public class Test2 {

	public static void main(String[] args) {

		final long TEST_LENGTH = 10000000000L;

		final Random ran = new Random();

		long nano_start0 = System.nanoTime();

		int test0 = 0;
		float f0 = 0;
		for (long i = 0; i < TEST_LENGTH; i++) {
			test0 += (int) (f0++);
		}

		System.out.println(test0);

		long nanos0 = System.nanoTime() - nano_start0;
		System.out.println(nanos0);

		// --------

		long nano_start1 = System.nanoTime();

		int test1 = 0;
		int i1 = 0;
		for (long i = 0; i < TEST_LENGTH; i++) {
			test1 += (i1++);
		}

		System.out.println(test1);

		long nanos1 = System.nanoTime() - nano_start1;
		System.out.println(nanos1);

		// --------

		long nano_start2 = System.nanoTime();

		float test2 = 0;
		float f2 = 0;
		for (long i = 0; i < TEST_LENGTH; i++) {
			test2 += (f2++);
		}

		System.out.println(test2);

		long nanos2 = System.nanoTime() - nano_start2;
		System.out.println(nanos2);

		System.out.println(nanos1 / (double) nanos0);
	}

}

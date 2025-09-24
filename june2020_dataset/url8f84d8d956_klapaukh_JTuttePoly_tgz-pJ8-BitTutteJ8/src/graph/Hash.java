package graph;

import util.Triple;

public class Hash {

	public static int rot(int x, int k) {
		return (x << k) | (x >> (32 - k));
	}

	public static Triple<Integer, Integer, Integer> mix(Triple<Integer, Integer, Integer> vals) {
		vals.first -= vals.third;
		vals.first ^= rot(vals.third, 4);
		vals.third += vals.second;

		vals.second -= vals.first;
		vals.second ^= rot(vals.first, 6);
		vals.first += vals.third;

		vals.third -= vals.second;
		vals.third ^= rot(vals.second, 8);
		vals.second += vals.first;

		vals.first -= vals.third;
		vals.first ^= rot(vals.third, 16);
		vals.third += vals.second;

		vals.second -= vals.first;
		vals.second ^= rot(vals.first, 19);
		vals.first += vals.third;

		vals.third -= vals.second;
		vals.third ^= rot(vals.second, 4);
		vals.second += vals.first;

		return vals;
	}

	public static Triple<Integer, Integer, Integer> finalise(Triple<Integer, Integer, Integer> vals) {
		vals.third ^= vals.second;
		vals.third -= rot(vals.second, 14);

		vals.first ^= vals.third;
		vals.first -= rot(vals.third, 11);

		vals.second ^= vals.first;
		vals.second -= rot(vals.first, 25);

		vals.third ^= vals.second;
		vals.third -= rot(vals.second, 16);

		vals.first ^= vals.third;
		vals.first -= rot(vals.third, 4);

		vals.second ^= vals.first;
		vals.second -= rot(vals.first, 14);

		vals.third ^= vals.second;
		vals.third -= rot(vals.second, 24);

		return vals;
	}

	public static int hashcode(int[] vals) {
		Triple<Integer, Integer, Integer> t = new Triple<Integer, Integer, Integer>(0, 0, 0);
		for (int i = 0; i < vals.length; i += 3) {
			if (i + 3 >= vals.length) {
				t.first += vals[i];
				if (i + 1 < vals.length) {
					t.second += vals[i + 1];
				}
				if (i + 2 < vals.length) {
					t.third += vals[i + 2];
				}
				finalise(t);
			} else {
				t.first += vals[i];
				t.second += vals[i + 1];
				t.third += vals[i + 2];
				mix(t);
			}
		}

		return t.third;
	}
}

package utility.security;

import java.math.BigInteger;
import java.util.ArrayList;

public class BigIntegerUtility {
	public final static double U = 1d, L = 0d;
	public static int U_HASHVALUE = hash(U + ""), L_HASHVALUE = hash(L + "");
	public final static BigInteger ZERO = BigInteger.ZERO;
	public final static BigInteger ONE = BigInteger.ONE;
	public final static BigInteger TWO = new BigInteger("2");
	public final static BigInteger PRIME_P = TWO.pow(107).subtract(
			BigInteger.ONE);
	public final static BigInteger PRIME_Q = TWO.pow(127).subtract(
			BigInteger.ONE);
	public final static BigInteger N = PRIME_P.multiply(PRIME_Q);
	public final static BigInteger R = new BigInteger("" + hash("0"));
	public final static BigInteger T = PRIME_P.subtract(ONE).multiply(
			PRIME_Q.subtract(ONE));

	private static BigInteger[] R2XPOWER_REAMINED_CACHE;
	private final static boolean USE_CACHE = false;

	static {
		if (USE_CACHE) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					BigIntegerUtility.cacheR2XPowerRemainN(1000000);//1000000
				}
			}).start();
		}
	}

	public static void main(String[] args) {
		int times = 1000;
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			compute2XPowerRemainWithMgmry(R, 999999, N);
		}
		long end = System.currentTimeMillis();
		double totalCpuTime = end - start;
		System.out.println(times + " times G() in method Mgmry costs time:"
				+ totalCpuTime + "ms");
	}

	/**
	 * 
	 * @param x
	 *            x must >= 0!
	 * @param y
	 * @param N
	 * @return (x^(2^y)) mod n
	 */
	public static BigInteger compute2XPowerRemain(BigInteger x, int y,
			BigInteger n) {
		return compute2XPowerRemainWithMgmry(x, y, n);
	}

	/**
	 * 
	 * @param x
	 *            x must >= 0!
	 * @param y
	 * @param N
	 * @return (x^(2^y)) mod default N
	 */
	public static BigInteger compute2XPowerRemainWithDefaultN(BigInteger x,
			int y) {
		return compute2XPowerRemainWithMgmry(x, y, N);
	}

	private static BigInteger compute2XPowerRemainWithMgmry(BigInteger x,
			int y, BigInteger n) {
		final BigInteger temp = computePowerRemainWithMgmry(TWO,
				new BigInteger("" + y), T);
		return computePowerRemainWithMgmry(x, temp, n);
	}

	/**
	 * 
	 * @param x
	 *            x must >= 0!
	 * @param y
	 * @param N
	 * @return (x^(2^y)) mod n
	 */
	private static BigInteger compute2XPowerRemainWithOutIterate(
			final BigInteger x, int y, final BigInteger n) {
		final BigInteger temp = computePowerRemainWithOutIterate(TWO,
				new BigInteger("" + y), T);
		return computePowerRemainWithOutIterate(x, temp, n);
	}

	/**
	 * 
	 * @param x
	 *            x must >= 0!
	 * @param y
	 * @param N
	 * @return (x^(2^y)) mod n
	 */
	private static BigInteger compute2XPowerRemainWithCache(BigInteger x,
			int y, BigInteger n) {
		if (y < 0) {
			System.out.println("compute2XPowerRemain:y=" + y + "<0!");
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(-1);
		}
		if (USE_CACHE) {
			if (R2XPOWER_REAMINED_CACHE == null) {
				//wait cache building finished
				System.out
						.println("compute2XPowerRemain:waiting until cache is built");
				while (R2XPOWER_REAMINED_CACHE == null) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		}
		if (R2XPOWER_REAMINED_CACHE != null) {
			//read from cache
			if (n.compareTo(N) == 0) {
				if (x.compareTo(R) == 0) {
					if (y == 0) {
						return BigInteger.ONE;
					} else if (y <= R2XPOWER_REAMINED_CACHE.length) {
						//read from cache
						return R2XPOWER_REAMINED_CACHE[y - 1];
					} else {
						//start from cache
						return compute2XPowerRemainWithCache(
								R2XPOWER_REAMINED_CACHE[R2XPOWER_REAMINED_CACHE.length - 1],
								y - R2XPOWER_REAMINED_CACHE.length, n);
					}
				} else {
					//find x from cache first
					boolean found = false;
					int startY = -1;
					for (int i = 0; i < R2XPOWER_REAMINED_CACHE.length; i++) {
						if (x.compareTo(R2XPOWER_REAMINED_CACHE[i]) == 0) {
							found = true;
							startY = i;
							break;
						}
					}
					if (found) {
						int realY = startY + 1 + y;
						if (realY <= R2XPOWER_REAMINED_CACHE.length) {
							//read from cache
							return R2XPOWER_REAMINED_CACHE[realY - 1];
						} else {
							//start from cache
							return compute2XPowerRemainWithCache(
									R2XPOWER_REAMINED_CACHE[R2XPOWER_REAMINED_CACHE.length - 1],
									realY - R2XPOWER_REAMINED_CACHE.length, n);
						}
					}
				}
			}
		}

		if (x.compareTo(n) == 0) {
			return ZERO;
		} else if (x.compareTo(BigInteger.ONE) == 0) {
			//x==1
			if (n.compareTo(BigInteger.ONE) > 0) {
				return BigInteger.ONE;
			} else {
				return ZERO;
			}
		} else if (x.compareTo(n) > 0) {
			return compute2XPowerRemainWithCache(x.mod(n), y, n);
		} else {
			// 0<x<n
			int powCount = 1;
			BigInteger xPow = x.multiply(x);
			while (y > powCount) {
				xPow = xPow.multiply(xPow);
				powCount++;
				//System.out.println("multiply count:" + powCount);
				if (xPow.compareTo(n) >= 0) {
					xPow = xPow.mod(n);
				}
			}
			return xPow;
		}
	}

	/**
	 * compute r^x MOD n with Montgomery method
	 * 
	 * @param n
	 * @param p
	 * @param m
	 * @return
	 */
	private static BigInteger computePowerRemainWithMgmry(BigInteger r,
			BigInteger x, BigInteger n) {
		if (x.compareTo(ZERO) == 0) {
			return ONE;
		}
		BigInteger k = ONE;
		r = r.mod(n);
		while (x.compareTo(ONE) != 0) {
			if (x.and(ONE).compareTo(ZERO) != 0) {
				k = k.multiply(r).mod(n);
			}
			r = r.multiply(r).mod(n);
			x = x.divide(TWO);
		}
		return r.multiply(k).mod(n);
	}

	/**
	 * compute r^x MOD n without iterate, this is more efficiency than
	 * computePowerRemain(BigInteger x, BigInteger y, BigInteger n) when r is
	 * VERY SMALL!
	 * 
	 * @param x
	 * @param n
	 * @return r^x MOD n
	 */
	private static BigInteger computePowerRemainWithOutIterate(BigInteger r,
			BigInteger x, BigInteger n) {
		boolean debug = false;
		if (debug) {
			System.out.println("x:" + x + "\nn:" + n);
		}
		//cache 2^a and r^(2^a)
		ArrayList<BigInteger> powerOfTwo = new ArrayList<BigInteger>();
		ArrayList<BigInteger> powerOfTwoOfR = new ArrayList<BigInteger>();
		powerOfTwo.add(ONE);
		powerOfTwoOfR.add(r);
		BigInteger powerResult = TWO;
		powerOfTwo.add(ZERO.add(powerResult));
		BigInteger tmpPowerOfROfR = r.multiply(r);
		powerOfTwoOfR.add(tmpPowerOfROfR);
		while (powerResult.compareTo(x) < 0) {
			powerResult = powerResult.multiply(TWO);
			tmpPowerOfROfR = tmpPowerOfROfR.multiply(tmpPowerOfROfR);
			if (tmpPowerOfROfR.compareTo(n) >= 0) {
				tmpPowerOfROfR = tmpPowerOfROfR.mod(n);
			}
			powerOfTwo.add(ZERO.add(powerResult));
			powerOfTwoOfR.add(tmpPowerOfROfR);
			if (debug) {
				System.out.println("cached 2^" + (powerOfTwo.size() - 1) + "="
						+ powerResult);
			}
		}
		//decompose x
		boolean[] isContainedTwoPower = new boolean[powerOfTwo.size()];
		BigInteger tmpX = ZERO.add(x);
		if (debug) {
			System.out.print("x=");
		}
		for (int i = isContainedTwoPower.length - 1; i >= 0; i--) {
			if (tmpX.compareTo(powerOfTwo.get(i)) >= 0) {
				isContainedTwoPower[i] = true;
				tmpX = tmpX.subtract(powerOfTwo.get(i));
				if (debug) {
					System.out.print("+2^" + i);
				}
			} else {
				isContainedTwoPower[i] = false;
			}
		}
		if (debug) {
			System.out.println();
		}
		//compute 2^x mod n
		BigInteger result = ONE;
		for (int i = 0; i < isContainedTwoPower.length; i++) {
			if (isContainedTwoPower[i]) {
				result = result.multiply(powerOfTwoOfR.get(i));
				if (result.compareTo(n) >= 0) {
					result = result.mod(n);
				}
			}
		}
		return result;
	}

	/**
	 * compute (x^y) MOD n with decompose method
	 * 
	 * @param x
	 *            x must >= 0!
	 * @param y
	 * @param N
	 * @return (x^y) mod n
	 */
	private static BigInteger computePowerRemain(BigInteger x, BigInteger y,
			BigInteger n) {
		//System.out.println("computePowerRemain(" + x + "," + y + "," + n + ")");
		if (x.compareTo(n) == 0) {
			return ZERO;
		} else if (x.compareTo(BigInteger.ONE) == 0) {
			//x==1
			if (n.compareTo(BigInteger.ONE) > 0) {
				return BigInteger.ONE;
			} else {
				return ZERO;
			}
		} else if (x.compareTo(n) > 0) {
			return computePowerRemain(x.mod(n), y, n);
		} else {
			// 0<x<n
			BigInteger powCount = BigInteger.ONE;
			BigInteger xPow = BigInteger.ONE.multiply(x);
			while (y.compareTo(powCount) > 0) {
				xPow = xPow.multiply(x);
				powCount = powCount.add(BigInteger.ONE);
				//System.out.println("multiply count:" + powCount);
				if (xPow.compareTo(n) >= 0) {
					BigInteger remain = xPow.mod(n);
					BigInteger newY = y.divide(powCount);
					xPow = computePowerRemain(remain, newY, n);
					powCount = y.subtract(y.mod(powCount));
				}
			}
			return xPow;
		}
	}

	private static void cacheR2XPowerRemainN(int end) {
		boolean debug = true;
		BigInteger[] caches = new BigInteger[end];
		// 0<x<n
		int powCount = 1;
		BigInteger xPow = R.multiply(R);
		caches[0] = xPow.mod(N);
		while (end > powCount) {
			xPow = xPow.multiply(xPow);
			powCount++;
			if (xPow.compareTo(N) >= 0) {
				xPow = xPow.mod(N);
			}
			caches[powCount - 1] = xPow;
			if (debug) {
				if (powCount % 100000 == 0) {
					System.out.println("BigIntegerUtility: cached powCount="
							+ powCount);
				}
			}
		}
		R2XPOWER_REAMINED_CACHE = caches;
	}

	private static int hash(String tmp) {
		return tmp.hashCode();
	}
}

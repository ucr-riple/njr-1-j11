package normchan.crapssim.engine.util;

public class BetNormalizer {
	private static final int [] PLACE_DIVISORS = {5, 5, 6, 6, 5, 5};
	private static final int [] ODDS_DIVISORS = {1, 2, 5, 5, 2, 1};
	private static final int [] LAY_DIVISORS = {2, 3, 6, 6, 3, 2};
	
	public static int normalizePlaceBet(int number, int amount) {
		return normalizeBet(PLACE_DIVISORS[toIndex(number)], amount);
	}
	
	public static int normalizeOddsBet(int number, int amount) {
		return normalizeBet(ODDS_DIVISORS[toIndex(number)], amount);
	}
	
	public static int normalizeBuyBet(int number, int amount) {
		return normalizeBet(ODDS_DIVISORS[toIndex(number)], amount);
	}
	
	public static int normalizeLayBet(int number, int amount) {
		return normalizeBet(LAY_DIVISORS[toIndex(number)], amount);
	}
	
	private static int normalizeBet(int divisor, int amount) {
		if (amount % divisor != 0) 
			amount += divisor - (amount % divisor);
		return amount;
	}
	
	private static int toIndex(int number) {
		if (number < 4 || number == 7 || number > 10) throw new IndexOutOfBoundsException();
		else if (number < 7) return number - 4;
		else return number - 5;
	}
}

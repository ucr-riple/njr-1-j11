package startrekj.hpbasic;

public final class Arithmetic {
	private Arithmetic() {
	}
	
	public static Number subtract(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() - otherNumber.intValue();
		return number.doubleValue() - otherNumber.doubleValue();
	}
	public static Number add(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() + otherNumber.intValue();
		return number.doubleValue() + otherNumber.doubleValue();
	}
	public static Number mult(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() * otherNumber.intValue();
		return number.doubleValue() * otherNumber.doubleValue();
	}
	public static Number div(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() / otherNumber.intValue();
		return number.doubleValue() / otherNumber.doubleValue();
	}
	public static boolean greaterThan(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() > otherNumber.intValue();
		return number.doubleValue() > otherNumber.doubleValue();
	}
	public static boolean greaterThanOrEqual(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() >= otherNumber.intValue();
		return number.doubleValue() >= otherNumber.doubleValue();
	}
	public static boolean lessThan(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() < otherNumber.intValue();
		return number.doubleValue() < otherNumber.doubleValue();
	}
	public static boolean lessThanOrEqual(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() <= otherNumber.intValue();
		return number.doubleValue() <= otherNumber.doubleValue();
	}
	public static boolean equal(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() == otherNumber.intValue();
		return number.doubleValue() == otherNumber.doubleValue();
	}
	public static boolean notEqual(Number number, Number otherNumber) {
		if(bothIntegers(number, otherNumber))
			return number.intValue() != otherNumber.intValue();
		return number.doubleValue() != otherNumber.doubleValue();
	}
	public static boolean bothIntegers(Number number, Number otherNumber) {
		return number instanceof Integer && otherNumber instanceof Integer;
	}
	public static Number abs(Number number) {
		if(number instanceof Integer) {
			Integer integer = (Integer) number;
			return Math.abs(integer);
		}
		Double doubleValue = (Double) number;
		return Math.abs(doubleValue);
	}
}

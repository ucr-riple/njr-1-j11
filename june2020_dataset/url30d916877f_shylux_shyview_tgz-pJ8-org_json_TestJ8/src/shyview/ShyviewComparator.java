package shyview;

import java.util.Comparator;

public class ShyviewComparator {
	class PictureComparator implements Comparator<IPicture> {
		@Override
		public int compare(IPicture arg0, IPicture arg1) {
			return compareString(arg0.getName(), arg1.getName());
		}
	}
	class ListComparator implements Comparator<IPicList> {
		@Override
		public int compare(IPicList o1, IPicList o2) {
			return compareString(o1.getName(), o2.getName());
		}
	}
	
	public int compareString(String unit1, String unit2) {
		// cut equal part. this includes potential numbers.
		int firstdif = this.firstDifference(unit1, unit2);
		unit1 = unit1.substring(firstdif, unit1.length());
		unit2 = unit2.substring(firstdif, unit2.length());
		
		// try to compare as numbers
		int compare = 0;
		try {
			Integer intpic1 = getFirstNumber(unit1);
			Integer intpic2 = getFirstNumber(unit2);
			compare = intpic1.compareTo(intpic2);
		} catch (NumberFormatException e) { // fall back to string comparing
			compare = unit1.compareToIgnoreCase(unit2);
		}
		return compare;
	}
	
	/**
	 * Returns the first number in the string.
	 * @param str String with a number in it
	 * @return first number in the given string.
	 * @throws NumberFormatException if no number found
	 * 
	 * Regex would be a cleaner option. TODO
	 */
	private int getFirstNumber(String str) throws NumberFormatException {
		StringBuilder buffer = new StringBuilder(str);
		// remove all non-digits in front of the string
		while (buffer.length() > 0) {
			if (Character.isDigit(buffer.charAt(0))) break; // break at first digit
			buffer.deleteCharAt(0);
		}
		// remove all non-digits from the back of the string
		int i = 0;
		for (; i < buffer.length(); i++) if (!Character.isDigit(buffer.charAt(i))) break;
		buffer.delete(i, buffer.length());
		
		// parse integer
		Integer integer = Integer.parseInt(buffer.toString());
		if (integer == null) throw new NumberFormatException();
		return integer.intValue();
	}
	private int firstDifference(String a, String b) {
		int minlength = (a.length() < b.length()) ? a.length() : b.length();
		int i = 0;
		for (i = 0; i < minlength; i++) {
			if (a.charAt(i) != b.charAt(i)) break;
		}
		return i;
	}
}

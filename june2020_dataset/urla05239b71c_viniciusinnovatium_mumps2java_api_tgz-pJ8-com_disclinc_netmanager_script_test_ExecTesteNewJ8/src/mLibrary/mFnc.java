package mLibrary;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.regex.Pattern;

import br.com.innovatium.mumps2java.todo.TODO;

public final class mFnc extends mParent {

	/**
	 * Converts a character to a numeric code.
	 * 
	 * @param expression
	 * <br>
	 *            expression The character to be converted. <br>
	 *            position Optional — The position of a character within a
	 *            character string, counting from 1. The default is 1.
	 * @return returns the character code value for a single character specified
	 *         in expression.
	 */
	public static Object $ascii(Object expression) {
		return $ascii(expression, 1);
	}

	/**
	 * Converts a character to a numeric code.
	 * 
	 * @param expression
	 * @param position
	 * <br/>
	 *            expression -> The character to be converted. <br/>
	 *            position -> Optional — The position of a character within a
	 *            character string, counting from 1. The default is 1.
	 * @return numeric code
	 */
	public static Object $ascii(Object expression, Object position) {
		Double convertedPosition = mFncUtil.numberConverter(position);
		return Character.codePointAt(String.valueOf(expression).toCharArray(),
				convertedPosition.intValue() - 1);
	}

	/**
	 * Converts the integer value of an expression to the corresponding ASCII or
	 * Unicode character.
	 * 
	 * @param i
	 * @return
	 */
	public static Object $c(Object... intArgs) {
		return $char(intArgs);
	}

	/**
	 * Compares expressions and returns the value of the first matching case.
	 * 
	 * @param args
	 *            </br> target - A literal or expression the value of which is
	 *            to be matched against cases. </br> case - A literal or
	 *            expression the value of which is to be matched with the
	 *            results of the evaluation of target. </br> value - The value
	 *            to be returned upon a successful match of the corresponding
	 *            case. </br> default - Optional — The value to be returned if
	 *            no case matches target.
	 * @return Object
	 */
	public static Object $case(Object... args) {
		Boolean found = false;
		Object returnObj = null;
		if (args != null) {
			if (args.length < 3) {
				throw new IllegalArgumentException(
						"This method requires at least one pair of condition and value that returns as true.");
			}
			Object target = args[0];
			for (int i = 1; i < args.length; i++) {
				if (i % 2 != 0) {
					if (mOp.Equal(target, args[i])) {
						found = true;
						returnObj = args[i + 1];
						break;
					}
				}
			}
			if (!found) {
				if (args.length % 2 != 0) {
					throw new IllegalArgumentException(
							"This method requires at least one pair of condition and value.");
				} else {
					returnObj = args[args.length - 1];
				}
			}
		}
		return returnObj;
	}

	/**
	 * Converts the integer value of an expression to the corresponding ASCII or
	 * Unicode character.
	 * 
	 * @param codes
	 * <br>
	 *            expression The integer value to be converted.
	 * @return returns the character that corresponds to the decimal (base-10)
	 *         integer value specified by expression.
	 */
	public static Object $char(Object... codes) {
		if (codes == null || codes.length == 0) {
			return null;
		}
		return mFncUtil.characterImpl(mFncUtil.castIntArray(codes));
	}

	public static ListObject $concat(ListObject... lists) {
		return ListObject.concat(lists);
	}

	public static boolean $d(mVar var) {
		return mFncUtil.booleanConverter($data(var));
	}

	public static int $data(mVar mVar) {
		return mVar.data();
	}

	public static String $extract(Object string) {
		return $extract(string, 1);
	}

	public static String $extract(Object value, Object index) {
		return $extract(value, index, index);
	}

	public static String $extract(Object string, Object from, Object to) {
		if (string == null) {
			return null;
		}

		int start = -1;
		int end = -1;
		start = mFncUtil.integerConverter(from);
		if (from.equals(to)) {
			end = start;
		} else {
			end = mFncUtil.integerConverter(to);
		}

		if (end <= 0) {
			return "";
		} else if (start <= 0 && end > 0) {
			start = 1;
		} else if (start > end) {
			return "";
		}
		String value = string.toString();
		final int length = value.length();
		try {
			if (end > length) {
				end = length;
			}
			if (start > length) {
				return "";
			}

			return value.substring(start - 1, end);
		} catch (Exception e) {
			return null;
		}

	}

	public static int $find(Object string, Object substring) {
		return $find(string, substring, 1);
	}

	public static int $find(Object string, Object substring, Object start) {
		return mFncUtil.findImpl(mFncUtil.castString(string),
				mFncUtil.castString(substring), mFncUtil.castInt(start));
	}

	public static Object $fnumber(Object inumber, String format) {
		return $fnumber(inumber, format, null);
	}

	/**
	 * Formats a numeric value with a specified format; optionally rounds to a
	 * specified precision.
	 * 
	 * @param inumber
	 * @param format
	 * @param decimal
	 * @return returns the number specified by inumber in the specified format
	 */
	public static Object $fnumber(Object inumber, String format, Object decimal) {

		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
		DecimalFormat formatter = new DecimalFormat();
		Double inumberDbl = Double.valueOf(String.valueOf(inumber));

		if (decimal != null) {
			formatter.setMaximumFractionDigits(Integer.valueOf(String
					.valueOf(decimal)));
			formatter.setMinimumFractionDigits(Integer.valueOf(String
					.valueOf(decimal)));
		}
		if (format != null) {
			if (".".contains(format)) {
				dfs.setGroupingSeparator(String.valueOf(format).charAt(0));
				dfs.setDecimalSeparator(',');
			} else if (",".contains(format)) {
				dfs.setGroupingSeparator(String.valueOf(format).charAt(0));
				dfs.setDecimalSeparator('.');
			} else if (String.valueOf(format).equals("T")) {
				formatter.setNegativeSuffix("-");
			} else if (String.valueOf(format).equals("T+")) {
				formatter.setPositiveSuffix("+");
			} else if (String.valueOf(format).equals("P")) {
				formatter.setNegativePrefix("(");
				formatter.setNegativeSuffix(")");
			} else if ("+".equals(format)) {
				if (!(inumberDbl < 0)) {
					formatter.setPositivePrefix("+");
				} else {
					formatter.setNegativePrefix("");
				}
			} else if ("-".equals(format)) {
				if ((inumberDbl < 0)) {
					formatter.setPositivePrefix("+");
				} else {
					formatter.setNegativePrefix("-");
				}
			}

		}

		formatter.setDecimalFormatSymbols(dfs);

		String formatedString = formatter.format(inumberDbl);

		return formatedString;
	}

	public static Object $get(Object content) {
		return $get(content, null);
	}

	public static Object $get(Object content, Object defaultValue) {
		if (content == null) {
			throw new IllegalArgumentException("Content must not be null");
		}
		if (content instanceof mVar) {
			try {
				content = ((mVar) content).get();
			} catch (Exception e) {
				content = null;
			}
		}

		if (content == null && defaultValue != null) {
			return defaultValue;
		} else if (content == null && defaultValue == null) {
			return "";
		} else {
			return content;
		}
	}

	public static Object $horolog() {
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(
				SimpleTimeZone.UTC_TIME, "UTC"));
		Double daysMumps = mFncUtil.dateJavaToMumps(cal.getTimeInMillis());
		Double sec = (daysMumps - daysMumps.longValue()) * 24d * 60d * 60d;

		return daysMumps.longValue() + "," + sec.longValue();
	}

	public static Object $increment(mVar var) {
		return $increment(var, 1);
	}

	public static Object $increment(mVar var, Object increment) {
		Object dbl = mOp.Add(var.get(), increment);
		var.set(dbl);
		return dbl;
	}

	public static Object $io() {
		return 1;
	}

	public static Object $isobject(Object object) {
		int isObject = 0;
		if (object != null && object instanceof Object) {
			isObject = 1;
		}
		return isObject;
	}

	public static boolean $isvalidnum(Object num) {
		if (num == null) {
			return false;
		}
		return num.toString().matches(
				"[\\+{0,1}\\-{0,1}]{0,1}\\d+(\\.{0,1}\\d+E{0,1}\\d+){0,1}");
	}

	public static Object $job() {
		return 0;
	}

	/**
	 * Returns the value of an expression right-aligned within the specified
	 * width.
	 * 
	 * @param expression
	 * @param width
	 * @param decimal
	 * @return <br/>
	 *         expression -> The value that is to be right-aligned. It can be a
	 *         numeric value, a string literal, the name of a variable, or any
	 *         valid Caché ObjectScript expression. <br/>
	 *         width -> The number of characters within which expression is to
	 *         be right-aligned. It can be a positive integer value, the name of
	 *         an integer variable, or any valid Caché ObjectScript expression
	 *         that evaluates to a positive integer. <br/>
	 *         decimal -> Optional — The position at which to place the decimal
	 *         point within the width. It can be a positive integer value, the
	 *         name of an integer variable, or any valid Caché ObjectScript
	 *         expression that evaluates to a positive integer.
	 */
	public static Object $justify(Object expression, int width, Object decimal) {
		if (expression == null) {
			return null;
		}
		if (decimal != null) {
			expression = mFncUtil.round(mFnc.numberConverter(expression),
					mFnc.integerConverter(decimal));
		}
		String result = String.valueOf(expression);
		int length = result.length();

		width = width > length ? width : length;
		String strFormated = new String(new char[width - length]).replace("\0",
				" ").concat(result);
		return strFormated;
	}

	public static Object $justify(Object expression, Object width) {
		int widthInt = mFncUtil.integerConverter(width);
		return $justify(expression, widthInt, null);
	}

	public static Object $justify(String expression, int width) {
		return $justify(expression, width, null);
	}

	public static ListObject $lb(Object... elements) {
		return $listbuild(elements);
	}

	public static Object $length(Object expression) {
		return String.valueOf(expression).length();
	}

	public static Object $length(Object expression, Object delimiter) {
		return String.valueOf(expression).split(
				Pattern.quote(String.valueOf(delimiter))).length;
	}

	public static ListObject $list(ListObject list, int init, int end) {
		return list.sublist(init - 1, end);
	}

	public static ListObject $listbuild(Object... elements) {
		return ListObject.add(elements);
	}

	public static Object $listfind(Object $listbuild, Object object) {
		// TODO Auto-generated method stub
		if ($listbuild instanceof ListObject) {
			ListObject lo = (ListObject) $listbuild;
			return lo.find(object);
		} else {
			return 0;
		}
	}

	public static Object $listget(Object... object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $listget(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $listlength(Object list) {
		if (Boolean.valueOf(String.valueOf($listvalid(list)))) {
			return ((ListObject) list).length();
		} else {
			return null;
		}
	}

	/**
	 * Determines if an expression is a list.
	 * 
	 * @param object
	 * @return $LISTVALID determines whether exp is a list, and returns a
	 *         Boolean value: If exp is a list, $LISTVALID returns 1; if exp is
	 *         not a list, $LISTVALID returns 0. <br>
	 *         exp Any valid expression.
	 */
	public static Object $listvalid(Object object) {
		if (object instanceof ListObject) {
			return 1;
		} else {
			return 0;
		}
	}

	public static Object $name(Object object, Object object2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $order(mVar var) {
		return $order(var, 1);
	}

	public static Object $order(mVar var, Object dir) {
		final Object[] subs = var.getSubs();
		final int lenght = subs.length;

		final boolean isEmpty = lenght == 1 && subs[0].toString().length() == 0;
		final boolean isOrderOverVariables = lenght == 1 && !isEmpty;
		Object next = null;
		if (isOrderOverVariables) {
			boolean isPublic = subs[0].toString().indexOf("%") >= 0;
			boolean isGlobal = subs[0].toString().indexOf("^") >= 0;
			if (isGlobal) {
				throw new UnsupportedOperationException(
						"Does not be able order over global variables");
			}
			if (isPublic) {
				next = m$.getmDataPublic().order(subs,
						mFncUtil.numberConverter(dir).intValue());
			} else {
				next = m$.getmDataLocal().order(subs,
						mFncUtil.numberConverter(dir).intValue());
			}

			boolean isEndPublic = isPublic && "".equals(next);
			if (isEndPublic) {
				next = m$.getmDataLocal().order(new Object[] { "" },
						mFncUtil.numberConverter(dir).intValue());
			}

		} else if (isEmpty) {
			mData mdata = m$.hasPublicVariables() ? m$.getmDataPublic() : m$
					.getmDataLocal();
			next = new mVar(new Object[] { "" }, mdata).order((Integer) dir);
		} else {
			next = var.order(mFncUtil.numberConverter(dir).intValue());
		}
		return next;
	}

	public static Object $piece(Object string, Object delimiter) {
		return mFncUtil.pieceImpl(mFncUtil.castString(string),
				mFncUtil.castString(delimiter), 1);
	}

	public static String $piece(Object string, Object delimiter, Object index) {
		return mFncUtil.pieceImpl(mFncUtil.castString(string),
				mFncUtil.castString(delimiter), mFncUtil.castInt(index));
	}

	public static Object $piece(Object string, Object delimiter, Object from,
			Object to) {
		return mFncUtil.pieceImpl(mFncUtil.castString(string),
				mFncUtil.castString(delimiter), mFncUtil.castInt(from),
				mFncUtil.castInt(to));
	}

	public static Object $principal() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $qlength(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static ListObject $qsubscript(Object object, Object object2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $query(mVar reference) {
		return $query(reference, 1, null);
	}

	public static Object $query(mVar reference, Object direction) {
		return $query(reference, direction, null);
	}

	public static Object $query(mVar reference, Object direction, mVar target) {
		int _direction = (mFncUtil.numberConverter(direction) == -1) ? -1 : 1;
		mVar qreference = reference;
		Object[] qsubscripts = reference.getSubs();
		Object qorder = qsubscripts[qsubscripts.length - 1];
		boolean first = true;
		while (true) {
			if ((qorder == null) || (qorder.equals(""))) {
				if (!first) {
					if (qsubscripts.length <= 1) {
						return "";
					}
					Object[] qnewsubscripts = new Object[qsubscripts.length - 1];
					for (int i = 0; i < qsubscripts.length - 1; i++) {
						qnewsubscripts[i] = qsubscripts[i];
					}
					qsubscripts = qnewsubscripts;
					qreference = new mVar(qsubscripts, reference.getmData());
				}
				if (qsubscripts.length <= 1) {
					return "";
				}
				qorder = $order(qreference, direction);
				qsubscripts[qsubscripts.length - 1] = qorder;
			} else {
				qreference = qreference.var("");
				qsubscripts = qreference.getSubs();
				qorder = $order(qreference, _direction);
				qsubscripts[qsubscripts.length - 1] = qorder;
			}
			first = false;
			if ((qorder == null) || (qorder.equals(""))) {
				continue;
			}
			qreference = new mVar(qsubscripts, reference.getmData());
			if (qreference.get() == null) {
				continue;
			}
			break;
		}
		String result = mFncUtil.toString(qsubscripts[0]);
		if (qsubscripts.length > 1) {
			result = result + "(";
			String strsub;
			for (int i = 1; i < qsubscripts.length; i++) {
				strsub = mFncUtil.toString(qsubscripts[i]);
				if (Character.isDigit(strsub.charAt(0))) {
					result = result + ((i > 1) ? "," : "") + strsub;
				} else {
					result = result + ((i > 1) ? "," : "") + "\"" + strsub
							+ "\"";
				}
			}
			result = result + ")";
		}
		if (target != null) {
			target.set(m$.indirectVar(result));
		}
		return result;
	}

	/**
	 * Returns a pseudo-random integer value in the specified range.
	 * 
	 * @param range
	 * <br/>
	 *            range A nonzero positive integer used to calculate the random
	 *            number.
	 * @return returns a pseudo-random integer value between 0 and range-1
	 *         (inclusive).
	 */
	public static Object $random(Object range) {
		return new Random().nextInt(mFncUtil.numberConverter(range).intValue());
	}

	public static Object $replace(Object object, Object oldSubstring,
			Object newSubstring) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static String $replace(String string, String oldSubstring,
			String newSubstring) {
		if (string == null) {
			return null;
		}

		if (oldSubstring == null) {
			return string;
		}

		return string.replace(oldSubstring, newSubstring);
	}

	public static String $reverse(Object string) {
		if (string == null) {
			return null;
		}
		return new StringBuilder(String.valueOf(string)).reverse().toString();
	}

	public static Object $select(BooleanObject... conditions) {
		if (conditions == null || conditions.length == 0) {
			return null;
		}

		Object value = null;
		for (BooleanObject conditional : conditions) {
			if (conditional.isTrue()) {
				value = conditional.getValue();
				break;
			}
		}
		return value;
	}

	/**
	 * Returns the value associated with the first true expression.
	 * 
	 * @param args
	 * <br/>
	 *            expression - The select test for the associated value
	 *            parameter. <br/>
	 *            value - The value to be returned if the associated expression
	 *            evaluates to true.
	 * @return Object associated
	 */
	public static Object $select(Object... args) {
		Boolean hasTrue = false;
		Object returnObj = null;
		if (args != null) {
			if (args.length % 2 != 0) {
				throw new IllegalArgumentException(
						"This method requires at least one pair of  condition and value that returns as true.");
			}
			for (int i = 0; i < args.length; i++) {
				if (i % 2 == 0) {
					boolean bool = mFncUtil.booleanConverter(args[i]);
					if (bool) {
						hasTrue = bool;
						returnObj = args[i + 1];
						break;
					}
				}
			}
			if (!hasTrue) {
				throw new IllegalArgumentException(
						"This method requires at least one pair of  condition and value that returns as true.");
			}
		}
		return returnObj;
	}

	public static Object $setpiece(Object string, Object delimiter,
			Object position, Object value) {
		return mFncUtil.setPieceImpl(mFncUtil.castString(string),
				mFncUtil.castString(delimiter), mFncUtil.castInt(position),
				value);
	}

	public static Object $stack(Object... objs) {
		// TODO REVISAR IMPLEMENTAÇÃO DA FUNÇÃO
		return "";
	}

	public static Object $storage() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@TODO
	public static Object $test() {
		// TODO Auto-generated method stub
		return 1;
	}

	public static Object $tlevel() {
		// TODO REVISAR RETORNO PROVISÓRIO
		return 1;
	}

	//
	public static Object $translate(Object string, Object identifier) {
		return $translate(string, identifier, null);
	}

	public static Object $translate(Object string, Object oldCharsequence,
			Object newCharsequence) {
		return mFncUtil.translateImpl(mFncUtil.castString(string),
				mFncUtil.castString(oldCharsequence),
				mFncUtil.castString(newCharsequence));
	}

	/**
	 * Absolute value function.
	 * 
	 * @param n
	 * @return returns the absolute value of n.
	 */
	public static Object $zabs(Object n) {
		return Math.abs(mFncUtil.numberConverter(n));
	}

	public static Object $zconvert(Object string, String mode) {
		String zconverted = String.valueOf(string);
		if (mode.equalsIgnoreCase("U")) {
			zconverted = String.valueOf(string).toUpperCase();
		}
		if (mode.equalsIgnoreCase("L")) {
			zconverted = String.valueOf(string).toLowerCase();
		}
		return zconverted;
	}

	public static Object $zconvert(Object object, String string, String string2) {
		// TODO REVISAR IMPLEMENTAÇÃO DO MÉTODO throw new
		// UnsupportedOperationException();
		return object;
	}

	public static Object $zcrc(Object object, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zdate(Object hdate) {

		return $zdate(hdate, null, null, null, null, null, null, null, null);
	}

	public static Object $zdate(Object hdate, Object dformat) {

		return $zdate(hdate, dformat, null, null, null, null, null, null, null);
	}

	/**
	 * Validates date and converts format.
	 * 
	 * @param hdate
	 * @param dformat
	 * @param monthlist
	 * @param yearopt
	 * @param startwin
	 * @param endwin
	 * @param mindate
	 * @param maxdate
	 * @param erropt
	 * @return
	 */
	private static Object $zdate(Object hdate, Object dformat,
			Object monthlist, Object yearopt, Object startwin, Object endwin,
			Object mindate, Object maxdate, Object erropt) {

		Date dt = new Date(mFncUtil.dateMumpsToJava(hdate).longValue());

		SimpleDateFormat sdf = new SimpleDateFormat(
				mFncUtil.dateCodeFormatMumpsToJava(dformat));

		return sdf.format(dt);
	}

	public static Object $zdateh(Object date) {
		throw new UnsupportedOperationException();
	}

	public static Object $zdateh(Object date, Object dformat) {

		SimpleDateFormat sdf = new SimpleDateFormat(
				mFncUtil.dateCodeFormatMumpsToJava(dformat));
		String returnDate = null;
		try {
			returnDate = String.valueOf(mFncUtil.dateJavaToMumps(
					sdf.parse(String.valueOf(date))).longValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	public static Object $zdateh(Object date, Object dformat, Object monthlist,
			Object yearopt, Object startwin, Object endwin, Object mindate,
			Object maxdate, Object erropt) {

		SimpleDateFormat sdf = new SimpleDateFormat(
				mFncUtil.dateCodeFormatMumpsToJava(dformat));
		String returnDate = erropt != null ? String.valueOf(erropt) : null;
		try {
			returnDate = String.valueOf(mFncUtil.dateJavaToMumps(
					sdf.parse(String.valueOf(date))).longValue());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return returnDate;
	}

	public static Object $zdatetime(Object... object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zdatetimeh(Object object, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zeof() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zerror() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zhex(Object $zjob) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zhorolog() {
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(
				SimpleTimeZone.UTC_TIME, "UTC"));
		cal.set(2010, 01, 01);
		return Double
				.valueOf(
						mFncUtil.dateJavaToMumps(cal.getTimeInMillis()) * 24d * 60d * 60d)
				.longValue();
	}

	public static Object $zjob() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $znspace() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zorder(mVar reference) {
		return $query(reference);
	}

	public static Object $zsearch(Object string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes types of characters and individual characters from a specified
	 * string.
	 * 
	 * @param object
	 * @param string
	 * @return <BR>
	 *         string -> The string to be stripped. <BR>
	 *         action -> What to strip from string. A string consisting of an
	 *         action code followed by one or more mask codes. Specified as a
	 *         quoted string. <BR>
	 *         remchar -> Optional � A string of specific character values to
	 *         remove. Generally, these are additional characters not covered by
	 *         the action parameter�s mask code. <BR>
	 *         keepchar -> Optional � A string of specific character values to
	 *         not remove that are designated for removal by the action
	 *         parameter�s mask code.
	 */
	public static Object $zstrip(Object targetString, Object maskCode) {
		return $zstrip(targetString, maskCode, "", "");
	}

	public static Object $zstrip(Object targetString, Object maskCode,
			Object remChars) {
		return $zstrip(targetString, maskCode, remChars, "");
	}

	public static Object $zstrip(Object targetString, Object maskCode,
			Object remChars, Object keepChars) {
		return mZStripParser.zstrip(String.valueOf(targetString),
				String.valueOf(maskCode), String.valueOf(remChars),
				String.valueOf(keepChars));
	}

	public static String $ztimestamp() {
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(
				SimpleTimeZone.UTC_TIME, "UTC"));
		Double daysMumps = mFncUtil.dateJavaToMumps(cal.getTimeInMillis());
		Double sec = (daysMumps - daysMumps.longValue()) * 24d * 60d * 60d;
		Double fra = (sec - sec.longValue()) * 1000d;

		return daysMumps.longValue() + "," + sec.longValue() + "."
				+ fra.longValue();
	}

	public static String $zts() {
		throw new UnsupportedOperationException();
	}

	public static ListObject $zu(int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zutil(int i) {
		if (i == 5) {
			return "DEFAULT";
		} else if (i == 110) {
			InetAddress addr;
			try {
				addr = InetAddress.getLocalHost();
				return addr.getHostName();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public static Object $zutil(int i, Object... obj) {
		if (i == 139) {
			return null;
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public static Object $zutil(Object obj1, Object obj2, Object obj3,
			Object obj4, Object obj5) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zversion() {
		// TODO REVISAR IMPLEMENTAÇÃO PROVISÓRIA
		return "NetManager Java Version 1.0";
	}

	public static Boolean booleanConverter(Object obj) {
		Boolean bool = false;
		if (obj instanceof Boolean) {
			bool = Boolean.parseBoolean(String.valueOf(obj));
		} else {
			Double dbl = numberConverter(obj);
			if (dbl != 0) {
				bool = true;
			}
		}
		return bool;
	}

	public static Double castDouble(Object obj) {
		try {
			return (Double) obj;
		} catch (ClassCastException e) {
			return 0d;
		}
	}

	public static Integer castInt(Object obj) {
		try {
			return (Integer) obj;
		} catch (ClassCastException e) {
			return 0;
		}
	}

	public static Integer[] castIntArray(Object... obj) {
		if (obj == null) {
			return null;
		} else if (obj.length == 0) {
			return new Integer[] {};
		}

		final Integer[] array = new Integer[obj.length];
		for (int i = 0; i < obj.length; i++) {
			array[i] = castInt(obj[i]);
		}
		return array;
	}

	public static String castString(Object obj) {
		try {
			return (String) obj;
		} catch (ClassCastException e) {
			return null;
		}
	}

	public static String characterImpl(Integer... codes) {
		if (codes == null) {
			return null;
		}

		if (codes.length == 1 && codes[0] == null) {
			return null;
		}

		final Character[] chars = new Character[codes.length];
		for (int i = 0; i < codes.length; i++) {
			// Apenas os inteiros nao-negativos deve ser convertidos.
			if (codes[i] != null && codes[i] > 0) {
				chars[i] = Character.toChars(codes[i])[0];
			}
		}

		return generateString(chars, null, true);
	}

	public static String extractImpl(String string, int from, int to) {
		if (string == null) {
			return null;
		}
		try {
			return string.substring(from - 1, to);
		} catch (Exception e) {
			return null;
		}

	}

	public static int findImpl(String string, String substring, int start) {
		if (string == null || string.trim().length() == 0 || substring == null
				|| substring.length() == 0) {
			return 0;
		}

		if (start < 1 || start >= string.length()) {
			return 0;
		}

		string = string.substring(start);

		char[] substringChar = substring.toCharArray();
		char[] stringChar = string.toCharArray();

		int j = 0;
		int max = substringChar.length;
		for (int i = 0; i < stringChar.length; i++) {
			if (stringChar[i] == substringChar[j]) {
				j++;
			} else {
				j = 0;
			}

			if (j == max) {
				return ++i + (start + 1);
			}
		}
		return 0;
	}

	public static String generateString(Object[] array, String delimiter) {
		return generateString(array, delimiter, false);
	}

	public static String generateString(Object[] array, String delimiter,
			boolean avoidNull) {
		final StringBuilder result = new StringBuilder();
		final int indexToInsert = array.length - 1;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				array[i] = "";
			}
			if (avoidNull) {
				continue;
			}
			result.append(array[i]);
			if (delimiter != null && i < indexToInsert) {
				result.append(delimiter);
			}
		}
		return result.toString();
	}

	private static boolean hasElementOnListObject(ListObject list, int position) {
		Object elemet = list(list, position);
		return elemet != null && !"".equals(elemet.toString().trim());
	}

	public static Integer integerConverter(Object number) {
		return numberConverter(number).intValue();
	}

	public static Object list(ListObject list) {
		return list.firstElement();
	}

	public static Object list(ListObject list, int position) {
		return list.element(position);
	}

	public static int listData(ListObject list, int position) {
		return hasElementOnListObject(list, position) ? 1 : 0;
	}

	public static Object listGet(ListObject list, int position,
			String defaultValue) {
		if (hasElementOnListObject(list, position)) {
			return list(list, position);
		} else {
			return defaultValue;
		}
	}

	public static Double numberConverter(Object obj) {
		Double dbl = null;
		try {
			dbl = Double.valueOf(String.valueOf(obj));
		} catch (NumberFormatException nfe) {
			String result = "";
			char[] charArray = obj.toString().toCharArray();
			boolean startNumber = false;
			boolean hasPoint = false;
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (!startNumber && (c == '+' || c == '-')) {
					if (result.isEmpty()) {
						result = String.valueOf(c);
					} else {
						if (result.equals(String.valueOf(c))) {
							result = "+";
						} else {
							result = "-";
						}
					}
					continue;
				}
				if (Character.isDigit(c)) {
					startNumber = true;
					result = result.concat(String.valueOf(c));
					continue;
				}
				if (c == '.') {
					startNumber = true;
					if (!hasPoint) {
						hasPoint = true;
						result = result.concat(String.valueOf(c));
						continue;
					}
				}
				if (result.isEmpty() || !startNumber) {
					result = "0";
				}
				break;
			}
			dbl = Double.valueOf(result);
		}
		return dbl;
	}

	public static String pieceImpl(String string, String delimiter, int index) {

		if (string == null) {
			return null;
		}

		return string.split(delimiter)[index - 1];
	}

	public static String pieceImpl(String string, String delimiter, int from,
			int to) {
		if (string == null) {
			return null;
		}

		if (from > to) {
			return null;
		}

		final String[] array = Arrays.copyOfRange(string.split(delimiter),
				from - 1, to);

		return generateString(array, delimiter);
	}

	public static String setPieceImpl(String string, String delimiter,
			Integer position, Object value) {
		Object[] array = string == null ? new Object[position] : string
				.toString().split(delimiter);
		if (position > array.length) {
			array = Arrays.copyOfRange(array, 0, position);
		}
		if (value == null) {
			value = "";
		}

		array[position - 1] = value.toString();

		return generateString(array, delimiter);
	}

	public static String translateImpl(String string, String oldCharsequence,
			String newCharsequence) {

		if (string == null || oldCharsequence == null
				|| oldCharsequence.length() == 0) {
			return string;
		}

		/*
		 * Esse mecanismo foi escolhido para evidar de criar muitas strings no
		 * pool e evitar o consumo de memoria excessivo.
		 */
		char[] stringChars = string.toCharArray();
		char[] oldChars = oldCharsequence.toCharArray();
		char[] newChars = (newCharsequence != null ? newCharsequence : "")
				.toCharArray();

		// Aqui vamos completar o newChars de substiticao pois quando
		// os tamanhos nao forem identicos isso indicara que os valores
		// excedentes devem ser substituidos pelo caracter "vazio".
		if (newChars.length < oldChars.length) {
			newChars = Arrays.copyOf(newChars, oldChars.length);
		}

		final StringBuilder result = new StringBuilder();
		char copy;
		for (int i = 0; i < stringChars.length; i++) {
			copy = stringChars[i];
			for (int j = 0; j < oldChars.length; j++) {
				if (copy == oldChars[j]) {
					if (newCharsequence != null) {
						copy = newChars[j];
					} else {
						copy = '\0';
					}
					break;
				}
			}

			// Caso o caracter seja vazio nao devemos inclui-lo na string
			// gerada.
			if ('\0' != copy) {
				result.append(copy);
			}

		}

		return result.toString();
	}

	public static Object zconvert(Object string, Object mode) {
		return zconvertImpl(castString(string), castString(mode));
	}

	public static String zconvertImpl(String string, String mode) {
		if (mode == null || mode.trim().length() == 0) {
			return string;
		}

		if ("L".equalsIgnoreCase(mode)) {
			string = string.toLowerCase();
		} else if ("U".equalsIgnoreCase(mode)) {
			string = string.toUpperCase();
		}
		return string;
	}

	public mFnc(mContext m$) {
		super(m$);
	}

	public Object $ztrap() {
		return m$.var("$ZTRAP").get();
	}

	public Object $zversion(int i) {
		throw new UnsupportedOperationException();
	}

	public Object length(Object string) {
		return length(castString(string));
	}

	public Object length(Object string, Object delimiter) {
		return lengthImpl(castString(string), castString(delimiter));
	}

	public int lengthImpl(String string) {
		if (string == null) {
			return 0;
		}

		return string.length();
	}

	public Object lengthImpl(String string, String delimiter) {
		if (delimiter == null && string == null) {
			return 0;
		}

		if (delimiter != null && string == null) {
			return 1;
		}

		return string.split(delimiter).length;
	}

	public String pieceImpl(String string, String delimiter) {
		return $piece(string, delimiter, 1);
	}

	public Object $zbitget(Object $zversion, int i) {
		throw new UnsupportedOperationException();
	}

	public Object $zboolean(Object object, int i, int j) {
		throw new UnsupportedOperationException();
	}

	public Object $ztime(String $piece, int i) {
		throw new UnsupportedOperationException();
	}

	public Object $text(Object object) {
		throw new UnsupportedOperationException();
	}

	public Object $zf(Object negative, Object concat) {
		throw new UnsupportedOperationException();
	}

	public Object $zobjproperty(Object object, Object object2) {
		throw new UnsupportedOperationException();
	}

	public Object $zobjclassmethod(Object object, String string, Object object2) {
		throw new UnsupportedOperationException();
	}

	public Object $ztime(Object object, int i) {
		throw new UnsupportedOperationException();
	}

	public Object $view(Object $zutil, Object negative, int i) {
		throw new UnsupportedOperationException();
	}

	public Object $ztimeh(Object object, int i, String string) {
		throw new UnsupportedOperationException();
	}
}

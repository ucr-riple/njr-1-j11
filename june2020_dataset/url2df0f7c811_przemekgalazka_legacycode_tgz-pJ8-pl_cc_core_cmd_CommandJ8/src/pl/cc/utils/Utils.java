package pl.cc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * Rďż˝ne poďż˝yteczne
 * 
 * @since 2007-06-26
 */
public class Utils {
	public static final Logger log = Logger.getLogger(Utils.class);
	//public static final String NULL = "- null -";
	public static final String NULL = "";
	
	public static String getNumber(String s){
		//+INFO New Call from '977', agent: 'Agent/301', queue: 'kolejka'
		int a = s.indexOf("'");
		int b = s.indexOf("'", a+1);
		int c = s.indexOf("'", b+1);
		int d = s.indexOf("'", c+1);
		int e = s.indexOf("'", d+1);
		int f = s.indexOf("'", e+1);
		return s.substring(e+1,f)+" "+s.substring(a+1,b);
	}

    public static String[] getInfo(String s){
        //+INFO New Call from '977', agent: 'Agent/301', queue: 'kolejka'
        int a = s.indexOf("'");
        int b = s.indexOf("'", a+1);
        int c = s.indexOf("'", b+1);
        int d = s.indexOf("'", c+1);
        int e = s.indexOf("'", d+1);
        int f = s.indexOf("'", e+1);
        return new String[] {s.substring(e+1,f),s.substring(a+1,b)};
    }


    public static Date getNow(){
		GregorianCalendar gc = new GregorianCalendar();
		return gc.getTime();
	}
	
	/**
	 * @return obecna data w formacie "HH:mm:ss"
	 */
	public static String getNowFormated(){
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(gc.getTime());
	}

	
	/**
	 * @return obecna data w formacie "yyyy-MM-dd HH:mm:ss"
	 */
	public static String formatDate(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * @param seconds
	 * @return czas w formacie mmm:ss
	 */
	public static String secToString(final long seconds){
		long total =  seconds;
		long min = total/60;
		long sec = total-min*60;
		String s = new Long(sec).toString();
		if (s.length()==1) s="0"+s;
		return new Long(min).toString()+":"+s;
	}

	public static Date parseDate(String date) {
		if (date==null) return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			log.error(e);
			return null;
		}

	}

	public static String toPL(Boolean value) {
		if (value==null){
			return null;
		} else if (value) {
			return "tak";
		} else {
			return "nie";
		}
	}
}

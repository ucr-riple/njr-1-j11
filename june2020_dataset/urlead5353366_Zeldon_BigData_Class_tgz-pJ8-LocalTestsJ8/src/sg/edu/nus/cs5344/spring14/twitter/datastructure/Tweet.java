package sg.edu.nus.cs5344.spring14.twitter.datastructure;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.hadoop.io.WritableComparable;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.HashTagList;

/**
 * A single tweet.
 * This class contains all the information we need about a single tweet.
 *
 * @author Tobias Bertelsen
 *
 */
public class Tweet implements WritableComparable<Tweet>, Copyable<Tweet> {

	public static final int EXPECTED_COLUMNS = 14;

	public static String[] split(String lineString) {
		return lineString.split("\\t");
	}

	private static SimpleDateFormat dateFormat = getDateFormat();

	private Time time = new Time();
	private LatLong latLong = new LatLong();
	private HashTagList hashTagList = new HashTagList();

	public Tweet(){
	}

	public Tweet(Time time, LatLong latLong, HashTagList hashTagList){
		this.time = time.copy();
		this.latLong = latLong.copy();
		this.hashTagList = hashTagList.copy();
	}

	/**
	 * Parse a line of the input data.
	 * @param line
	 * @throws IllegalArgumentException if the line cannot be parsed.
	 */
	public Tweet(String lineString) {
		this(split(lineString));
	}

	public Tweet(String[] line) {

		if (line.length!=EXPECTED_COLUMNS) {
			throw new IllegalArgumentException("Wrong number of columns: " + line.length);
		}
		// Line contents
		// 0:  ID
		// 1:  Time of creation YYYY-MM-DD kk:mm:ss
		// 2:  Tweet Text
		// 3:  Hashtabs (comma sep) or "\\N"
		// 4:  Keywords
		// 5:  lat
		// 6:  long
		// 7:  ulocation
		// 8:  Place full name
		// 9:  Country
		// 10: User name
		// 11: User Language
		// 12: ulocation
		// 13: Time of ??? YYYY-MM-DD kk:mm:ss


		// Convert hashtags:
		hashTagList = readHashtags(line);

		// Convert Time:
		try {
			time = new Time(dateFormat.parse(line[1]).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Can't parse time", e);
		}

		// Convert location:
		double lat = Double.parseDouble(line[5]);
		double lon = Double.parseDouble(line[6]);
		latLong = new LatLong(lat, lon);

	}


	private HashTagList readHashtags(String[] line) {
		String tagsString = line[3];
		HashTagList list = new HashTagList();

		if (!"\\N".equals(tagsString)) {
			for (String tag : tagsString.split(",")) {
				list.add(new Hashtag(tag));
			}
		}
		return list;
	}



	private static SimpleDateFormat getDateFormat() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));;
		return format;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		time.write(out);
		latLong.write(out);
		hashTagList.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		time.readFields(in);
		latLong.readFields(in);
		hashTagList.readFields(in);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tweet) {
			Tweet other = (Tweet) obj;
			if (time.equals(other.time) &&
					latLong.equals(other.latLong) &&
					hashTagList.equals(other.hashTagList)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = time.hashCode();
		hash += 31*latLong.hashCode();
		hash += 31*31*hashTagList.hashCode();
		return hash;
	}

	@Override
	public int compareTo(Tweet o) {
		int comparison = time.compareTo(o.time);
		if (comparison != 0) {
			return comparison;
		}

		comparison = latLong.compareTo(o.latLong);
		if (comparison != 0) {
			return comparison;
		}

		return hashTagList.compareTo(o.hashTagList);
	}

	@Override
	public Tweet copy() {
		return new Tweet(time, latLong, hashTagList);
	}

	public HashTagList getHashTagList() {
		return hashTagList;
	}

	public LatLong getLatLong() {
		return latLong;
	}

	public Time getTime() {
		return time;
	}
}

package sg.edu.nus.cs5344.spring14.twitter.datastructure;

import org.apache.hadoop.io.Text;

/**
 * A single hashtag.
 * <p>
 * It is essentially just an extension of {@link Text}, to increase type
 * safety as well as ensuring the # char is not stored
 * @author Tobias Bertelsen
 *
 */
public class Hashtag extends Text implements Copyable<Hashtag> {

	public Hashtag() {
	}

	public Hashtag(String tag) {
		super(clearLeadingHash(tag));
	}

	private static String clearLeadingHash(String tag) {
		if (tag.charAt(0) == '#') {
			return tag.substring(1);
		}
		return tag;
	}

	@Override
	public Hashtag copy() {
		return new Hashtag(getTagString());
	}

	/**
	 * Returns the string of this hashtag (without #)
	 * @return
	 */
	public String getTagString() {
		return super.toString();
	}

	/**
	 * Returns the string of this hashtag (including #)
	 * @return
	 */
	@Override
	public String toString() {
		return "#" + super.toString();
	}

}

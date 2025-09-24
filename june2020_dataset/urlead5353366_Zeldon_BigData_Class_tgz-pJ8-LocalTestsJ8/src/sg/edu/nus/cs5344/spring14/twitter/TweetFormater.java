package sg.edu.nus.cs5344.spring14.twitter;

import java.text.SimpleDateFormat;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Hashtag;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.LatLong;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Tweet;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.collections.TweetList;

public class TweetFormater implements KeyValueFormatter<Hashtag, TweetList> {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String format(Hashtag key, TweetList value) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n// New hashtag\n");
		sb.append(key.toString());
		for (Tweet tweet : value) {
			sb.append("\n");
			sb.append(dateFormat.format(tweet.getTime().getDateCopy()));
			sb.append("\t");
			LatLong latlon = tweet.getLatLong();
			sb.append(latlon.getLat());
			sb.append("\t");
			sb.append(latlon.getLong());
		}

		return sb.toString();
	}

}

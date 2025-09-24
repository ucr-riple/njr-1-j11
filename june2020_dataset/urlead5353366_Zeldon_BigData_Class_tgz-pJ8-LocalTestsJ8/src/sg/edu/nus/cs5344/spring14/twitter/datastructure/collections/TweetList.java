package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Copyable;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Tweet;

public class TweetList extends ListWritable<Tweet> implements Copyable<TweetList>{

	public TweetList() {
		super(new Tweet());
	}

	@Override
	public TweetList copy() {
		TweetList list = new TweetList();
		list.addAll(this);
		return list;
	}

}

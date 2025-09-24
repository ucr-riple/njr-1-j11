package sg.edu.nus.cs5344.spring14.twitter.datastructure.collections;

import sg.edu.nus.cs5344.spring14.twitter.datastructure.Copyable;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Hashtag;

public class HashTagList extends ListWritable<Hashtag> implements Copyable<HashTagList> {

	public HashTagList() {
		super(new Hashtag());
	}

	@Override
	public HashTagList copy() {
		HashTagList hashTagList = new HashTagList();
		hashTagList.addAll(this);
		return hashTagList;
	}

}

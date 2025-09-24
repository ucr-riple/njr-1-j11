package pl.cc.core;

import java.util.ArrayList;

public class GroupData {
	String name;
	private ArrayList<String> topicList;
	
	public GroupData(String name, ArrayList<String> topicList) {
		super();
		this.name = name;
		this.setTopicList(topicList);
	}
	
	public String toString(){
		return name;
	}

	public ArrayList<String> getTopicList() {
		return topicList;
	}

	public void setTopicList(ArrayList<String> topicList) {
		this.topicList = topicList;
	}
}

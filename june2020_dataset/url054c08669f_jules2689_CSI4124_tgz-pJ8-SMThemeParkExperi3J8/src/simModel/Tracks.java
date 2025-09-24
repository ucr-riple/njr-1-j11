package simModel;

import java.util.ArrayList;

public class Tracks {

	protected ArrayList<Integer> trainList = new ArrayList<Integer>();

	// Required methods to manipulate the queue
	protected void spInsertQue(Integer train) {
		trainList.add(train);
	}

	protected Integer spRemoveQue() {
		if (trainList.size() > 0) {
			return trainList.remove(0);
		} else {
			return null;
		}
	}

	protected int getN() {
		return trainList.size();
	}

}

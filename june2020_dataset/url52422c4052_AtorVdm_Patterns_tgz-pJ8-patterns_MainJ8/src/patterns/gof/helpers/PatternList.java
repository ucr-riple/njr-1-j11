package patterns.gof.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternList {
	private String listName;
	private List<Client> patterns = new ArrayList<Client>();
	
	public PatternList(String listName, Client[] array) {
		this.listName = listName;
		patterns = Arrays.asList(array);
	}

	public String getListName() {
		return listName;
	}

	public List<Client> getPatterns() {
		return patterns;
	}
}

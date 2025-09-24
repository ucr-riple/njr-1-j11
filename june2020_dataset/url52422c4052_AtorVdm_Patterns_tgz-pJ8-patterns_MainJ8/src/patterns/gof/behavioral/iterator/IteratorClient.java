package patterns.gof.behavioral.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import patterns.gof.helpers.Client;

public class IteratorClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		
		Iterator<String> it = list.iterator();

		String s;
		while(it.hasNext()) {
			s = it.next();
			if (s.equals("4")) {
				it.remove();
				continue;
			}
			addOutput("item: " + s);
		}
		
		super.main("Iterator");
	}
}

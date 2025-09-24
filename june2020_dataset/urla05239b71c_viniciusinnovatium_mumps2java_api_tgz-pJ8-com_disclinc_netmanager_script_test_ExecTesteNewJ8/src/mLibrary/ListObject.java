package mLibrary;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;

public final class ListObject {
	private final List<Object> list = new LinkedList<Object>();

	private ListObject() {
	};

	static ListObject getInstance() {
		return new ListObject();
	}

	static ListObject concat(ListObject... lists) {
		ListObject l = new ListObject();
		if (lists != null && lists.length > 0) {
			for (ListObject listObject : lists) {
				l.list.addAll(listObject.list);
			}
		}
		return l;
	}

	static ListObject add(Object... elements) {
		ListObject l = new ListObject();
		l.list.addAll(Arrays.asList(elements));
		return l;
	}

	static int find(Object listObj, Object obj) {
		if (listObj instanceof ListObject) {
			List<Object> list = ((ListObject) listObj).list;
			for (int i = 0; i < list.size(); i++) {
				Object item = list.get(i);
				item = mFncUtil.toString(item);
				obj = mFncUtil.toString(obj);
				if (item.equals(obj)) {
					return i;
				}

			}
		}
		return 0;
	}

	public int find(Object obj) {
		return find(this, obj);
	}

	ListObject sublist(int init, int end) {
		final ListObject l = new ListObject();
		if (init < 0) {
			init = 0;
		}

		if (end > this.list.size()) {
			end = this.list.size();
		}

		if (init > end) {
			init = end;
		}

		l.list.addAll(this.list.subList(init, end));
		return l;
	}

	public Object element(int position) {
		if (position <= 0) {
			position = 0;
		} else {
			position = position - 1;
		}
		return this.list.get(position);
	}

	public Object firstElement() {
		return element(1);
	}

	public int length() {
		return list.size();
	}

	public String toString() {
		int lastDelimiter = list.size() - 1;
		int count = 0;
		StringBuilder string = new StringBuilder();
		for (Object o : list) {
			string.append(o);
			if (count < lastDelimiter) {
				string.append(DataStructureUtil.DELIMITER);
			}
			count++;
		}
		return string.toString();
	}
}

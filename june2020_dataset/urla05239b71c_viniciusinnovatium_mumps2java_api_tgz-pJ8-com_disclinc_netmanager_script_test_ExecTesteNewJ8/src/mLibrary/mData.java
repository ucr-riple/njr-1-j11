package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKey;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyToLikeQuery;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyWithoutVarName;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateSubs;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateTableName;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.dataaccess.DAO;
import br.com.innovatium.mumps2java.datastructure.OrderDataCache;
import br.com.innovatium.mumps2java.datastructure.Tree;

public class mData {
	Object[] currentSubs;

	DAO dao;
	final Tree tree = new Tree();
	final Set<String> cacheOrderFunction = new HashSet<String>(50);
	final OrderDataCache orderDataCache = new OrderDataCache();

	public Object get(Object... subs) {
		Object value = tree.get(subs);
		if (isDiskAccess(subs) && value == null) {
			initDAO();
			final String tableName = generateTableName(subs);
			value = dao.find(tableName, generateKeyWithoutVarName(subs));
			tree.set(subs, value);
		}
		return value;
	}

	/*
	 * This method was create to support lastVar function and should not remove.
	 */
	public Object[] getCurrentSubs() {
		return currentSubs;
	}

	public boolean isEmpty() {
		return tree.isEmpty();
	}

	public void set(Object value) {
		if (isDiskAccess(currentSubs)) {
			if (currentSubs != null) {
				initDAO();
				final String tableName = generateTableName(currentSubs);
				// Here we have calling toString method because ListObject
				// should be persisted as string
				dao.insert(tableName, generateKeyWithoutVarName(currentSubs),
						value != null ? value.toString() : null);
			}
		}
		tree.set(currentSubs, value);
	}

	public void merge(Object[] dest, Object[] orig) {
		tree.merge(dest, orig);
	}

	public void stacking(Object... variables) {
		if (!isDiskAccess(variables)) {
			tree.stacking(variables);
		} else {
			throw new UnsupportedOperationException(
					"Stacking variable is not supported to access data on disk");
		}
	}

	public void stackingExcept(Object... variables) {
		tree.stackingExcept(variables);
	}

	public void unstacking() {
		tree.unstacking();
	}

	public String dump() {
		return tree.dump();
	}

	public void kill(Object... subs) {
		currentSubs = null;
		if (isDiskAccess(subs)) {
			initDAO();
			dao.remove(generateTableName(subs), generateKeyWithoutVarName(subs));
			tree.kill(subs);
		} else {
			tree.kill(subs);
		}
	}

	public int data(Object... subs) {
		currentSubs = subs;
		populateTree();
		return tree.data(subs);
	}

	public Object order(Object[] subs, int direction) {
		this.currentSubs = subs;
		populateTree();
		return tree.order(subs, direction);
	}

	public Object order(Object[] subs) {
		return order(subs, 1);
	}

	public mData subs(Object... subs) {
		currentSubs = subs;
		return this;
	}

	private void populateTree() {
		if (isDiskAccess(currentSubs)) {
			if (!orderDataCache.isCached(currentSubs)) {
				orderDataCache.add(currentSubs);
				initDAO();
				findDataOnDisk();
			}
		}
	}

	private boolean isDiskAccess(Object... subs) {
		boolean bool = false;
		if (subs != null && subs.length > 0 && subs[0] != null
				&& !subs[0].toString().isEmpty()) {
			bool = subs[0].toString().charAt(0) == '^';
		}
		return bool;
	}

	private void initDAO() {
		if (dao == null) {
			this.dao = new DAO();
		}
	}

	private void findDataOnDisk() {

		String tableName = generateTableName(currentSubs);

		Map<String, String> map = dao.like(tableName,
				generateKeyToLikeQuery(currentSubs));

		if (map != null) {
			Set<Entry<String, String>> result = map.entrySet();
			for (Entry<String, String> entry : result) {
				// Here we have to include variable or table name into the key
				// because this is part of the subscripts.
				tree.set(generateSubs(tableName, entry.getKey()),
						entry.getValue());
			}
		}
	}
}

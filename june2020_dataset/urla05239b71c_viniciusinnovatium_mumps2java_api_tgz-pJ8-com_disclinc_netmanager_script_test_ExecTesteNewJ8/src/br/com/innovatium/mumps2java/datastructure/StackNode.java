package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class StackNode {
	private Map<Integer, Deque<Node>> mapLevel;

	public StackNode() {
		mapLevel = new HashMap<Integer, Deque<Node>>();
	}

	public Node push(Node node) {
		if (node == null) {
			return null;
		}

		Integer level = node.getStackLevel();
		if (level == null) {
			throw new IllegalArgumentException(
					"The inclusion of empty level node is not allowed.");
		}
		Deque<Node> queue = mapLevel.get(level);

		if (queue == null) {
			queue = new ArrayDeque<Node>();
			mapLevel.put(level, queue);
		}
		queue.addFirst(node);
		return node;
	}

	public List<Node> pull(Integer stackLevel) {
		Deque<Node> queue = this.mapLevel.get(stackLevel);
		if (queue != null) {
			List<Node> nodes = new ArrayList<Node>(queue);
			this.mapLevel.remove(stackLevel);
			return nodes;
		}
		return null;
	}
}

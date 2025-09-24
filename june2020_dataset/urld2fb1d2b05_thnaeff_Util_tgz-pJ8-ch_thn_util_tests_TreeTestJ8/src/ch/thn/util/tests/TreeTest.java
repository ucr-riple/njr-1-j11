/**
 *    Copyright 2013 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util.tests;

import java.util.Comparator;

import ch.thn.util.file.FileUtil;
import ch.thn.util.tree.KeyListTreeNode;
import ch.thn.util.tree.KeySetTreeNode;
import ch.thn.util.tree.ListTreeIterator;
import ch.thn.util.tree.ListTreeNode;
import ch.thn.util.tree.SetTreeNode;
import ch.thn.util.tree.TreeIterator;
import ch.thn.util.tree.TreeNodeEvent;
import ch.thn.util.tree.TreeNodeListener;
import ch.thn.util.tree.core.CollectionTreeNodeInterface;
import ch.thn.util.tree.core.ListTreeNodeInterface;
import ch.thn.util.tree.core.MapTreeNodeInterface;
import ch.thn.util.tree.core.SetTreeNodeInterface;
import ch.thn.util.tree.printer.TreeNodeCSVPrinter;
import ch.thn.util.tree.printer.TreeNodeDebugPrinter;
import ch.thn.util.tree.printer.TreeNodeHTMLPrinter;
import ch.thn.util.tree.printer.TreeNodePlainTextPrinter;


/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class TreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Comparator<Integer> comparator = new Comparator<Integer>() {

			@Override
			public int compare(Integer node1, Integer node2) {
				return 0; //Integer.compare(node1, node2);
			}
		};

		ListTreeNode<String> listTree = new ListTreeNode<String>("( 0) Value-only test\nNewline1");
		SetTreeNode<String> setTree = new SetTreeNode<String>("( 0) Value-only test\nNewline1");
		KeyListTreeNode<Integer, String> keyListTree = new KeyListTreeNode<Integer, String>(0, "Key-Value test\nNewline1");
		KeySetTreeNode<Integer, String> keySetTree = new KeySetTreeNode<Integer, String>(comparator, null, 0, "Key-Value test\nNewline1");

		listTree.addTreeNodeListener(new TestTreeNodeListener());

		fillCollectionTree(listTree);
		fillCollectionTree(setTree);
		fillMapTree(keyListTree);
		fillMapTree(keySetTree);

		System.out.println("has=" + keySetTree.hasChildNodes(111));
		System.out.println("node=" + keySetTree.getChildNodes(111));

		//For TreeIterator test
		CollectionTreeNodeInterface<String, ?> collectionTreeGeneral = setTree;	//keyListTree, keySetTree, setTree or listTree
		//
		MapTreeNodeInterface<Integer, String, ?> mapTreeGeneral = keyListTree;	//keyListTree or keySetTree
		//For ListTreeIterator test
		ListTreeNodeInterface<String, ?> listTreeGeneral = listTree;	//keyListTree or listTree
		//
		SetTreeNodeInterface<String, ?> setTreeGeneral = keySetTree;	//keySetTree or setTree

		//Testing the TreeIterator
		TreeIterator<?> iterator = collectionTreeGeneral.iterator();

		while (iterator.hasNext()) {
			System.out.println(iterator.next().getNodeValue().toString().replace("\n", " "));
		}

		//Testing the ListTreeIterator
		ListTreeIterator<?> listIterator = listTreeGeneral.listIterator();

		System.out.println(" --- ");

		while (listIterator.hasNext()) {
			System.out.println(listIterator.next().getNodeValue().toString().replace("\n", " "));
		}

		System.out.println(" - ");

		while (listIterator.hasPrevious()) {
			System.out.println(listIterator.previous().getNodeValue().toString().replace("\n", " "));
		}

		System.out.println(" --- ");

		listIterator = listTreeGeneral.getChildNode(1).getChildNode(0).listIterator(true);

		while (listIterator.hasNext()) {
			System.out.println(listIterator.next().getNodeValue().toString().replace("\n", " "));
		}

		System.out.println(" - ");

		while (listIterator.hasPrevious()) {
			System.out.println(listIterator.previous().getNodeValue().toString().replace("\n", " "));
		}

		System.out.println(" --- ");

		listIterator = listTreeGeneral.getChildNode(1).getChildNode(0).listIterator(false);

		while (listIterator.hasNext()) {
			System.out.println(listIterator.next().getNodeValue().toString().replace("\n", " "));
		}

		System.out.println(" - ");

		while (listIterator.hasPrevious()) {
			System.out.println(listIterator.previous().getNodeValue().toString().replace("\n", " "));
		}

		System.out.println(" --- ");

		System.out.println(listIterator.next().getNodeValue().toString().replace("\n", " "));
		System.out.println(listIterator.previous().getNodeValue().toString().replace("\n", " "));
		System.out.println(listIterator.next().getNodeValue().toString().replace("\n", " "));
		System.out.println(listIterator.previous().getNodeValue().toString().replace("\n", " "));
		System.out.println(listIterator.next().getNodeValue().toString().replace("\n", " "));
		System.out.println(listIterator.next().getNodeValue().toString().replace("\n", " "));
		System.out.println(listIterator.previous().getNodeValue().toString().replace("\n", " "));
		System.out.println(listIterator.previous().getNodeValue().toString().replace("\n", " "));

		System.out.println(" --- ");


		//		//Add node through iterator
		//		String nodeValue = "Child 1.2.2";
		//
		//		while (iterator.hasNext()) {
		//			KeyListTreeNode<Integer, String> node = iterator.next();
		//			if (node.getNodeValue().equals(nodeValue)) {
		//				iterator.add(node.nodeFactory(0, "Child x"));
		//			}
		//		}
		//
		//		while (iterator.hasPrevious()) {
		//			KeyListTreeNode<Integer, String> node = iterator.previous();
		//			if (node.getNodeValue().equals(nodeValue)) {
		//				iterator.add(node.nodeFactory(0, "Child y"));
		//			}
		//		}


		//		System.out.println("Highest node level: " + TreeUtil.highestNodeLevel(keyListTree));
		//
		//		LeftRightTextTreePrinter<String, KeySetTreeNode<Integer, String>> textKeyPrinterFirstOnRight = new LeftRightTextTreePrinter<>(LeftRightTextPrinterMode.FIRSTCHILDONRIGHT);
		//		System.out.println(textKeyPrinterFirstOnRight.print(keySetTree));
		//
		//		LeftRightTextTreePrinter<String, KeySetTreeNode<Integer, String>> textKeyPrinter = new LeftRightTextTreePrinter<>(LeftRightTextPrinterMode.STANDARD);
		//		System.out.println(textKeyPrinter.print(keySetTree));
		//
		//		DebugTextTreePrinter<String, KeySetTreeNode<Integer, String>> debugKeyPrinter = new DebugTextTreePrinter<>();
		//		System.out.println(debugKeyPrinter.print(keySetTree));
		//
		//		System.out.println(" ");
		//
		//		CSVTreePrinter<String, KeySetTreeNode<Integer, String>> csvKeyPrinter = new CSVTreePrinter<>(LeftRightTextPrinterMode.STANDARD_CONNECTTOFIRST, true, true);
		//		FileUtil.writeStringToFile("/home/thomas/Desktop/familienfest/testprinter.csv", csvKeyPrinter.print(keySetTree));
		//
		//		HTMLTreePrinter<String, KeySetTreeNode<Integer, String>> htmlKeyPrinter = new HTMLTreePrinter<>(LeftRightTextPrinterMode.STANDARD_CONNECTTOFIRST, false, true);
		//		StringBuilder sb = new StringBuilder();
		//		htmlKeyPrinter.appendSimpleHeader(sb, "Test tree");
		//		sb.append(htmlKeyPrinter.print(keySetTree));
		//		htmlKeyPrinter.appendSimpleFooter(sb);
		//		FileUtil.writeStringToFile("/home/thomas/Desktop/familienfest/testprinter.html", sb);



		//========================================================

		TreeNodePlainTextPrinter<KeySetTreeNode<Integer, String>> textPrinter2 = new TreeNodePlainTextPrinter<>();
		System.out.println(textPrinter2.print(keySetTree));

		TreeNodeDebugPrinter<KeySetTreeNode<Integer, String>> debugPrinter2 = new TreeNodeDebugPrinter<>();
		System.out.println(debugPrinter2.print(keySetTree));

		TreeNodeCSVPrinter<KeySetTreeNode<Integer, String>> csvPrinter2 = new TreeNodeCSVPrinter<>(true);
		FileUtil.writeStringToFile("/home/thomas/Desktop/familienfest/testprinter.csv", csvPrinter2.print(keySetTree));

		TreeNodeHTMLPrinter<KeySetTreeNode<Integer, String>> htmlPrinter2 = new TreeNodeHTMLPrinter<>(true, true);
		StringBuilder sb = new StringBuilder();
		htmlPrinter2.appendSimpleHeader(sb, "Test tree");
		sb.append("<table>");
		sb.append(htmlPrinter2.print(keySetTree));
		sb.append("</table>");
		htmlPrinter2.appendSimpleFooter(sb);
		FileUtil.writeStringToFile("/home/thomas/Desktop/familienfest/testprinter.html", sb);

	}


	/**
	 * 
	 * 
	 * @param mapTree
	 */
	private static void fillMapTree(MapTreeNodeInterface<Integer, String, ?> mapTree) {
		//		treeKey.ignoreNode(false);
		mapTree.addChildNode(1, "Child 1\nTest asdfas dfasfdsdfddddd")
		.addChildNode(11, "Child 1.1\nTest1\nTest2\nTest3")
		.getParentNode().addChildNode(12, "Child 1.2\nTest")
		.addChildNode(122, "a Child 1.2.2\nTest")	//122 before 121 to test key sorting. "a" to make sure value sorting does not interfere (key sorting is "stronger")
		.getParentNode().addChildNode(121, "c Child 1.2.1\nTest")	//bcd to test value sorting
		.getParentNode().addChildNode(121, "b Child 1.2.1\nTest")
		.getParentNode().addChildNode(121, "d Child 1.2.1\nTest")
		.getParentNode().getParentNode().addChildNode(13, "Child 1.3\nTest")
		.getParentNode().getParentNode().addChildNode(2, "Child 2\nTest")
		.addChildNode(21, "Child 2.1")
		.getParentNode().addChildNode(22, "Child 2.2")
		.getParentNode().addChildNode(23, "Child 2.3\nTest")
		.getParentNode().getParentNode().addChildNode(3, "Child 3")
		.addChildNode(31, "Child 3.1")
		.getParentNode().addChildNode(32, "Child 3.2")
		.getParentNode().addChildNode(33, "Child 3.3")
		.addChildNode(331, "Child 3.3.1");
	}

	/**
	 * 
	 * 
	 * @param collectionTree
	 */
	private static void fillCollectionTree(CollectionTreeNodeInterface<String, ?> collectionTree) {
		collectionTree.addChildNode("( 1) Child 1")
		.getParentNode().addChildNode("( 2) Child 2")
		.addChildNode("( 3) Child 2.1")
		.addChildNode("( 4) Child 2.1.1")
		.getParentNode().getParentNode().addChildNode("( 5) Child 2.2")
		.getParentNode().addChildNode("( 6) Child 2.3")
		.getParentNode().addChildNode("( 7) Child 2.4")
		.addChildNode("( 8) Child 2.4.1")
		.getParentNode().getParentNode().getParentNode().addChildNode("( 9) Child 3")
		.getParentNode().addChildNode("(10) Child 4")
		.addChildNode("(11) Child 4.1");
	}


	/***************************************************************************
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	private static class TestTreeNodeListener implements TreeNodeListener<ListTreeNode<String>> {

		@Override
		public void nodeRemoved(TreeNodeEvent<ListTreeNode<String>> e) {
			System.out.println("Nodes removed: " + e.getNode().toString());
		}

		@Override
		public void nodeAdded(TreeNodeEvent<ListTreeNode<String>> e) {
			System.out.println("Node added: " + e.getNode().toString());
		}

		@Override
		public void nodeReplaced(TreeNodeEvent<ListTreeNode<String>> e) {
			System.out.println("Node replaced: " + e.getOldNode() + " -> " + e.getNode().toString());
		}

		@Override
		public void nodeValueChanged(TreeNodeEvent<ListTreeNode<String>> e) {
			System.out.println("Node value changed: " + e.getNode().toString());
		}


	}

}

/**
 *    Copyright 2014 Thomas Naeff (github.com/thnaeff)
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

import java.util.ArrayList;
import java.util.List;

import ch.thn.util.object.ObjectTree;
import ch.thn.util.object.ObjectTreeProcessor;
import ch.thn.util.tree.ListTreeNode;
import ch.thn.util.tree.printer.TreeNodePlainTextPrinter;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ObjectTreeTest {



	public static void main(String[] args) {

		//Instead of just using
		//ObjectTree<Object> objectTree = new ObjectTree<>(testPanel);
		//the object tree class is extended so that it creates a custom
		//node variable which then outputs a custom string when printing the tree.
		TestObjectTree objectTree = new TestObjectTree(new TestObject());


		objectTree.registerProcessor(new TestObjectProcessor());
		//		objectTree.registerProcessor(new JComponentProcessor());
		//		objectTree.registerProcessor(new JTextFieldProcessor());


		objectTree.buildTree();

		TreeNodePlainTextPrinter<ListTreeNode<RecorderObject>> treePrinter = new TreeNodePlainTextPrinter<>();
		System.out.println(treePrinter.print(objectTree));

		System.out.println("-----");

		for (ListTreeNode<RecorderObject> node : objectTree) {
			System.out.println(node.getNodeValue().o.getClass().getSimpleName());
		}


	}


	/**
	 * A sample object which contains other objects. Since the object tree class
	 * can not know how to access the inner objects, a processor has to be defined
	 * for this class. See {@link TestObjectProcessor}
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	private static class TestObject {

		protected Object obj1 = null;
		protected Object obj2 = null;

		/**
		 * 
		 */
		public TestObject() {

			obj1 = new Object();
			obj2 = new Object();

		}



	}

	/**
	 * This processor will be used whenever a {@link TestObject} is found.
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	private static class TestObjectProcessor extends ObjectTreeProcessor<TestObject> {

		/**
		 * @param c
		 */
		public TestObjectProcessor() {
			super(TestObject.class);
		}

		@Override
		public List<?> getInternalObjects(TestObject obj) {
			List<Object> objects = new ArrayList<>();

			objects.add(obj.obj1);
			objects.add(obj.obj2);


			return objects;
		}

	}


	/***************************************************************************
	 * 
	 * A recorder with specific tree node values so that the tree printer output
	 * can be formatted
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	public static class TestObjectTree extends ObjectTree<RecorderObject> {

		/**
		 * @param obj
		 */
		public TestObjectTree(Object obj) {
			super(obj);
		}


		@Override
		protected RecorderObject newObject(Object obj) {
			return new RecorderObject(obj);
		}

	}


	/***************************************************************************
	 * 
	 * A tree node class so that the tree printer output can be formatted
	 * 
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	public static class RecorderObject {

		private Object o = null;

		/**
		 * 
		 * 
		 * @param o
		 */
		public RecorderObject(Object o) {
			this.o = o;

		}

		public Object getObject() {
			return o;
		}

		@Override
		public String toString() {
			return o.getClass().getSimpleName();
		}

	}


}

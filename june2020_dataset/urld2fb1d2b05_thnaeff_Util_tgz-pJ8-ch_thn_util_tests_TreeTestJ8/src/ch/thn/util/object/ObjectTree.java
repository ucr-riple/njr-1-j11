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
package ch.thn.util.object;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextField;

import ch.thn.util.ReflectionUtil;
import ch.thn.util.tree.ListTreeNode;
/**
 * Creates an object tree which contains the structure of an object and its child
 * objects.<br />
 * <br />
 * The {@link ObjectTree} is an implementation of the {@link ListTreeNode}. All
 * child nodes are {@link ListTreeNode}s.<br />
 * <br />
 * For each object class, a {@link ObjectTreeProcessor} has to be
 * defined which specifies how the child objects have to be accessed. A {@link JComponent}
 * for example has to access all its child objects through {@link JComponent#getComponents()}
 * (see {@link JComponentProcessor} as example).<br />
 * Another possibility would be to return all global class variables as child objects, or
 * often models (e.g. column model, table model, documents, ...) are objects of
 * interest so for a {@link JTextField} the processor could return the
 * {@link JTextField#getDocument()} so that it is added to the tree.
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <V>
 */
public class ObjectTree<V> extends ListTreeNode<V> {

	private Map<Class<?>, ObjectTreeProcessor<?>> registeredProcessors = null;

	private Object startObject = null;

	/**
	 * 
	 * @param startObject
	 */
	public ObjectTree(Object startObject) {
		super(null);

		this.startObject = startObject;

		registeredProcessors = new LinkedHashMap<Class<?>, ObjectTreeProcessor<?>>();

	}


	/**
	 * 
	 * 
	 * @param processor
	 */
	public void registerProcessor(ObjectTreeProcessor<?> processor) {
		registeredProcessors.put(processor.getInternalClass(), processor);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public void buildTree() {
		buildTree(0);
	}

	/**
	 * 
	 * 
	 * @param maxDepth
	 * @return
	 */
	public void buildTree(int maxDepth) {
		setNodeValue(newObject(startObject));
		buildTree(this, startObject, 0, maxDepth);
	}


	/**
	 * 
	 * 
	 * @param parentNode
	 * @param nextObj
	 * @param depth
	 * @param maxDepth
	 */
	private void buildTree(ListTreeNode<V> parentNode, Object nextObj, int depth, int maxDepth) {

		if (maxDepth > 0 && depth > maxDepth) {
			return;
		}

		List<ObjectTreeProcessor<?>> processors = findProcessors(nextObj);

		for (@SuppressWarnings("rawtypes") ObjectTreeProcessor processor : processors) {

			@SuppressWarnings("unchecked")
			List<Object> nextObjects = processor.getInternalObjects(processor.getInternalClass().cast(nextObj));

			//Follow the objects which have been returned by the processor
			for (Object nextObject : nextObjects) {
				ListTreeNode<V> newNode = parentNode.addChildNode(newObject(nextObject));

				buildTree(newNode, nextObject, depth + 1, maxDepth);
			}
		}



	}

	/**
	 * Called whenever a new object has been found. Can be overwritten to create
	 * the desired node value object.
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected V newObject(Object obj) {
		return (V)obj;
	}


	/**
	 * Looks through the registered processors to find one that matches
	 * the objects class or any of its interfaces or superclasses
	 * 
	 * @param obj
	 * @return
	 */
	private List<ObjectTreeProcessor<?>> findProcessors(Object obj) {
		List<ObjectTreeProcessor<?>> processors = new ArrayList<>();
		Class<?> objCls = obj.getClass();

		//The class of this object
		if (registeredProcessors.containsKey(objCls)) {
			processors.add(registeredProcessors.get(objCls));
		}

		//Look for matching interfaces
		for (Class<?> c : ReflectionUtil.getAllInterfaces(objCls)) {
			if (registeredProcessors.containsKey(c)) {
				processors.add(registeredProcessors.get(c));
			}
		}

		//Look for matching superclasses
		for (Class<?> c : ReflectionUtil.getAllSuperclasses(objCls)) {
			if (registeredProcessors.containsKey(c)) {
				processors.add(registeredProcessors.get(c));
			}
		}

		return processors;
	}




}

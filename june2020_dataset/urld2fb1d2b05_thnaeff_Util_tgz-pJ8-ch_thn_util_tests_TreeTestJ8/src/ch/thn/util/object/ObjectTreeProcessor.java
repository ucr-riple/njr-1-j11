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
import java.util.List;


/**
 * A processor defines how to continue with any child objects of a certain type. <br />
 * <br />
 * For example, a text field has a document which should be used to deal with
 * document events, but that document can only be retrieved with getDocument.
 * Therefore, a {@link JTextFieldProcessor} exists so that the text field
 * itself and also the document are recorded.
 *
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class ObjectTreeProcessor<C extends Object> {

	private Class<C> c = null;

	/**
	 * 
	 * 
	 * @param c
	 */
	public ObjectTreeProcessor(Class<C> c) {
		this.c = c;

	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Class<C> getInternalClass() {
		return c;
	}

	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public abstract List<?> getInternalObjects(C obj);

	/**
	 * Just a little helper method which creates a list with only the given object,
	 * because often there is only one single internal object to be returned.
	 * 
	 * @param obj
	 * @return
	 */
	protected List<Object> createSingleItemList(Object obj) {
		List<Object> l = new ArrayList<>();
		l.add(obj);
		return l;
	}


}

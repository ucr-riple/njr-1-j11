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

import ch.thn.util.file.SingleInstanceLock;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class SingleInstanceLockTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SingleInstanceLock.setApplicationId("singleinstancetest");

		if (SingleInstanceLock.lock()) {
			System.out.println("locked");
		} else {
			System.out.println("already locked");
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Lock is automatically released when application ends

	}

}

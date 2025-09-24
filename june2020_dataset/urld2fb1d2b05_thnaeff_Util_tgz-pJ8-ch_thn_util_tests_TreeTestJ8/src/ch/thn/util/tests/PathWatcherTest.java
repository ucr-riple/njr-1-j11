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

import java.nio.file.Path;

import ch.thn.util.file.PathWatcher;
import ch.thn.util.file.PathWatcher.PathWatcherListener;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class PathWatcherTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PathWatcher w = new PathWatcher(1000);

		String path = "/home/thomas/Projects/java/Util/test";

		w.addPathWatcherListener(new PathWatcherEventListener());

		w.registerPath(path, true, false);

		Thread t = new Thread(w);
		t.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//		//Request pause and wait. Since the path watcher blocks if there is no event
		//		//happening, this pause call will block until the path watcher sees any
		//		//changes. At least one change has to happen for it to go into the runnable
		//		//pause state.
		//		w.pause(true, true);
		//
		//		try {
		//			Thread.sleep(5000);
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}
		//
		//		//Leave pause and block until pause is actually done
		//		w.pause(false, true);
		//
		//		try {
		//			Thread.sleep(10000);
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}

		System.out.println("Time expired. Stopping.");

		//Stops the path watcher service
		w.stop();

		System.out.println("END");
	}


	public static class PathWatcherEventListener implements PathWatcherListener {

		@Override
		public void newPathWatched(Path path) {
			System.out.println("Watching path " + path);
		}

		@Override
		public void pathChanged(Path path, Path context, boolean overflow) {

			System.out.println("CHANGED: " + context);

		}

		@Override
		public void directoryCreated(Path path, Path created) {

			System.out.println("CREATED: " + created);

		}

		@Override
		public void directoryDeleted(Path path, Path deleted) {

			System.out.println("DELETED: " + deleted);
		}

		@Override
		public void directoryModified(Path path, Path modified) {

			System.out.println("MODIFIED: " + modified);

		}

	}


}

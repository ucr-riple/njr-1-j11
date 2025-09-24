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
package ch.thn.util.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import ch.thn.util.thread.ControlledRunnable;

/**
 * An implementation of a {@link WatchService} which periodically checks the registered
 * paths for changes.
 * 
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class PollingWatchService extends ControlledRunnable implements WatchService {

	private long pollTimeout = 0;

	private ConcurrentHashMap<Path, PollingWatchKey> registeredPaths = null;

	/**
	 * A directory and all the files and their last modified timestamps<br />
	 * &lt;Directory, &lt;File path, last modified timestamp&gt;&gt;
	 */
	private ConcurrentHashMap<Path, Map<Path, Long>> lastModified = null;

	/**
	 * A FIFO list of all the watch keys which have events pending
	 */
	private LinkedBlockingQueue<PollingWatchKey> keysWithEvents = null;

	private FilenameFilter fileNameFilter = null;

	/**
	 * 
	 * 
	 * @param pollTimeout The timeout to wait between two file checks.
	 */
	protected PollingWatchService(long pollTimeout) {
		this(pollTimeout, null);
	}

	/**
	 * 
	 * 
	 * @param pollTimeout The timeout to wait between two file checks.
	 * @param fileNameFilter A filter to only check the filtered files. This can
	 * be a big performance improvements when dealing with large and/or many directories
	 */
	protected PollingWatchService(long pollTimeout, FilenameFilter fileNameFilter) {
		super(true, true);

		this.pollTimeout = pollTimeout;
		this.fileNameFilter = fileNameFilter;

		registeredPaths = new ConcurrentHashMap<>();
		lastModified = new ConcurrentHashMap<>();
		keysWithEvents = new LinkedBlockingQueue<PollingWatchKey>();

	}



	@Override
	public void run() {
		runStart();

		while (!isStopRequested()) {
			runReset();
			runPause(false);

			if (isStopRequested()) {
				break;
			}

			if (isPauseRequested() || isResetRequested()) {
				continue;
			}

			lookForChanges();

			controlledWait(pollTimeout);

		}

		registeredPaths.clear();
		lastModified.clear();
		keysWithEvents.clear();

		for (PollingWatchKey key : registeredPaths.values()) {
			key.cancel();
		}

		notifyWaitingTake();

		runEnd();
	}


	/**
	 * 
	 * 
	 */
	private void lookForChanges() {

		//Set<Path> tempPaths = new HashSet<>();

		for (Path directoryPath : registeredPaths.keySet()) {
			//If a registered path gets deleted because it does not exist any more
			//and it is the next path in line, it still gets returned. Check here again
			//if it it still available.
			if (!registeredPaths.containsKey(directoryPath)) {
				continue;
			}

			File directory = directoryPath.toFile();
			PollingWatchKey key = registeredPaths.get(directoryPath);


			//Does registered directory still exist?
			if (!directory.exists()) {
				entryDelete(key, directoryPath);
				continue;
			}

			File[] files = null;
			if (fileNameFilter != null) {
				files = directory.listFiles(fileNameFilter);
			} else {
				files = directory.listFiles();
			}

			if (!lastModified.containsKey(directoryPath)) {
				//It is a new path which has just been added and has not been checked yet.
				//Record all files and their modified timestamp

				Map<Path, Long> filesMap = new ConcurrentHashMap<Path, Long>();
				lastModified.put(directoryPath, filesMap);

				for (File f : files) {
					Path filePath = f.toPath();
					long fileLastModified = f.lastModified();

					filesMap.put(filePath, fileLastModified);
				}

			} else {
				Map<Path, Long> directoryFileMap = lastModified.get(directoryPath);

				//Check all existing files
				for (File f : files) {
					Path filePath = f.toPath();
					long fileLastModified = f.lastModified();

					if (directoryFileMap.containsKey(filePath)) {
						//File has previously been around

						if (filePath.toFile().exists()) {
							//Modified?

							long oldLastModified = directoryFileMap.get(filePath);

							if (oldLastModified < fileLastModified) {
								//New modification date on current file
								directoryFileMap.put(filePath, fileLastModified);
								fileModified(key, filePath, StandardWatchEventKinds.ENTRY_MODIFY);
							}
						}

					} else {
						//File has not been recorded yet and must therefore be new
						directoryFileMap.put(filePath, fileLastModified);
						fileModified(key, filePath, StandardWatchEventKinds.ENTRY_CREATE);
					}
				}

				//Go through the list of recorded files and check if they all still exist
				for (Path path : directoryFileMap.keySet()) {
					File f = path.toFile();
					if (!f.exists()) {
						entryDelete(key, path);
					}
				}

			}
		}

	}

	/**
	 * 
	 * 
	 * @param key
	 * @param path
	 */
	private void entryDelete(PollingWatchKey key, Path path) {

		//Unregister
		registeredPaths.remove(path);

		//Clear last modified records
		lastModified.remove(path);

		//If its parent path is in the list of modified, clear its record
		Path parent = path.getParent();
		//Because the parent path object is a new one, the paths have to be compared one by one
		for (Path p : lastModified.keySet()) {
			if (p.equals(parent)) {
				lastModified.get(p).remove(path);
			}
		}

		fileModified(key, path, StandardWatchEventKinds.ENTRY_DELETE);

	}


	/**
	 * 
	 * 
	 * @param key
	 * @param path
	 */
	private void fileModified(PollingWatchKey key, Path path, Kind<Path> kind) {
		key.addWatchEvent(new PollingWatchEvent(path, kind));
		keysWithEvents.add(key);

		synchronized (keysWithEvents) {
			keysWithEvents.notify();
		}
	}

	/**
	 * 
	 * 
	 */
	private void notifyWaitingTake() {
		synchronized (keysWithEvents) {
			keysWithEvents.notify();
		}

	}


	@Override
	public void close() throws IOException {
		stop();
	}

	@Override
	public WatchKey poll() {
		WatchKey key = keysWithEvents.poll();

		if (isStopped()) {
			throw new ClosedWatchServiceException();
		}

		return key;
	}

	@Override
	public WatchKey poll(long timeout, TimeUnit unit)
			throws InterruptedException {
		WatchKey key = keysWithEvents.poll(timeout, unit);

		if (isStopped()) {
			throw new ClosedWatchServiceException();
		}

		return key;

	}


	@Override
	public WatchKey take() throws InterruptedException {
		synchronized (keysWithEvents) {
			while (keysWithEvents.size() == 0 && !isStopped()) {
				keysWithEvents.wait();
			}
		}

		if (isStopped()) {
			throw new ClosedWatchServiceException();
		}

		//Instead of using keysWithEvents.take() we are using keysWithEvents.poll().
		//The waiting is done with wait() because only like this we are able to
		//notify the waiting in order to stop the thread.

		return keysWithEvents.poll();
	}


	/**
	 * 
	 * 
	 * @param path
	 * @return
	 */
	public PollingWatchKey register(Path path) {
		PollingWatchKey watchKey = new PollingWatchKey();
		registeredPaths.put(path, watchKey);

		reset();

		return watchKey;
	}


	/*************************************************************************
	 * 
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	protected class PollingWatchKey implements WatchKey {

		private LinkedBlockingQueue<WatchEvent<?>> pollEvents = null;

		/**
		 * 
		 */
		public PollingWatchKey() {

			pollEvents = new LinkedBlockingQueue<WatchEvent<?>>();

		}

		/**
		 * 
		 * 
		 * @param watchEvent
		 */
		public synchronized void addWatchEvent(PollingWatchEvent watchEvent) {
			pollEvents.add(watchEvent);
		}

		@Override
		public synchronized void cancel() {
			pollEvents.clear();
		}

		@Override
		public boolean isValid() {
			//TODO
			return true;
		}

		@Override
		public synchronized List<WatchEvent<?>> pollEvents() {
			synchronized (pollEvents) {
				//Create a copy of the poll events because they might get changed
				//while the events are still being processed. Also, only the current
				//events have to be returned.
				LinkedList<WatchEvent<?>> e = new LinkedList<>(pollEvents);
				pollEvents.clear();
				return e;
			}

		}

		@Override
		public synchronized boolean reset() {

			//TODO
			return true;
		}

		@Override
		public Watchable watchable() {
			return null;
		}



	}


	/*************************************************************************
	 * 
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	protected class PollingWatchEvent implements WatchEvent<Path> {

		private Path path = null;
		private Kind<Path> kind = null;


		/**
		 * 
		 */
		public PollingWatchEvent(Path path, Kind<Path> kind) {
			this.path = path;
			this.kind = kind;

		}


		@Override
		public Path context() {
			return path;
		}

		@Override
		public int count() {
			return 0;
		}

		@Override
		public Kind<Path> kind() {
			return kind;
		}

	}


}

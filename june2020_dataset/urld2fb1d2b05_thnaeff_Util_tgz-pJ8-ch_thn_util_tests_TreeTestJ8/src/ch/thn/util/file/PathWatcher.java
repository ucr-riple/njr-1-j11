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
package ch.thn.util.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.thn.util.thread.ControlledRunnable;

/**
 * The path watcher watches one or multiple directories for changes. This can be file or
 * folder changes. Any registered {@link PathWatcherListener} is notified when a change occurs.
 * 
 * 
 * A lot of this code is from: http://docs.oracle.com/javase/tutorial/essential/io/notification.html
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class PathWatcher extends ControlledRunnable {

	private static final Logger logger = LoggerFactory.getLogger(PathWatcher.class);

	private WatchService watcher = null;

	private boolean usePolling = false;

	/**
	 * Currently watched paths and their watch keys to identify them
	 */
	private HashMap<WatchKey, Path> keys = null;

	/**
	 * For each path there is a flag which defines if all its children should be
	 * added to the watch list or not
	 */
	private HashMap<WatchKey, Boolean> allChildren = null;

	private ArrayList<PathWatcherListener> listeners = null;


	/**
	 * A path watcher service
	 * 
	 */
	public PathWatcher() {
		this(0, null);
	}

	/**
	 * A path watcher service with optional polling mode
	 * 
	 * @param pollingTime If no time (=0) is set here, the java file system
	 * watch service is used. If a time is set (>0), the {@link PollingWatchService}
	 * is used.
	 */
	public PathWatcher(long pollingTime) {
		this(pollingTime, null);
	}

	/**
	 * A path watcher service with optional polling mode
	 * 
	 * @param pollingTime If no time (=0) is set here, the java file system
	 * watch service is used. If a time is set (>0), the {@link PollingWatchService}
	 * is used.
	 * @param fileNameFilter A filter for when polling is used to only check the
	 * filtered files. This can be a big performance improvements when dealing
	 * with large and/or many directories
	 */
	public PathWatcher(long pollingTime, FilenameFilter fileNameFilter) {
		super(true, false);

		//Use polling if polling time is set
		usePolling = pollingTime != 0;

		if (usePolling) {
			PollingWatchService w = new PollingWatchService(pollingTime, fileNameFilter);

			Thread t = new Thread(w);
			t.setDaemon(true);
			t.start();
			t.setName(PollingWatchService.class.getSimpleName());

			watcher = w;
		} else {
			try {
				watcher = FileSystems.getDefault().newWatchService();
			} catch (IOException e) {
				throw new PathWatcherError("Failed to construct new watch service", e);
			} catch (UnsupportedOperationException e) {
				throw new PathWatcherError("File system seems not to support file system watching", e);
			}
		}

		keys = new HashMap<>();
		allChildren = new HashMap<>();
		listeners = new ArrayList<>();

	}

	/**
	 * Returns an unmodifiable collection of all paths which are currently being watched
	 * 
	 * @return
	 */
	public Collection<Path> getWatchedPaths() {
		return Collections.unmodifiableCollection(keys.values());
	}

	/**
	 * Adds a {@link PathWatcherListener} which is notified when an event occurs
	 * 
	 * @param l
	 */
	public void addPathWatcherListener(PathWatcherListener l) {
		listeners.add(l);
	}

	/**
	 * Removes a {@link PathWatcherListener}
	 * 
	 * @param l
	 */
	public void removePathWatcherListener(PathWatcherListener l) {
		listeners.remove(l);
	}

	/**
	 * Fires the listener method which matches the current event
	 * 
	 * @param eventKind
	 * @param path
	 * @param context
	 * @param overflow
	 */
	public void firePathWatcherListener(Kind<?> eventKind, Path path, Path context, boolean overflow) {

		for(PathWatcherListener l : listeners) {
			l.pathChanged(path, context, overflow);
		}

		if (eventKind == StandardWatchEventKinds.ENTRY_CREATE) {
			for(PathWatcherListener l : listeners) {
				l.directoryCreated(path, context);
			}
		} else if (eventKind == StandardWatchEventKinds.ENTRY_DELETE) {
			for(PathWatcherListener l : listeners) {
				l.directoryDeleted(path, context);
			}
		} else if (eventKind == StandardWatchEventKinds.ENTRY_MODIFY) {
			for(PathWatcherListener l : listeners) {
				l.directoryModified(path, context);
			}
		}

	}

	/**
	 * 
	 * 
	 * @param path
	 */
	public void fireNewPathWatched(Path path) {

		for(PathWatcherListener l : listeners) {
			l.newPathWatched(path);
		}

	}

	/**
	 * 
	 */
	public void clearAllRegisteredPaths() {
		//Cancel all old keys
		for(WatchKey k : keys.keySet()) {
			k.cancel();
		}

		keys.clear();

	}

	/**
	 * Adds a new path to the list of watched paths. If a path to a file is given, its
	 * parent directory is registered instead because only directories can be watched.
	 * 
	 * 
	 * @param path
	 * @param allChildren If set to <code>true</code>, all child directories are
	 * registered too
	 * @param allParents If set to <code>true</code>, all parent directories are
	 * registered too
	 * @return
	 */
	public boolean registerPath(Path path, boolean allChildren, boolean allParents) {
		File f = path.toFile();

		if (f.isFile()) {
			//The path exists and it is a file -> get its parent directory
			path = path.getParent();
		} else if (!f.exists()) {
			//The path is not a file and does not exist
			//logger.warn("Failed to register " + path + ". Path does not exist.");
			return false;
		}

		if (!allChildren && !allParents) {
			return register(path, false);
		} else {
			if (allChildren) {
				registerAllChildren(path);
			}

			if (allParents) {
				registerAllParents(path);
			}
		}

		return true;
	}

	/**
	 * Adds a new path to the list of watched paths. If a path to a file is given, its
	 * parent directory is registered instead because only directories can be watched.
	 * 
	 * 
	 * @param path
	 * @param allChildren If set to <code>true</code>, all child directories are
	 * registered too
	 * @param allParents If set to <code>true</code>, all parent directories are
	 * registered too
	 * @return
	 */
	public boolean registerPath(String path, boolean allChildren, boolean allParents) {
		return registerPath(Paths.get(path), allChildren, allParents);
	}

	/**
	 * Adds a new path to the list of watched paths.
	 * 
	 * @param path
	 * @return
	 */
	public boolean registerPath(String path) {
		return registerPath(path, false, false);
	}

	/**
	 * Adds a new path to the list of watched paths.
	 * 
	 * @param path
	 * @return
	 */
	public boolean registerPath(Path path) {
		return registerPath(path, false, false);
	}

	/**
	 * Registers the given path for all the events
	 * 
	 * @param dir
	 * @param allChildren
	 * @return
	 * @throws IOException
	 */
	private boolean register(Path dir, boolean allChildren) {

		WatchKey key = null;

		try {
			if (usePolling) {
				key = ((PollingWatchService)watcher).register(dir);
			} else {
				key = dir.register(watcher,
						StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE,
						StandardWatchEventKinds.ENTRY_MODIFY);
			}
		} catch (Exception e) {
			throw new PathWatcherError("Failed to register path " + dir, e);
		}

		//If its the same one it will just be updated
		keys.put(key, dir);
		this.allChildren.put(key, allChildren);

		fireNewPathWatched(dir);

		return true;
	}

	/**
	 * Walks through the file tree and registers all child paths
	 * 
	 * @param path
	 * @throws IOException
	 */
	private void registerAllChildren(Path path) {

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					try {
						register(dir, true);
					} catch (Exception e) {
						logger.warn("Failed to recursively register child path " + dir, e);
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			logger.warn("Failed to recursively register path and children of " + path, e);
		}

	}

	/**
	 * Walks upwards through the file tree and registers all parent paths
	 * 
	 * @param path
	 * @throws IOException
	 */
	private void registerAllParents(Path path) {
		Path p = path;

		while (p != null) {
			try {
				register(p, false);
			} catch (Exception e) {
				logger.warn("Failed to recursively register parent path " + path, e);
			}
			p = p.getParent();
		}

	}


	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>)event;
	}


	@Override
	public void run() {
		runStart();

		while (!isStopRequested()) {
			runPause(false);

			//Check if just stop requested or pausing exited because stop has been requested
			if (isStopRequested()) {
				break;
			}

			WatchKey key = null;

			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				continue;
			} catch (ClosedWatchServiceException e) {
				break;
			}

			//Check if stop has been requested while waiting for a change
			if (isStopRequested()) {
				key.reset();
				break;
			}

			//Check if pause has been requested while waiting for a change
			//If paused, just don't process the keys and go to the beginning for pausing
			if (isPauseRequested()) {
				key.reset();
				continue;
			}

			Path dir = keys.get(key);

			for (WatchEvent<?> event : key.pollEvents()) {
				Kind<?> kind = event.kind();

				//TODO is overflow handled correctly?
				if (event == StandardWatchEventKinds.OVERFLOW) {
					firePathWatcherListener(kind, dir, null, true);
					continue;
				}

				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path child = dir.resolve(name);

				firePathWatcherListener(kind, dir, child, false);

				//Add new directories and their child directories to the watch
				if (allChildren.get(key) && kind == StandardWatchEventKinds.ENTRY_CREATE) {
					if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
						registerAllChildren(child);
					}
				}

			}


			boolean valid = key.reset();
			if (!valid) {
				//Directory not accessible any more -> remove it
				keys.remove(key);
			}

		}

		clearAllRegisteredPaths();

		runEnd();
	}

	/**
	 * 
	 * 
	 */
	private void closeWatcher() {
		try {
			watcher.close();
		} catch (IOException e) {}
	}

	@Override
	public void stop() {
		//Close the watcher to have it exit the waiting watcher.take()
		closeWatcher();
		super.stop();
	}

	@Override
	public void stop(boolean wait) {
		//Close the watcher to have it exit the waiting watcher.take()
		closeWatcher();
		super.stop(wait);
	}




	/**************************************************************************
	 * 
	 * 
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	public class PathWatcherError extends Error {
		private static final long serialVersionUID = -2064090030585897604L;

		/**
		 * 
		 * 
		 * @param msg
		 * @param e
		 */
		public PathWatcherError(String msg, Throwable e) {
			super(msg, e);
		}


	}


	/**************************************************************************
	 * 
	 * 
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	public static interface PathWatcherListener {


		/**
		 * Fired when a new path is added to the list of watched paths
		 * 
		 * @param path
		 */
		public void newPathWatched(Path path);

		/**
		 * Any change in the path, with indicator if an event overflow occurred.<br>
		 * Always gets fired before created, deleted or modified.
		 * 
		 * @param path
		 * @param context The created, deleted or modified path, or <code>null</code> if overflow=<code>true</code>
		 * @param overflow An indicator by the watch service which indicates that events may
		 * have been lost or discarded
		 */
		public void pathChanged(Path path, Path context, boolean overflow);

		/**
		 * Fired when a directory or its content has been created
		 * 
		 * @param path
		 * @param created
		 */
		public void directoryCreated(Path path, Path created);

		/**
		 * Fired when a directory or its content has been deleted
		 * 
		 * @param path
		 * @param deleted
		 */
		public void directoryDeleted(Path path, Path deleted);

		/**
		 * Fired when a directory or its content has been modified
		 * 
		 * @param path
		 * @param modified
		 */
		public void directoryModified(Path path, Path modified);

	}


	public class WK implements WatchKey {

		@Override
		public void cancel() {
		}

		@Override
		public boolean isValid() {
			return false;
		}

		@Override
		public List<WatchEvent<?>> pollEvents() {
			return null;
		}

		@Override
		public boolean reset() {
			return false;
		}

		@Override
		public Watchable watchable() {
			return null;
		}



	}

}

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
package ch.thn.util.thread;


/**
 * An abstract implementation of the {@link ControlledRunnable} which has
 * a prepared run-method for many common thread uses. The run-method contains two
 * loops: one main loop which keeps the thread running until the runnable is stopped,
 * and one sub loop which is executed the number of times given with {@link #go(int, boolean)}.
 * The sub loop also implements pausing, which means the sub loop is paused right
 * after {@link #execute()} if {@link #pause(boolean)} with <code>true</code> is called.<br />
 * <br />
 * {@link #execute()} is called in the sub loop. {@link #execute()} is an abstract method
 * which has to be implemented by the extended class.<br />
 * As for the run method in {@link ControlledRunnable}, the {@link #execute()} method
 * in {@link RepeatingRunnable} can
 * contain any methods like {@link #runStart()}, {@link #runEnd()},
 * {@link #runPause(boolean, boolean)}, {@link #isResetRequested()}, {@link #isPauseRequested()}
 * or {@link #isStopRequested()} etc. to further control the runnable.
 * <br />
 * <br />
 * Here is an abstract simplified example how the run method looks like:
 * <pre>
 * run () {
 * 	main_thread_loop (until stop requested) {
 * 		wait_for_loop_command
 * 
 * 		sub_loop (number of loops) {
 * 
 * 			if (execute()) {
 * 				end_sub_loop
 * 			}
 * 
 * 			do_pause_if_requested();
 * 
 * 		}
 * 
 * 	}
 * 
 * }
 * </pre>
 * After the thread is started, it starts the run method and waits at
 * <code>wait_for_loop_command</code>. When {@link #go(int, boolean)} is called,
 * the thread continues and starts the <code>sub_loop</code> (either infinitely or
 * the number of given loops). The return value of the {@link #execute()} method
 * defines if the looping is done or if it should continue. If {@link #pause(boolean)}
 * is called within the {@link #execute()} method, the thread is paused right after
 * executing. The sub loop is exited if stop or reset are requested. The main loop
 * is only exited if stop is requested (which ends the run method and therefore the
 * thread).<br />
 * Note: If pause/reset/stop has to be controlled more finely, it has to be implemented
 * in the {@link #execute()} method (e.g. with {@link #isResetRequested()}, {@link #isPauseRequested()}
 * and {@link #isStopRequested()}).
 * <br />
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class RepeatingRunnable extends ControlledRunnable {


	private int loops = 0;

	private boolean stopWhenDone = false;

	/**
	 * 
	 * 
	 */
	protected RepeatingRunnable() {
		super(true, true);
	}


	/**
	 * Wakes the thread up from waiting and repeats with the given number of loops.
	 * If stopWhenDone=true, this thread will be ended once all repeats are done.
	 * 
	 * @param loops The number of repeats
	 * @param stopWhenDone Stops the thread when the given number of loops are done.
	 */
	public void go(int loops, boolean stopWhenDone) {
		this.loops = loops;
		this.stopWhenDone = stopWhenDone;

		reset();
		pause(false);

	}

	/**
	 * Wakes the thread up from waiting and repeats with the given number of loops.
	 * 
	 * @param loops The number of repeats
	 */
	public void go(int loops) {
		go(loops, false);
	}

	/**
	 * Wakes the thread up from waiting and repeats infinitely (until reset, pause
	 * or stop is called).
	 * 
	 * @param loops The number of repeats
	 */
	public void go() {
		go(0, false);
	}


	/**
	 * Executes the thread code.
	 * 
	 * @return If execution is done, yes (<code>true</code>) or no (<code>false</code>).
	 * If yes, the sub loop ends and the thread goes into waiting state. If no,
	 * the sub loop continues calling {@link #execute()}.
	 */
	public abstract boolean execute();


	@Override
	public void run() {
		runStart();

		//Main loop. Keeping the thread running
		while (!isStopRequested()) {
			runReset();
			runPause(false);

			if (isStopRequested()) {
				break;
			}

			int currentLoop = 0;

			//Loop repeats. Zero loops means infinite repeats.
			while ((currentLoop < loops || loops == 0)
					&& !isResetRequested() && !isStopRequested()) {


				if (execute()) {
					//Done.
					break;
				}

				if (isPauseRequested()) {
					runPause(true);
				}

				currentLoop++;

			}	// End loop repeats

			if (isStopRequested()
					|| stopWhenDone && !isResetRequested()) {
				break;
			}

			if (!isResetRequested()) {
				//Done executing. Pause at the next runPause
				pause(true);
			}

		}	// End thread main loop

		runEnd();
	}

}

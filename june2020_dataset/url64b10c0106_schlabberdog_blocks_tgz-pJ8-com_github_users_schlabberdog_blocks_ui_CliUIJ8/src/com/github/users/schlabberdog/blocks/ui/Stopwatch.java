package com.github.users.schlabberdog.blocks.ui;

public class Stopwatch {

	private long startTime;
	private long elapsed = 0;
	private boolean running = false;

	public synchronized void start() {
		if(running)
			return;

		startTime = System.currentTimeMillis();
		running = true;
	}

	public synchronized void stop() {
		if(!running)
			return;

		long endTime = System.currentTimeMillis();
		elapsed += (endTime - startTime);
		running = false;
	}

	public synchronized void reset() {
		elapsed = 0;
		running = false;
	}

	public synchronized long getElapsedTime() {
		long val = elapsed;
		if(running) {
			val += (System.currentTimeMillis() - startTime);
		}
		return val;
	}
}

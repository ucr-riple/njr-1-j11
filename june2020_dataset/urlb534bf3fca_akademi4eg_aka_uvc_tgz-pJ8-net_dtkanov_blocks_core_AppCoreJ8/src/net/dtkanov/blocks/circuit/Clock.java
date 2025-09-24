package net.dtkanov.blocks.circuit;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.dtkanov.blocks.logic.AddrPair;

/**
 * Describes clocking device. Changes state with appr. given frequency.
 * @author akademi4eg
 *
 */
public class Clock {
	public static long DEFAULT_FREQUENCY = 3;
	
	/** Frequency in Hertz. */
	private long freq;
	/** Step in nanoseconds. */
	private long step;
	private boolean state;
	/** List of destination nodes. */
	private List<AddrPair> dst = new LinkedList<AddrPair>();
	private boolean alive;
	
	/** Constructs clocking object. */
	public Clock() {
		this(DEFAULT_FREQUENCY);
	}
	
	/**
	 * Constructs clocking object.
	 * @param frequency in Hertz
	 */
	public Clock(long frequency) {
		freq = frequency;
		step = 1000000000/freq;
		state = false;
	}
	
	/**
	 * Adds destination node for this device.
	 * @param sink destination node
	 * @return pointer to this device
	 */
	public Clock addSink(AddrPair sink) {
		dst.add(sink);
		return this;
	}
	
	/** Starts clocking. */
	public void start() {
		alive = true;
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(
				new Runnable() {
					public void run() {
						if (alive) {
							state = !state;
							for (AddrPair sink : dst) {
								sink.getNode().in(sink.getPort(), state);
								sink.getNode().propagate();
							}
						}
					}
				}, 0, step, TimeUnit.NANOSECONDS);
	}
	
	/** Stops clocking after next cycle. */
	public void stop() {
		alive = false;
	}
}

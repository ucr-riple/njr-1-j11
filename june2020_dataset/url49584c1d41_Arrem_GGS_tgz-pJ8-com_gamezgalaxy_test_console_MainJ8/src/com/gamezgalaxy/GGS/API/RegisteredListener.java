/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API;

public class RegisteredListener {
	private Listener listen;
	private Executor executor;
	private Priority priority;
	
	public RegisteredListener(Listener listen, Executor executor, Priority priority) {
		this.executor = executor;
		this.listen = listen;
		this.priority = priority;
	}
	
	public RegisteredListener(Listener listen, Executor executor) {
		this(listen, executor, Priority.Normal);
	}
	
	public Listener getListen() {
		return listen;
	}
	
	public Executor getExecutor() {
		return executor;
	}
	
	public void execute(Event event) throws Exception {
		if (event instanceof Cancelable) {
			if (((Cancelable)event).isCancelled())
				return;
		}
		executor.execute(listen, event);
	}
	
	public Priority getPriority() {
		return priority;
	}

}

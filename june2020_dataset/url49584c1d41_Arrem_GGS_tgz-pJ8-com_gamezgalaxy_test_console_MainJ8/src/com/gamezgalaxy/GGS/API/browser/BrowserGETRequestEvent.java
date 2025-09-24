/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.API.browser;

import com.gamezgalaxy.GGS.API.Cancelable;
import com.gamezgalaxy.GGS.API.EventList;
import com.gamezgalaxy.GGS.iomodel.Browser;

public class BrowserGETRequestEvent extends BrowserEvent implements Cancelable {

	private static EventList events = new EventList();
	
	private boolean _canceled;
	
	private String getRequest;
	
	private String full;
	
	private String response = "";
	
	private boolean ishtml;
	
	public BrowserGETRequestEvent(Browser b) {
		super(b);
	}
	
	public BrowserGETRequestEvent(Browser b, String getRequest, String full) {
		super(b);
		this.getRequest = getRequest;
		this.full = full;
	}

	/**
	 * The GET request the browser sent (This removes the GET at the beginning)
	 * @return The request
	 */
	public String getGETRequest() {
		return getRequest;
	}
	
	/**
	 * Returns the full request the browser sent
	 * This includes the GET at the beginning, the url and the headers
	 * @return The full request
	 */
	public String getFullRequest() {
		return full;
	}
	
	/**
	 * Set the String response that will sent to the browser
	 * HTML can be used here
	 * @param response The response to send
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	
	/**
	 * Weather or not the response is html
	 * You should call this event after setting the response
	 * @param bool If true, then the response is html. If false, then the response is plain/text
	 */
	public void setHTML(boolean bool) {
		this.ishtml = bool;
	}
	
	/**
	 * Weather or not the reponse is html
	 * @return If true, then the response is html. If false, then the response is plain/text
	 */
	public boolean isHTML() {
		return ishtml;
	}
	
	/**
	 * Get the response that will be sent to the browser
	 * @return The response
	 */
	public String getResponse() {
		return response;
	}

	@Override
	public EventList getEvents() {
		return events;
	}
	public static EventList getEventList() {
		return events;
	}

	@Override
	public boolean isCancelled() {
		return _canceled;
	}

	@Override
	public void Cancel(boolean cancel) {
		_canceled = cancel;
	}

}

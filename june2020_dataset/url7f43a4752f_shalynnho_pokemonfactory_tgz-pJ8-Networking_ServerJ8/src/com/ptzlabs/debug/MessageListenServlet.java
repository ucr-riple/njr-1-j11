package com.ptzlabs.debug;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class MessageListenServlet extends HttpServlet {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	resp.setContentType("text/plain");
	if(req.getParameter("start").equals("y")) {
	    Entity console = new Entity("console");
	    
	    String randomID = md5(String.valueOf(new Date().getTime() + Math.random() * 100)).substring(0, 7);
	    
	    console.setProperty("consoleID", randomID);
	    console.setProperty("startTime", new Date());
	    datastore.put(console);
	    
	    resp.getWriter().println(randomID);
	}
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	if(req.getParameter("start") != null && req.getParameter("start").equals("y")) {
	    Entity console = new Entity("console");
	    String randomID = md5(String.valueOf(new Date().getTime() + Math.random() * 100)).substring(0, 7);
	    
	    console.setProperty("consoleID", randomID);
	    console.setProperty("startTime", new Date());
	    datastore.put(console);
	    
	    resp.getWriter().println(randomID);
	} else {
	    Entity message = new Entity("message");
	    message.setProperty("consoleID", req.getParameter("consoleID"));
	    message.setProperty("message", req.getParameter("message"));
	    datastore.put(message);
	    
	    // finding the list of channel tokens to send message to
	    Filter consoleIDFilter = new FilterPredicate("console", FilterOperator.EQUAL, req.getParameter("consoleID"));
	    Query q = new Query("channel").setFilter(consoleIDFilter);
	    PreparedQuery pq = datastore.prepare(q);
	    for(Entity result : pq.asIterable()) {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage((String) result.getProperty("clientID"), req.getParameter("message")));
	    }
	    
	    resp.getWriter().println("");
	}
    }

    public static String md5(String input) {
	String md5 = null;
	if (null == input)
	    return null;
	try {
	    // Create MessageDigest object for MD5
	    MessageDigest digest = MessageDigest.getInstance("MD5");

	    // Update input string in message digest
	    digest.update(input.getBytes(), 0, input.length());

	    // Converts message digest value in base 16 (hex)
	    md5 = new BigInteger(1, digest.digest()).toString(16);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return md5;
    }
}

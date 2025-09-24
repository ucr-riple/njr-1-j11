package com.dhana.broker;

public interface Message extends Cloneable {

	public String getMessage();

	public void setMessage(String message);

	public void setExpiration(long timeStamp);

	public long getExpiration();

	public boolean hasExpired();

	public Message clone(); 
}

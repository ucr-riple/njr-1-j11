package org.amon;

import java.util.ArrayList;

public class AmonBuilder {
	
	private String projectRoot;

	private String url;
	
	private String amonProxyPort;
	
	private String amonProxyHost;
	
    private String amonServerUrl;
    
	private String message;
	
	private String errorMessage;

	private MessageType messageType;
	
	private boolean isException;
	
	private EnvironmentType environmentType;

	private ArrayList<String> backtrace = new ArrayList<String>();
    
    public AmonBuilder(String message){
    	this.message = message;
    	this.isException = false;
    }
    
    public AmonBuilder(String message, MessageType messageType){
    	this.message = message;
    	this.messageType = messageType;
    	this.isException = false;
    }
    
    public AmonBuilder(Throwable throwable){
    	this.isException = true;
    	this.errorMessage = throwable.getMessage();
    	backtrace(throwable);
    }
    
	private void backtrace(Throwable throwable) {
		StackTraceElement[] traceElements = throwable.getStackTrace();
		for(StackTraceElement traceElement: traceElements){
			backtrace.add(traceElement.getClassName()+"."+traceElement.getMethodName()+":"+traceElement.getLineNumber());
		}
		
	}

	protected void setAmonServerUrl(String amonServerUrl){
		this.amonServerUrl = amonServerUrl;
	}
	
	protected void setAmonServerUrl(ProtocolType amonServerProtocal, String amonServerHost, String amonServerPort){
		this.amonServerUrl = amonServerProtocal.toString() +"://"+amonServerHost+":"+amonServerPort+"/";
	}
	
	protected void setAmonServerProxy(String amonProxyHost, String amonProxyPort){
		this.amonProxyHost = amonProxyHost;
		this.amonProxyPort = amonProxyPort;
	}

	protected void serClientDetails(EnvironmentType environmentType, String projectRoot, String clientUrl){
		this.environmentType = environmentType;
		this.projectRoot = projectRoot;
		this.url = clientUrl;
	}
	
	protected void setEnvironmentType(EnvironmentType environmentType) {
		this.environmentType = environmentType;
	}

	protected void setClientUrl(String clientUrl) {
		this.url = clientUrl;
	}

	protected void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
	}

	protected MessageType getMessageType() {
		return messageType;
	}

	protected boolean isException() {
		return isException;
	}

	protected String getMessage() {
		return message;
	}

	protected ArrayList<String> getBacktrace() {
		return backtrace;
	}

	protected String getUrl() {
		String newUrl = "";
		if(url != null && !"".equals(url.trim()))
			newUrl = newUrl+ url;
		if(url != null && !"".equals(url.trim()) && projectRoot != null && !"".equals(projectRoot.trim()))
			newUrl = newUrl + " in ";
		if(projectRoot != null && !"".equals(projectRoot.trim()))
			newUrl = newUrl + projectRoot;
		return newUrl;
	}

	protected String getAmonServerUrl() {
		if(amonServerUrl == null || "".equals(amonServerUrl.trim())){
			amonServerUrl = "http://localhost:2464/";
		}else{
			if(amonServerUrl.charAt(amonServerUrl.length()-1) != '/') amonServerUrl = amonServerUrl + "/";
		}
		amonServerUrl = amonServerUrl + "api/" + (isException ? "exception" : "log");
		
		return amonServerUrl;
	}

	protected String getAmonProxyHost() {
		return amonProxyHost;
	}
    
	protected String getAmonProxyPort() {
		return amonProxyPort;
	}
	
	public Amon getInstance(){
		return new Amon(this);
	}
	
	protected EnvironmentType getEnvironmentType() {
    	if(environmentType == null)
    		environmentType = EnvironmentType.TEST;
		return environmentType;
	}

	protected String getErrorMessage() {
		return errorMessage;
	}
}

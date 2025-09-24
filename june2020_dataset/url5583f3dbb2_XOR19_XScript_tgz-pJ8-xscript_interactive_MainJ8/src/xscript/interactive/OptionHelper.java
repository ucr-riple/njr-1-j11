package xscript.interactive;

import java.io.File;

import javax.script.ScriptEngine;

import xscript.executils.Log;


interface OptionHelper {

	public Log getLog();

	public String getOwnName();

	public String getCommandLine();
	
	public ScriptEngine getScriptEngine();
	
	public void noFileNeeded();

	public boolean addSearchPath(File file);
	
}

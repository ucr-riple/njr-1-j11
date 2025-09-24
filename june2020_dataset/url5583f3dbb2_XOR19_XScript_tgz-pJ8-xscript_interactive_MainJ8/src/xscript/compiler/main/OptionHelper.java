package xscript.compiler.main;

import java.io.File;

import xscript.executils.Log;


interface OptionHelper {

	public Log getLog();

	public String getOwnName();
	
	public String getCommandLine();

	public boolean addSourceDir(File file);

	public boolean setOutputDir(File file);
	
	public void error(String key, Object...args);
	
	public void putOption(String key, String value);
	
}

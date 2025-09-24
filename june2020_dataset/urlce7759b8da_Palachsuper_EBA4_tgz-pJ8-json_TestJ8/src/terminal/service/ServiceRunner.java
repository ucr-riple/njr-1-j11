/**
 * Created with IntelliJ IDEA.
 * User: Palachsuper
 * Date: 21.04.14
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */

package terminal.service;

import terminal.tools.PropTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class ServiceRunner {

	private int i=0; // seconds
	boolean ok = true;
	Properties props;
	String terminalDir;
	File japp;
	File rn;
	File fl;
	File pid;
	String adv;
	
	
	public static void main(String[] args) 
	{
		ServiceRunner sr = new ServiceRunner();
		sr.start();
		try {
			sr.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ServiceRunner()
	{
		props = PropTools.load(PropTools.getPropFile());
		terminalDir = props.getProperty("terminalDir");
		adv = props.getProperty("adv_monitor",""); // монитор для рекламы
		
		japp = new File(terminalDir+"/temp/japp.pid");
		fl = new File(terminalDir+"/temp/start.txt");
		pid = new File(terminalDir+"/temp/socket.pid");
		
		// флаг запуска ServiceRunner
		try 
		{
			rn = new File(terminalDir+"/temp/runner.txt");
			if (!rn.exists())
				rn.createNewFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() 
	{
		//debugLog.log("ServiceRunner.start()");
		
		this.i = 80;
		this.loop();
	}
	
	public void loop() 
	{
		this.ok = true;
		
		while (this.ok)
		{
			// 1 sec.
			try{Thread.sleep(1000L);}catch(Exception e){}
			
			if (!japp.exists())
			{
				this.doRuntime("sh "+ terminalDir+"/autoexec.sh");
				System.exit(0);
			}
			
			if (!rn.exists())
			{
				//debugLog.log("!rn.exists() > System.exit(0)");
				System.exit(0);
			}
			
			if (fl.exists())
			{
				if (pid.exists())
					pid.delete();
				
				// 1 sec.
				try{Thread.sleep(1000L);}catch(Exception e){}
				
				try {					
					this.doRuntime("sh "+ terminalDir+"/run.sh");
		        	fl.delete();
		        	this.ok = false;
		       } catch (Exception e){}
			}
			
			this.i++;
			
			if (this.i>100)
			{
				this.checkBrowserLoading();
				this.i=0;
			}
		}
		
		// 8 sec.
		try{Thread.sleep(10000L);}catch(Exception e){}
		
		restart();
	}
	
	private void checkBrowserLoading()
	{
        // браузер терминала
        if (( (new File("/usr/local/bin/chrome_ps.sh").exists()) && this.getRuntime("/usr/local/bin/chrome_ps.sh").equals("0")))
        {
          	this.doRuntime("sh "+ terminalDir+"/browser.sh");
          	this.ok = false;
        }
        
		// браузер с рекламой
        if (adv.equals("1"))
        {
	    	// 5 sec.
	    	try{Thread.sleep(2000L);}catch(Exception e){}
        	
        	if (( (new File("/usr/local/bin/chromium_ps.sh").exists()) && this.getRuntime("/usr/local/bin/chromium_ps.sh").equals("0")))
        	{     	
        		this.doRuntime("sh "+ terminalDir+"/adv_screen.sh");
        		this.ok = false;
            }
        }
	}
	
	private String getRuntime(String command)
	{
		//debugLog.log(command);
		String result="";
		String s = null;
		try {
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(command).getInputStream()));
			while ((s = stdInput.readLine()) != null) {
				result+=s;
			}
		} catch (IOException e) {
			//debugLog.log("IOException" + e.getStackTrace());
		}
		//debugLog.log("result: " + result);
        return result;
	}
	
	private void doRuntime(String command)
	{
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			//debugLog.log("IOException" + e.getStackTrace());
		}
	}
	
	static public void restart()
	{
		String terminalDir = PropTools.load(PropTools.getPropFile()).getProperty("terminalDir");
		try {
			//debugLog.log("exec(start.sh)");
			Runtime.getRuntime().exec("sh "+ terminalDir+"/start.sh");
			Thread.sleep(2000L);
		} catch (Exception e) {
			
		}  
		
		//debugLog.log("System.exit(0)");
		System.exit(0);
	}

}
/**
 * Created with IntelliJ IDEA.
 * User: Palachsuper
 * Date: 21.04.14
 * Time: 11:24
 * To change this template use File | Settings | File Templates.
 */

package terminal.service;

import terminal.tools.PropTools;

import java.io.*;
import java.util.Date;
import java.util.Properties;


public class StartService {

	public static void main(String[] args) 
	{
        Service.WriteLog("Запуск системы. Дата: "+ new Date());
        try{
            daemonize();
        }
        catch (Exception e){
            Service.WriteLog("Запустить демон не удалось");
        }
        Service.main();
		//System.exit(0);
	}

    static public void daemonize()
    {
        //getPidFile().deleteOnExit();
        System.out.close();
        System.err.close();
    }


}

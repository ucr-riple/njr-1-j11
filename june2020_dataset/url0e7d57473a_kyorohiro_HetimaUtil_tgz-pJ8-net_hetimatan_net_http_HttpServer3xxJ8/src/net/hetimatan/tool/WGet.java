package net.hetimatan.tool;

import java.io.IOException;

import net.hetimatan.net.http.HttpGet;
import net.hetimatan.net.http.HttpGetListener;
import net.hetimatan.util.event.CloseRunnerTask;
import net.hetimatan.util.event.net.KyoroSocketEventRunner;

public class WGet {

	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("java"+WGet.class.getSimpleName()+" http://www.example.com");				
			return;
		}
		System.out.println("start args[0]=" + args[0]);		
		try {
			HttpGet getter = new HttpGet( new HttpGetListener() {
				@Override
				public boolean onReceiveHeader(HttpGet httpGet) throws IOException {return false;}
				@Override
				public boolean onReceiveBody(HttpGet httpGet) throws IOException {
					System.out.println("\n-------@1-------\n:"+new String(httpGet.getHeader())+"\n-------@/1-------\n:");
					System.out.println("\n-------@2-------\n:"+new String(httpGet.getBody())+"\n-------@/1-------\n:");
					return false;
				}
			}).update(args[0]);
			KyoroSocketEventRunner runner = getter.startTask(null, new CloseRunnerTask(null));
			runner.waitByClose(30000);
			runner.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();			
		} finally {
			System.out.println("end");
		}
	}
}

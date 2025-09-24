package net.hetimatan.net.http;

import java.io.IOException;

public interface HttpGetListener {
	boolean onReceiveHeader(HttpGet httpGet) throws IOException ;
	boolean onReceiveBody(HttpGet httpGet) throws IOException ;
	
	public static class NullHttpGetListener implements HttpGetListener {
		@Override
		public boolean onReceiveHeader(HttpGet httpGet) throws IOException {
			return false;
		}

		@Override
		public boolean onReceiveBody(HttpGet httpGet) throws IOException {
			//System.out.println("\n@1\n:"+new String(httpGet.getHeader()));
			//System.out.println("\n@2\n:"+new String(httpGet.getBody()));
			return false;
		}		
	}
}

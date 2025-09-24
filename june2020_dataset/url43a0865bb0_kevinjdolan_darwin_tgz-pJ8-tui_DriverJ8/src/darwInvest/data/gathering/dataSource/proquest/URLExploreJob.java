package darwInvest.data.gathering.dataSource.proquest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import darwin.work.Job;

/**
 * This object abstracts away the parallelism of URL exploration
 * to speed up the data gathering process significantly
 * 
 * @author Kevin Dolan
 */
public class URLExploreJob implements Job  {
	
	@Override
	public Object run(Object parameters, HashMap<String, Object> shared) {
		URLExploreJobInput input = (URLExploreJobInput) parameters;	
		URL exploreurl = input.exploreurl;
		System.out.println("Story@" + (new Date(input.time)) + ":" + exploreurl);
	
		URLExploreJobOutput output = new URLExploreJobOutput();
		output.content = URLExplorer(exploreurl);
		output.time = input.time;
		output.title = input.title;
		return output;
	}
	
	private String URLExplorer(URL exploreurl) {		
		BufferedReader proquestreader = null;
		try {
			proquestreader = new BufferedReader(new InputStreamReader(exploreurl.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scanner s = new Scanner(proquestreader);
		Boolean done = false;
		String contents = null;
		s.useDelimiter("[\n\r]+");
		while(s.hasNext() && !done) {
			String next = s.next();
			if(next.contains("<!--Start FULL TEXT-->")) {
				contents = next.substring(next.indexOf("<!--Start FULL TEXT-->"),next.indexOf("<!--End FULL TEXT-->"));
				done = true;
			}
		}
		if (s != null) {
			s.close();
		}
		if (proquestreader != null) {
			try {
				proquestreader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return contents;
	}
	
}

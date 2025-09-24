package zz.imageweb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zz.spider.DownloadImage;

public class JandanImage extends ImageInterface {
	private String firstUrl = "http://jandan.net/ooxx/page-"; //1111#comments
	private String connUrl = "#comments";
	private int beginIndex = 1114;
	
	@Override
	public void next() {
		if(beginIndex > 14) {
            System.out.println("没有更多了，停止抓取");
            htmlurl = null;
        }
		beginIndex++;
		setURLAndDir();
	}

	@Override
	public void init() {
		setURLAndDir();
		dirName = "0927";
	}
	
	public void setURLAndDir(){
		htmlurl = firstUrl + beginIndex + connUrl;
//		dirName = Integer.toString(beginIndex);
	}
	
	@Override
	public void downImage(String savePath, String contnet) {
		Document doc = Jsoup.parse(contnet);
		Elements elements = doc.select(".row img");
        
        for(Element e : elements) {
            String imgSrc = e.attr("src");
            new Thread(new DownloadImage(imgSrc, savePath)).start();
        }
	}
}

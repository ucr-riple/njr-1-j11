package zz.imageweb;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zz.config.ZZConfig;
import zz.spider.DownloadImage;

public class BaiduImage extends ImageInterface {
	
	private static String tag = ZZConfig.BAIDUTAG2;
	private static String tag1 = ZZConfig.BAIDUTAG;
	private static String tag2 = ZZConfig.BAIDUTAG2;
	private static String column = ZZConfig.BAIDUTAG;
	
	static{
		tag = encoder(tag);
		tag1 = encoder(tag1);
		tag2 = encoder(tag2);
		column = encoder(column);
	}
	
	private static String firstUrl = "http://image.baidu.com/detail/info?fr=channel&tag1="+tag1+"&tag2="+tag2+"&column="+column+"&tag="+tag+"&ie=utf-8&oe=utf-8&word=1&t="; //1111#comments
	
	private int index = 1;
	@Override
	public void init() {
		this.dirName = "baidu//"+ZZConfig.BAIDUTAG+"//"+ZZConfig.BAIDUTAG2;
		setURLAndDir();
	}

	@Override
	public void next() {
		if( index > 10) {
			index = 0;
        }
		index++;
		setURLAndDir();
	}

	@Override
	public void downImage(String savePath, String content) {
		final String baiduSavePath = savePath ;
		try {
			JSONObject jb = new JSONObject(content);
			JSONObject jbdata = jb.getJSONObject("data");
			JSONArray jr = jbdata.getJSONArray("thumbs");
			for (int i = 0; i < jr.length(); i++){
				JSONObject eachJb = jr.getJSONObject(i);
				if (eachJb != null){
					try{
						String imagUrl = eachJb.getString("thumb_url");
						if (imagUrl != null && !"".equals(imagUrl)){
							final String downPicUrl = imagUrl.replaceAll("image/.+/sign=([a-z]|[0-9]){32}", "image/pic/item");
							pool.execute(new DownloadImage(downPicUrl, baiduSavePath));
						}
					}catch (JSONException e) {
						System.out.println("imageUrl is not found.");
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setURLAndDir(){
			htmlurl = firstUrl+ Integer.toString(index) ;
	}
	
	
	@SuppressWarnings("deprecation")
	private static String encoder(String str){
		return URLEncoder.encode(str);
	}
}

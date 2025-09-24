package zz.spider;

import java.io.File;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Strings;

import zz.config.ZZConfig;
import zz.imageweb.BaiduImage;
import zz.imageweb.ImageInterface;

public class Spider  implements Runnable {
	private String preHtml;
     
    private String mSavePath;
     
    public Spider(String savePath) {
        setmSavePath(savePath);
    }
     
	@Override
    public void run() {
        String html ;
         
        ImageInterface url = new BaiduImage();		//ImageInterface接口，自由替换
        for(;;) {
        	url.init();
            try {
            	if (Strings.isEmpty(url.getHtmlURL())){
            		break;
            	}
            	Response resp = Http.get(url.getHtmlURL());
            	if (resp.isOK()) {
            	    try {
                        html = resp.getContent();
                        if(html.equals(preHtml)) {
                            continue;
                        }
                         
                        preHtml = html;
                        
                        File savePathDir = new File(new File(ZZConfig.loaclFileDir).getAbsolutePath() +"\\" + url.getDirName());
                        
                        if (!savePathDir.exists()){
                        	savePathDir.mkdirs();
                        }
                        String savePath = savePathDir.getAbsolutePath();
                        
                        url.downImage(savePath,html);
                            
					} catch (Exception e) {
						continue;
					}
            	}
            	url.next();
            }catch(Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
     

	public void setmSavePath(String mSavePath) {
		this.mSavePath = mSavePath;
	}

	public String getmSavePath() {
		return mSavePath;
	}
}


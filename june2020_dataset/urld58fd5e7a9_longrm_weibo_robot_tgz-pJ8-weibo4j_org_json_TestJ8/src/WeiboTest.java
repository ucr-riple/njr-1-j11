import weibo4j.Weibo;
import weibo4j.WeiboException;


public class WeiboTest {

  public static String myToken = "590cf24b47bcb87be482534a2044712b";
  public static String myTokenSecret = "29760b995c9c7ab36d7362dfe900c3b2";
  
  public static void main(String[] args) {
    System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    
    Weibo weibo = new Weibo();
    weibo.setToken(myToken, myTokenSecret);
    try {
      weibo.getFriendsIDs("");
    } catch (WeiboException e) {
      e.printStackTrace();
    }
  }

}

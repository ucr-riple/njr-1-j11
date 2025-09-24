package com.ufgov.longrm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import weibo4j.http.AccessToken;

public class AccessTokenUtil {

  private static final String file = "accessToken.properties";

  public static Map<String, String> getToken(String screenName) throws IOException {
    Properties properties = new Properties();
    FileInputStream inStream = new FileInputStream(file);
    properties.load(inStream);

    Map<String, String> tokenMap = new HashMap<String, String>();
    tokenMap.put("USER_ID", properties.getProperty(screenName + ".USER_ID"));
    tokenMap.put("TOKEN", properties.getProperty(screenName + ".TOKEN"));
    tokenMap.put("TOKEN_SECRET", properties.getProperty(screenName + ".TOKEN_SECRET"));

    inStream.close();
    return tokenMap;
  }

  public static List<String> getScreenNameList() throws IOException {
    Properties properties = new Properties();
    FileInputStream inStream = new FileInputStream(file);
    properties.load(inStream);

    List<String> screenNameList = new ArrayList<String>();
    for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {
      String property = (String) e.nextElement();
      if (property.indexOf(".USER_ID") == -1)
        continue;
      String screenName = property.substring(0, property.indexOf(".USER_ID"));
      if (!screenNameList.contains(screenName))
        screenNameList.add(screenName);
    }
    return screenNameList;
  }

  public static void save(String screenName, AccessToken accessToken) throws IOException {
    Properties properties = new Properties();
    FileInputStream inStream = new FileInputStream(file);
    properties.load(inStream);

    properties.setProperty(screenName + ".USER_ID", accessToken.getUserId() + "");
    properties.setProperty(screenName + ".TOKEN", accessToken.getToken());
    properties.setProperty(screenName + ".TOKEN_SECRET", accessToken.getTokenSecret());

    OutputStream outStream = new FileOutputStream(file);
    properties.store(outStream, "Update " + screenName + "'s access token!");
    inStream.close();
    outStream.close();
  }

}

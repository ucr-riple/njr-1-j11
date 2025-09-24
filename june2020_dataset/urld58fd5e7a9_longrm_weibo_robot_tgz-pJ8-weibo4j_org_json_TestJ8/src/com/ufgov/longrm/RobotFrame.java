package com.ufgov.longrm;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import weibo4j.Count;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Trend;
import weibo4j.Trends;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class RobotFrame extends JFrame {

  private static final long serialVersionUID = 8360241284738511718L;

  private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private User user;

  private String token;

  private String tokenSecret;

  private Thread robotThread;

  private SystemTray sysTray;// 当前操作系统的托盘对象

  private TrayIcon trayIcon;// 当前对象的托盘

  private String settingFile = "setting.properties";

  private JPanel settingPanel = new JPanel();

  private IntegerField fetchFrequencyTf = new IntegerField(5);
  
  private IntegerField fetchCountTf = new IntegerField(3);

  private IntegerField repostFrequencyTf = new IntegerField(5);

  private IntegerField repostCountTf = new IntegerField(3);

  private IntegerField commentCountTf = new IntegerField(3);
  
  private JTextField remarkTf = new JTextField(30);

  private JButton startButton = new JButton("开始");

  private JButton stopButton = new JButton("停止");

  private JEditorPane displayEp = new JEditorPane();

  private List<Status> myStatuses;

  public RobotFrame(String token, String tokenSecret) {
    this.token = token;
    this.tokenSecret = tokenSecret;
//    vertify();
    initInterface();
//    this.setTitle(user.getScreenName() + " (关" + user.getFriendsCount() + " 粉" + user.getFollowersCount() + " 文" + user.getStatusesCount() + ")");
    this.setSize(640, 400);

    createTrayIcon();
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowIconified(WindowEvent e) {
        addTrayIcon();
      }

      @Override
      public void windowClosing(WindowEvent e) {
        addTrayIcon();
      }
    });
    //    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void vertify() {
    Weibo weibo = new Weibo();
    weibo.setToken(token, tokenSecret);
    try {
      this.user = weibo.verifyCredentials();
    } catch (WeiboException e) {
      e.printStackTrace();
    }
  }

  private void addTrayIcon() {
    try {
      sysTray.add(trayIcon);// 将托盘添加到操作系统的托盘
      setVisible(false);// 使得当前的窗口隐藏
    } catch (AWTException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * 创建系统托盘的对象 步骤: 1,获得当前操作系统的托盘对象 2,创建弹出菜单popupMenu 3,创建托盘图标icon
   * 4,创建系统的托盘对象trayIcon
   */
  public void createTrayIcon() {
    sysTray = SystemTray.getSystemTray();// 获得当前操作系统的托盘对象
    PopupMenu popupMenu = new PopupMenu();// 弹出菜单
    MenuItem showMi = new MenuItem("Show");
    MenuItem exitMi = new MenuItem("Exit");
    popupMenu.add(showMi);
    popupMenu.add(new MenuItem("-"));
    popupMenu.add(exitMi);
    // 为弹出菜单项添加事件
    showMi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(true);
        sysTray.remove(trayIcon);
      }
    });
    exitMi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    trayIcon = new TrayIcon(new ImageIcon("icon.png").getImage(), "新浪微博-机器人", popupMenu);
  }

  private void initInterface() {
    initSettingPanel();

    //    displayEp.setEditable(false);
    displayEp.setContentType("text/rtf");
    //    displayEp.setBackground(Color.LIGHT_GRAY);

    Container container = getContentPane();
    container.setLayout(new BorderLayout());
    container.add(settingPanel, BorderLayout.NORTH);
    container.add(new JScrollPane(displayEp), BorderLayout.CENTER);
  }

  private void initSettingPanel() {
    JPanel panel = new JPanel();
    panel.add(new JLabel("   抓取频率(s)："));
    panel.add(fetchFrequencyTf);
    panel.add(new JLabel("抓取数："));
    panel.add(fetchCountTf);
    panel.add(new JLabel("转发频率(s)："));
    panel.add(repostFrequencyTf);
    panel.add(new JLabel("转发数："));
    panel.add(repostCountTf);
    panel.add(new JLabel("评论数："));
    panel.add(commentCountTf);
    JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    upPanel.add(panel);

    panel = new JPanel();
    panel.add(new JLabel("   说明文本(少于140)："));
    panel.add(remarkTf);
    panel.add(startButton);
    panel.add(stopButton);
    JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    downPanel.add(panel);

    settingPanel.setLayout(new BorderLayout());
    settingPanel.add(upPanel, BorderLayout.NORTH);
    settingPanel.add(downPanel, BorderLayout.SOUTH);

    try {
      Properties properties = new Properties();
      FileInputStream inStream = new FileInputStream(settingFile);
      properties.load(inStream);

      fetchFrequencyTf.setText(properties.getProperty("FETCH_FREQUENCY"));
      fetchCountTf.setText(properties.getProperty("FETCH_COUNT"));
      repostFrequencyTf.setText(properties.getProperty("REPOST_FREQUENCY"));
      repostCountTf.setText(properties.getProperty("REPOST_COUNT"));
      commentCountTf.setText(properties.getProperty("COMMENT_COUNT"));
      remarkTf.setText(properties.getProperty("REMARK"));

      inStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doStart();
      }
    });
    stopButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doStop();
      }
    });
    stopButton.setEnabled(false);
  }

  protected void doStart() {
    if ("".equals(fetchFrequencyTf.getText()) || "".equals(repostFrequencyTf.getText()) || "".equals(repostCountTf.getText()) || "".equals(commentCountTf.getText())) {
      JOptionPane.showMessageDialog(null, "请输入信息！", "设置", JOptionPane.ERROR_MESSAGE);
      return;
    }
    // 保存设置
    try {
      Properties properties = new Properties();
      FileInputStream inStream = new FileInputStream(settingFile);
      properties.load(inStream);

      properties.setProperty("FETCH_FREQUENCY", fetchFrequencyTf.getText());
      properties.setProperty("FETCH_COUNT", fetchCountTf.getText());
      properties.setProperty("REPOST_FREQUENCY", repostFrequencyTf.getText());
      properties.setProperty("REPOST_COUNT", repostCountTf.getText());
      properties.setProperty("COMMENT_COUNT", commentCountTf.getText());
      properties.setProperty("REMARK", remarkTf.getText());

      OutputStream outStream = new FileOutputStream(settingFile);
      properties.store(outStream, "Update setting!");
      inStream.close();
      outStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, e.toString(), "保存设置", JOptionPane.ERROR_MESSAGE);
      return;
    }

    robotThread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (1 == 1) {
          try {
            autoForward();
            try {
              setDocs("------------------------sleeping...(wait to fetch next time)----------------------------\n", Color.blue, false, 12);
              robotThread.sleep(fetchFrequencyTf.getInteger().longValue() * 1000);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            setDocs("\n------------------------------\n" + e.toString() + "\n------------------------------\n\n", Color.red, false, 12);
            try {
              robotThread.sleep(60 * 1000);
            } catch (InterruptedException e2) {
              // TODO Auto-generated catch block
              e2.printStackTrace();
            }
          }
        }
      }
    });

    startButton.setEnabled(false);
    stopButton.setEnabled(true);
    displayEp.setText("");
    robotThread.start();
  }

  private void autoForward() throws WeiboException, InterruptedException {
    Weibo weibo = new Weibo();
    weibo.setToken(token, tokenSecret);
    List<Status> statuses = getHotTrendsStatus(weibo);
    for (Status status : statuses) {
      Status realStatus = status.getRetweeted_status() == null ? status : status.getRetweeted_status();
      weibo.repost(realStatus.getId() + "", remarkTf.getText().equals("") ? "repost by weibo-robot" : remarkTf.getText());
      setDocs(format.format(new Date()) + " ", new Color(100, 100, 0), true, 12);
      setDocs("@" + realStatus.getUser().getName() + "(" + realStatus.getId() + "): ", Color.BLUE, false, 12);
      setDocs(realStatus.getText() + "\n\n");
      int halfTime = repostFrequencyTf.getInteger().intValue() / 2;
      int waitTime = new Random().nextInt(halfTime);
      robotThread.sleep((halfTime + waitTime) * 1000);
    }
  }

  // 获取前20*x条关注用户的微博信息
  private List<Status> getFriendsTimeline(Weibo weibo, int pageCount) throws WeiboException {
    List<Status> statuses = new ArrayList<Status>();
    Paging page;
    for (int i = 1; i <= pageCount; i++) {
      page = new Paging(i);
      statuses.addAll(weibo.getFriendsTimeline(page));
    }
    return statuses;
  }

  // 获取前20*x条当前用户转发的微博信息
  private List<Status> getrepostbyme(Weibo weibo, String userId, int pageCount) throws WeiboException {
    List<Status> statuses = new ArrayList<Status>();
    Paging page;
    for (int i = 1; i <= pageCount; i++) {
      page = new Paging(i);
      statuses.addAll(weibo.getrepostbyme(userId, page));
    }
    return statuses;
  }

  // 获取前20*x条该话题的微博信息
  private List<Status> getTrendStatus(Weibo weibo, String trendName, int pageCount) throws WeiboException {
    List<Status> statuses = new ArrayList<Status>();
    Paging page;
    for (int i = 1; i <= pageCount; i++) {
      page = new Paging(i);
      statuses.addAll(weibo.getTrendStatus(trendName, page));
    }
    return statuses;
  }

  // 获取热门话题的热门微博
  private List<Status> getHotTrendsStatus(Weibo weibo) throws WeiboException {
    List<Status> statuses = new ArrayList<Status>();
    if (myStatuses == null)
      myStatuses = getrepostbyme(weibo, user.getId() + "", 15);

    // 保持缓冲池里只有300个weibo
    if (myStatuses.size() > 300) {
      for (int i = 0; i < 30; i++)
        myStatuses.remove(0);
    }

    setDocs("正在获取热门话题的热门微博......\n", Color.red, false, 12);
    List<Trends> trendsHourly = weibo.getTrendsHourly(0);
    for (Trends trends : trendsHourly) {
      Trend[] trendArray = trends.getTrends();
      int trendCount = fetchCountTf.getInteger().intValue(); // 每次随机取30个热门话题
      Trend[] randomTrends = new Trend[trendCount];
      for (int i = 0; i < trendCount; i++) {
        int index = new Random().nextInt(trendArray.length - i - 1);
        randomTrends[i] = trendArray[index];
        if (index != trendArray.length - i - 1)
          trendArray[index] = trendArray[trendArray.length - i - 1];
      }
      for (Trend trend : randomTrends) {
        setDocs("正在获取【" + trend.getName() + "】的热门微博......\n", Color.red, false, 12);
        List<Status> trendStatuses = getTrendStatus(weibo, trend.getName(), 2);

        // 去掉以前发表过的微博
        List<Status> repeatedStatus = new ArrayList<Status>();
        for (Status status : trendStatuses) {
          if (isRepeated(myStatuses, status))
            repeatedStatus.add(status);
        }
        trendStatuses.removeAll(repeatedStatus);

        // 获取转发和评论数
        Map<String, Count> countMap = getCounts(weibo, trendStatuses);
        Count hotCount = null;
        Iterator<Count> it = countMap.values().iterator();
        while (it.hasNext()) {
          Count count = it.next();
          if (hotCount == null || count.getComments() > hotCount.getComments())
            hotCount = count;
        }
        if (hotCount == null)
          continue;
        if (hotCount.getRt() < repostCountTf.getInteger().longValue() || hotCount.getComments() < commentCountTf.getInteger().longValue())
          continue;
        long statusId = hotCount.getId();
        for (Status status : trendStatuses) {
          Status realStatus = status.getRetweeted_status() == null ? status : status.getRetweeted_status();
          if (realStatus.getId() == statusId) {
            statuses.add(realStatus);
            myStatuses.add(realStatus);
            break;
          }
        }
      }
    }
    setDocs("\n");
    return statuses;
  }

  // 批量统计微博的评论数，转发数
  private Map<String, Count> getCounts(Weibo weibo, List<Status> statuses) throws WeiboException {
    if (statuses.size() == 0)
      return new HashMap<String, Count>();
    StringBuilder ids = new StringBuilder();
    for (Status status : statuses) {
      status = status.getRetweeted_status() == null ? status : status.getRetweeted_status();
      ids.append(status.getId()).append(',');
    }
    ids.deleteCharAt(ids.length() - 1);
    List<Count> counts = weibo.getCounts(ids.toString());
    Map<String, Count> countMap = new HashMap<String, Count>();
    for (Count count : counts)
      countMap.put(count.getId() + "", count);
    return countMap;
  }

  // 判断是否之前转发过
  private boolean isRepeated(List<Status> myStatuses, Status status) {
    for (Status myStatus : myStatuses) {
      Status realStatus = status.getRetweeted_status() == null ? status : status.getRetweeted_status();
      Status myRealStatus = myStatus.getRetweeted_status() == null ? myStatus : myStatus.getRetweeted_status();
      if (getSimilarPercent(realStatus, myRealStatus) >= 80)
        return true;
    }
    return false;
  }

  public static final char[] UNREAL_CHARS = { '\\', '/', ':', '*', '?', '"', '<', '>', '|', '\'', '-', '，', '。', '：', '！', '；', '（', '）', '【', '】',
    '『', '』' };

  // 获取有意义的文本（去掉间隔符）
  private String getRealText(String text) {
    for (char illegalChar : UNREAL_CHARS) {
      text = text.replace(illegalChar, ' ');
    }
    text = text.replaceAll(" ", "");
    return text;
  }

  private int getSimilarPercent(Status status1, Status status2) {
    if (status1.getId() == status2.getId())
      return 200;

    int percent = 0;
    // 发表用户是同一用户，加20分
    if (status1.getUser().getName().equals(status2.getUser().getName()))
      percent += 20;
    // 图片相同，加80分
    if (status1.getBmiddle_pic().length() > 0 && status1.getBmiddle_pic().equals(status2.getBmiddle_pic()))
      percent += 80;

    String text1 = getRealText(status1.getText());
    String text2 = getRealText(status2.getText());
    percent += getSimilarPercent(text1, text2);
    return percent;
  }

  // 获取相似度：100为满分
  private int getSimilarPercent(String text1, String text2) {
    int percent = 0;
    if (text1.length() > 5 && text2.length() > 5) {
      if (text1.equals(text2))
        return 100;
      // 分5段判断
      int splitLen = text1.length() / 5;
      for (int i = 0; i < 5; i++) {
        String text = text1.substring(i * splitLen, (i + 1) * splitLen);
        if (text2.indexOf(text) != -1)
          percent += 20;
      }
    }
    return percent;
  }

  private boolean isRepeated(List<Status> myStatuses, long statusId) {
    for (Status myStatus : myStatuses) {
      Status realStatus = myStatus.getRetweeted_status() == null ? myStatus : myStatus.getRetweeted_status();
      if (statusId == realStatus.getId())
        return true;
    }
    return false;
  }

  protected void doStop() {
    if (robotThread != null && robotThread.isAlive())
      robotThread.stop();
    startButton.setEnabled(true);
    stopButton.setEnabled(false);
  }

  private void setDocs(String str) {
    setDocs(str, Color.black, false, 12);
  }

  private void setDocs(String str, Color col, boolean bold, int fontSize) {
    SimpleAttributeSet attrSet = new SimpleAttributeSet();
    StyleConstants.setForeground(attrSet, col);
    if (bold == true) {
      StyleConstants.setBold(attrSet, true);
    }
    StyleConstants.setFontSize(attrSet, fontSize);
    Document doc = displayEp.getDocument();
    try {
      doc.insertString(doc.getLength(), str, attrSet);
      displayEp.setCaretPosition(doc.getLength());
    } catch (BadLocationException e) {
      System.out.println("BadLocationException: " + e);
    }
  }
  
  public static void main(String[] args) {
	new RobotFrame(null, null).setVisible(true);
}

}

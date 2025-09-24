package com.ufgov.longrm;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;
import weibo4j.util.BareBonesBrowserLaunch;

public class LoginFrame extends JFrame {

  private static final long serialVersionUID = -4165192862088896838L;

  private JComboBox screenCb = new JComboBox();

  private JButton loginButton = new JButton("登陆");

  private JTextField pinTf = new JTextField(10);

  private JButton oAuthButton = new JButton("授权");
  
  private RequestToken requestToken;
  
  public LoginFrame() {
    initSysEnv();
    initInterface();
    this.setTitle("新浪微博");
    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  private void initSysEnv() {
    System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
  }

  private void initInterface() {
    try {
      List<String> screenList = AccessTokenUtil.getScreenNameList();
      for (String screenName : screenList)
        screenCb.addItem(screenName);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, e.toString(), "初始化", JOptionPane.ERROR_MESSAGE);
    }
    screenCb.setPreferredSize(new Dimension(114, 20));
    
    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String screenName = (String) screenCb.getSelectedItem();
        try {
          Map<String, String> tokenMap = AccessTokenUtil.getToken(screenName);
          login(tokenMap.get("TOKEN"), tokenMap.get("TOKEN_SECRET"));
        } catch (IOException e1) {
          e1.printStackTrace();
          JOptionPane.showMessageDialog(null, "获取用户ACCESS_TOKEN失败，建议使用授权登录！", "登录", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    
    JPanel loginPanel = new JPanel();
    loginPanel.add(new JLabel("请选择帐号："));
    loginPanel.add(screenCb);
    loginPanel.add(loginButton);
    loginPanel.setBorder(new TitledBorder("直接登录"));

    JLabel label = new JLabel("<html><color='blue'><b>获取授权码：</b></color></html>");
    label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    label.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Weibo weibo = new Weibo();
        try {
          requestToken = weibo.getOAuthRequestToken();
          BareBonesBrowserLaunch.openURL(requestToken.getAuthorizationURL());
        } catch (WeiboException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          JOptionPane.showMessageDialog(null, e1.toString(), "获取授权码", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    
    oAuthButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AccessToken accessToken;
        try {
          accessToken = requestToken.getAccessToken(pinTf.getText());
        } catch (WeiboException te) {
          te.printStackTrace();
          if (401 == te.getStatusCode()) {
            JOptionPane.showMessageDialog(null, "无法获取ACCESS_TOKEN！", "授权登录", JOptionPane.ERROR_MESSAGE);
          } else {
            JOptionPane.showMessageDialog(null, te.toString(), "授权登录", JOptionPane.ERROR_MESSAGE);
          }
          return;
        }
        try {
          Weibo weibo = new Weibo();
          weibo.setToken(accessToken);
          User user = weibo.verifyCredentials();
          AccessTokenUtil.save(user.getScreenName(), accessToken);
          login(accessToken.getToken(), accessToken.getTokenSecret());
        } catch (Exception e1) {
          e1.printStackTrace();
          JOptionPane.showMessageDialog(null, e1.toString(), "用户验证", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    
    JPanel oAuthPanel = new JPanel();
    oAuthPanel.add(label);
    oAuthPanel.add(pinTf);
    oAuthPanel.add(oAuthButton);
    oAuthPanel.setBorder(new TitledBorder("授权登录"));

    this.setLayout(new BorderLayout(20, 5));
    this.add(loginPanel, BorderLayout.CENTER);
    this.add(oAuthPanel, BorderLayout.SOUTH);
  }
  
  private void login(String token, String tokenSecret) {
    this.dispose();
    RobotFrame robot = new RobotFrame(token, tokenSecret);
    robot.setLocationRelativeTo(null);
    robot.setVisible(true);
  }

  public static void main(String[] args) {
    LoginFrame loginFrame = new LoginFrame();
    loginFrame.setLocationRelativeTo(null);
    loginFrame.setVisible(true);
  }

}
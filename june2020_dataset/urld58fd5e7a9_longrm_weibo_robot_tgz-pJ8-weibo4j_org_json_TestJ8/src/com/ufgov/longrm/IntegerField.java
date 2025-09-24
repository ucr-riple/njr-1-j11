package com.ufgov.longrm;

import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntegerField extends JTextField {

  private static final long serialVersionUID = -232160282343867947L;

  public IntegerField() {
    this.setDocument(new IntDoc());
    this.setHorizontalAlignment(JTextField.RIGHT);
  }

  public IntegerField(int col) {
    this(col, JTextField.RIGHT);
  }
  
  public IntegerField(int col, int alignment) {
    this.setDocument(new IntDoc());
    this.setColumns(col);
    this.setHorizontalAlignment(JTextField.RIGHT);
  }
  

  public IntegerField(boolean minusAllow) {
    this(minusAllow, JTextField.RIGHT);
  }
  
  public IntegerField(boolean minusAllow, int alignment) {
    this.setDocument(new IntDoc(minusAllow));
    this.setHorizontalAlignment(JTextField.RIGHT);
  }

  public void setInteger(Integer i) {
    if (i == null) {
      super.setText("");
    } else {
      super.setText(i.toString());
    }

  }

  public Integer getInteger() {
    String s = this.getText();
    if (s != null && !"".equals(s.trim())) {
      return new Integer(s);
    }else{
      return null;
    }
  }

}

class IntDoc extends PlainDocument {

  private static final long serialVersionUID = 5804526634140173207L;

  private String regex;

  boolean minusAllow = false;

  private Toolkit toolkit = Toolkit.getDefaultToolkit();

  public IntDoc() {
    super();
    init();
  }

  public IntDoc(boolean minusAllow) {
    super();
    this.minusAllow = minusAllow;
    init();

  }

  private void init() {
    regex = "[0-9]*";
    if (minusAllow) {
      regex = "-?" + regex;
    }
  }

  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException,
    NumberFormatException {
    if (str == null) {
      return;
    }
    StringBuffer oldStr = new StringBuffer(this.getText(0, this.getLength()));

    StringBuffer newStr = oldStr.insert(offs, str);
    if (this.regex != null && !this.regex.trim().equals("")) {
      if (!newStr.toString().matches(this.regex)) {
        toolkit.beep();
        return;
      }
    }
    String[] parts = oldStr.toString().split("\\.");
    if (parts[0].length() > 15) {
      toolkit.beep();
      return;
    }
    super.insertString(offs, str, a);
  }
}

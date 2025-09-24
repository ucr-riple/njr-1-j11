package edu.services.docs;

/**
 * Created by yurii.pyvovarenko on 05.03.14.
 *
 * Abstract superclass for Documents and Emails classes
 */
public abstract class Text {
    String text;
    boolean isFinalized = false;

    public abstract String getText();

    public abstract void setText(String text);

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean isFinalized) {
        this.isFinalized = isFinalized;
    }
}

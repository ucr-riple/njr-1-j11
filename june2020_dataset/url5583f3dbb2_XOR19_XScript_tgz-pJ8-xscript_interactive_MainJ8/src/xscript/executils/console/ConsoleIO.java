package xscript.executils.console;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;

public class ConsoleIO {

	private static final ThreadLocal<Boolean> IS_WRITING = new ThreadLocal<Boolean>();

	JTextPane textPane;

	private String input = "";

	private String inputRet;

	private int inputOffset;

	private CountDownLatch cdl;

	private LinkedList<CountDownLatch> waitings = new LinkedList<CountDownLatch>();

	private AttributeSet attrs;
	
	public ConsoleIO(AttributeSet attrs){
		this.attrs = attrs;
	}
	
	public boolean doWrite() {
		Boolean b = IS_WRITING.get();
		return b == null ? false : b.booleanValue();
	}

	public boolean expectInput() {
		return !waitings.isEmpty();
	}

	public int getInputOffset() {
		return inputOffset;
	}

	public void append(String text, AttributeSet attrs) {
		boolean isWrite = doWrite();
		if(!isWrite){
			IS_WRITING.set(true);
		}
		try {
			Document doc = textPane.getDocument();
			int start = textPane.getCaretPosition();
			int end = textPane.getCaret().getMark();
			int ipo = inputOffset;
			doc.insertString(inputOffset, text, attrs);
			boolean swap = start>end;
			if(swap){
				int tmp = end;
				end = start;
				start = tmp;
			}
			if(start<ipo && end>ipo){
				textPane.setCaretPosition(text.length()+(swap?end:ipo));
				textPane.moveCaretPosition(text.length()+(swap?ipo:end));
			}else if(start>=ipo && end>=ipo){
				textPane.setCaretPosition(text.length()+(swap?end:start));
				textPane.moveCaretPosition(text.length()+(swap?start:end));
			}
		} catch (Throwable e) {}
		if(!isWrite){
			IS_WRITING.remove();
		}
	}

	public void nonInput(int length) {
		inputOffset += length;
	}

	public void inputreplace(int offset, int length, String text) {
		input = input.substring(0, offset) + text
				+ input.substring(offset + length);
		int i = this.input.indexOf('\n');
		if (i == -1)
			return;
		if (cdl != null) {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		cdl = new CountDownLatch(1);
		inputRet = this.input.substring(0, i + 1);
		this.input = this.input.substring(i + 1);
		inputOffset += i + 1;
		synchronized(waitings){
			waitings.removeFirst().countDown();
		}
	}

	public String readInput() {
		CountDownLatch cdl = new CountDownLatch(1);
		synchronized(waitings){
			waitings.addLast(cdl);
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {
			synchronized(waitings){
				if(!waitings.remove(cdl)){
					this.cdl.countDown();
					this.cdl = null;
				}
			}
			throw new RuntimeException(e);
		}
		String input = inputRet;
		inputRet = null;
		this.cdl.countDown();
		this.cdl = null;
		return input;
	}

	public AttributeSet getInputAttrs(AttributeSet attrs) {
		return this.attrs.copyAttributes();
	}

}

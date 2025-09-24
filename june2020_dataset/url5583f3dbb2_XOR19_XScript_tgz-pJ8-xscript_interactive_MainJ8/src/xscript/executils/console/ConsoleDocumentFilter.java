package xscript.executils.console;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ConsoleDocumentFilter extends DocumentFilter {

	private ConsoleIO io;
	
	public ConsoleDocumentFilter(ConsoleIO io){
		this.io = io;
	}
	
	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		if(io.doWrite()){
			fb.remove(offset, length);
			return;
		}
		if(!io.expectInput())
			return;
		int inputOffset = io.getInputOffset();
		if(offset<inputOffset){
			length += offset-inputOffset;
			if(length>0){
				fb.remove(inputOffset, length);
				io.inputreplace(0, length, "");
			}
		}else{
			fb.remove(offset, length);
			io.inputreplace(offset-inputOffset, length, "");
		}
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		int inputOffset = io.getInputOffset();
		if(io.doWrite()){
			if(offset>inputOffset){
				offset = inputOffset;
			}
			fb.insertString(offset, string, attr);
			io.nonInput(string.length());
		}else if(io.expectInput()){
			if(offset<inputOffset){
				offset = inputOffset;
			}
			fb.insertString(offset, string, io.getInputAttrs(attr));
			io.inputreplace(offset-inputOffset, 0, string);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		int inputOffset = io.getInputOffset();
		if(io.doWrite()){
			if(offset>=inputOffset){
				return;
			}
			if(offset+length>inputOffset){
				length = inputOffset-offset;
			}
			io.nonInput(text.length()-length);
			fb.replace(offset, length, text, attrs);
		}else if(io.expectInput()){
			if(offset<inputOffset){
				length += offset-inputOffset;
				offset = inputOffset;
			}
			if(length<0)
				length=0;
			fb.replace(offset, length, text, io.getInputAttrs(attrs));
			io.inputreplace(offset-inputOffset, length, text);
		}
	}
	
}

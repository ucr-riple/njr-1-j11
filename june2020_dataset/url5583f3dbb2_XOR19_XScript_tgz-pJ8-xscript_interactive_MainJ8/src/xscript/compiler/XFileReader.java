package xscript.compiler;

import java.io.IOException;
import java.io.Reader;

public class XFileReader extends Reader{

	private XPosition position;
	private Reader reader;
	private boolean newLine;
	
	public XFileReader(String source, Reader reader){
		this.reader = reader;
		position = new XPosition(source);
	}
	
	public XPosition getPosition(){
		return position.clone();
	}

	@Override
	public int read(){
		int c;
		try{
			c = (int) reader.read();
		}catch(IOException e){
			c=-1;
		}
		if(c==-1){
			return -1;
		}
		if(position.pos==XDiagnostic.NOPOS){
			position.pos = 0;
			position.column = 1;
			position.line = 1;
		}else{
			if(newLine){
				position.column=1;
				position.line++;
			}else{
				position.column++;
			}
			position.pos++;
			newLine = c=='\n';
		}
		return c;
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		for(int i=0; i<len; i++){
			int c = read();
			if(c==-1){
				return i;
			}
			cbuf[off+i] = (char)c;
		}
		return len;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}
	
}

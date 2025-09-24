package xscript.executils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class MultiplexOutputStream extends OutputStream{

	private List<OutputStream> ouputStreams = new LinkedList<OutputStream>();
	
	public void addOutputStream(OutputStream ouputStream){
		if(!ouputStreams.contains(ouputStream)){
			ouputStreams.add(ouputStream);
		}
	}
	
	public void removeOutputStream(OutputStream ouputStream){
		ouputStreams.remove(ouputStream);
	}
	
	@Override
	public void write(int b) throws IOException {
		IOException exc = null;
		for(OutputStream ouputStream:ouputStreams){
			try{
				ouputStream.write(b);
			}catch(IOException e){
				if(exc==null)
					exc = e;
			}
		}
		if(exc!=null)
			throw exc;
	}

	@Override
	public void write(byte[] b) throws IOException {
		IOException exc = null;
		for(OutputStream ouputStream:ouputStreams){
			try{
				ouputStream.write(b);
			}catch(IOException e){
				if(exc==null)
					exc = e;
			}
		}
		if(exc!=null)
			throw exc;
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		IOException exc = null;
		for(OutputStream ouputStream:ouputStreams){
			try{
				ouputStream.write(b, off, len);
			}catch(IOException e){
				if(exc==null)
					exc = e;
			}
		}
		if(exc!=null)
			throw exc;
	}

	@Override
	public void flush() throws IOException {
		IOException exc = null;
		for(OutputStream ouputStream:ouputStreams){
			try{
				ouputStream.flush();
			}catch(IOException e){
				if(exc==null)
					exc = e;
			}
		}
		if(exc!=null)
			throw exc;
	}

	@Override
	public void close() throws IOException {
		IOException exc = null;
		for(OutputStream ouputStream:ouputStreams){
			try{
				ouputStream.close();
			}catch(IOException e){
				if(exc==null)
					exc = e;
			}
		}
		if(exc!=null)
			throw exc;
	}
	
	

}

package xscript.compiler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XDataOutput implements DataOutput {

	private ByteArrayOutputStream baos;
	
	private List<Integer> ints = new ArrayList<Integer>();
	
	private List<Long> longs = new ArrayList<Long>();
	
	private List<Float> floats = new ArrayList<Float>();
	
	private List<Double> doubles = new ArrayList<Double>();
	
	private List<String> strings = new ArrayList<String>();
	
	private List<XCodeGen> codes = new ArrayList<XCodeGen>();
	
	private List<byte[]> bytes = new ArrayList<byte[]>();
	
	@Override
	public void write(int b) {
		writeByte(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		baos.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) {
		baos.write(b, off, len);
	}

	@Override
	public void writeBoolean(boolean b) {
		writeByte(b?0xFF:0);
	}

	@Override
	public void writeByte(int b) {
		if(b<Byte.MIN_VALUE || b>0xFF)
			throw new AssertionError();
		baos.write(b);
	}

	@Override
	public void writeBytes(String b) throws IOException {
		write(b.getBytes());
	}

	@Override
	public void writeChar(int c) {
		writeShort(c);
	}

	@Override
	public void writeChars(String s) {
		writeUTF(s);
	}

	@Override
	public void writeDouble(double d) {
		int id = doubles.indexOf(d);
		if(id==-1){
			id = doubles.size();
			doubles.add(d);
		}
		if(id>=65535){
			throw new IllegalStateException();
		}
		writeShort(id);
	}

	@Override
	public void writeFloat(float f) {
		int id = floats.indexOf(f);
		if(id==-1){
			id = floats.size();
			floats.add(f);
		}
		if(id>=65535){
			throw new IllegalStateException();
		}
		writeShort(id);
	}

	@Override
	public void writeInt(int i) {
		int id = ints.indexOf(i);
		if(id==-1){
			id = ints.size();
			ints.add(i);
		}
		if(id>=65535){
			throw new IllegalStateException();
		}
		writeShort(id);
	}

	@Override
	public void writeLong(long l) {
		int id = longs.indexOf(l);
		if(id==-1){
			id = longs.size();
			longs.add(l);
		}
		if(id>=65535){
			throw new IllegalStateException();
		}
		writeShort(id);
	}

	@Override
	public void writeShort(int s) {
		if(s<Short.MIN_VALUE || s>0xFFFF)
			throw new AssertionError();
		write((s>>>8)&0xFF);
		write(s&0xFF);
	}

	@Override
	public void writeUTF(String s) {
		int id = strings.indexOf(s);
		if(id==-1){
			id = strings.size();
			strings.add(s);
		}
		if(id>=65535){
			throw new IllegalStateException();
		}
		writeShort(id);
	}

	public byte[] toByteArray(XCodeGen codeGen, XCompilerOptions options){
		codes.add(codeGen);
		for(int i=0; i<codes.size(); i++){
			XCodeGen code = codes.get(i);
			baos = new ByteArrayOutputStream();
			code.getCode(this, options);
			bytes.add(baos.toByteArray());
		}
		baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try{
			dos.writeShort(ints.size());
			for(Integer i:ints){
				dos.writeInt(i);
			}
			dos.writeShort(longs.size());
			for(Long l:longs){
				dos.writeLong(l);
			}
			dos.writeShort(floats.size());
			for(Float f:floats){
				dos.writeFloat(f);
			}
			dos.writeShort(doubles.size());
			for(Double d:doubles){
				dos.writeDouble(d);
			}
			dos.writeShort(strings.size());
			for(String s:strings){
				dos.writeUTF(s);
			}
			dos.writeShort(bytes.size());
			for(byte[] b:bytes){
				if(b.length>=65535)
					throw new IllegalStateException();
				dos.writeShort(b.length);
				dos.write(b);
			}
		}catch(IOException e){
			throw new AssertionError();
		}
		return baos.toByteArray();
	}

	public void writeCode(XCodeGen codeGen) {
		if(codes.size()>=65535)
			throw new IllegalStateException();
		writeShort(codes.size());
		codes.add(codeGen);
	}
	
}

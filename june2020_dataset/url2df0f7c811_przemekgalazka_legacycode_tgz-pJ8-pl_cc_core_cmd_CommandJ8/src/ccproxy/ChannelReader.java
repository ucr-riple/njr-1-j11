package ccproxy;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class ChannelReader {

	LinkedList<String> listToRead;
	ByteBuffer b;
	String partial;
	SocketChannel channel;
	Charset charset;

	public ChannelReader(SocketChannel channel) {
		listToRead = new LinkedList<String>();
		partial = "";
		b = ByteBuffer.allocateDirect(5000);
		this.channel = channel;
		this.charset = Charset.forName("UTF-8");
	}

	public String readLine() throws IOException{

		String[] sArr = partial.split("\r\n",2);
		String ret = null;
		if (sArr.length>1){		
			ret = sArr[0];
			partial = sArr[1];
			return ret;
		}

		b.clear();
		//System.out.println("READ");
		int numread=channel.read(b);
		if (numread<0) throw new IOException("Socket closed");
		b.flip();
		String bufdecode=charset.decode(b).toString();
		//System.out.println(">"+partial+"<");
		//System.out.println(">>"+bufdecode+"<<");
		partial = partial + bufdecode;

		sArr = partial.split("\r\n",2);
		if (sArr.length>1){		
			ret = sArr[0];
			partial = sArr[1];
		}

		return ret;
	}

	public void close() {
		try {
			channel.socket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

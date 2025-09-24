import org.zeromq.ZMQ;

/**
 * ZMQ Hello World Client/Server
 * @author sinbad
 *
 */
public class Client {

	public static void main(String arg[]) {
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket request = context.socket(ZMQ.REQ);
		request.connect("tcp://localhost:5556");

		String hello = "Hello";
		System.out.println("Requesting....." + hello);
		request.send(hello.getBytes(), 0);

		// System.out.println("Responding....");
		byte[] reply = request.recv(0);
		System.out.println("Server Responding...." + new String(reply));

		request.close();
		context.term();
	}
}

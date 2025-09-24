import org.zeromq.ZMQ;
/**
 * ZMQ Hello World Client/Server
 * @author sinbad
 *
 */
public class Server {

	public static void main(String arg[]) {
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket respond = context.socket(ZMQ.REP);
		respond.bind("tcp://*:5556");
		while (!Thread.currentThread().isInterrupted()) {

			byte[] request = respond.recv(0);
			System.out.println("Request:" + new String(request));

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String world = "World";
			respond.send(world.getBytes(), 0);
			System.out.println("Response:" + world);

		}

		respond.close();
		context.term();

	}
}

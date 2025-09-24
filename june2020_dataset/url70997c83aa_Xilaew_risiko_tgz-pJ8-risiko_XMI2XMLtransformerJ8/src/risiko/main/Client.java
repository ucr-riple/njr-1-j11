package risiko.main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import risiko.GameMonitor;
import risiko.actions.AddPlayer;
import risiko.actions.actionFactory;
import risiko.gamestate.Player;
import risiko.gamestate.stateFactory;

public class Client implements Runnable {
	private GameMonitor game = new GameMonitor();
	SocketChannel s;
	private ByteBuffer buf = ByteBuffer.allocate(256);

	public static void main(String[] args) {
		new Thread(new Client()).start();
	}

	public void run() {
		try {
			s = SocketChannel.open(new InetSocketAddress("localhost", 10523));

			read();

			game.getBoard(System.out);
			System.out.println();
			game.getState(System.out);

			// Add two new Players
			AddPlayer ap1 = actionFactory.eINSTANCE.createAddPlayer();
			Player p = stateFactory.eINSTANCE.createPlayer();
			p.setName("xilaew");
			ap1.getPlayers().add(p);
			p = stateFactory.eINSTANCE.createPlayer();
			AddPlayer ap2 = actionFactory.eINSTANCE.createAddPlayer();
			p = stateFactory.eINSTANCE.createPlayer();
			p.setName("newPlayer");
			ap2.getPlayers().add(p);
			EList<EObject> actions = game.getActionResource().getContents();
			actions.clear();
			actions.add(ap1);
			s.configureBlocking(true);
			ap1.eResource().save(s.socket().getOutputStream(), null);
			s.socket().getOutputStream().write('\0');
			s.socket().getOutputStream().flush();

			read();

			System.out.println();
			game.getState(System.out);

			actions.clear();
			actions.add(ap2);
			s.configureBlocking(true);
			ap2.eResource().save(s.socket().getOutputStream(), null);
			s.socket().getOutputStream().write('\0');
			s.socket().getOutputStream().flush();

			read();

			System.out.println();
			game.getState(System.out);

			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void read() throws IOException {
		s.configureBlocking(true);
		StringBuilder sb = new StringBuilder();
		while ((s.read(buf)) > 0) {
			s.configureBlocking(false);
			buf.flip();
			byte[] bytes = new byte[buf.limit()];
			buf.get(bytes);
			sb.append(new String(bytes));
			buf.clear();
		}
		String msg = sb.toString();
		System.out.println(msg);
		int i;
		String current;
		while ((i = msg.indexOf('\0')) >= 0) {
			current = msg.substring(0, i);
			msg = msg.substring(i + 1);
			try {
				game.parseAndHandle(new ByteArrayInputStream(current.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

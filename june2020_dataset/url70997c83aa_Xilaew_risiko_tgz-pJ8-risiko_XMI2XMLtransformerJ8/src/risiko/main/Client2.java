package risiko.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import risiko.GameMonitor;
import risiko.actions.AddPlayer;
import risiko.actions.StartGame;
import risiko.actions.actionFactory;
import risiko.gamestate.Player;
import risiko.gamestate.stateFactory;

public class Client2 implements Runnable {
	private Selector selector;
	SocketChannel s;
	private ByteBuffer buf = ByteBuffer.allocate(256);
	private GameMonitor game = new GameMonitor();
	private static final int PORT = 10523;
	private boolean sent = false;
	private boolean started = false;

	public static void main(String[] args) {
		SocketAddress addr = new InetSocketAddress("localhost", PORT);
		try {
			new Client2(addr).run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Client2(SocketAddress address) throws IOException {
		this.selector = Selector.open();
		this.s = SocketChannel.open(address);
		s.configureBlocking(false);
		ClientState cs = new ClientState("Server");
		this.s.register(this.selector, SelectionKey.OP_READ, cs);
	}

	private void handleRead(SelectionKey key) {
		SocketChannel ch = (SocketChannel) key.channel();
		ClientState cs = (ClientState) key.attachment();
		StringBuilder sb = new StringBuilder();

		sb.append(cs.getBuffer());

		buf.clear();
		int read = 0;
		try {
			while ((read = ch.read(buf)) > 0) {
				buf.flip();
				byte[] bytes = new byte[buf.limit()];
				buf.get(bytes);
				sb.append(new String(bytes));
				buf.clear();
			}

			String msg = sb.toString();
			int i;
			String current;
			while ((i = msg.indexOf('\0')) >= 0) {
				current = msg.substring(0, i);
				msg = msg.substring(i + 1);
				try {
					System.out.println(cs.getName() + ":" + current);
					// System.out.flush();

					ByteArrayInputStream in = new ByteArrayInputStream(
							current.getBytes());
					game.parseAndHandle(in);

				} catch (Exception e) {
					e.printStackTrace();
					System.err.flush();
				}
			}
			if (read < 0) {
				System.out.println(cs.getName() + " left the chat.\n");
				ch.close();
			} else {
				cs.setBuffer(msg);
			}
		} catch (IOException e) {
			key.cancel();
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			System.out.println("Client connecting on port " + PORT);

			Iterator<SelectionKey> iter;
			SelectionKey key;
			while (this.s.isOpen()) {
				selector.select();
				iter = this.selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					key = iter.next();
					iter.remove();
					if (key.isReadable())
						this.handleRead(key);
					if (key.isWritable())
						this.handleWrite(key);
					if (game.getState() != null && !sent) {
						sent = true;
						addPlayers(key);
					}					
					if (game.getState() != null && game.getState().getPlayers().size()==2 && !started) {
						started = true;
						startGame(key);
					}
				}
				System.out.println("state:" + game.getState());
				// System.out.flush();
				// TODO send after receiving
			}
			System.out.println("received both!");
		} catch (IOException e) {
			System.out.println("IOException, server of port " + PORT
					+ " terminating. Stack trace:");
			e.printStackTrace();
		}
	}

	private void handleWrite(SelectionKey key) {
		SocketChannel ch = (SocketChannel) key.channel();
		ClientState cs = (ClientState) key.attachment();
		ByteBuffer toSend = ByteBuffer.wrap(cs.getOutputBuffer().getBytes());

		int written = 0;
		try {
			while ((written = ch.write(toSend)) > 0) {
			}
			if (toSend.hasRemaining()) {
				cs.setOutputBuffer(toSend.compact().toString());
			} else {
				toSend = null;
				key.interestOps(SelectionKey.OP_READ);
			}
			if (written < 0) {
				System.out.println(cs.getName() + " left the chat.\n");
				ch.close();
			}
		} catch (IOException e) {
			key.cancel();
			e.printStackTrace();
		}
	}

	private void addPlayers(SelectionKey key) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
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
		ap1.eResource().save(out, null);
		out.write('\0');
		actions.clear();
		actions.add(ap2);
		ap2.eResource().save(out, null);
		out.write('\0');
		((ClientState) key.attachment()).setOutputBuffer(out.toString());
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	private void startGame(SelectionKey key) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// Add two new Players
		StartGame action = actionFactory.eINSTANCE.createStartGame();
		EList<EObject> actions = game.getActionResource().getContents();
		actions.clear();
		actions.add(action);
		action.eResource().save(out, null);
		out.write('\0');
		((ClientState) key.attachment()).setOutputBuffer(out.toString());
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}
}

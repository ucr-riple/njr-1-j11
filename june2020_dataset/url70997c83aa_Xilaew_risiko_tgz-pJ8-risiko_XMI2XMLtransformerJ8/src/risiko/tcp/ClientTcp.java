package risiko.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTcp implements Runnable {
	private static final Logger LOG = Logger.getLogger(ClientTcp.class
			.getName());
	protected static final int DEFAULT_PORT = 10523;
	protected static final String DEFAULT_IP = "localhost";
	protected SocketAddress serverAdress;

	protected LinkedList<TcpListener> listeners = new LinkedList<TcpListener>();

	private ByteBuffer buf = ByteBuffer.allocate(256);

	ClientTcp() {
		this(new InetSocketAddress(DEFAULT_IP, DEFAULT_PORT));
	}

	ClientTcp(SocketAddress address) {
		this.serverAdress = address;
	}

	public void deregisterListener(TcpListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public void registerListener(TcpListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
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
				LOG.log(Level.INFO, cs.getName() + ":" + current);
				synchronized (listeners) {
					for (TcpListener listener : listeners) {
						listener.handleIncomming(current, key);
					}
				}
			}
			if (read < 0) {
				LOG.log(Level.INFO, cs.getName() + " left the chat.\n");
				ch.close();
			} else {
				cs.setBuffer(msg);
			}
		} catch (IOException e) {
			key.cancel();
			LOG.log(Level.WARNING, "IOException, " + serverAdress.toString()
					+ " terminated the connection.", e);
		}
	}

	@Override
	public void run() {
		try {
			Selector selector = Selector.open();
			SocketChannel s = SocketChannel.open(serverAdress);
			s.configureBlocking(false);
			ClientState cs = new ClientState(serverAdress.toString());
			s.register(selector, SelectionKey.OP_READ, cs);
			LOG.log(Level.INFO,
					"Client connecting to Server: " + serverAdress.toString());

			Iterator<SelectionKey> iter;
			SelectionKey key;
			while (s.isOpen()) {
				selector.select();
				iter = selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					key = iter.next();
					iter.remove();
					if (key.isReadable())
						this.handleRead(key);
					if (key.isWritable())
						this.handleWrite(key);
				}
			}
		} catch (IOException e) {
			LOG.log(Level.WARNING,
					"IOException, connection to " + serverAdress.toString()
							+ " could not be established.", e);
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
				LOG.log(Level.INFO, cs.getName() + " left the chat.\n");
				ch.close();
			}
		} catch (IOException e) {
			key.cancel();
			LOG.log(Level.WARNING, "IOException, " + serverAdress.toString()
					+ " terminated the connection.", e);
		}
	}

	public boolean send(String message, SelectionKey key) {
		synchronized (key) {
			ClientState cs = (ClientState) key.attachment();
			StringBuilder sb = new StringBuilder();
			sb.append(cs.getOutputBuffer());
			sb.append(message);
			sb.append('\0');
			cs.setOutputBuffer(sb.toString());
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
		return true;
	}
}

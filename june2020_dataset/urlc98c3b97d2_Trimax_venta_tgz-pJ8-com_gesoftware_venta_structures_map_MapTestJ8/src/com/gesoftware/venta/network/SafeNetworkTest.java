package com.gesoftware.venta.network;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.network.handlers.IClientHandler;
import com.gesoftware.venta.network.handlers.IServerHandler;
import com.gesoftware.venta.network.model.Message;
import com.gesoftware.venta.network.model.ServerResponse;

import java.net.InetAddress;
import java.util.TimerTask;

public class SafeNetworkTest {
    private final static int c_Port = 5503;
    private final static int c_Bits = 2048;

    private static void startServer() {
        final SafeServer server = new SafeServer(c_Port, c_Bits, new IServerHandler() {
            @Override
            public boolean onConnect(final String clientID, final InetAddress clientAddress) {
                LoggingUtility.info("Client connected: " + clientID);
                return true;
            }

            @Override
            public ServerResponse onReceive(final String clientID, final Message message) {
                LoggingUtility.info("Client send message: " + message.toString());
                return new ServerResponse(message);
            }

            @Override
            public void onDisconnect(final String clientID) {
                LoggingUtility.info("Client disconnected: " + clientID);
            }
        });

        (new Thread(server)).start();
    }

    private static class Task extends TimerTask {
        private final SafeConnection m_Connection;

        public Task(final SafeConnection connection) {
            m_Connection = connection;
        }

        @Override
        public void run() {
            m_Connection.send(new Message("Hello, current time is: " + System.currentTimeMillis()));
        }
    }

    private static void startClient() {
        final SafeConnection connection = new SafeConnection("localhost", c_Port, c_Bits, new IClientHandler() {
            @Override
            public void onReceive(final Message message) {
                LoggingUtility.info("Server answer: " + message.toString());
            }

            @Override
            public void onConnectionLost(final String message) {
                LoggingUtility.info("Connection lost: " + message);
            }
        });

        connection.connect();
        (new java.util.Timer("Client")).schedule(new Task(connection), 0, 1000);
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_DEBUG);

        startServer();
        startClient();
    }
}

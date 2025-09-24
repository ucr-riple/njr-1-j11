package server;

import com.sun.istack.internal.NotNull;
import daoo.ioc.Task;
import daoo.ioc.TaskExecutor;
import daoo.ioc.TaskServer;
import ioc.Context;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DaooTaskServer implements TaskServer {
    @Override public void start(@NotNull TaskExecutor executor, int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.println("Socket is waiting for request");
                Socket socket = serverSocket.accept();
                Task task = new EncodeDecodeTask(socket, Context.getMsgEncoder());
                System.out.println("Socket is executing request");
                executor.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

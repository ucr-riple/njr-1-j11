package server;

import daoo.ioc.TaskServer;
import ioc.Context;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final TaskServer server = new DaooTaskServer();
        try {

            server.start(Context.getTaskExecutor(), 8080);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

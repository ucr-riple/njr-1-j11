package daoo.ioc;

import java.io.*;
import java.net.Socket;

public abstract class Task implements Runnable {

    protected final Socket socket;

    protected Task(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            task();
            close();
        } catch (IOException e) {
            throw new RuntimeException("Task socket io exception!", e);
        }
    }

    protected abstract void task() throws IOException;

    /**
     * To be called before finishing run execution.
     */
    protected void close() throws IOException {
        socket.close();
    }

    protected void print(String msg) {
        System.out.println("Task with " + socket + " at " + System.currentTimeMillis() + " " + msg);
    }

    /**
     * Sleepy task: it takes given sleep time to run.
     */
    public static Task sleepy(Socket socket, long sleep) {
        return new SleepyTask(socket, sleep);
    }

    /**
     * Infinite task: it takes for ever...
     */
    public static Task infinite(Socket socket) {
        return new InfiniteTask(socket);
    }

    /**
     * Echo task: it takes for ever...
     */
    public static Task echo(Socket socket) {
        return new EchoTask(socket);
    }

    private static class SleepyTask extends EchoTask {
        private final long sleep;

        private SleepyTask(Socket socket, long sleep) {
            super(socket);
            this.sleep = sleep;
        }

        @Override
        public void task() throws IOException {
            super.task();
            print("sleeping...");
            sleep();
            print("waking up...");
        }

        private void sleep() {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException("Sleepy task interrupted!", e);
            }
        }
    }

    private static class InfiniteTask extends EchoTask {
        private InfiniteTask(Socket socket) {
            super(socket);
        }

        @Override
        public void task() throws IOException {
            super.task();
            print("endless...");
            endless();
        }

        private void endless() {
            //noinspection InfiniteLoopStatement,StatementWithEmptyBody
            for (; ; ) {
            }
        }
    }

    private static class EchoTask extends Task {
        protected EchoTask(Socket socket) {
            super(socket);
        }

        @Override
        protected void task() throws IOException {
            print("echoing...");
            echo();
        }

        private void echo() throws IOException {
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            final StringBuilder header = new StringBuilder();

            while (true) {
                final String s = in.readLine();
                if (s == null || s.isEmpty()) break;
                header.append(s);
            }

            out.write("HTTP/1.1 200 OK\r\n");
            out.write("Content-Type: text/plain\r\n");

            // Echo path
            out.write("\r\n");
            final String path = getHeaderPath(header.toString());
            out.write(path + "\r\n");

            out.close();
            in.close();
        }

        private String getHeaderPath(String header) {
            final int method = header.indexOf("GET /");
            if (method == -1) throw unsupported("Unsupported HTTP Method! GET only!");
            final int protocol = header.indexOf("HTTP/1.");
            if (protocol == -1) throw unsupported("Unsupported HTTP Protocol! HTTP/1.* only!");
            return header.substring(method + 4, protocol);
        }

        private UnsupportedOperationException unsupported(String s) {
            return new UnsupportedOperationException(s);
        }
    }
}

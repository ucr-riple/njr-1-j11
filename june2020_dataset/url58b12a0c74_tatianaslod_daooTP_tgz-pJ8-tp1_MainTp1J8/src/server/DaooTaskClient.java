package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class DaooTaskClient
{
    private final int clients;
    private final int loopCount;

    public DaooTaskClient(int clients, int loopCount) {
        this.clients = clients;
        this.loopCount = loopCount;
    }

    public void request(String host, int port, String path) {

        final String url = "http://" + host + ":" + port + (path.startsWith("/") ? path : "/" + path);

        final ExecutorService executor = Executors.newFixedThreadPool(clients);

        final List<Callable<RequestMetering>> callables = new ArrayList<Callable<RequestMetering>>(clients * loopCount);

        for(int loop = 0; loop < loopCount; loop++) {
            for (int client = 0; client < clients; client++) {
                callables.add(task(url));
            }
        }

        try {
            final long start = System.currentTimeMillis();
            print(start, executor.invokeAll(callables));
        } catch (Exception e) {
            throw new RuntimeException("Task execution exception!", e);
        }
    }

    private Callable<RequestMetering> task(final String url) {
        return new Callable<RequestMetering>() {
            @Override public RequestMetering call() throws Exception {
                final RequestMetering result = RequestMetering.create();
                result.code = connect(url);
                result.end = System.currentTimeMillis();
                return result;
            }
        };
    }

    private void print(long start, final List<Future<RequestMetering>> results)
            throws ExecutionException, InterruptedException
    {
        final long overall = System.currentTimeMillis() - start;

        final Map<Integer, Integer> result = new HashMap<Integer, Integer>();

        int count = 0;
        long total = 0;
        double max = Integer.MIN_VALUE;
        double min = Integer.MAX_VALUE;

        for (Future<RequestMetering> future : results) {
            final RequestMetering metering = future.get();
            final Integer amount = result.get(metering.code);
            if(amount == null) result.put(metering.code, 1);
            else result.put(metering.code, amount + 1);
            count++;
            final long duration = metering.getDuration();
            total += duration;
            max = Math.max(max, duration);
            min = Math.min(min, duration);
        }

        System.out.println("DaooTaskClient.print result = " + result);
        System.out.println("result = " + result);
        System.out.println("max = " + max);
        System.out.println("min = " + min);
        System.out.println("avg = " + (total/count));
        System.out.println("overall = " + overall);
    }

    private int connect(String url) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            return connection.getResponseCode();

            /*final int code = connection.getResponseCode();

            if (code == 200) {
                // Read and print response
                final InputStream response = connection.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(response));

                while(true) {
                    final String s = reader.readLine();
                    if(s == null || s.isEmpty()) break;
                    System.out.println("response = " + s);
                }
            }

            return code;*/

        } catch (IOException e) {
            throw new RuntimeException("IO exception during client connection!", e);
        }
    }

    private static class RequestMetering {
        private long start;
        private long end;
        private int code;

        private RequestMetering() { }

        /** Result code. */
        private int getCode() { return code; }

        /** Duration in milliseconds. */
        private long getDuration() { return end - start; }

        private static RequestMetering create() {
            final RequestMetering instance = new RequestMetering();
            instance.start = System.currentTimeMillis();
            return instance;
        }
    }
}

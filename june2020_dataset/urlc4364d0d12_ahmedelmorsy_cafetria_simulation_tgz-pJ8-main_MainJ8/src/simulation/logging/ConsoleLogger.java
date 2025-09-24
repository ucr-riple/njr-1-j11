package simulation.logging;

public class ConsoleLogger implements EventsLogger {

    @Override
    public void log(String s) {
        System.out.println(s);
    }

    @Override
    public void close() {
        //Do nothing
    }

}

package simulation.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileLogger implements EventsLogger {

    private PrintWriter out;
    
    public FileLogger(File f) {
        try {
            this.out = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void log(String s) {
        out.write(s + "\n");
    }

    @Override
    public void close() {
        out.close();
    }

}

package edu.sandbox.collectionsandthreads;

import java.io.FileWriter;
import java.io.IOException;
import edu.sandbox.collectionsandthreads.CollectionsTests;
/**
* Created by yurij.pyvovarenko on 17.04.14.
 */
public class SynchroHTML {
    private FileWriter fileWriter;

    public  SynchroHTML(String HTMLFilePath) throws IOException {
        //super(HTMLFilePath);
        fileWriter = new FileWriter(HTMLFilePath, false);
    }
    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void writingHTML(String str, int threadNumber) {
        if (-1 == threadNumber) {
            //System.out.println("\nWriting Header or Footer to the HTML file. threadNumber = " + threadNumber + ".");
            System.out.println(str);
            try {
                fileWriter.append(str);
            } catch (IOException e) {
                System.err.println("22222 ошибка файла");
                e.printStackTrace();
            }
        } else {
            System.out.println(CollectionsTests.HTMLString(str, "thread" + threadNumber));
            //System.out.println("\nWriting to the HTML file. threadNumber = " + threadNumber + ".");
            try {
                fileWriter.append(
                        CollectionsTests.HTMLString(str,"thread" + threadNumber));
            } catch (IOException e) {
                System.err.println("11111 ошибка файла");
                e.printStackTrace();
            }
        }
    }
}
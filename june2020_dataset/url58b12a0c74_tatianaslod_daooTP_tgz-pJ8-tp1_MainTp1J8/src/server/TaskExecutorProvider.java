package server;

import daoo.ioc.TaskExecutor;

import java.io.*;
import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 07/06/13
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class TaskExecutorProvider {

    public static void printEncoders(){
        final ServiceLoader<TaskExecutor> loader = ServiceLoader.load(TaskExecutor.class);
        for (TaskExecutor taskExecutor : loader) {
            System.out.println(taskExecutor.getClass().getName());
        }
    }

    public static TaskExecutor getExecutor() throws Exception {
        final ServiceLoader<TaskExecutor> loader = ServiceLoader.load(TaskExecutor.class);
        for (TaskExecutor taskExecutor : loader) {
            if(taskExecutor.getClass().getName().equals(getImplementationName())){
                return taskExecutor;
            }
        }
        throw new Exception("No such Executor found");
    }

    private static String getImplementationName() {
        final FileReader fr;
        try {
            fr = new FileReader("Executor.txt");
            final BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            fr.close();
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }




    public static void main(String[] args) {
        try {
            TaskExecutorProvider.getExecutor();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

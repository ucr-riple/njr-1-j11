package jswang.ConcurrencyTest;
import java.io.*;

/**
 * Created by willwjs on 11/17/14.
 */

class CountDown extends Thread {
    public volatile static boolean should_I_continue = false; //This variable can be used by all the threads
    //private Exception IntException;

    @Override
    public void run() {
        int count = 10; //Used to Count Down
        synchronized (this) {
            try {
                while(count != -1) { //Count from 10 to 0
                    System.out.println(count--);
                    if(count != -1) Thread.sleep(1000); //The interval is 1000 ms
                    //if(should_I_continue) throw IntException; //If Enter button is pressed, throw an exception
                }
                //wait(1000);
            } catch (InterruptedException ex) { //With the interupt to exit
                System.out.println("interrupted");
            }

            if(!should_I_continue) { //Wait for the interupt to exit and hand over CPU
                try {
                    this.wait();
                } catch (InterruptedException ex) { }
            }
        }

        System.out.println("Finished");
    }
}

public class Solution {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        CountDown cd = new CountDown(); //Create
        cd.start(); //Start the thread

        while(true)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Prepared to input
            int s; //Get the input
            s = br.read(); //Input
            if(s == '\n') {
                CountDown.should_I_continue = true; //When Enter button has been pressed
                System.out.println("user hits the Enter key");
                cd.interrupt(); //Interupt when sleep or wait
                break;
            }
        }
    }
}


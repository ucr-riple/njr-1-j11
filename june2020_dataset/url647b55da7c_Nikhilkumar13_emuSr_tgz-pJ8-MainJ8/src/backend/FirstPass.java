/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import frontend.FrontEnd;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 *
 * @author Nikhil
 */
public class FirstPass
{

    private static boolean commentflag = false;
    public static Hashtable branchtable = new Hashtable();
    public static Object obj[][];
    //  static Enumeration check;

    public static int scan(File path)
      {
        long ct = 0;
        int count = 0;
        obj = new Object[1000][2];
        RandomAccessFile br = null;

        try
        {

            int ctl = 0;
            File f = path;
            br = new RandomAccessFile(f, "r");
            FileChannel inChannel = br.getChannel();
            ct = inChannel.position();
            String strLine;

            int idx = 0;
            while ((strLine = br.readLine()) != null)
            {
                //System.out.println("Doing First Pass");

                strLine = strLine.trim().toUpperCase();
                // System.out.println("commentflag in First pass" + commentflag);

                RemoveComments.setflag(commentflag);
                strLine = backend.RemoveComments.RC(strLine);
                commentflag = RemoveComments.getcommetnflag();

                ctl++;
                if (strLine.isEmpty())
                {
                     // System.out.println("Empty Line");
                     // System.out.println(ct + " hahhah");

                    continue;
                }
                //
                if (!strLine.startsWith("."))
                {
                    // do nothing
                } //                if(strLine.substring(1,6).equals("PRINT"))
                //                     {
                //                         //System.out.println("\n Print encounterred");
                //                         break;
                //                     }
                else
                {
//                    //System.out.println("Doing FIRST PASS  " + strLine);
                    count++;
                     
                    
                    
//                    line contains a label
                    StringTokenizer st = new StringTokenizer(strLine, ":");
//                    other_things   labelname : other_things
                    String label = st.nextToken();
                    label = label.trim();
                    label = label.substring(1);
                    label = label.toUpperCase();

//                    //System.out.println("LAbel here is ->>" + label);
                    if (label.equals("MAIN"))
                    {
//                        //System.out.println("main Found");
                        backend.ScanFile.mainPresent = true;
                        backend.ScanFile.mainPosition = ct;
                    } else if (label.startsWith("PRINT"))
                    {
                        ////System.out.println("\n PRINT encounterd in first pass");
                        count--;
                        continue;
                    }
                    if (idx < 1000)
                    {
                        obj[idx][0] = (String) label;
                        obj[idx++][1] = Integer.toString(ctl);
                    }
                     //if(label.equalsIgnoreCase("PRINT"))
                    {
                        if(!strLine.contains(":"))
                    {
                          FrontEnd.appendToPane(FrontEnd.statuswindow,"Invalid Label (Should Finish With colon)\n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("Error occured while accessing source file");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
                        
                    }
                       // System.out.println("Nikhil");
                        //System.out.println(ctl);
                        //System.out.println(label);
                       // System.out.println(ct);

                        branchtable.put(label.toUpperCase(), ct);
                    }

                }
                ct = inChannel.position();
            }
            commentflag = false;
            //  branchtable.remove();
            //  check = branchtable.keys();
//            while(check.hasMoreElements()) {
//        String  l = (String) check.nextElement();
//         //System.out.println(l + ": " +branchtable.get(l));
//                                            } 
            /// branchtable.put("PRINTF", new Long(-1));
            ///  branchtable.put("SCANF", new Long(-2));
            ///  branchtable.put("__MODSI3",new Long(-3));

        }
        catch (IOException ex)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Accessing Source File\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("Error occured while accessing source file");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
        } finally
        {
            try
            {

                br.close();
            } catch (IOException ex)
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Closing Source File\n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("Error occured while accessing source file");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
            }
        }
        return count;

      }
}

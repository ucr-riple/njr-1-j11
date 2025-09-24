/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.awt.Color;

/**
 *
 * @author NIKHIL
 */
public class RemoveComments
{

    public static boolean flagcomment = false;
    public static boolean flag = false;

    public static void setflag(boolean f)
      {
        flagcomment = f;
      }

    public static String RC(String strLine)
      {
        try{
            
        
        strLine=strLine.trim();
        strLine = strLine.replaceAll("//", "@");

        // String input =strLine;
        //System.out.println("Input => " + strLine);
        //System.out.println("flag value => " + flagcomment);
        //  String result="";

        int ds, ss = 0;
        int atr = 0;
        /**
         * d_s=double slash , s_s=slash star,atr=at the rate
         */
         // System.out.println(strLine);
         // System.out.println(flagcomment);
          if(strLine.isEmpty())
          { 
              //System.out.println("what happend");
              
              return "";
          }
          if(strLine.equals(""))
          {
              return ""; 
          }
          
        ds = strLine.indexOf("@");
        ss = strLine.indexOf("/*");
       // System.out.println(ds+" "+ss);
        // atr=strLine.indexOf("@");
        if (flagcomment)
        {
            if (strLine.contains("*/"))
            {
                int b = strLine.indexOf("*/");
                flagcomment = false;

                return RC( strLine.substring(b + 2));

                

            } 
            else
            {
                return "";
            }
        }
        if (ds == 0)
        {
            return "";
        }
//        if (ss == 0)
//        {
//            if (strLine.contains("*/"))
//            {
//                int b = strLine.indexOf("*/");
//
//                strLine = strLine.substring(b + 2);
//
//                flagcomment = false;
//
//            } else
//            {
//                flagcomment = true;
//
//                return "";
//            }
//        }

        if (ds > ss && ss >= 0)
        {
            int a = strLine.indexOf("/*");
            if (strLine.contains("*/"))
            {

                int b = strLine.indexOf("*/");
                String temp = strLine;
                strLine = temp.substring(0, a) + temp.substring(b + 2);

                flagcomment = false;
            } else
            {
                //System.out.println("\n comment on");
                flagcomment = true;
                strLine = strLine.substring(0, a);

            }
        } 
        else if (ds > ss && ss < 0)
        {
            int a = strLine.indexOf("@");
            strLine = strLine.substring(0, a);
            flagcomment = false;

        } 
        else if (ss > ds)
        {
            if (ds > 0)
            {
                //int a = strLine.indexOf("@");
                strLine = strLine.substring(0, ds);
                flagcomment = false;

            } // .label: /*naskdnaksnd*/ mov r6 6
            else
            {
               // System.out.println(strLine);
                if (strLine.contains("*/"))
                {
                   // System.out.println("yeppii");
                    int a = strLine.indexOf("/*");
                    int b = strLine.indexOf("*/");
                    flagcomment = false;
                    String temp = strLine;
                    strLine = temp.substring(0, a) + temp.substring(b + 2);
                } else
                {
                   // System.out.println("hiiiiiiiii");

                    flagcomment = true;
                    int a = strLine.indexOf("/*");
                    strLine = strLine.substring(0, a).trim();

                }
            }
        }
        if (strLine.contains("@") | strLine.contains("/*") | strLine.contains("*/"))
        {
            flag = true;

        } else
        {
            flag = false;
        }
        if (flag)
        {
            return RC(strLine);
        }
        // System.out.println(" output =>" + strLine);
        // System.out.println("flag value " + flagcomment);
        return strLine.trim();
        }
        catch(Exception e)
        {
            frontend.FrontEnd.appendToPane(frontend.FrontEnd.statuswindow, "Error Occured While HandlingComment", Color.red);
            return "";
        }

      }

    public static boolean getcommetnflag()
      {

        return flagcomment;
      }
}

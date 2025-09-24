/**
 * Created with IntelliJ IDEA.
 * User: Palachsuper
 * Date: 21.04.14
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */

package terminal.service;

import java.io.*;
import json.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import terminal.devices.cashe_machine;
import terminal.devices.printer;
import terminal.devices.card_reader;
import terminal.devices.dispencer;
import terminal.http_server.HttpServer;


public class Service {
    public static String rate = "32";
    public Service()
    {

    }

    public static void main() {
        WriteLog("=====================================================");
        HttpServer.main("");
        WriteLog("sdfdsfsd");
    }

    public static void get_CPU_hash(){
        try{
            Runtime.getRuntime().exec("sh /home/terminal/lib/CPU_hash.sh");
        }
        catch (Exception e){

        }

    }

    enum MyEnum {
        print,
        dispense,
        card,
        cashe
    }

    public static void Parser_comand(String cmd)
    {
        int length_cmd = cmd.length();
        StringBuilder aaa = new StringBuilder();
        char str;
        for (int k=11; k<length_cmd-9;k++)
        {
           str= cmd.charAt(k);
           aaa.append(str);
        }
        cmd = aaa+"";
        WritePaymentLog(cmd, cashe_machine.id_payment);
        String result = cmd;
         try
         {
             result = java.net.URLDecoder.decode(result, "UTF-8");
             JSONObject json_cmd = new JSONObject(result);
             String start_cmd = json_cmd.getString("command");
             try {
                 MyEnum e = MyEnum.valueOf(start_cmd);
                 switch (e) {
                     case print:
                         printer.main(json_cmd);  // Отправили на в функцию печать JSON
                         break;
                     case dispense:
                         dispencer.main(cmd);
                         break;
                     case card:
                         card_reader.main(cmd);
                         break;
                     case cashe:
                         cashe_machine.main(json_cmd);
                         break;
                 }
             } catch (IllegalArgumentException iae) {

             }
             String pr_string = json_cmd.getString("params");
             JSONObject param = new JSONObject(pr_string);
         }
         catch (Exception e){

         }
    }

    public static void WritePaymentLog(String log, String id_payment){
        StringBuilder sb = new StringBuilder();
        try{
            String path_log = "/home/terminal/Log/LogPayment/Payment_"+id_payment+".txt";
            File Log_path = new File(path_log);
            if (!Log_path.exists()){
                try {
                    Log_path.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            BufferedReader in = new BufferedReader(new FileReader( new File(path_log).getAbsoluteFile()));
            String read_log;
            if (sb.length()>0)
            {
                sb.delete(0,sb.length()-1);
            }
            while ((read_log = in.readLine()) != null)
            {
                if (sb.length()>0)
                {
                    sb.append(read_log);
                    sb.append("\n");
                }
            }
            in.close();
            String str="";
            str =sb + log;
            PrintStream printStream = new PrintStream(new FileOutputStream(path_log, true), true);
            printStream.println(str);
            printStream.close();
        }
        catch (Exception e){

        }
    }

    public static void WriteLog(String log){
        String dat_time_path="";
        StringBuilder sb = new StringBuilder();
        try
        {
            try
            {
                Calendar cal = new GregorianCalendar();
                String name_cal=new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
                dat_time_path= "/home/terminal/Log/LogSystem/log"+name_cal+".txt";
                //dat_time_path= "/home/terminal/Log/print_query.txt";
                File Log_path = new File(dat_time_path);
                if (!Log_path.exists())
                {
                    try {
                        Log_path.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                BufferedReader in = new BufferedReader(new FileReader( new File(dat_time_path).getAbsoluteFile()));
                String read_log;
                if (sb.length()>0)
                {
                    sb.delete(0,sb.length()-1);
                }
                while ((read_log = in.readLine()) != null)
                {
                    if (sb.length()>0)
                    {
                        sb.append(read_log);
                        sb.append("\n");
                    }
                }
                in.close();
            }
            catch(IOException e)
            {
                throw new RuntimeException(e);
            }
            PrintStream printStream = new PrintStream(new FileOutputStream(dat_time_path, true), true);
            printStream.println(sb + log);
            printStream.close();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }



}

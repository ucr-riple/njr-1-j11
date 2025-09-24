package terminal.devices;




/**
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * Created with IntelliJ IDEA.
 * User: Palachsuper
 * Date: 23.04.14
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import json.JSONObject;
import java.io.File;

import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import terminal.service.Service;

public class printer
{

    public static JSONObject payment = null;
    public static int null_print_line = 15;
    public static String[] DataTable;
    public static String[] NameTable;
    public static String[] JSONTable;
    public static int count_field = 0;
    public static String type_pay="";
    public static String term=" ";
    public static int kol_chek = 1;
    public static String file_print;

    public static void get_query(){
        try{
            Runtime.getRuntime().exec("sh /home/terminal/lib/print_query.sh");
        }
        catch (Exception e){

        }
    }
    public static String pr_act="CUSTOM-Engineering-VKP80 is readyno entries"; // 10 рублевая купюра
    public static String s="";

    public static boolean is_ready(){
        get_query();
        try{
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        catch (Exception e){

        }
        s = "";
        try {
            Scanner in = new Scanner(new File("/home/terminal/Log/print_query.txt"));
            while(in.hasNext())
                s += in.nextLine();
            in.close();
        }
        catch (Exception e){

        }
        Service.WriteLog(s);
        if(pr_act.equals(s)){
            Service.WriteLog("Printer is ready");
            return true;
        }
        else {
            Service.WriteLog("Printer is not ready");
            return false;
        }

    }

    public static void main(JSONObject data)
    {
        String pr_string="";
        payment = data;
        Service.WriteLog(payment.toString());
        try {
            pr_string = payment.getString("params");

            File fXmlFile = new File("/home/terminal/lib/print/print.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            Service.WriteLog("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("field");
            DataTable= new String[nList.getLength()];
            NameTable= new String[nList.getLength()];
            JSONTable= new String[nList.getLength()];
            count_field=nList.getLength();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //Service.WriteLog("Current Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Service.WriteLog("data : " + eElement.getElementsByTagName("data").item(0).getTextContent());
                    Service.WriteLog("name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    DataTable[temp] = eElement.getElementsByTagName("data").item(0).getTextContent();
                    NameTable[temp] = eElement.getElementsByTagName("name").item(0).getTextContent();
                    try
                    {
                        JSONObject param = new JSONObject(pr_string);
                        JSONTable[temp]=param.getString(DataTable[temp]);
                    }
                    catch (Exception e)
                    {
                        JSONTable[temp]="Ошибка ответа!";
                    }
                    try
                    {
                        JSONObject param = new JSONObject(pr_string);
                        term=param.getString("id_terminal");
                    }
                    catch (Exception e)
                    {
                        term="Ошибка ответа!";
                    }
                }

            }
            try
            {
                kol_chek=1;
                JSONObject param = new JSONObject(pr_string);
                kol_chek = param.getInt("copy_count");

            }
            catch (Exception e)
            {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.defaultPage();
        Paper paper = new Paper();
        //pj.
        paper.setSize(240, 350);
        paper.setImageableArea(0,0, 240, 350);
        pf.setPaper(paper);
        pj.setPrintable(new FilePagePainter("test.doc"), pf);
        try{
            pj.print();
        }
        catch(PrinterException e)
        {

        }
       // System.exit(0);
        cashe_machine.send_json_cashe_answ = "{\"command\":\"print\",\"error\":\"0\"}";

    }
    }
         class FilePagePainter implements Printable {

            private String file;
            private int page = -1;
            private boolean eof=false;
            public FilePagePainter(String file){

                this.file = file;

            }
            @Override
            public int print(Graphics g, PageFormat pf, int ind)throws PrinterException{
                //g.setPaint(Color.black);
                if(ind > printer.kol_chek-1)
                    return NO_SUCH_PAGE;
                Graphics2D g2 = (Graphics2D)g;
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File("/home/terminal/lib/header.png"));
                    //g2.draw
                    g2.drawRenderedImage(bufferedImage, AffineTransform.getTranslateInstance(10,0));
                   // g2.drawImage(bufferedImage,10,10,null);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                g2.setColor(Color.black);

                g2.setFont(new Font("Arial", Font.ITALIC, 14));
                printer.null_print_line=91;
                g2.drawString(printer.type_pay, 50, printer.null_print_line);
                printer.null_print_line=printer.null_print_line+20;
                g2.setFont(new Font("Arial", Font.ITALIC, 10));
                for (int i=0; i<printer.count_field;i++)
                {
                    g2.drawString(printer.NameTable[i]+" "+printer.JSONTable[i], 15, printer.null_print_line);
                    printer.null_print_line=printer.null_print_line+14;
                }

                g2.setFont(new Font("Arial", Font.ITALIC, 8));
                printer.null_print_line=printer.null_print_line+13;
                // напечатали номер терминала
                if (printer.term.length()<40){
                    printer.null_print_line=printer.null_print_line+7;
                    g2.drawString(printer.term, 15, printer.null_print_line);
                }
                else {
                    int k=printer.term.length()/40;
                    String res="";
                    for (int i=1;i==k;i++)
                    {
                        res = printer.term.substring((i-1)*40,i*40-1);
                        g2.drawString(res, 15, printer.null_print_line);
                        printer.null_print_line=printer.null_print_line+12;
                    }
                    res = printer.term.substring(k*40-1,printer.term.length());
                    g2.drawString(res, 15, printer.null_print_line);
                    printer.null_print_line=printer.null_print_line+12;
                }



                //напечатали спасибо
                g2.setFont(new Font("Arial", Font.ITALIC, 14));
                printer.null_print_line=printer.null_print_line+17;
                g2.drawString("Спасибо :)", 67, printer.null_print_line);
                //g.drawString("Димка привет!", 15, 15);
                //g.setFont(new Font("Times new roman", Font.PLAIN, 10));
                try{
// Если система печати запросила эту страницу первый раз
                   /* if (ind != page){
                        if (eof) return Printable.NO_SUCH_PAGE;
// Читаем строки из файла и формируем массив строк
                        eof = true;
                        page=ind;
                    } */

                    return Printable.PAGE_EXISTS;



                }catch(Exception e){

                    return Printable.NO_SUCH_PAGE;

                }

            }
        }
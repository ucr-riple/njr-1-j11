package karlney.tetris.swing;

import javax.swing.*;
import java.io.*;




class HighScorePost implements Serializable{

    public int score;
    public String name;

    public HighScorePost(){}

    public HighScorePost(int s,String n){
        name=n;
        score=s;
    }

    public String toString(){
        return score+"\t \t"+name;
    }

}




public class Highscore{
    static HighScorePost[] scorename = new HighScorePost[10];
    static String name, outputfile = "highscore.dat";
    static int place;


    public static void readFile(){
        try{
            FileInputStream reader = new FileInputStream(outputfile);
            ObjectInputStream r = new ObjectInputStream(reader);
            String line = null;

            for (int row=0; row<10; row++){
                scorename[row]=(HighScorePost)r.readObject();
                //System.out.println(scorename[row]);
            }

            r.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public static void updateScore(int score){
        readFile();
        if((score>scorename[9].score)){
            getPlace(score);
            getName();
            updateFile(score);
        }
        showHighscore();
    }


    public static void getPlace(int score){
        for(int i=0; i<9; i++){
            if(score<scorename[i].score){
            }
            else{
                place=i;
                return;
            }
        }
    }


    public static void getName(){
        name = JOptionPane.showInputDialog("Congratulations, you've made it to the higscore list." + "\n Please enter your name: ");
        if(name==null || name.length()==0) name="Anonymous";
    }

    public static void updateFile(int score){
        try{
            FileOutputStream writer = new FileOutputStream(outputfile);
            ObjectOutputStream w = new ObjectOutputStream(writer);

            for(int i=0; i<place; i++){
                w.writeObject(scorename[i]);
            }

            w.writeObject(new HighScorePost(score,name));

            for(int i=place+1; (i<10); i++){
                w.writeObject(scorename[i-1]);
            }
            w.close();
        }

        catch(IOException e){}
    }


    public static void showHighscore(){
        readFile();
        String s="";
        String[] button = {"OK"};
        for (int i=0; i<10; i++){
            s=s+scorename[i]+"\n";
        }
        //JOptionPane.showOptionDialog(null,s,"Highscore",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,button,0);
    }
}
        
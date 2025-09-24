package com.alibaba;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * User: sweeliao@gmail.com
 * Date: 14-3-21
 * Time: 上午10:52
 */
public class DataRead {

    private BufferedReader br;
    private Map<String, Map<String, Map<Integer, HashSet<Integer>>>> table = new HashMap<String, Map<String, Map<Integer, HashSet<Integer>>>>();

    public Boolean read() {
        try {
            br = new BufferedReader(new FileReader("E:\\workspace\\alibaba\\t_alibaba_data.csv"));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] stat = line.split(",");
                String [] date = stat[3].split("\\W+");
                String user_id = stat[0],
                        brand_id = stat[1],
                        type = stat[2];

                //Integer datehash = Integer.valueOf(date[0]) * 31 + Integer.valueOf(date[1]); // result number is 0
                //Integer datehash = Integer.valueOf(date[0]); // result number is 2
                Integer datehash = 1;  // result number is 1098

                Map<String, Map<Integer, HashSet<Integer>>> oneline = table.get(user_id);
                if (oneline == null) {
                    oneline = new HashMap<String, Map<Integer, HashSet<Integer>>>();
                    Map<Integer, HashSet<Integer>> temp = new HashMap<Integer, HashSet<Integer>>();
                    HashSet<Integer> tempSet = new HashSet<Integer>();
                    tempSet.add(Integer.valueOf(type));
                    temp.put(datehash, tempSet);
                    oneline.put(brand_id, temp);
                }
                else {
                    Map<Integer, HashSet<Integer>> nextline = oneline.get(brand_id);
                    if (nextline == null) {
                        nextline = new HashMap<Integer, HashSet<Integer>>();
                        HashSet<Integer> tempSet = new HashSet<Integer>();
                        tempSet.add(Integer.valueOf(type));
                        nextline.put(datehash, tempSet);
                        oneline.put(brand_id, nextline);
                    }
                    else {
                        HashSet<Integer> thirdline = nextline.get(datehash);
                        if (thirdline == null) {
                            HashSet<Integer> tempSet = new HashSet<Integer>();
                            tempSet.add(Integer.valueOf(type));
                            nextline.put(datehash, tempSet);
                        }
                        else {
                            thirdline.add(Integer.valueOf(type));
                            nextline.put(datehash, thirdline);
                        }
                        oneline.put(brand_id, nextline);
                    }
                }
                table.put(user_id, oneline);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Map<String, Map<String, Map<Integer, HashSet<Integer>>>> getData() {
        return table;
    }

    public void close() {
        try {
            br.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

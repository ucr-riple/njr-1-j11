package jswang.reversewords;

/**
 * Created by Jiashuo Wang on 14-6-9.
 * 1.使用split方法时，字符串尾部的空格会被删除，如“ a b ”分割完为“”、“a”、“b”
 * 2.要考虑单词间有多个空格、字符串首尾空格、仅有单个字符如“a”、仅有空格的情况
 */
public class Solution {
    public String reverseWords(String s) {
        String words[] = s.split(" ");
        int wordsLength = words.length;
        if(wordsLength == 0) return ""; //Blank Space
        int flag = 1;
        for (int i = wordsLength-1; i >= 0; i--) {
            if(words[i].equals("")) continue; //Remove Space
            if(flag == 1) {s = words[i]; flag = 0; continue;}
            s = s + " " + words[i];
        }
        return s;
    }
}
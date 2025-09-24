/*
 * Copyright (C) 2016 geoagdt.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geoagdt
 */
public class Generic_ReadCSV {

    public static boolean testRead(
            File f,
            File testDir,
            int syntax) {

        ArrayList<String> result = null;

        File test;
        test = new File(testDir,
                "test" + syntax + ".csv");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(test);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generic_ReadCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (f.exists()) {
            try {
                BufferedReader br;
                StreamTokenizer st;
                br = Generic_StaticIO.getBufferedReader(f);
                if (br != null) {
                    result = new ArrayList<String>();
                    st = new StreamTokenizer(br);
                    switch (syntax) {
                        case 1:
                            Generic_StaticIO.setStreamTokenizerSyntax1(st);
                            break;
                        case 2:
                            Generic_StaticIO.setStreamTokenizerSyntax2(st);
                            break;
                        case 3:
                            Generic_StaticIO.setStreamTokenizerSyntax3(st);
                            break;
                        case 4:
                            Generic_StaticIO.setStreamTokenizerSyntax4(st);
                            break;
                        case 5:
                            Generic_StaticIO.setStreamTokenizerSyntax5(st);
                            break;
                        case 6:
                            Generic_StaticIO.setStreamTokenizerSyntax6(st);
                            break;
                        default:
                            System.out.println("No Special StreamTokenizerSyntax");

                    }
                    int token = st.nextToken();
//                    long RecordID = 0;
                    String line = "";
                    while (!(token == StreamTokenizer.TT_EOF)) {
                        switch (token) {
                            case StreamTokenizer.TT_EOL:
                                result.add(line);
                                pw.println(line);
                                line = "";
//                                if (RecordID % 100 == 0) {
//                                    System.out.println(line);
//                                }
//                                RecordID++;
                                break;
                            case StreamTokenizer.TT_WORD:
                                line += st.sval;
                                break;
                            case StreamTokenizer.TT_NUMBER:
                                break;
                            default:
                                if (token == 26 || token == 160) {
                                    // A type of space " ". It is unusual as st 
                                    // probably already set to parse space as
                                    // words.
                                    line += (char) token;
                                }
                                if (token == 13) {
                                    // These are returns or tabs or something...
                                    //line += (char) token;
                                }
                                System.out.println("line so far " + line);
                                System.out.println("Odd token " + token +  " \"" + (char) token + "\" encountered.");
                        }
                        token = st.nextToken();
                    }
                    br.close();
                }
                pw.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        long length0;
        length0 = test.length();
        System.out.println("length of test file = " + length0);

        long length1;
        length1 = f.length();
        System.out.println("length of original file = " + length1);

        if (length0 == length1) {
            System.out.println("length0 == length1");
            return true;
        } else {
            return false;
        }
        //return result;
    }

}

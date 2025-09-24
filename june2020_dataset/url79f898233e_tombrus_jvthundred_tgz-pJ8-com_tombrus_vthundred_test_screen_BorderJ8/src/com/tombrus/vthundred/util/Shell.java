package com.tombrus.vthundred.util;

import java.io.*;

public class Shell {
    public static void shell (String cmd) {
        try {
            final Process     process = new ProcessBuilder("/bin/sh", "-c", cmd).start();
            final InputStream stdout  = process.getInputStream();
            while (stdout.read() >=0) {
            }
            if (process.waitFor() !=0) {
                DB.error("process returned "+process.exitValue()+": "+cmd);
            }
            DB.t("\n++++++++ shell '"+cmd +"': " +process.exitValue() +"\n");
        } catch (Exception e) {
            DB.error(e);
        }
    }
}

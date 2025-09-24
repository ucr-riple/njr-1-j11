package com.tombrus.vthundred.test.basic;

import java.io.*;

public class InputTest {
    public static void main (String[] args) throws IOException {
        while (true) {
            int b = System.in.read();
            System.out.printf("c=%-3s hex=0x%02x dec=%3d\n", b==0x1b?"ESC":""+(char)b, b, b);
        }
    }
}

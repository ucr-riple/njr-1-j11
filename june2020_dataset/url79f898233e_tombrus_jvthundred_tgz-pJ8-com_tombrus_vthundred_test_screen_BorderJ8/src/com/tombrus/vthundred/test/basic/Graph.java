package com.tombrus.vthundred.test.basic;

import com.tombrus.vthundred.terminal.*;
import com.tombrus.vthundred.terminal.types.*;

public class Graph {
    public static void main (String[] args) {
        TerminalType type = new AnsiType();

        for (int i=0x60;i<0x7f; i++){
            System.out.print((char)i);
        }
        System.out.println();
        System.out.print(type.getGraphOnSeq());
        for (int i = 0x60; i<0x7f; i++) {
            System.out.print((char) i);
        }
        System.out.println();
        for (int i = 0x60; i<0x7f; i++) {
            System.out.print((char) i);
        }
        System.out.println();
        System.out.print(type.getGraphOffSeq());
    }
}

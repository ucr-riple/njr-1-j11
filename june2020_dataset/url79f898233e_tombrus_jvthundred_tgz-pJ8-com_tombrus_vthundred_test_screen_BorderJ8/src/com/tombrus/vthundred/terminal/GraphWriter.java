package com.tombrus.vthundred.terminal;

import java.io.*;

public class GraphWriter extends FilterWriter {
    private       boolean      graphOn;
    private final TerminalType type;

    public GraphWriter (TerminalType type, Writer out) {
        super(out);
        this.type = type;
    }

    @Override
    public void write (int c) throws IOException {
        char graph = type.toGraph((char) c);
        if (graph !=0) {
            graphOn();
            super.write(graph);
        }else {
            graphOff();
            super.write(c);
        }
    }

    @Override
    public void write (char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i<off+len; i++) {
            write(cbuf[i]);
        }
    }

    @Override
    public void write (String str, int off, int len) throws IOException {
        for (int i = off; i<off+len; i++) {
            write(str.charAt(i));
        }
    }

    @Override
    public void flush () throws IOException {
        graphOff();
        super.flush();
    }

    @Override
    public void close () throws IOException {
        graphOff();
        super.close();
    }

    public void graphOn () throws IOException {
        if (!graphOn) {
            graphOn = true;
            writeControlSeq(type.getGraphOnSeq());
        }
    }

    public void graphOff () throws IOException {
        if (graphOn) {
            graphOn = false;
            writeControlSeq(type.getGraphOffSeq());
        }
    }

    public void writeControlSeq (char[] seq) throws IOException {
        super.write(seq, 0, seq.length);
    }
}

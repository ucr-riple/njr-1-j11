package be.demmel.fun.jgwcaconstants.bll;

import java.io.IOException;

public interface GWCAConnection {
    void sendPacket(GWCAPacket gwcaPacket) throws IOException;
    GWCAPacket receivePacket() throws IOException;
    void open() throws IOException;
    void close() throws IOException;
}

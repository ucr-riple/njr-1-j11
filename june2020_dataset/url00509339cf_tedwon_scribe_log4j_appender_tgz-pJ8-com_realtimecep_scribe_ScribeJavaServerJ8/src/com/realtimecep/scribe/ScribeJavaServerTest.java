package com.realtimecep.scribe;

import org.junit.Test;

/**
 * Scribe Client Quick Start Class.
 * <p/>See https://github.com/alexlod/scribe-log4j-appender/blob/master/ScribeAppender.java
 *
 * @author <a href="mailto:tedd824@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class ScribeJavaServerTest {

    @Test
    public void runServer() {

        ScribeJavaServer server = new ScribeJavaServer();
        server.runServer();

    }
}

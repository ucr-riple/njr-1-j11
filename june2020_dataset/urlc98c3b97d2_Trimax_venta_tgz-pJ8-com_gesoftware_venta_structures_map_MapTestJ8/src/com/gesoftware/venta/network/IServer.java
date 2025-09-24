package com.gesoftware.venta.network;

/* *
 * Server interface definition
 * */
public interface IServer extends Runnable {
    /* *
     * METHOD: Stops server and closes connections
     * AUTHOR: Eliseev Dmitry
     * */
    public void stop();
} /* End of 'IServer' interface */

package com.gesoftware.venta.network.handlers;

import com.gesoftware.venta.network.model.Message;

public interface IClientHandler {
    public void onReceive(final Message message);
    public void onConnectionLost(final String message);
}

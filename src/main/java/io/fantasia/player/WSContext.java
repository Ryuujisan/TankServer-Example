package io.fantasia.player;

import io.fantasia.packet.Protos;


public interface WSContext {
    void onConnect();
    void onClose(int statusCode, String reason);
    void onMessage(Protos.Packet data);
    void onError(Throwable error);

    AbstractConnection unwrap();
}

package io.fantasia.player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import io.fantasia.packet.Protos;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public abstract class AbstractConnection {
    private Session session;
    private WSContext context;

    public AbstractConnection() {

    }

    public void kick(String reason) {
        if (session != null) {
            try {
                session.close(1000, reason);
            } catch (IOException ignored) {

            }
        }
    }

    private void send(ByteBuffer data){
        session.getRemote().sendBytesByFuture(data);
    }

    public void send(Protos.Packet packet) {
        send(ByteBuffer.wrap(packet.toByteArray()));
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        context.onConnect();
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        context.onClose(statusCode, reason);
    }

    @OnWebSocketMessage
    public void onMessage(byte buf[], int offset, int length) {
        InputStream data = new ByteArrayInputStream(buf, offset, length);
        try {
            context.onMessage(Protos.Packet.parseFrom(data));
        } catch (IOException e) {
            kick(e.getMessage());
        }
    }

    @OnWebSocketError
    public void onError(Throwable error) {
        kick("AbstractConnection error: " + error.getMessage());
        context.onError(error);
    }

    public void setContext(WSContext context) {
        this.context = context;
    }
}

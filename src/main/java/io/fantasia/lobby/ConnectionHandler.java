package io.fantasia.lobby;

import io.fantasia.player.AbstractConnection;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


public class ConnectionHandler {
    private Server server;
    private AbstractLobby lobby;

    public ConnectionHandler(short port, AbstractLobby lobby) {
        this.lobby = lobby;
        server = new Server(port);

        ConnectionHandler that = this;
        server.setHandler(new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.setCreator(that::onNewConnection);
            }
        });
    }

    private Object onNewConnection(UpgradeRequest req, UpgradeResponse res) {
        try {
            AbstractConnection session = AbstractMainFactory.getInstance().newConnection();
            AbstractMainFactory.getInstance().newLobbyPlayer(lobby, session);
            return session;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startAccepting() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

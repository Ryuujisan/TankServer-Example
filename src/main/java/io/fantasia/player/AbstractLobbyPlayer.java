package io.fantasia.player;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.packet.Protos;
import io.fantasia.utils.Poolable;


public abstract class AbstractLobbyPlayer implements WSContext, Poolable {
    private final AbstractLobby lobby;
    private Integer id;
    private AbstractConnection connection;


    public AbstractLobbyPlayer(AbstractLobby lobby, AbstractConnection connection){
        this.lobby = lobby;
        this.connection = connection;
        connection.setContext(this);
    }

    public void kick(String reason) {
        connection.kick(reason);
    }

    public void send(Protos.Packet packet) {
        connection.send(packet);
    }

    @Override
    public void onConnect() {
        lobby.playerJoined(this);
    }

    @Override
    public void onClose(int statusCode, String reason) {
        lobby.playerLeaved(this);
    }

    @Override
    public void onMessage(Protos.Packet data) {
        lobby.packetHandler.push(this, data);
    }

    @Override
    public void onError(Throwable error) {
        kick(error.getMessage());
    }

    @Override
    public AbstractConnection unwrap() {
        lobby.playerLeaved(this);
        return getConnection();
    }

    public Integer getId() {
        return id;
    }

    public AbstractConnection getConnection() {
        return connection;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}

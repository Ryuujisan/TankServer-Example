package io.fantasia.lobby;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractConnection;
import io.fantasia.player.AbstractLobbyPlayer;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.room.AbstractGame;
import io.fantasia.room.AbstractPacketHandler;
import io.fantasia.room.AbstractRoom;


public abstract class AbstractMainFactory {
    private static AbstractMainFactory instance;

    public static void setInstance(AbstractMainFactory instance){
        AbstractMainFactory.instance = instance;
    }
    public static AbstractMainFactory getInstance(){
        return instance;
    }

    public abstract AbstractLobbyPacketHandler newLobbyPacketHandler(AbstractLobby lobby);
    public abstract AbstractPacketHandler newPacketHandler(AbstractRoom lobby);
    public abstract AbstractRoom newRoom(AbstractLobby lobby);
    public abstract AbstractGame newGame(AbstractRoom abstractRoom);
    public abstract AbstractLobbyPlayer newLobbyPlayer(AbstractLobby lobby, AbstractConnection connection);
    public abstract AbstractPlayer newPlayer(AbstractRoom room, AbstractConnection connection, Protos.JoinGame joinGame);
    public abstract AbstractConnection newConnection();
}

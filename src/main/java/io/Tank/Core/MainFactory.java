package io.Tank.Core;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.lobby.AbstractLobbyPacketHandler;
import io.fantasia.lobby.AbstractMainFactory;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractLobbyPlayer;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.player.AbstractConnection;
import io.fantasia.room.AbstractGame;
import io.fantasia.room.AbstractPacketHandler;
import io.fantasia.room.AbstractRoom;

class MainFactory extends AbstractMainFactory {

    @Override public AbstractLobbyPacketHandler newLobbyPacketHandler(AbstractLobby lobby) {
        return new LobbyPacketHandler(lobby);
    }

    @Override public AbstractPacketHandler newPacketHandler(AbstractRoom lobby) {
        return new PacketHandler(lobby);
    }

    @Override public AbstractRoom newRoom(AbstractLobby lobby) {
        return new Room(lobby);
    }

    @Override public AbstractGame newGame(AbstractRoom room) {
        return new Game(room);
    }

    @Override public AbstractLobbyPlayer newLobbyPlayer(AbstractLobby lobby, AbstractConnection connection) {
        return new LobbyPlayer(lobby, connection);
    }

    @Override public AbstractPlayer newPlayer(AbstractRoom room, AbstractConnection connection, Protos.JoinGame joinGame) {
        return new Player(room, connection, joinGame);
    }

    @Override public AbstractConnection newConnection() {
        return new Connection();
    }
}

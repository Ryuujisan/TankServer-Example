package io.tanks.core;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.player.AbstractLobbyPlayer;
import io.fantasia.player.AbstractConnection;

class LobbyPlayer extends AbstractLobbyPlayer {
    public LobbyPlayer(AbstractLobby lobby, AbstractConnection connection) {
        super(lobby, connection);
    }
}

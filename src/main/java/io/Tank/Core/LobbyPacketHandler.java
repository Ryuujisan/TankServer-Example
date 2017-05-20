package io.Tank.Core;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.lobby.AbstractLobbyPacketHandler;
import io.fantasia.packet.OwnedPacket;
import io.fantasia.player.AbstractLobbyPlayer;


class LobbyPacketHandler extends AbstractLobbyPacketHandler {
    public LobbyPacketHandler(AbstractLobby masterServer) {
        super(masterServer);
    }

    @Override protected void handleCustom(OwnedPacket<AbstractLobbyPlayer> message) {

    }
}

package io.Tank.Core;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractLobbyPlayer;


class Lobby extends AbstractLobby {
    public Lobby(short port) {
        super(port);
    }

    @Override protected void onPlayerJoined(AbstractLobbyPlayer player) {
        System.out.println("Player Joined Lobby.");
    }

    @Override protected void onPlayerLeaved(AbstractLobbyPlayer player) {
        System.out.println("Player Leaved Lobby.");
    }

    @Override public Protos.Lobby lobbyProto(AbstractLobbyPlayer player) {
        return Protos.Lobby.newBuilder()
                .build();
    }
}

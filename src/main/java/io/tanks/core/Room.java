package io.tanks.core;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.room.AbstractRoom;


class Room extends AbstractRoom {

    public Room(AbstractLobby lobby) {
        super(lobby);
        startGame();
    }

    @Override protected void onPlayerJoined(AbstractPlayer player, Protos.JoinGame joinGame) {
        System.out.println("Player " + ((Player)player).getName() + " Joined Room.");

    }

    @Override protected void onPlayerLeaved(AbstractPlayer player, String reason) {
        System.out.println("Player " + ((Player)player).getName() + " leaved Room: " + reason);
    }

    @Override public Protos.Room roomProto(AbstractPlayer player) {
        return Protos.Room.newBuilder()
                .setLocalId(player.getId())
                .addAllPlayers(() -> players.stream()
                        .map(otherPlayer -> Protos.PlayerJoined.newBuilder()
                                .setId(otherPlayer.getId())
                                .setName(((Player)otherPlayer).getName())
                                .build())
                        .iterator())
                .build();
    }

    @Override public Protos.PlayerJoined joinProto(AbstractPlayer player) {
        return Protos.PlayerJoined.newBuilder()
                .setId(player.getId())
                .setName(((Player)player).getName())
                .build();
    }

    @Override public Protos.PlayerLeaved leaveProto(AbstractPlayer player, String reason) {
        return Protos.PlayerLeaved.newBuilder()
                .setId(player.getId())
                .setReason(reason)
                .build();
    }
}

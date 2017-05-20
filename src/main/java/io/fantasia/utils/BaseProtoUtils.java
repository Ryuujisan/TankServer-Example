package io.fantasia.utils;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.room.AbstractGame;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractLobbyPlayer;
import io.fantasia.room.AbstractRoom;

import java.util.List;


public class BaseProtoUtils {

    public static Protos.Packet lobby(AbstractLobby lobby, AbstractLobbyPlayer player) {
        return Protos.Packet.newBuilder()
                .setLobby(lobby.lobbyProto(player))
                .build();
    }

    public static Protos.Packet room(AbstractRoom room, AbstractPlayer player) {
        return Protos.Packet.newBuilder()
                .setRoom(room.roomProto(player))
                .build();
    }

    public static Protos.Packet playerJoined(AbstractRoom room, AbstractPlayer player) {
        return Protos.Packet.newBuilder()
                .setPlayerJoined(room.joinProto(player))
                .build();
    }

    public static Protos.Packet playerLeaved(AbstractRoom room, AbstractPlayer player, String reason) {
        return Protos.Packet.newBuilder()
                .setPlayerLeaved(room.leaveProto(player, reason))
                .build();
    }

    public static Protos.Packet gameStart(AbstractGame game, AbstractPlayer player) {
        return Protos.Packet.newBuilder()
                .setGameStart(game.startProto(player))
                .build();
    }

    public static Protos.Packet gameEnd(AbstractGame game, AbstractPlayer player) {
        return Protos.Packet.newBuilder()
                .setGameEnd(game.endProto(player))
                .build();
    }

    public static Protos.Packet update(List<Protos.Update.Event> events) {
        return Protos.Packet.newBuilder()
                .setUpdate(Protos.Update.newBuilder()
                        .addAllEvents(events))
                .build();
    }


    public static Protos.Packet gameCurrentStatus(AbstractGame game, AbstractPlayer player) {
        return Protos.Packet.newBuilder()
                .setGameCurrentStatus(game.currentStatusProto(player))
                .build();
    }
}

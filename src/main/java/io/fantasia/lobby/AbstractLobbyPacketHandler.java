package io.fantasia.lobby;

import io.fantasia.packet.OwnedPacket;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractLobbyPlayer;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.room.AbstractRoom;
import io.fantasia.utils.Config;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;


public abstract class AbstractLobbyPacketHandler {
    private final Queue<OwnedPacket<AbstractLobbyPlayer>> incoming = new LinkedBlockingDeque<>();
    public final AbstractLobby lobby;

    public AbstractLobbyPacketHandler(AbstractLobby masterServer) {
        this.lobby = masterServer;
        masterServer.newTimer(this::cycle, 1000 / Config.getInteger("packetHandlingRate"));
    }

    public void push(AbstractLobbyPlayer player, Protos.Packet data) {
        OwnedPacket<AbstractLobbyPlayer> message = new OwnedPacket<>(player, data);
        if (message.packet.hasPing()) {
            player.send(message.packet);
        } else {
            incoming.add(message);
        }
    }

    public void cycle() {
        while (!incoming.isEmpty()) {
            handle(incoming.remove());
        }
    }

    private void handle(OwnedPacket<AbstractLobbyPlayer> message) {
        if (message.packet.hasJoinGame()) {
            joinGame(message.player, message.packet.getJoinGame());
        } else {
            handleCustom(message);
        }
    }

    protected abstract void handleCustom(OwnedPacket<AbstractLobbyPlayer> message);

    private void joinGame(AbstractLobbyPlayer lobbyPlayer, Protos.JoinGame joinGame) {
        AbstractRoom room = lobby.findRoom();
        AbstractPlayer player = AbstractMainFactory.getInstance().newPlayer(room, lobbyPlayer.unwrap(), joinGame);
        room.playerJoined(player, joinGame);
    }
}


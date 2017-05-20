package io.fantasia.room;

import io.fantasia.lobby.AbstractMainFactory;
import io.fantasia.packet.OwnedPacket;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractLobbyPlayer;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.utils.Config;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;


public abstract class AbstractPacketHandler {
    private final Queue<OwnedPacket<AbstractPlayer>> incoming;
    public final AbstractRoom room;

    public AbstractPacketHandler(AbstractRoom room) {
        this.room = room;
        incoming = new LinkedBlockingDeque<>();
        room.newTimer(this::cycle, 1000 / Config.getInteger("packetHandlingRate"));
    }

    public void push(AbstractPlayer player, Protos.Packet data) {
        OwnedPacket<AbstractPlayer> message = new OwnedPacket<>(player, data);
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

    private void handle(OwnedPacket<AbstractPlayer> message) {
        if (message.packet.hasLeaveGame()) {
            leaveGame(message.player, message.packet.getLeaveGame());
        } else {
            handleCustom(message);
        }
    }

    protected abstract void handleCustom(OwnedPacket<AbstractPlayer> message);

    private void leaveGame(AbstractPlayer player, Protos.LeaveGame leaveGame) {
        room.lobby.players.add(AbstractMainFactory.getInstance().newLobbyPlayer(room.lobby, player.unwrap()));
    }
}

package io.Tank.Core;

import io.fantasia.packet.OwnedPacket;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.room.AbstractPacketHandler;
import io.fantasia.room.AbstractRoom;

class PacketHandler extends AbstractPacketHandler {
    public PacketHandler(AbstractRoom room) {
        super(room);
    }

    @Override protected void handleCustom(OwnedPacket<AbstractPlayer> message) {
        if (message.packet.hasChat()) {
            chat(message.player, message.packet.getChat());
        }
    }

    private void chat(AbstractPlayer player, Protos.Chat chat) {
        room.sendAll(ProtoUtils.chat(player, chat.getText()));
    }
}

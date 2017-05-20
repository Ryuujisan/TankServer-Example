package io.Tank.Core;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;


class ProtoUtils {
    public static Protos.Packet chat(AbstractPlayer player, String message) {
        return Protos.Packet.newBuilder()
                .setChat(Protos.Chat.newBuilder()
                        .setText(message)
                        .setPlayerId(player.getId()))
                .build();
    }
}

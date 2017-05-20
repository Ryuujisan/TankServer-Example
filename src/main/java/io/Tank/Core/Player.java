package io.Tank.Core;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.player.AbstractConnection;
import io.fantasia.room.AbstractRoom;


class Player extends AbstractPlayer {
    private final String name;

    public Player(AbstractRoom room, AbstractConnection connection, Protos.JoinGame joinGame) {
        super(room, connection);
        name = joinGame.getName();
    }


    public String getName() {
        return name;
    }
}

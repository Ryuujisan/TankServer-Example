package io.tanks.core;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.player.AbstractConnection;
import io.fantasia.room.AbstractRoom;
import io.tanks.physics.*;


class Player extends AbstractPlayer {

    private final String name;
    private Tank tank;

    public Player(AbstractRoom room, AbstractConnection connection, Protos.JoinGame joinGame) {
        super(room, connection);
        name = joinGame.getName();
        tank = new Tank(((Game) room.getGame()).getPhysics());
    }

    public Tank getTank() {
        return tank;
    }

    public String getName() {
        return name;
    }
}

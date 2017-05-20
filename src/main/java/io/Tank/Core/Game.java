package io.Tank.Core;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.room.AbstractGame;
import io.fantasia.room.AbstractRoom;


class Game extends AbstractGame {
    public Game(AbstractRoom room) {
        super(room);
    }

    @Override protected void onStart() {
        System.out.println("Game has started.");
    }

    @Override protected void onSimulate(float dt) {

    }

    @Override protected void onEnd() {
        System.out.println("Game has ended.");
    }

    @Override public Protos.GameStart startProto(AbstractPlayer player) {
        return Protos.GameStart.newBuilder()
                .build();
    }

    @Override public Protos.GameEnd endProto(AbstractPlayer player) {
        return Protos.GameEnd.newBuilder()
                .build();
    }

    @Override public Protos.GameCurrentStatus currentStatusProto(AbstractPlayer player) {
        return Protos.GameCurrentStatus.newBuilder()
                .build();
    }
}

package io.tanks.core;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.room.AbstractGame;
import io.fantasia.room.AbstractRoom;
import io.tanks.physics.Physics;


public class Game extends AbstractGame {

    private Physics physics;

    public Game(AbstractRoom room) {
        super(room);
        physics = new Physics(this);
    }

    public Physics getPhysics() {
        return physics;
    }

    @Override protected void onStart() {
        System.out.println("Game has started.");
    }

    @Override protected void onSimulate(float dt) {
        if (physics == null) {
            return;
        }
        physics.simulateWorld();
        addEvent(ProtoUtils.updateGameplay((Room) room), EventFilter.everyone);
        room.players.forEach(player -> physics.simulateTank(((Player) player).getTank()));
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

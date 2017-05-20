package io.fantasia.room;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.utils.Config;
import io.fantasia.utils.BaseProtoUtils;

import java.util.TimerTask;


public abstract class AbstractGame {
    public AbstractRoom room;
    private TimerTask simulation;
    private TimerTask updater;
    private long lastSimulate = System.currentTimeMillis();

    public AbstractGame(AbstractRoom room){
        this.room = room;

        simulation = room.newTimer(this::simulate, 1000 / Config.getInteger("simulationRate"));
        updater = room.newTimer(this::sendEvents, 1000 / Config.getInteger("updateRate"));
    }

    private void simulate() {
        float dt = (System.currentTimeMillis() - lastSimulate) / 1000.0f;
        lastSimulate = System.currentTimeMillis();
        onSimulate(dt);
    }

    protected abstract void onSimulate(float dt);

    public void start() {
        onStart();
        room.players.forEach(player -> player.send(BaseProtoUtils.gameStart(this, player)));
    }

    protected abstract void onStart();

    public void end() {
        simulation.cancel();
        updater.cancel();
        onEnd();
        room.players.forEach(player -> player.send(BaseProtoUtils.gameEnd(this, player)));
    }

    protected abstract void onEnd();

    public void addEvent(Protos.Update.Event event, EventFilter filter) {
        for (AbstractPlayer player : room.players) {
            if(filter.visibleFor(player)) {
                player.addEvent(event);
            }
        }
    }

    private void sendEvents() {
        room.players.forEach(AbstractPlayer::sendEvents);
    }

    public abstract Protos.GameStart startProto(AbstractPlayer player);

    public abstract Protos.GameEnd endProto(AbstractPlayer player);

    public abstract Protos.GameCurrentStatus currentStatusProto(AbstractPlayer player);

    public interface EventFilter {
        EventFilter everyone = player -> true;
        EventFilter none = player -> false;
        boolean visibleFor(AbstractPlayer player);
    }
}

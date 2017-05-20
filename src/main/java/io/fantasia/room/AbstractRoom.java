package io.fantasia.room;

import io.fantasia.lobby.AbstractLobby;
import io.fantasia.lobby.AbstractMainFactory;
import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.fantasia.utils.Pool;
import io.fantasia.utils.Poolable;
import io.fantasia.utils.BaseProtoUtils;

import java.util.Timer;
import java.util.TimerTask;


public abstract class AbstractRoom implements Poolable {
    private int id;
    private final Timer timer = new Timer();
    private AbstractGame game;
    private boolean locked;
    public final AbstractLobby lobby;
    public final Pool<AbstractPlayer> players = new Pool<>();
    public final AbstractPacketHandler packetHandler;

    public AbstractRoom(AbstractLobby lobby) {
        this.lobby = lobby;
        packetHandler = AbstractMainFactory.getInstance().newPacketHandler(this);
    }

    public void playerJoined(AbstractPlayer player, Protos.JoinGame joinGame) {
        players.add(player);
        sendAllExcept(BaseProtoUtils.playerJoined(this, player), player);
        player.send(BaseProtoUtils.room(this, player));

        onPlayerJoined(player, joinGame);

        if(game != null){
            player.send(BaseProtoUtils.gameCurrentStatus(game, player));
        }
    }

    protected abstract void onPlayerJoined(AbstractPlayer player, Protos.JoinGame joinGame);

    public void playerLeaved(AbstractPlayer player, String reason) {
        onPlayerLeaved(player, reason);

        players.remove(player);
        sendAll(BaseProtoUtils.playerLeaved(this, player, reason));
    }

    protected abstract void onPlayerLeaved(AbstractPlayer player, String reason);

    public boolean startGame() {
        if(game == null){
            game = AbstractMainFactory.getInstance().newGame(this);
            game.start();
            return true;
        } else {
            return false;
        }
    }

    public boolean endGame() {
        if(game == null){
            return false;
        } else {
            game.end();
            game = null;
            return false;
        }
    }

    public void sendAll(Protos.Packet packet) {
        for(AbstractPlayer player : players){
            player.send(packet);
        }
    }

    private void sendAllExcept(Protos.Packet packet, AbstractPlayer except) {
        for(AbstractPlayer player : players){
            if(player == except) continue;
            player.send(packet);
        }
    }

    public TimerTask newTimer(Runnable task, long delay){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, delay / 2, delay);

        return timerTask;
    }

    public TimerTask newTimeout(Runnable task, long delay){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, delay);

        return timerTask;
    }

    @Override public void setId(int id) {
        this.id = id;
    }

    public AbstractGame getGame() {
        return game;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public abstract Protos.Room roomProto(AbstractPlayer player);

    public abstract Protos.PlayerJoined joinProto(AbstractPlayer player);

    public abstract Protos.PlayerLeaved leaveProto(AbstractPlayer player, String reason);
}

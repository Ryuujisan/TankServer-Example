package io.fantasia.lobby;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractLobbyPlayer;
import io.fantasia.room.AbstractRoom;
import io.fantasia.utils.BaseProtoUtils;
import io.fantasia.utils.Config;
import io.fantasia.utils.Pool;

import java.util.Timer;
import java.util.TimerTask;


public abstract class AbstractLobby {
    private final Timer timer = new Timer();
    private final Pool<AbstractRoom> rooms = new Pool<>();
    private final ConnectionHandler connectionHandler;
    public final Pool<AbstractLobbyPlayer> players = new Pool<>();
    public final AbstractLobbyPacketHandler packetHandler;


    public AbstractLobby(short port) {
        this.packetHandler = AbstractMainFactory.getInstance().newLobbyPacketHandler(this);
        connectionHandler = new ConnectionHandler(port, this);
    }

    public void playerJoined(AbstractLobbyPlayer player) {
        players.add(player);
        player.send(BaseProtoUtils.lobby(this, player));

        onPlayerJoined(player);
    }

    protected abstract void onPlayerJoined(AbstractLobbyPlayer player);

    public void playerLeaved(AbstractLobbyPlayer player) {
        players.remove(player);

        onPlayerLeaved(player);
    }

    protected abstract void onPlayerLeaved(AbstractLobbyPlayer player);

    public AbstractRoom findRoom() {
        return rooms.stream()
                .filter(room -> room.players.size() < Config.getInteger("roomMaxPlayers") && !room.getLocked())
                .findAny()
                .orElseGet(() -> {
                    AbstractRoom room = AbstractMainFactory.getInstance().newRoom(this);
                    rooms.add(room);
                    return room;
                });
    }


    public void start() {
        connectionHandler.startAccepting();
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

    public abstract Protos.Lobby lobbyProto(AbstractLobbyPlayer player);
}

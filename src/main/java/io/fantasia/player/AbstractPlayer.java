package io.fantasia.player;

import io.fantasia.packet.Protos;
import io.fantasia.room.AbstractRoom;
import io.fantasia.utils.BaseProtoUtils;
import io.fantasia.utils.Poolable;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractPlayer implements WSContext, Poolable {
    private final AbstractRoom room;
    private Integer id;
    private AbstractConnection connection;
    private List<Protos.Update.Event> events = new ArrayList<>();

    public AbstractPlayer(AbstractRoom room, AbstractConnection connection){
        this.room = room;
        this.connection = connection;
        if(connection != null){
            connection.setContext(this);
        }
    }

    public void kick(String reason) {
        connection.kick(reason);
    }

    public void send(Protos.Packet packet) {
        connection.send(packet);
    }

    @Override
    public void onConnect() {
        room.playerJoined(this, null);
    }

    @Override
    public void onClose(int statusCode, String reason) {
        room.playerLeaved(this, reason);
    }

    @Override
    public void onMessage(Protos.Packet data) {
        room.packetHandler.push(this, data);
    }

    @Override
    public void onError(Throwable error) {
        kick(error.getMessage());
    }

    @Override
    public AbstractConnection unwrap() {
        room.playerLeaved(this, "AbstractPlayer has left the gameStart.");
        return connection;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void addEvent(Protos.Update.Event event) {
        events.add(event);
    }

    public void sendEvents() {
        if(events.isEmpty()) return;
        send(BaseProtoUtils.update(events));
        events.clear();
    }
}

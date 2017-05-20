package io.fantasia.packet;

public class OwnedPacket<Owner> {
    public Owner player;
    public io.fantasia.packet.Protos.Packet packet;

    public OwnedPacket(Owner player, io.fantasia.packet.Protos.Packet data) {
        this.player = player;
        this.packet = data;
    }
}

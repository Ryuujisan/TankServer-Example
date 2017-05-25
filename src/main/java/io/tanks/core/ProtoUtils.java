package io.tanks.core;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import org.jbox2d.common.Vec2;


class ProtoUtils {

    public static Protos.Packet chat(AbstractPlayer player, String message) {
        return Protos.Packet.newBuilder()
                .setChat(Protos.Chat.newBuilder()
                        .setText(message)
                        .setPlayerId(player.getId()))
                .build();
    }

    public static void playerSteering(Player player, Protos.PlayerStering packet) {
        if (packet.hasDirX() && packet.hasDirY()) player.getTank().setDirectionalVector(packet.getDirX(), packet.getDirX());
        if (packet.hasBarrelDirX() && packet.hasBarrelDirY()) player.getTank().setAimingVector(packet.getBarrelDirX(), packet.getBarrelDirY());
        if (packet.hasAccelerates()) player.getTank().setAccelerating(packet.getAccelerates());
        if (packet.hasShot()) player.getTank().setShooting(packet.getShot());
    }

    private static Protos.Player player(Player player) {
        return Protos.Player.newBuilder()
                .setBarrelCourse(player.getTank().getAimingVectorsAngleInRadians())
                .setId(player.getId())
                .setPositionX(player.getTank().getPosition().x)
                .setPostionY(player.getTank().getPosition().y)
                .setTankCourse(player.getTank().getBodyAngleInRadians())
                .build();
    }

    public static Protos.Update.Event updateGameplay(Room room) {
        return Protos.Update.Event.newBuilder()
                .setUpdateGamePlay(
                        Protos.UpdateGamePlay.newBuilder()
                        .addAllPlayer(() -> room.players.stream().map(player -> player((Player) player))
                                .iterator())
                        .build()
                ).build();
    }


}

package io.tanks.core;

import io.fantasia.packet.Protos;
import io.fantasia.player.AbstractPlayer;
import io.tanks.physics.Bullet;
import org.jbox2d.common.Vec2;


public class ProtoUtils {

    public static Protos.Packet chat(AbstractPlayer player, String message) {
        return Protos.Packet.newBuilder()
                .setChat(Protos.Chat.newBuilder()
                        .setText(message)
                        .setPlayerId(player.getId()))
                .build();
    }

    public static void playerSteering(Player player, Protos.PlayerStering packet) {
        if (packet.hasDirX() && packet.hasDirY()) {
            player.getTank().setDirectionalVector(packet.getDirX(), packet.getDirY());
        }
        if (packet.hasBarrelDirX() && packet.hasBarrelDirY()) {
            player.getTank().setAimingVector(packet.getBarrelDirX(), packet.getBarrelDirY());
        }
        if (packet.hasShot()) {
            System.out.println(packet.getShot());
            player.getTank().setShooting(packet.getShot());
        }
        player.getTank().setAccelerating(packet.hasAccelerates());
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

    public static Protos.Update.Event bulletFire(Bullet bullet) {
        return Protos.Update.Event.newBuilder()
                .setBulletFire(
                        Protos.BulletFire.newBuilder()
                                .setBulletID(bullet.getID())
                                .setPosX(bullet.getBody().getPosition().x)
                                .setPosY(bullet.getBody().getPosition().y)
                                .setVelX(bullet.getBody().getLinearVelocity().x)
                                .setVelY(bullet.getBody().getLinearVelocity().y)
                                .build()
                ).build();
    }
}

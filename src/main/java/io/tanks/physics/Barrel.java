package io.tanks.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bezik on 08.02.17.
 */
public class Barrel {

    private Tank tank;
    private long lastShotTimestamp = 0l;

    private float angle;

    private List<Bullet> bullets;

    public Barrel(Tank tank) {
        this.tank = tank;
        bullets = new ArrayList<Bullet>();
    }

    public void update(float dt, boolean isShooting) {
        updatePosition(dt);

        if (isShooting)
            shot();

        for (int i=0; i<bullets.size(); i++)
            bullets.get(i).update();
    }

    public void shot() {
        //TODO delay first shot (if lastshot was more than 3 secs ago then wait 1.5 secs
        if (System.currentTimeMillis() - lastShotTimestamp > tank.getModel().getShootingDelay()) {
            bullets.add(new Bullet(tank,
                    calculateBulletPosition(), calculateBulletDirection()));
            lastShotTimestamp = System.currentTimeMillis();
        }
    }

    private Vec2 calculateBulletDirection() {
        return Utils.getVector(Utils.degreesToRadians(getAngleInDegrees()), 1f);
    }

    private Vec2 calculateBulletPosition() {
        float distanceFromCenter = 30f;
        Vec2 center = getPosition();
        Vec2 move = Utils.getVector(Utils.degreesToRadians(getAngleInDegrees()), distanceFromCenter / Physics.PPM);
        Vec2 position = center.add(move);
        return position;
    }

    public void updatePosition(float dt) {
        float destination = tank.getAimingVectorsAngleInDegrees();
        float difference = Math.abs(destination - angle);

        float divider = dt / Physics.FPS60;

        if (angle <= 180 && difference > 180) {
            angle -= (360 - difference) / (tank.getModel().getBarrelTurningSpeed() / divider);
            if (angle < 0.01f) {
                angle = 360;
            }
        } else if (angle > 180 && difference > 180) {
            angle += (360 - difference) / (tank.getModel().getBarrelTurningSpeed() / divider);
            if (angle > 359.99f) {
                angle = 0;
            }
        } else {
            angle += (destination - angle) / (tank.getModel().getBarrelTurningSpeed() / divider);
        }
    }

    public float getAngleInDegrees() {
        return angle;
    }

    public float getAngleInRadians() {
        return Utils.degreesToRadians(getAngleInDegrees());
    }

    public Vec2 getPosition() {
        Vec2 barrelPosition = tank.getBody().getWorldPoint(tank.getModel().getBarrelPosition());
//        barrelPosition.mul(Physics.PPM);
        return barrelPosition;
    }

    public void removeBullet(Bullet bullet) {
        if (bullets.contains(bullet)) {
            bullets.remove(bullet);
        }
    }

    public void destroy() {
        for (int i=0; i<bullets.size(); i++) {
            Body body = bullets.get(i).getBody();
            tank.getPhysics().addBodyToRemove(body);
        }
        bullets.clear();
    }

}

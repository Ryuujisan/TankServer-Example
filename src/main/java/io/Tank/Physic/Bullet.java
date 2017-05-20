package Physic;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 * Created by bezik on 08.02.17.
 */
public class Bullet {

    private static final float bulletVelocity = 120f;
    private Tank tank;
    private long spawnTimestamp;
    private long bulletLifelength;
    private Body body;


    public Bullet(Tank tank, Vec2 position, Vec2 direction) {
        this.tank = tank;
        spawn(tank.getBody().getWorld(), position, direction);
        calculateBulletLifelength(tank.getModel().getBulletRange());
    }

    private void calculateBulletLifelength(float range) {
        //TODO uzaleznic od DT
        bulletLifelength = (long) (range / bulletVelocity * 1000);
    }

    private void spawn(World world, Vec2 position, Vec2 direction) {
        synchronized (world) {
            spawnTimestamp = System.currentTimeMillis();
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position.set(position.x / Physics.PPM, position.y / Physics.PPM);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.filter.categoryBits = ContactHandler.BULLET_BIT;

            CircleShape shape = new CircleShape();
            shape.m_radius = tank.getModel().getBulletRadius() / Physics.PPM;

            fixtureDef.shape = shape;

            body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(this);

            applyForce(direction);
        }
    }

    private void applyForce(Vec2 direction) {
        body.setLinearVelocity(direction.mul(bulletVelocity));
    }

    public void update() {
        if (System.currentTimeMillis() - spawnTimestamp > bulletLifelength) {
            destroyBullet();
        }
    }

    public Body getBody() {
        return body;
    }

    public void destroyBullet() {
        tank.getPhysics().addBodyToRemove(body);
        tank.getBarrel().removeBullet(this);
    }

    public Tank getTank() {
        return tank;
    }
}

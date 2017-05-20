package Physic;

import Physic.TankModels.TankModel;
import Physic.TankModels.TankModel_Default;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import static Physic.Utils.angle;

/**
 * Created by bezik on 08.02.17.
 */
public class Tank {

    private Body body;
    private Vec2 directionalVector;
    private Vec2 aimingVector;
    private boolean accelerating = false;
    private boolean shooting = false;
    private Barrel barrel;
    private TankModel model;
    private Physics physics;

    private long tankID;

    public Tank(Physics physics) {
        directionalVector = new Vec2(0, 1);
        aimingVector = new Vec2(0, 1);
        barrel = new Barrel(this);
        model = new TankModel_Default();
        this.physics = physics;
        physics.addTank(this);
    }

    public void update(float dt) {
        barrel.update(dt, isShooting());
    }

    public Body getBody() {
        return body;
    }

    public float getAimingVectorsAngleInDegrees() {
        return angle(aimingVector);
    }

    public void setDirectionalVector(Vec2 value) {
        directionalVector = value;
    }

    public float getDirectionalVectorAngle() {
        return angle(directionalVector);
    }

    public void setAimingVector(Vec2 value) {
        aimingVector = value;
    }

    public boolean isAccelerating() {
        return accelerating;
    }

    public Vec2 getStartingPosition() {
        return new Vec2(0 / Physics.PPM, 0 / Physics.PPM);
    }

    public float getStartingAngle() {
        return 0f;
    }

    public float getBodyAngleInDegrees() {
        return Utils.radiansToDegrees(body.getAngle() - Physics.PI/2);
    }

    public float getBodyAngleInRadians() {
        return body.getAngle() - Physics.PI/2;
    }

    public void setBody(Body body) {
        this.body = body;
    }



    public void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public Vec2 getPosition() {
        Vec2 position = body.getPosition();
        position.mul(Physics.PPM);
        return position;
    }

    public long getTankID() {
        return tankID;
    }

    public void setTankID(long tankID) {
        this.tankID = tankID;
    }

    public boolean isSteeringBlocked() {
        return false;
    }

    public TankModel getModel() {
        return model;
    }

    public Barrel getBarrel() {
        return barrel;
    }

    public Physics getPhysics() {
        return physics;
    }

    public void destroyAllBodies() {
        synchronized (body.getWorld()) {
            body.getWorld().destroyBody(body);
            barrel.destroy();
        }
    }

    public boolean gotShot(Bullet bullet) {
        if (this != bullet.getTank()) {
            System.out.println("Just got shot!");
            //Do something about this
            return true;
        } else {
            return false;
        }
    }

    public Vec2 getDirectionalVector() {
        return directionalVector;
    }
}

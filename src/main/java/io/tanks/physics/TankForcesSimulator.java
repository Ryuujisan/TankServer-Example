package io.tanks.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import static io.tanks.physics.Physics.FPS60;
import static io.tanks.physics.Physics.PI;

public class TankForcesSimulator {

    public static void simulate(Tank tank, float dt) {
        simulateRotation(tank, dt);
        simulateForces(tank, dt);
    }


    private static void simulateRotation(Tank tank, float dt) {
        if (tank.isAccelerating()) {
            Body body = tank.getBody();
            float difference = (Utils.degreesToRadians(tank.getDirectionalVectorAngle())) -
                    body.getAngle();
            difference = Utils.normalize(difference, -PI, PI);
            difference = Math.signum(difference) * Math.min(tank.getModel().getMaxTurningSpeed()
                    * (dt / FPS60), Math.abs(difference));

            float targetAngle = difference - (body.getAngularVelocity() * dt);
            float desiredAngularVelocity = targetAngle / dt;
            float torque = body.getInertia() * desiredAngularVelocity * 0.8f;

            body.applyAngularImpulse(torque);
        }
    }

    private static void simulateForces(Tank tank, float dt) {
        applyEngineForce(tank, dt);
        killLateralVelocity(tank, dt);
        applyBreakingForce(tank, dt);
    }

    private static void applyEngineForce(Tank tank, float dt) {
        if (tank.isAccelerating() && !tank.isSteeringBlocked()) {
            Body body = tank.getBody();

            float bodyAngle = body.getAngle() - PI / 2;

            float maxSpeed = tank.getModel().getSpeed();
            float maxForce = maxSpeed * 0.75f;

            float velocityChange = maxSpeed - body.getLinearVelocity().length();
            float impulse = velocityChange * body.getMass();
            impulse = Math.min(maxForce, impulse) * (dt / FPS60);

            Vec2 directional = Utils.getVector(bodyAngle, impulse);

            body.applyLinearImpulse(directional, body.getWorldCenter());
        }
    }


    private static void killLateralVelocity(Tank tank, float dt) {
        Body body = tank.getBody();
        Vec2 currentRightNormal = body.getWorldVector(new Vec2(0, 1));
        Vec2 lateralVelocity = currentRightNormal;
        float force = body.getMass() * Vec2.dot(currentRightNormal, body.getLinearVelocity())
                * tank.getModel().getDriftMultiplier();
        Vec2 lateralVector = lateralVelocity.mul(force);
        body.applyLinearImpulse(lateralVector, body.getWorldCenter());
    }

    private static void applyBreakingForce(Tank tank, float dt) {
        if (!tank.isAccelerating()) {
            Body body = tank.getBody();
            float multiplier = tank.getModel().getBreakingSpeed() /
                    ((float) Math.pow((double) (dt / FPS60), 0.125));
            multiplier = Math.min(0.99f, multiplier);
            body.setLinearVelocity(body.getLinearVelocity().mul(multiplier));
            body.setAngularVelocity(body.getAngularVelocity() * multiplier);
        }
    }
}

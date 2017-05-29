package io.tanks.physics.models;

import io.tanks.physics.Physics;
import org.jbox2d.common.Vec2;

public class TankModel_Default extends TankModel {

    private static final float WIDTH = 32f;
    private static final float HEIGHT = 17f;
    private static final long SHOOTING_DELAY = 1500l;

    private static final float SPEED_MULTIPLIER = 12.0f;
    private static final float TURNING_SPEED_MULTIPLIER = 1.0f;
    private static final float BARREL_TURNING_SPEED_MULTIPLIER = 1.0f;
    private static final float BREAKING_SPEED_MULTIPLIER = 1.0f;
    private static final float DRIFT_MULTIPLIER = 1.0f;
    private static final float BULLET_RANGE_MULTIPLIER = 1.0f;

    protected static final Vec2 BARREL_POSITION = new Vec2(-6f / Physics.PPM, 0f);

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public float getMaxTurningSpeed() {
        return DEFAULT_MAX_TURNING_SPEED * TURNING_SPEED_MULTIPLIER;
    }

    @Override
    public float getDriftMultiplier() {
        return DEFAULT_DRIFT_MULTIPLIER * DRIFT_MULTIPLIER;
    }

    @Override
    public float getBreakingSpeed() {
        return DEFAULT_BREAKING_SPEED * BREAKING_SPEED_MULTIPLIER;
    }

    @Override
    public Vec2 getBarrelPosition() {
        return BARREL_POSITION;
    }

    @Override
    public float getBarrelTurningSpeed() {
        return DEFAULT_BARREL_TURNING_SPEED * BARREL_TURNING_SPEED_MULTIPLIER;
    }

    @Override
    public long getShootingDelay() {
        return SHOOTING_DELAY;
    }

    @Override
    public float getSpeed() {
        return DEFAULT_SPEED * SPEED_MULTIPLIER;
    }

    @Override
    public float getBulletRange() {
        return DEFAULT_BULLET_RANGE * BULLET_RANGE_MULTIPLIER;
    }
}

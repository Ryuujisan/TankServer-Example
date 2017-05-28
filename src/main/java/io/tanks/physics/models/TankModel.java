package io.tanks.physics.models;


import org.jbox2d.common.Vec2;

public abstract class TankModel {

    protected static final float DEFAULT_MAX_TURNING_SPEED = 0.035f;
    protected static final float DEFAULT_DRIFT_MULTIPLIER = -0.075f;
    protected static final float DEFAULT_BREAKING_SPEED = 0.97f;
    protected static final float DEFAULT_BARREL_TURNING_SPEED = 20f;
    protected static final float DEFAULT_SPEED = 1.5f;
    protected static final float DEFAULT_BULLET_RANGE = 40f;
    private static final float BULLET_RADIUS = 5f;

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract float getMaxTurningSpeed();

    public abstract float getDriftMultiplier();

    public abstract float getBreakingSpeed();

    public abstract float getBarrelTurningSpeed();

    public abstract Vec2 getBarrelPosition();

    public abstract float getSpeed();

    public abstract long getShootingDelay();

    public abstract float getBulletRange();

    public float getBulletRadius() {
        return BULLET_RADIUS;
    }
}



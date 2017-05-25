package io.tanks.physics;

import org.jbox2d.common.Vec2;

public class Utils {

    public static float normalize(float angle, float min, float max) {
        while (angle > max) {
            angle = angle - (max - min);
        }
        while (angle < min) {
            angle = angle + (max - min);
        }
        return angle;
    }

    public static float limit(float angle, float min, float max) {
        if (angle > max) {
            angle = max;
        } else if (angle < min) {
            angle = min;
        }
        return angle;
    }

    public static float radiansToDegrees(float angle) {
        return angle / ((float) Math.PI / 180f);
    }

    public static float degreesToRadians(float angle) { return angle * ((float) Math.PI / 180f); }

    public static Vec2 getVector(float angleRadians, float length) {
        return new Vec2(-length * (float) Math.sin(angleRadians), length * (float) Math.cos(angleRadians));
    }

    public static float angle(Vec2 v) {
        float angle = radiansToDegrees((float) Math.atan2(v.y, v.x));
        if (angle < 0) angle += 360;
        return angle;
    }
}

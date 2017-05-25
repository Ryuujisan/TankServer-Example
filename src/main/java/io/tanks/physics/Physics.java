package io.tanks.physics;



import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by bezik on 04.02.17.
 */

public class Physics {

    public static final float PPM = 10;

    public static final float PI = (float) Math.PI;
    public static final float FPS60 = 1f / 60f;

    private float dt = 0.001f; //0.001f is NOT actual delta time, but it have to be initialized as ANYTHING at start to prevent bugs
    private long lastSimulationTimestamp = System.currentTimeMillis();
    private World world;

    private List<Body> bodiesScheduledToDestroy;

    private ContactHandler contactHandler;

    public Physics() {
        Vec2 gravity = new Vec2(0.0f, 0.0f);
        world = new World(gravity);
        bodiesScheduledToDestroy = new ArrayList<Body>();
        contactHandler = new ContactHandler();
        world.setContactListener(contactHandler);
    }

    public void simulateWorld() {
        synchronized (world) {
            step();
            removeScheduledBodies();
        }
    }

    private void step() {
        dt = (float) (System.currentTimeMillis() - lastSimulationTimestamp) / 1000.0f;
        world.step(dt, 3, 1);
        lastSimulationTimestamp = System.currentTimeMillis();
    }

    private void removeScheduledBodies() {
        if (!world.isLocked()) {
            Iterator iterator = bodiesScheduledToDestroy.iterator();
            while (iterator.hasNext()) {
                Body body = (Body) iterator.next();
                world.destroyBody(body);
                iterator.remove();
            }
            bodiesScheduledToDestroy.clear();
        }
    }

    public void addTank(Tank tank) {
        synchronized (world) {
            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DYNAMIC;
            body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox((tank.getModel().getWidth() - 15) / PPM,
                    tank.getModel().getHeight() / PPM, new Vec2(-15 / PPM, 0), 0);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 1.0f;
            fixtureDef.isSensor = false;
            fixtureDef.filter.categoryBits = ContactHandler.TANK_BIT;
            fixtureDef.filter.maskBits = ContactHandler.EVERYTHING;
            body.createFixture(fixtureDef);

            CircleShape circle = new CircleShape();
            circle.m_radius = (tank.getModel().getHeight() + 2) / PPM;
            circle.m_p.set(new Vec2(tank.getModel().getWidth()*0.5f/PPM, 0));

            fixtureDef.shape=circle;
            body.createFixture(fixtureDef);


            body.setUserData(tank);
            body.setTransform(tank.getStartingPosition(), tank.getStartingAngle());
            tank.setBody(body);
        }
    }

    public void simulateTank(Tank tank) {
        tank.update(dt);
        synchronized (world) {
            TankForcesSimulator.simulate(tank, dt);
        }
    }

    public void addBodyToRemove(Body body) {
        bodiesScheduledToDestroy.add(body);
    }

}

package io.tanks.physics;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactHandler implements ContactListener {


    public static final short TANK_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short STATIC_BIT = 8;

    public static final short EVERYTHING = TANK_BIT | BULLET_BIT | STATIC_BIT;

    @Override
    public void beginContact(Contact contact) {
        checkForBulletAndTankContact(contact);
    }

    private void checkForBulletAndTankContact(Contact contact) {
        if (isItTheseTwo(contact, TANK_BIT, BULLET_BIT)) {
            Body bulletBody = contact.getFixtureA().getFilterData().categoryBits == BULLET_BIT ?
                    contact.getFixtureA().getBody() : contact.getFixtureB().getBody();
            Body tankBody = contact.getFixtureA().getFilterData().categoryBits == TANK_BIT ?
                    contact.getFixtureA().getBody() : contact.getFixtureB().getBody();

            Bullet bullet = ((Bullet ) bulletBody.getUserData());
            Tank tank = ((Tank) tankBody.getUserData());

            if (tank.gotShot(bullet)) {
                bullet.destroyBullet();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isItTheseTwo(Contact contact, short filterA, short filterB) {
        return ((contact.getFixtureA().getFilterData().categoryBits == filterA &&
                contact.getFixtureB().getFilterData().categoryBits == filterB) ||
                (contact.getFixtureB().getFilterData().categoryBits == filterA &&
                        contact.getFixtureA().getFilterData().categoryBits == filterB));
    }
}
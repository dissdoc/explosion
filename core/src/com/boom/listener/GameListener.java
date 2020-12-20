package com.boom.listener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.boom.domain.entity.BodyData;
import com.boom.domain.entity.HeroActor;

import static com.boom.Config.*;

public class GameListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        if (contact.isTouching()) {
            Fixture attacker = contact.getFixtureA();
            Fixture defender = contact.getFixtureB();

            if (isCollideHeroAndFloor(attacker, defender)) {
                BodyData attackerData = (BodyData) attacker.getBody().getUserData();
                BodyData defenderData = (BodyData) defender.getBody().getUserData();

                PositionCollide collide = isCollide(
                    attacker.getBody().getPosition(),
                    defender.getBody().getPosition(),
                    attackerData,
                    defenderData
                );

                HeroActor actor = (HeroActor) attackerData.def;
                // Если столкнулись с любой стороны, то прыжок сразу завершаем и начинаем падать
//                if (collide != null) {
//                    actor.isJump = false;
//                    actor.isFall = true;
//                }

//                if (PositionCollide.BOTTOM.equals(collide)) {
//                    actor.isJump = false;
//                    actor.isClimb = true;
//                } else if (PositionCollide.TOP.equals(collide)) {
//
//                } else if (PositionCollide.LEFT.equals(collide)) {
//                    if (actor.isJump) actor.isClimb = false;
//                } else if (PositionCollide.RIGHT.equals(collide)) {
//                    if (actor.isJump) actor.isClimb = false;
//                }
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

    private boolean isCollideHeroAndFloor(Fixture fixtureA, Fixture fixtureB) {
        return Entity.HERO_NAME.equals(fixtureA.getUserData()) &&
                Obstacle.FLOOR_NAME.equals(fixtureB.getUserData());
    }

    private PositionCollide isCollide(Vector2 vec1, Vector2 vec2, BodyData data1, BodyData data2) {
        float attackerX = (float) ((float) Math.round(vec1.x * 10.0)/10.0);
        float attackerY = (float) ((float) Math.round(vec1.y * 10.0)/10.0);

        float defenderX = (float) ((float) Math.round(vec2.x * 10.0)/10.0);
        float defenderY = (float) ((float) Math.round(vec2.y * 10.0)/10.0);

        float leftX1 = attackerX - data1.width/2;
        float rightX1 = attackerX + data1.width/2;
        float bottomY1 = attackerY - data1.height/2;
        float topY1 = attackerY + data1.height/2;

        float leftX2 = defenderX - data2.width/2;
        float rightX2 = defenderX + data2.width/2;
        float bottomY2 = defenderY - data2.height/2;
        float topY2 = defenderY + data2.height/2;

        if (rightX1 == leftX2)
            return PositionCollide.RIGHT;
        if (bottomY1 == topY2)
            return PositionCollide.BOTTOM;
        if (topY1 == bottomY2)
            return PositionCollide.TOP;
        if (leftX1 == rightX2)
            return PositionCollide.LEFT;
        return null;
    }

    private static enum PositionCollide {
        TOP, BOTTOM, LEFT, RIGHT
    }
}

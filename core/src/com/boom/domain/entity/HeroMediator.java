package com.boom.domain.entity;

import com.boom.items.models.Hero;

public class HeroMediator {

    public Hero hero;
    public HeroActor heroActor;

    public void jump() {
        if (!hero.isJump()) {
            hero.jump();
            heroActor.jump(hero.getJumpImpulse());
        }
    }

    public void run() {
//        hero.run();
//        heroActor.leftRun();
    }
}

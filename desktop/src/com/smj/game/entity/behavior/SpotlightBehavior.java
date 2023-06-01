package com.smj.game.entity.behavior;

import com.smj.game.GameLevel;
import com.smj.game.entity.GameEntity;
import com.smj.util.mask.Circle;

public class SpotlightBehavior implements EntityBehavior {
    public int radius = 0;
    public int strength = 0;
    public SpotlightBehavior(Integer radius, Integer strength) {
        this.radius = radius;
        this.strength = strength;
    }
    public void onLoad(GameEntity entity, GameLevel level) {
        entity.spotlight = new Circle(radius, strength);
    }
}

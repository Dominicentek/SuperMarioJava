package com.smj.game.tile;

import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.particle.ExplosionParticle;
import com.smj.game.particle.SmokeParticle;

public class MeltableTile extends GameTile {
    public int replacement;
    public MeltableTile(int replacement) {
        this.replacement = replacement;
    }
    public void onTouch(GameEntity entity, GameLevel level, int x, int y) {
        if (entity.entityType == EntityType.FIREBALL) {
            level.setTileAt(replacement, x, y);
            Game.particles.add(new SmokeParticle(x * 16 + 8, y * 16 + 8));
            level.getEntityManager().unloadEntity(entity);
            Game.particles.add(new ExplosionParticle(entity.getPhysics().getHitbox().x * 16 / 100, entity.getPhysics().getHitbox().y * 16 / 100));
        }
    }
}

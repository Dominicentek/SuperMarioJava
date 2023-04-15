package com.smj.game.entity;

import com.badlogic.gdx.Gdx;
import com.smj.game.GameLevel;
import com.smj.game.entity.behavior.*;
import com.smj.game.entity.texture.*;
import com.smj.jmario.entity.EntityProperties;
import com.smj.jmario.entity.physics.PhysicsConfig;
import com.smj.util.InstanceBuilder;

import java.awt.Dimension;

import static com.smj.util.TextureLoader.*;

public enum EntityType {
    PLAYER(new InstanceBuilder<>(PlayerTextureProvider.class, get(Gdx.files.internal("assets/images/entities/mario.png"))), new Dimension(75, 88), new InstanceBuilder<>(PlayerBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    STOMPED_GOOMBA(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/goomba_stomped.png"))), new Dimension(100, 50), new InstanceBuilder<>(DespawnBehavior.class, 60)),
    GOOMBA(new InstanceBuilder<>(WalkingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/goomba.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(StompableBehavior.class, EntityType.STOMPED_GOOMBA), new InstanceBuilder<>(WalkingBehavior.class, 0.087890625), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    GREEN_KOOPA_SHELL(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/green_koopa_shell.png"))), new Dimension(100, 100), new InstanceBuilder<>(ShellBehavior.class), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    RED_KOOPA_SHELL(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/red_koopa_shell.png"))), new Dimension(100, 100), new InstanceBuilder<>(ShellBehavior.class), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    BUZZY_BEETLE_SHELL(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/buzzy_beetle_shell.png"))), new Dimension(100, 100), new InstanceBuilder<>(ShellBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(FireballInvulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    GREEN_KOOPA(new InstanceBuilder<>(WalkingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/green_koopa.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(StompableBehavior.class, EntityType.GREEN_KOOPA_SHELL), new InstanceBuilder<>(WalkingBehavior.class, 0.087890625), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    RED_KOOPA(new InstanceBuilder<>(WalkingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/red_koopa.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(StompableBehavior.class, EntityType.RED_KOOPA_SHELL), new InstanceBuilder<>(LedgeTurnWalkingBehavior.class, 0.087890625), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    SPINY(new InstanceBuilder<>(WalkingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/spiny.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(HarmfulBehavior.class), new InstanceBuilder<>(WalkingBehavior.class, 0.087890625), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    BUZZY_BEETLE(new InstanceBuilder<>(WalkingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/buzzy_beetle.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(StompableBehavior.class, EntityType.BUZZY_BEETLE_SHELL), new InstanceBuilder<>(WalkingBehavior.class, 0.087890625), new InstanceBuilder<>(FireballInvulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    PIRANHA_PLANT(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/piranha_plant.png")), 2), new Dimension(100, 150), new EntityProperties().setImmuneToFluid(true).setDrawInBG(true), new InstanceBuilder<>(PiranhaPlantBehavior.class), new InstanceBuilder<>(HarmfulBehavior.class), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    DEAD_BULLET_BILL(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/bullet_bill.png"))), new Dimension(100, 100), new InstanceBuilder<>(FallingEnemyBehavior.class)),
    BULLET_BILL(new InstanceBuilder<>(FlyingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/bullet_bill.png"))), new Dimension(100, 100), new InstanceBuilder<>(StompableBehavior.class, EntityType.DEAD_BULLET_BILL), new InstanceBuilder<>(BulletBehavior.class), new InstanceBuilder<>(FlyingBehavior.class, 7.5), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    CHEEP_CHEEP(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/cheep_cheep.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(HarmfulBehavior.class), new InstanceBuilder<>(FlyingBehavior.class, 2.5), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    BLOOPER(new InstanceBuilder<>(BlooperTextureProvider.class, get(Gdx.files.internal("assets/images/entities/blooper.png"))), new Dimension(100, 100), new InstanceBuilder<>(BlooperBehavior.class), new InstanceBuilder<>(HarmfulBehavior.class), new InstanceBuilder<>(FireballVulnerableBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(WindBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    PODOBOO(new InstanceBuilder<>(PodobooTextureProvider.class, get(Gdx.files.internal("assets/images/entities/podoboo.png"))), new Dimension(100, 100), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(PodobooBehavior.class), new InstanceBuilder<>(HarmfulBehavior.class), new InstanceBuilder<>(IceballVulnerableBehavior.class), new InstanceBuilder<>(ShellVulnerableBehavior.class), new InstanceBuilder<>(IcicleVulnerableBehavior.class), new InstanceBuilder<>(FireballInvulnerableBehavior.class), new InstanceBuilder<>(CoinDeathRewardBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    MUSHROOM(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/mushroom.png"))), new Dimension(100, 100), new InstanceBuilder<>(PowerupBehavior.class, 1), new InstanceBuilder<>(EmergeWalkingBehavior.class, 0.17578125), new InstanceBuilder<>(WindBehavior.class)),
    FIRE_FLOWER(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/fire_flower.png")), 4, 3), new Dimension(100, 100), new InstanceBuilder<>(PowerupBehavior.class, 2), new InstanceBuilder<>(EmergeBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    ICE_FLOWER(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/ice_flower.png")), 4, 3), new Dimension(100, 100), new InstanceBuilder<>(PowerupBehavior.class, 3), new InstanceBuilder<>(EmergeBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    JUMP_SHOES(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/jump_shoes.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(PowerupBehavior.class, 4), new InstanceBuilder<>(EmergeBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    SPEED_SHOES(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/speed_shoes.png")), 2), new Dimension(100, 100), new InstanceBuilder<>(PowerupBehavior.class, 5), new InstanceBuilder<>(EmergeBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    POISONOUS_MUSHROOM(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/poisonous_mushroom.png"))), new Dimension(100, 100), new InstanceBuilder<>(HarmfulBehavior.class), new InstanceBuilder<>(EmergeWalkingBehavior.class, 0.17578125), new InstanceBuilder<>(DespawnOnTouchBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    LIFE_MUSHROOM(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/life_mushroom.png"))), new Dimension(100, 100), new InstanceBuilder<>(LifeBehavior.class), new InstanceBuilder<>(EmergeWalkingBehavior.class, 0.17578125), new InstanceBuilder<>(WindBehavior.class)),
    SUPERSTAR(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/superstar.png")), 4), new Dimension(100, 100), new InstanceBuilder<>(SuperstarBehavior.class), new InstanceBuilder<>(EmergeWalkingBehavior.class, 0.17578125), new InstanceBuilder<>(BouncingBehavior.class, 1.5f), new InstanceBuilder<>(DespawnOnTouchBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    FIREBALL(new InstanceBuilder<>(RotatingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/fireball.png"))), new Dimension(50, 50), new InstanceBuilder<>(WalkingBehavior.class, 1.0546875), new InstanceBuilder<>(BouncingBehavior.class, 0.75f), new InstanceBuilder<>(DestroyOnCollisionBehavior.class)),
    ICEBALL(new InstanceBuilder<>(RotatingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/iceball.png"))), new Dimension(50, 50), new InstanceBuilder<>(WalkingBehavior.class, 1.0546875), new InstanceBuilder<>(BouncingBehavior.class, 0.75f), new InstanceBuilder<>(DestroyOnCollisionBehavior.class)),
    ICE_CUBE(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/ice_cube.png"))), new Dimension(100, 100), new InstanceBuilder<>(SolidHitboxBehavior.class), new InstanceBuilder<>(FireballMeltingBehavior.class), new InstanceBuilder<>(RequiredToNotExistInEnemyFightBehavior.class)),
    COIN(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/coin.png"))), new Dimension(100, 100), new EntityProperties().setDrawInBG(true).setImmuneToFluid(true), new InstanceBuilder<>(CoinBehavior.class)),
    BRICK(new InstanceBuilder<>(BrickTextureProvider.class, get(Gdx.files.internal("assets/images/entities/brick.png"))), new Dimension(50, 50), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(BrickBehavior.class), new InstanceBuilder<>(WindBehavior.class)),
    FLAME_UP(new InstanceBuilder<>(FlameTextureProvider.class, get(Gdx.files.internal("assets/images/entities/flame_vertical.png")), FlameTextureProvider.FlipState.ANIMATE, FlameTextureProvider.FlipState.NEVER), new Dimension(100, 300), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(FlameBehavior.class)),
    FLAME_LEFT(new InstanceBuilder<>(FlameTextureProvider.class, get(Gdx.files.internal("assets/images/entities/flame_horizontal.png")), FlameTextureProvider.FlipState.NEVER, FlameTextureProvider.FlipState.ANIMATE), new Dimension(300, 100), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(FlameBehavior.class)),
    FLAME_DOWN(new InstanceBuilder<>(FlameTextureProvider.class, get(Gdx.files.internal("assets/images/entities/flame_vertical.png")), FlameTextureProvider.FlipState.ANIMATE, FlameTextureProvider.FlipState.ALWAYS), new Dimension(100, 300), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(FlameBehavior.class)),
    FLAME_RIGHT(new InstanceBuilder<>(FlameTextureProvider.class, get(Gdx.files.internal("assets/images/entities/flame_horizontal.png")), FlameTextureProvider.FlipState.ALWAYS, FlameTextureProvider.FlipState.ANIMATE), new Dimension(300, 100), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(FlameBehavior.class)),
    BOWSER(new InstanceBuilder<>(AnimatedTextureProvider.class, get(Gdx.files.internal("assets/images/entities/bowser.png")), 4), new Dimension(200, 300), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(BowserBehavior.class)),
    BOWSER_FIRE(new InstanceBuilder<>(BowserFireTextureProvider.class, get(Gdx.files.internal("assets/images/entities/bowserfire.png"))), new Dimension(150, 50), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(FlyingBehavior.class, 7.5), new InstanceBuilder<>(HarmfulBehavior.class)),
    BRICK_BLOCK(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/brickblock.png"))), new Dimension(200, 200), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(BrickBlockBehavior.class), new InstanceBuilder<>(DespawnBehavior.class, 600)),
    FIREBAR_FIREBALL(new InstanceBuilder<>(RotatingTextureProvider.class, get(Gdx.files.internal("assets/images/entities/fireball.png"))), new Dimension(50, 50), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(FirebarBallBehavior.class), new InstanceBuilder<>(HarmfulBehavior.class)),
    PUSHABLE_STONE(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/pushable_stone.png"))), new Dimension(100, 100), new InstanceBuilder<>(SolidHitboxBehavior.class), new InstanceBuilder<>(PushableBehavior.class)),
    ICICLE(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/icicle.png"))), new Dimension(100, 200), new InstanceBuilder<>(IcicleBehavior.class)),
    UP_PIPE_STREAM(new InstanceBuilder<>(BlankTextureProvider.class), new Dimension(200, 400), new InstanceBuilder<>(PipeStreamBehavior.class, 0, -1, 50, 0)),
    LEFT_PIPE_STREAM(new InstanceBuilder<>(BlankTextureProvider.class), new Dimension(400, 200), new InstanceBuilder<>(PipeStreamBehavior.class, -1, 0, -150, 100)),
    DOWN_PIPE_STREAM(new InstanceBuilder<>(BlankTextureProvider.class), new Dimension(200, 400), new InstanceBuilder<>(PipeStreamBehavior.class, 0, 1, 50, 300)),
    RIGHT_PIPE_STREAM(new InstanceBuilder<>(BlankTextureProvider.class), new Dimension(400, 200), new InstanceBuilder<>(PipeStreamBehavior.class, 1, 0, 150, 100)),
    CRATE(new InstanceBuilder<>(StaticTextureProvider.class, get(Gdx.files.internal("assets/images/entities/crate.png"))), new Dimension(200, 100), new EntityProperties().setImmuneToFluid(true), new InstanceBuilder<>(SolidHitboxBehavior.class), new InstanceBuilder<>(CrateBehavior.class)),
    BUMP_TILE(new InstanceBuilder<>(TileTextureProvider.class), new Dimension(100, 100), new InstanceBuilder<>(BumpTileBehavior.class)),
    ;
    private final InstanceBuilder<? extends EntityBehavior>[] behaviors;
    private final EntityProperties properties;
    private final InstanceBuilder<? extends TextureProvider> provider;
    private final Dimension hitbox;
    EntityType(InstanceBuilder<? extends TextureProvider> provider, Dimension hitbox, InstanceBuilder<? extends EntityBehavior>... behaviors) {
        this(provider, hitbox, new EntityProperties(), behaviors);
    }
    EntityType(InstanceBuilder<? extends TextureProvider> provider, Dimension hitbox, EntityProperties properties, InstanceBuilder<? extends EntityBehavior>... behaviors) {
        this.behaviors = behaviors;
        this.provider = provider;
        this.properties = properties;
        this.hitbox = hitbox;
    }
    public GameEntity spawn(GameLevel level) {
        return spawn(level, 0, 0);
    }
    public GameEntity spawn(GameLevel level, int x, int y) {
        EntityBehavior[] behaviors = new EntityBehavior[this.behaviors.length];
        for (int i = 0; i < behaviors.length; i++) {
            behaviors[i] = this.behaviors[i].build();
        }
        GameEntity entity = new GameEntity(new PhysicsConfig(level.getPhysicsConfig()).hitbox(hitbox), properties.copy(), provider.build(), this, behaviors);
        entity.getPhysics().begin(level);
        entity.getPhysics().getHitbox().x = x;
        entity.getPhysics().getHitbox().y = y;
        if (this == PLAYER) level.getEntityManager().entities.add(0, entity);
        else level.getEntityManager().loadEntity(entity);
        entity.getPhysics().getHitbox().x += entity.spawnOffsetX;
        entity.getPhysics().getHitbox().y += entity.spawnOffsetY;
        entity.updateTexture();
        return entity;
    }
    public Dimension getHitbox() {
        return hitbox;
    }
}

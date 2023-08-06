package com.smj.game.particle;

import com.badlogic.gdx.Gdx;
import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.SaveFile;
import com.smj.game.entity.GameEntity;
import com.smj.gui.menu.Menu;
import com.smj.gui.menu.Menus;
import com.smj.util.AudioPlayer;
import com.smj.util.Saveable;
import com.smj.util.TextureLoader;
import com.smj.util.Transition;

import java.awt.Point;
import java.awt.Rectangle;

public class MarioDeathParticle extends Particle {
    private int deathTimeout = 36;
    private int deathJumpTimeout = 120;
    private final int x;
    private double y;
    private double speedY = -5;
    private int gameOverTimeout = 0;
    public static boolean played = false;
    public MarioDeathParticle(GameEntity entity) {
        super(TextureLoader.get("images/entities/mario.png"));
        entity.invisible = true;
        this.x = (int)((entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2.0) / 100 * 16);
        this.y = (entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2.0) / 100 * 16;
    }
    public void update() {
        if (deathTimeout > 0) deathTimeout--;
        else {
            if (deathJumpTimeout > 0) deathJumpTimeout--;
            if (deathJumpTimeout == 0) {
                if (Game.savefile.lives == 0 && !played) {
                    played = true;
                    gameOverTimeout = 240;
                    AudioPlayer.GAME_OVER.play();
                }
                if (gameOverTimeout == 0) {
                    despawn();
                    Main.setTransition(new Transition(0.5, () -> {
                        if (Game.currentChallenge != null) {
                            Game.loadSavefile();
                            Game.title = true;
                            Game.loadLevel(Game.savefile.levelsCompleted, true);
                            Menu.loadMenu(null);
                            Menu.loadMenu(Menus.MAIN);
                            Menu.loadMenu(Menus.CHALLENGES);
                            Menu.loadMenu(Menus.CHALLENGE_CONFIRM);
                            return;
                        }
                        boolean gameOver = false;
                        if (Game.savefile.lives == 0) {
                            Game.savefile = new SaveFile();
                            Game.checkpointX = -1;
                            Game.checkpointY = -1;
                            Game.checkpointTime = -1;
                            Game.currentLevelID = 0;
                            gameOver = true;
                        }
                        Saveable.save(Game.savefile, Gdx.files.local("save.sav"));
                        if (gameOver) Game.levelIntro(Game.savefile.levelsCompleted);
                        else Game.loadLevel(Game.savefile.levelsCompleted, true);
                    }));
                }
                else gameOverTimeout--;
            }
            y += speedY;
            speedY += 0.15;
        }
    }
    public Rectangle getTextureRegion() {
        return new Rectangle(96, 0, 16, 16);
    }
    public Point getPosition() {
        return new Point(x, (int)y);
    }
}

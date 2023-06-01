package com.smj.game;

// bad code warning (possible heart attack)

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.smj.Main;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.entity.behavior.WarperBehavior;
import com.smj.game.fluid.FluidType;
import com.smj.game.options.Controls;
import com.smj.game.tile.GameTile;
import com.smj.game.tile.Tiles;
import com.smj.gui.hud.HUDLayout;
import com.smj.game.particle.*;
import com.smj.game.score.StaticScore;
import com.smj.game.score.Score;
import com.smj.gui.font.Font;
import com.smj.gui.menu.Menu;
import com.smj.gui.menu.Menus;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.entity.physics.Physics;
import com.smj.jmario.level.LevelBackground;
import com.smj.jmario.level.decoration.Decoration;
import com.smj.jmario.level.decoration.DecorationType;
import com.smj.jmario.level.decoration.Decorations;
import com.smj.jmario.tile.level.LevelTile;
import com.smj.util.command.Command;
import com.smj.util.command.console.TextConsole;
import com.smj.util.mask.Circle;
import com.smj.util.*;
import com.smj.util.Readable;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

public class Game {
    public static final Theme[] THEMES = new Theme[11];
    public static GameLevel currentLevel;
    public static boolean dead = false;
    public static GameEntity player;
    public static int currentLevelID = 0;
    public static boolean paused;
    public static SaveFile savefile;
    public static int time = 0;
    public static int timeTimeout = 60;
    public static int spedUpMusicTimeout = -1;
    public static boolean timeRunningOut = false;
    public static boolean isCrouching;
    public static int invincibilityTimeout = 0;
    public static int invincibilityFrames = 0;
    public static int powerupTimeout = 0;
    public static int finishTimeout = -1;
    public static boolean playCastleCutscene = false;
    public static int prevState = 0;
    public static ArrayList<Particle> particles = new ArrayList<>();
    public static int checkpointX = -1;
    public static int checkpointY = -1;
    public static int checkpointTime = -1;
    public static boolean bossFightBegan = false;
    public static int bossFightTilesTimeout = -1;
    public static boolean bossFightTiles = false;
    public static int bossFightFinishTimeout = -1;
    public static int warpTimeout = -1;
    public static int warpExitTimeout = -1;
    public static Warp warp = null;
    public static int keycoinsCollected = 0;
    public static boolean onOff = true;
    public static boolean pauseMenuOpen = false;
    public static boolean title = true;
    public static int legalNoticeTimeout = 300;
    public static String legalNoticeText = GameStrings.get("legal_notice");
    public static HashMap<Point, Boolean> snowCache = new HashMap<>();
    public static boolean consoleOpen = false;
    public static TextConsole console = new TextConsole();
    public static String cmd = "";
    public static Recording recording = null;
    public static boolean recordNextLevel = false;
    public static int demoTimeout = 600;
    public static int displayLevelID = 0;
    public static Recording playback;
    public static void render(Renderer renderer) {
        if (legalNoticeTimeout > 0) {
            renderer.setColor(0xFFFFFFFF);
            legalNoticeTimeout--;
            if (legalNoticeTimeout == 30) {
                Main.setTransition(new Transition(0.5, () -> {
                    Game.loadLevel(savefile.levelsCompleted, true);
                    Menu.loadMenu(Menus.MAIN);
                }));
            }
            String[] lines = legalNoticeText.split("\n");
            int y = Main.HEIGHT / 2 - (lines.length * 12 - 4) / 2;
            for (String line : lines) {
                int x = Main.WIDTH / 2 - Font.stringWidth(line) / 2;
                renderer.drawString(line, x, y);
                y += 12;
            }
            return;
        }
        if (currentLevel != null) {
            if (currentLevel.gimmick == GameLevel.Gimmick.UPSIDE_DOWN) renderer.setTransformMatrix(renderer.getTransformMatrix().scale(1, -1, 1).translate(0, -Main.HEIGHT, 0));
            currentLevel.draw(renderer, 16, 16, Game.player);
            double cameraX = currentLevel.camera.x * 16 - Main.WIDTH / 2.0;
            double cameraY = currentLevel.camera.y * 16 - Main.HEIGHT / 2.0;
            if (cameraX < 0) cameraX = 0;
            if (cameraY > 0) cameraY = 0;
            if (cameraX > currentLevel.getLevelBoundaries().width * 16 - Main.WIDTH) cameraX = currentLevel.getLevelBoundaries().width * 16 - Main.WIDTH;
            if (cameraY < Main.HEIGHT - currentLevel.getLevelBoundaries().height * 16) cameraY = Main.HEIGHT - currentLevel.getLevelBoundaries().height * 16;
            for (Particle particle : particles) {
                renderer.setColor(0xFFFFFF00 | Math.min(255, Math.max(0, particle.getTextureAlpha())));
                Point position = particle.getPosition();
                Rectangle region = particle.getTextureRegion();
                Rectangle particleBounds = new Rectangle(position.x - region.width / 2, position.y - region.height / 2, region.width, region.height);
                Rectangle cameraBounds = new Rectangle((int)cameraX, -(int)cameraY, Main.WIDTH, Main.HEIGHT);
                if (particleBounds.intersects(cameraBounds)) renderer.draw(particle.texture, region, position.x - region.width / 2, position.y - region.height / 2);
            }
            renderer.setColor(0xFFFFFFFF);
            if (currentLevel.gimmick == GameLevel.Gimmick.UPSIDE_DOWN) renderer.setTransformMatrix(renderer.getTransformMatrix().translate(0, Main.HEIGHT, 0).scale(1, -1, 1));
            renderer.resetTranslation();
            if (currentLevel.gimmick == GameLevel.Gimmick.SPOTLIGHT) Main.renderMask();
        }
        else {
            String error = GameStrings.get("level_not_loaded");
            renderer.setColor(0xFFFFFFFF);
            renderer.drawString(error, Main.WIDTH / 2 - Font.stringWidth(error) / 2, Main.HEIGHT / 2 - Font.getHeight() / 2);
        }
        if ((!Main.options.hiddenHUD && !title) || Menu.currentMenu == Menus.HUD_LAYOUT) HUDLayout.renderAll(renderer);
        if (Menu.currentMenu == Menus.MAIN) renderer.draw(TextureLoader.get("images/logo.png"), Main.WIDTH / 2 - 168 / 2, 24);
        if (consoleOpen) {
            renderer.setColor(0x0000007F);
            renderer.rect(4, 4, Main.WIDTH - 8, Main.HEIGHT - 20 - Font.getHeight());
            renderer.rect(4, Main.HEIGHT - 12 - Font.getHeight(), Main.WIDTH - 8, Font.getHeight() + 8);
            renderer.setColor(0xFFFFFFFF);
            List<String> lineList = Arrays.asList(console.data.split("\n"));
            Collections.reverse(lineList);
            String[] consoleText = lineList.toArray(new String[0]);
            int height = (Main.HEIGHT - 20 - Font.getHeight()) / Font.getHeight();
            for (int i = 0; i < Math.min(consoleText.length, height); i++) {
                String text = consoleText[i];
                boolean isError = false;
                if (text.startsWith("{E}")) {
                    text = text.substring(3);
                    isError = true;
                }
                while (Font.stringWidth(text) > Main.WIDTH - 16) {
                    text = text.substring(0, text.length() - 1);
                }
                renderer.topLeft = false;
                renderer.drawString((isError ? "$cFF3F3F" : "") + text, 8, 16 + i * Font.getHeight() + Font.getHeight());
                renderer.topLeft = true;
            }
            String text = "> " + cmd;
            while (Font.stringWidth(text + "_") > Main.WIDTH - 16) {
                text = text.substring(1);
            }
            renderer.drawString(text + (System.currentTimeMillis() % 1000 < 500 ? "_" : ""), 8, Main.HEIGHT - 8 - Font.getHeight());
        }
    }
    public static void keyPressed(int keycode) {
        if (!consoleOpen) return;
        if (keycode == 19) {
            Command.historyIndex++;
            if (Command.historyIndex == Command.commandHistory.size()) Command.historyIndex--;
            cmd = Command.commandHistory.get(Command.historyIndex);
        }
        if (keycode == 20) {
            Command.historyIndex--;
            if (Command.historyIndex == -1) Command.historyIndex++;
            cmd = Command.commandHistory.get(Command.historyIndex);
        }
    }
    public static void keyTyped(char character) {
        if (!consoleOpen) return;
        String prev = cmd;
        if (character == 8 && cmd.length() > 0) cmd = cmd.substring(0, cmd.length() - 1);
        else if (character >= 32 && character <= 126) cmd += character;
        else if (character == 10) {
            Command.run(cmd);
            cmd = "";
        }
        if (!prev.equals(cmd)) Command.commandHistory.set(0, cmd);
    }
    public static void update() {
        if (Menu.currentMenu != null) Menu.currentMenu.update();
        HUDLayout.WORLD_TEXT.text = GameStrings.get("world_prefix") + " " + (displayLevelID / 5 + 1) + "-" + (displayLevelID % 5 + 1);
        if (legalNoticeTimeout > 0) return;
        HUDLayout.COIN_COUNTER.attachment.target = Math.min(savefile.coins, 100);
        HUDLayout.LIFE_COUNTER.attachment.target = savefile.lives;
        savefile.lives = Math.min(99, savefile.lives);
        HUDLayout.SCORE.target = savefile.score;
        HUDLayout.DEATH_TEXT.text = "$cFF3F3F" + (MarioDeathParticle.played ? GameStrings.get("game_over") : time == 0 && finishTimeout == -1 ? GameStrings.get("time_up") : "");
        HUDLayout.DEATH_TEXT.position.x = HUDLayout.WIDTH / 2 - Font.stringWidth(HUDLayout.DEATH_TEXT.text) / 8;
        HUDLayout.DEATH_TEXT.position.y = HUDLayout.HEIGHT / 2 - 1;
        HUDLayout.KEY_COIN_COUNTER.attachment.target = keycoinsCollected;
        HUDLayout.STAR_TIMER.visible = invincibilityTimeout > 0;
        HUDLayout.STAR_TIMER.attachment.target = (int)Math.ceil(invincibilityTimeout / 60f);
        HUDLayout.SPEEDRUN_TIMER.visible = Main.options.speedrunTimer;
        if (savefile.coins >= 100) {
            HUDLayout.COIN_COUNTER.attachment.value = 0;
            savefile.coins -= 100;
            HUDLayout.COIN_COUNTER.attachment.target = savefile.coins;
            addLives(1);
        }
        HUDLayout.updateAll();
        if (powerupTimeout > 0) {
            powerupTimeout--;
            if (powerupTimeout == 0) paused = false;
            Game.player.updateTexture();
        }
        if (finishTimeout == 0) {
            if (time > 0) {
                time--;
                if (time % 4 == 0) AudioPlayer.BEEP.play(Location.entity(player).move(0, 128));
                awardScore(StaticScore.TIMER, Location.none());
            }
            else {
                finishTimeout = 999;
                if (playCastleCutscene) {
                    if (savefile.levelsCompleted == 39) Main.startCutscene("peach_saved", () -> {
                        Game.title = true;
                        Game.loadLevel(Game.savefile.levelsCompleted, true);
                        Menu.loadMenu(null);
                        Menu.loadMenu(Menus.MAIN);
                    });
                    else Main.startCutscene("in_another_castle_" + (savefile.levelsCompleted / 5 + 1), Game::nextLevel);
                }
                else Main.setTransition(new Transition(0.5, Game::nextLevel));
                playCastleCutscene = false;
            }
        }
        ArrayList<Particle> particles = new ArrayList<>(Game.particles);
        for (Particle particle : particles) {
            if (!particle.noUpdatePaused) particle.update();
        }
        if (finishTimeout > 0) finishTimeout--;
        if (warpTimeout > 0) {
            warpTimeout--;
            player.getPhysics().getHitbox().y += 3;
            if (warpTimeout == 0) {
                Main.setTransition(new Transition(0.5, () -> {
                    loadLevel(warp.levelID, false, warp.spawnX, warp.spawnY);
                }));
            }
        }
        if (warpExitTimeout > 0) {
            if (warpExitTimeout == 64) AudioPlayer.WARP.play();
            warpExitTimeout--;
            paused = true;
            player.getPhysics().getHitbox().y += warp.goDown ? 3 : -3;
            if (warpExitTimeout == 0) {
                warpExitTimeout = -1;
                warp = null;
                paused = false;
                player.getProperties().drawInBG = false;
                player.getPhysics().getConfig().collisionEnabled = true;
                player.getPhysics().getConfig().gravity = currentLevel.getPhysicsConfig().gravity;
                player.getPhysics().getConfig().underwaterGravity = currentLevel.getPhysicsConfig().underwaterGravity;
            }
        }
        if (Controls.PAUSE.isJustPressed() && paused == pauseMenuOpen) {
            AudioPlayer.PAUSE.play();
            pauseMenuOpen = !pauseMenuOpen;
            paused = pauseMenuOpen;
            if (paused) {
                SMJMusic.pause();
                Menu.loadMenu(Menus.PAUSE);
            }
            else {
                SMJMusic.resume();
                Menu.loadMenu(null);
            }
        }
        if (Controls.CONSOLE.isJustPressed()) {
            consoleOpen = !consoleOpen;
        }
        if (currentLevel == null) return;
        currentLevel.tileAnimationTimeout--;
        if (currentLevel.tileAnimationTimeout == 0) {
            currentLevel.tileAnimationTimeout = 10;
            for (LevelTile tile : currentLevel.getTileList()) {
                if (tile instanceof GameTile) {
                    GameTile t = (GameTile)tile;
                    t.cycleTexture();
                }
            }
        }
        if (paused || (title && playback == null)) return;
        HUDLayout.SPEEDRUN_TIMER.frames++;
        if (playback != null) {
            playback.next();
            if (playback.done()) playback = null;
        }
        for (Entity entity : Arrays.asList(currentLevel.getEntityManager().array())) {
            entity.update(currentLevel);
            Physics physics = entity.getPhysics();
            if (!physics.begun()) physics.begin(currentLevel);
            physics.advance();
        }
        if (currentLevel.fluid != null) currentLevel.fluid.movement.update();
        timeTimeout--;
        if (timeTimeout == 0) {
            time--;
            timeTimeout = 60;
            if (time <= 100 && !timeRunningOut) {
                timeRunningOut = true;
                AudioPlayer.MUSIC[currentLevel.music].stop();
                AudioPlayer.TIMEOUT.play();
                spedUpMusicTimeout = 180;
            }
            if (time <= 10) {
                AudioPlayer.BEEP.play();
            }
            if (time == 0) die();
        }
        if (spedUpMusicTimeout > 0) {
            spedUpMusicTimeout--;
            if (spedUpMusicTimeout == 0) {
                AudioPlayer.MUSIC[currentLevel.music].play(true);
            }
        }
        if (invincibilityTimeout > 0) {
            invincibilityTimeout--;
            if (invincibilityTimeout == 0) {
                AudioPlayer.MUSIC[currentLevel.music].play(timeRunningOut);
            }
        }
        particles = new ArrayList<>(Game.particles);
        for (Particle particle : particles) {
            if (particle.noUpdatePaused) particle.update();
        }
        if (invincibilityFrames > 0) invincibilityFrames--;
        if (currentLevel.gimmick == GameLevel.Gimmick.ENEMY && currentLevel.finishedEnemyBattle() && time < currentLevel.time && currentLevel.getTileAt(12, 12) != Tiles.STAR && finishTimeout == -1) {
            currentLevel.setTileAt(Tiles.STAR, 12, 12);
            Game.particles.add(new SmokeParticle(200, 200));
            AudioPlayer.ALL_KEY_COINS.play();
        }
        Physics physics = player.getPhysics();
        Rectangle hitbox = physics.getHitbox();
        if (currentLevel.gimmick == GameLevel.Gimmick.WIND) {
            int y = RNG.bound(currentLevel.getLevelBoundaries().height * 16);
            Game.particles.add(new WindParticle(y));
        }
        if (currentLevel.theme == 3) {
            for (int i = 0; i < Math.max(1, currentLevel.getLevelBoundaries().width / 64); i++) {
                int x = RNG.bound(currentLevel.getLevelBoundaries().width * 16);
                Game.particles.add(new SnowParticle(x, 0));
            }
        }
        if (currentLevel.gimmick == GameLevel.Gimmick.CASTLE && !currentLevel.camera.locked && hitbox.x > (currentLevel.getLevelBoundaries().width - Main.WIDTH / 16) * 100) {
            currentLevel.camera.locked = true;
            currentLevel.camera.targetX = currentLevel.getLevelBoundaries().width - Main.WIDTH / 32.0 + 0.065;
            currentLevel.music = 12;
            AudioPlayer.MUSIC[12].play(timeRunningOut);
            bossFightBegan = true;
            playCastleCutscene = true;
            bossFightTilesTimeout = 300;
        }
        if (currentLevel.gimmick == GameLevel.Gimmick.CASTLE && bossFightFinishTimeout == -1) {
            boolean hasBowser = false;
            for (Entity entity : currentLevel.getEntityManager().array()) {
                if (((GameEntity)entity).entityType == EntityType.BOWSER) hasBowser = true;
            }
            if (!hasBowser) {
                bossFightFinishTimeout = 60;
            }
        }
        if (bossFightFinishTimeout > 0) {
            bossFightFinishTimeout--;
            if (bossFightFinishTimeout == 0) finishLevel();
        }
        if (bossFightTilesTimeout > 0) bossFightTilesTimeout--;
        if (bossFightTilesTimeout == 0) {
            bossFightTiles = !bossFightTiles;
            bossFightTilesTimeout = RNG.range(300, 600);
            Rectangle arena = new Rectangle(currentLevel.getLevelBoundaries().width - 23, 1, 19, 11);
            if (bossFightTiles) {
                int X = RNG.bound(arena.width) + arena.x;
                for (int x = arena.x; x < arena.x + arena.width; x++) {
                    for (int y = arena.y; y < arena.y + arena.height; y++) {
                        if (X == x && y == 1) currentLevel.setTileAt(Tiles.EXCLAMATION_POINT_CIRCLE_BALL_THING_IDK_HOW_TO_CALL_THIS_BUT_IT_IS_VERY_IMPORTANT_FOR_THE_BOWSER_BOSS_FIGHT_IG, x, y);
                        else if (RNG.chance(1 / 3f)) currentLevel.setTileAt(Tiles.FRAGILE_BRICK, x, y);
                    }
                }
            }
            else {
                for (int x = arena.x; x < arena.x + arena.width; x++) {
                    for (int y = arena.y; y < arena.y + arena.height; y++) {
                        currentLevel.setTileAt(Tiles.AIR, x, y);
                        if (RNG.chance(3 / 4f)) continue;
                        EntityType.BRICK.spawn(currentLevel, x * 100, y * 100);
                        EntityType.BRICK.spawn(currentLevel, x * 100 + 50, y * 100);
                        EntityType.BRICK.spawn(currentLevel, x * 100, y * 100 + 50);
                        EntityType.BRICK.spawn(currentLevel, x * 100 + 50, y * 100 + 50);
                    }
                }
                AudioPlayer.BRICK.play();
                for (Entity entity : Arrays.asList(currentLevel.getEntityManager().array())) {
                    if (((GameEntity)entity).entityType == EntityType.BOWSER_FIRE) currentLevel.getEntityManager().unloadEntity(entity);
                }
            }
        }
        if (WarperBehavior.warperTimeout > 0) WarperBehavior.warperTimeout--;
        if (invincibilityTimeout % 2 == 1) {
            spawnSparklesHitbox(hitbox, 1);
        }
        double minX = currentLevel.camera.locked ? (currentLevel.getLevelBoundaries().width - Main.WIDTH / 16.0) * 100.0 : 0;
        if (hitbox.x < minX) {
            hitbox.x = (int)minX;
            physics.setSpeedX(0);
        }
        if (hitbox.x > currentLevel.getLevelBoundaries().width * 100 - hitbox.width) {
            hitbox.x = currentLevel.getLevelBoundaries().width * 100 - hitbox.width;
            physics.setSpeedX(0);
        }
        if (savefile.powerupState > 0 && !physics.isInAir()) {
            Recording.RecordingFrame pressed = playback == null ? null : playback.pressed();
            isCrouching = currentLevel.gimmick == GameLevel.Gimmick.UPSIDE_DOWN ? (pressed == null ? Controls.UP.isPressed() : pressed.up) : (pressed == null ? Controls.DOWN.isPressed() : pressed.down);
            if (hitbox.height > 100 && isCrouching) {
                hitbox.height -= 100;
                hitbox.y += 100;
            }
            else if (hitbox.height < 100 && !isCrouching){
                hitbox.height += 100;
                hitbox.y -= 100;
            }
        }
        if (currentLevel.fluid != null) {
            int entityFluidPos = currentLevel.fluid.movement.getFluidLevel() * 100 / 16;
            for (Entity entity : Arrays.asList(currentLevel.getEntityManager().array())) {
                Rectangle h = entity.getPhysics().getHitbox();
                entity.getPhysics().getConfig().underwater = h.y >= entityFluidPos;
                entity.getPhysics().getConfig().maxWalkingSpeed = h.y >= entityFluidPos ? currentLevel.getPhysicsConfig().maxUnderwaterOnGroundSpeed : currentLevel.getPhysicsConfig().maxWalkingSpeed;
                entity.getPhysics().getConfig().maxRunningSpeed = h.y >= entityFluidPos ? currentLevel.getPhysicsConfig().maxUnderwaterOnGroundSpeed : currentLevel.getPhysicsConfig().maxRunningSpeed;
                if (h.y + h.height >= entityFluidPos) {
                    if (((GameEntity)entity).entityType == EntityType.PLAYER) {
                        if (currentLevel.fluid.type == FluidType.LAVA) die();
                        if (currentLevel.fluid.type == FluidType.POISON && invincibilityTimeout == 0) damagePlayer();
                    }
                    else {
                        if (currentLevel.fluid.type != FluidType.WATER && !entity.getProperties().immuneToFluid) {
                            AudioPlayer.KICK.play(Location.entity(entity));
                            currentLevel.getEntityManager().unloadEntity(entity);
                            Game.particles.add(new FallingEnemyParticle((GameEntity)entity));
                        }
                    }
                }
            }
        }
        for (Entity entity : new ArrayList<>(currentLevel.getEntityManager().entities)) {
            if (entity.getPhysics().getHitbox().y > (currentLevel.getLevelBoundaries().height + 5) * 100) {
                currentLevel.getEntityManager().unloadEntity(entity);
            }
        }
        Collections.shuffle(currentLevel.getEntityManager().entities);
        if (Controls.TOGGLE_HUD.isJustPressed()) Main.options.hiddenHUD = !Main.options.hiddenHUD;
        if (recording != null) recording.recordFrame();
    }
    public static void loadThemes() {
        for (int i = 0; i < THEMES.length; i++) {
            LevelBackground background = new LevelBackground(new Color(0.5f, 0.5f, 0.5f, 1), TextureLoader.get("images/themes/" + i + "/background.png"));
            Decorations decorations = new Decorations();
            decorations.add(new Decoration(TextureLoader.get("images/themes/" + i + "/decor_short.png"), DecorationType.SHORT));
            decorations.add(new Decoration(TextureLoader.get("images/themes/" + i + "/decor_normal.png"), DecorationType.NORMAL));
            decorations.add(new Decoration(TextureLoader.get("images/themes/" + i + "/decor_tall.png"), DecorationType.TALL));
            decorations.add(new Decoration(TextureLoader.get("images/themes/" + i + "/decor_wide.png"), DecorationType.WIDE));
            background.setDecorations(decorations);
            THEMES[i] = new Theme(TextureLoader.get("images/themes/" + i + "/tileset.png"), background);
        }
    }
    public static void loadSavefile() {
        FileHandle save = Gdx.files.local("save.sav");
        if (save.exists()) savefile = Readable.read(save.readBytes(), SaveFile.class);
        else savefile = new SaveFile();
        Saveable.save(savefile, save);
    }
    public static void loadLevel(int id, boolean newLevel) {
        loadLevel(id, newLevel, null, null);
    }
    public static void loadLevel(int id, boolean newLevel, Integer spawnX, Integer spawnY) {
        GameLevel.onOffTreatment = !newLevel;
        MarioDeathParticle.played = false;
        bossFightBegan = false;
        bossFightTiles = false;
        bossFightTilesTimeout = -1;
        bossFightFinishTimeout = -1;
        isCrouching = false;
        warpTimeout = -1;
        keycoinsCollected = 0;
        dead = false;
        particles.clear();
        snowCache.clear();
        GameLevel level;
        try {
            level = Readable.read(FileLoader.read("levels/level" + id + ".lvl").asBytes(), GameLevel.class);
        }
        catch (Exception e) {
            currentLevel = null;
            System.out.println("Failed to load level " + id);
            time = 999;
            HUDLayout.KEY_COIN_COUNTER.visible = false;
            if (newLevel) displayLevelID = id;
            if (Game.title) AudioPlayer.MUSIC[13].play();
            else SMJMusic.stopMusic();
            return;
        }
        if (Game.title) level.music = 13;
        if (currentLevel == null || currentLevel.music != level.music || !SMJMusic.isPlaying()) AudioPlayer.MUSIC[level.music].play();
        currentLevel = level;
        currentLevelID = id;
        currentLevel.id = id;
        if (newLevel) {
            onOff = true;
            time = checkpointTime == -1 ? level.time : checkpointTime;
            timeRunningOut = false;
            timeTimeout = 60;
            spedUpMusicTimeout = -1;
            HUDLayout.SCORE.set(savefile.score);
            if (recording != null) {
                Saveable.save(recording, Gdx.files.local(Saveable.ensureNotExists(Gdx.files.local("smj_recordings/" + Saveable.getTimestamp() + ".smjrec"))));
                recording = null;
            }
            playback = null;
            savefile = Readable.read(Gdx.files.local("save.sav").readBytes(), SaveFile.class);
            displayLevelID = id;
        }
        else {
            if (timeRunningOut || spedUpMusicTimeout > 0) {
                AudioPlayer.MUSIC[level.music].stop();
                AudioPlayer.MUSIC[level.music].play(true);
            }
        }
        if (level.gimmick == GameLevel.Gimmick.ENEMY) {
            time = 30;
            timeRunningOut = true;
        }
        int x = spawnX == null ? checkpointX == -1 ? level.spawnX : checkpointX : spawnX;
        int y = spawnY == null ? checkpointY == -1 ? level.spawnY : checkpointY : spawnY;
        Main.mask.clear();
        player = EntityType.PLAYER.spawn(level, x * 100 + 50 - EntityType.PLAYER.getHitbox().width / 2, y * 100 + 100 - EntityType.PLAYER.getHitbox().height);
        player.onLoad(currentLevel);
        Rectangle hitbox = player.getPhysics().getHitbox();
        double cameraX = currentLevel.camera.x - Main.WIDTH / 2.0 / 16;
        double cameraY = currentLevel.camera.y - Main.HEIGHT / 2.0 / 16;
        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if (cameraX > (currentLevel.getLevelBoundaries().width * 16 - Main.WIDTH) / (double)16) cameraX = (currentLevel.getLevelBoundaries().width * 16 - Main.WIDTH) / (double)16;
        if (cameraY > (currentLevel.getLevelBoundaries().height * 16 - Main.HEIGHT) / (double)16) cameraY = (currentLevel.getLevelBoundaries().height * 16 - Main.HEIGHT) / (double)16;
        currentLevel.camera.setTarget(cameraX, cameraY);
        currentLevel.camera.snap();
        if (savefile.powerupState != 0) player.getPhysics().setHitbox(new Rectangle(hitbox.x, hitbox.y - 100, hitbox.width, hitbox.height + 100));
        if (currentLevel.gimmick == GameLevel.Gimmick.CASTLE) EntityType.BOWSER.spawn(level, currentLevel.getLevelBoundaries().width * 100 - 300, 0);
        if (currentLevel.gimmick == GameLevel.Gimmick.WIND) {
            for (int X = 0; X < currentLevel.getLevelBoundaries().width * 16; X++) {
                particles.add(new WindParticle(X, RNG.bound(currentLevel.getLevelBoundaries().height * 16)));
            }
        }
        if (currentLevel.theme == 3) {
            for (int Y = 0; Y < currentLevel.getLevelBoundaries().height * 16; Y++) {
                for (int i = 0; i < Math.max(2, currentLevel.getLevelBoundaries().width / 32); i++) {
                    int X = RNG.bound(currentLevel.getLevelBoundaries().width * 16);
                    int tx = X / 16;
                    int ty = Y / 16;
                    boolean exposedToAir = true;
                    for (int j = 0; j <= ty; j++) {
                        if (currentLevel.getTileList().get(currentLevel.getTileAt(tx, j)).isSolid()) exposedToAir = false;
                    }
                    if (exposedToAir) Game.particles.add(new SnowParticle(X, Y));
                }
            }
        }
        if (warp != null) {
            warpExitTimeout = 64;
            player.getProperties().drawInBG = true;
            player.getPhysics().getConfig().collisionEnabled = false;
            player.getPhysics().getConfig().gravity = 0;
            player.getPhysics().getConfig().underwaterGravity = 0;
            player.getPhysics().getHitbox().x += 50;
            player.getPhysics().getHitbox().y += warp.goDown ? -200 : 200;
        }
        if (recordNextLevel && newLevel) {
            recordNextLevel = false;
            recording = new Recording(id);
        }
        GameLevel.onOffTreatment = true;
    }
    public static void damagePlayer() {
        damagePlayer(false);
    }
    public static void damagePlayer(boolean bypassInvincibilityFrames) {
        if ((invincibilityFrames != 0 && !bypassInvincibilityFrames) || dead) return;
        if (savefile.powerupState == 0) die();
        else if (savefile.powerupState == 1) setPowerup(0);
        else setPowerup(1);
    }
    public static void die() {
        if (dead) return;
        dead = true;
        SMJMusic.stopMusic();
        AudioPlayer.DEATH.play();
        paused = true;
        removeLives(1);
        savefile.powerupState = 0;
        invincibilityTimeout = 0;
        invincibilityFrames = 0;
        particles.add(new MarioDeathParticle(player));
    }
    public static void awardScore(Score score) {
        awardScore(score, Location.entity(player));
    }
    public static void awardScore(Score score, Location source) {
        int amount = score.awardScore();
        savefile.score += amount;
        int y = 0;
        if (amount > 0) {
            particles.add(new RisingTextParticle(amount + "", source.getX() + source.getWidth() / 4, source.getY()));
            y += 8;
        }
        int lives = score.awardLives();
        addLives(lives, source.move(0, -y));
        if (lives > 0) y += 8;
        awardCoins(score.awardCoins(), source.move(0, -y));
        score.awarded();
    }
    public static void addCoins(int coins) {
        savefile.coins += coins;
    }
    public static void awardCoins(int coins) {
        awardCoins(coins, Location.entity(player));
    }
    public static void awardCoins(int coins, Location source) {
        if (coins == 0) return;
        addCoins(coins);
        particles.add(new RisingTextParticle("$cFFFF00+" + coins, source.getX() + source.getWidth() / 4, source.getY()));
        AudioPlayer.BIG_COIN.play();
    }
    public static void addLives(int lives) {
        addLives(lives, Location.entity(player));
    }
    public static void addLives(int lives, Location source) {
        if (lives == 0) return;
        savefile.lives += lives;
        particles.add(new RisingTextParticle("$c00FF00" + lives + "UP", source.getX() + source.getWidth() / 4, source.getY()));
        AudioPlayer.LIFE.play();
    }
    public static void removeLives(int lives) {
        savefile.lives -= lives;
    }
    public static void setCoins(int coins) {
        addLives(coins / 100);
        coins %= 100;
        savefile.coins = coins;
        HUDLayout.COIN_COUNTER.attachment.set(coins);
    }
    public static void setLives(int lives) {
        lives %= 100;
        savefile.lives = lives;
        HUDLayout.LIFE_COUNTER.attachment.set(lives);
    }
    public static void setPowerup(int state) {
        Rectangle hitbox = player.getPhysics().getHitbox();
        if (state == 0 && savefile.powerupState > 0 && isCrouching) {
            hitbox.height += 100;
            hitbox.y -= 100;
            isCrouching = false;
        }
        if (savefile.powerupState == state) {
            awardScore(StaticScore.POWERUP);
            AudioPlayer.POWERUP.play();
            return;
        }
        powerupTimeout = 60;
        paused = true;
        if (savefile.powerupState < state || state >= 2) {
            awardScore(StaticScore.POWERUP);
            AudioPlayer.POWERUP.play();
        }
        else {
            AudioPlayer.WARP.play();
            invincibilityFrames = 180;
        }
        prevState = savefile.powerupState;
        savefile.powerupState = state;
        if (prevState != 0 && state == 0) {
            player.getPhysics().setHitbox(new Rectangle(hitbox.x, hitbox.y + 100, hitbox.width, hitbox.height - 100));
        }
        if (prevState == 0 && state != 0) {
            player.getPhysics().setHitbox(new Rectangle(hitbox.x, hitbox.y - 100, hitbox.width, hitbox.height + 100));
        }
    }
    public static void getSuperstar(int seconds) {
        awardScore(StaticScore.POWERUP);
        AudioPlayer.POWERUP.play();
        invincibilityTimeout = seconds * 60;
        AudioPlayer.MUSIC[11].play(timeRunningOut);
    }
    public static void spawnSparkles(Rectangle bounds, int amount) {
        for (int i = 0; i < amount; i++) {
            int x = RNG.bound(bounds.width);
            int y = RNG.bound(bounds.height);
            particles.add(new Sparkle(bounds.x + x, bounds.y + y));
        }
    }
    public static void spawnSparklesHitbox(Rectangle hitbox, int amount) {
        spawnSparkles(new Rectangle(hitbox.x * 16 / 100, hitbox.y * 16 / 100, hitbox.width * 16 / 100, hitbox.height * 16 / 100), amount);
    }
    public static void spawnSparklesTile(int x, int y, int amount) {
        spawnSparkles(new Rectangle(x * 16, y * 16, 16, 16), amount);
    }
    public static void finishLevel() {
        paused = true;
        finishTimeout = 330;
        SMJMusic.stopMusic();
        AudioPlayer.FINISH.play();
        List<Entity> entities = Arrays.asList(currentLevel.getEntityManager().array());
        for (Entity entity : entities) {
            if (((GameEntity)entity).entityType == EntityType.PLAYER) continue;
            currentLevel.getEntityManager().unloadEntity(entity);
            particles.add(new SmokeParticle((entity.getPhysics().getHitbox().x + entity.getPhysics().getHitbox().width / 2) * 16 / 100, (entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height / 2) * 16 / 100));
        }
    }
    public static void nextLevel() {
        savefile.levelsCompleted++;
        finishTimeout = -1;
        paused = false;
        checkpointX = -1;
        checkpointY = -1;
        Game.checkpointTime = -1;
        Saveable.save(savefile, Gdx.files.local("save.sav"));
        loadLevel(savefile.levelsCompleted, true);
    }
    public static void warp(Warp warp) {
        warpTimeout = 64;
        paused = true;
        player.getProperties().drawInBG = true;
        Game.warp = warp;
        AudioPlayer.WARP.play();
    }
    public static void playbackRecording(Recording recording) {
        recording.seek(0);
        Main.setTransition(new Transition(0.5, () -> {
            loadLevel(recording.level, true);
            Game.recording = null;
            savefile = new SaveFile();
            Rectangle hitbox = player.getPhysics().getHitbox();
            if (hitbox.height > 100) player.getPhysics().setHitbox(new Rectangle(hitbox.x, hitbox.y + 100, hitbox.width, hitbox.height - 100));
            playback = recording;
        }));
    }
}

package com.smj.jmario.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.GameLevel;
import com.smj.game.entity.EntityType;
import com.smj.game.entity.GameEntity;
import com.smj.game.fluid.Fluid;
import com.smj.game.options.Controls;
import com.smj.game.tile.GameTile;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.entity.EntityManager;
import com.smj.jmario.level.decoration.Decoration;
import com.smj.jmario.level.decoration.Decorations;
import com.smj.jmario.tile.level.LevelTile;
import com.smj.jmario.tile.level.LevelTileList;
import com.smj.util.Pair;
import com.smj.util.RNG;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

public class Level {
    public static final boolean displayHitboxes = false;
    private int[][] tilemap;
    private LevelTileList tileList = new LevelTileList();
    private EntityManager entities = new EntityManager(this);
    private LevelBackground bg = new LevelBackground(new Color(0xAFAFFFFF), new Texture(1, 1, Pixmap.Format.RGBA8888));
    public ArrayList<Pair<Point, Decoration>> decorations = new ArrayList<>();
    public LevelCamera camera = new LevelCamera();
    public Level(int width, int height) {
        tilemap = new int[width][height];
    }
    public Level assignTileList(LevelTileList list) {
        tileList = list;
        return this;
    }
    public Level setTileAt(int tile, int x, int y) {
        if (x < 0 || y < 0 || x >= getLevelBoundaries().width || y >= getLevelBoundaries().height) return this;
        Main.mask.remove(GameTile.spotlightLocations.get(new Point(x, y)));
        tilemap[x][y] = tile;
        for (Entity entity : entities) {
            entity.getPhysics().updateCollisionMap();
        }
        return this;
    }
    public Level assignBackground(LevelBackground background) {
        bg = background;
        populate(background.getDecorations());
        return this;
    }
    public int getTileAt(int x, int y) {
        if (x < 0 || y < 0 || x >= getLevelBoundaries().width || y >= getLevelBoundaries().height) return 0;
        return tilemap[x][y];
    }
    public EntityManager getEntityManager() {
        return entities;
    }
    public LevelTileList getTileList() {
        return tileList;
    }
    public Dimension getLevelBoundaries() {
        return new Dimension(tilemap.length, tilemap[0].length);
    }
    public LevelBackground getBackground() {
        return bg;
    }
    public void populate(Decorations decorations) {
        int decorationsToSpawn = 0;
        ArrayList<Point> availableSpots = new ArrayList<>();
        for (int x = 0; x < tilemap.length; x++) {
            for (int y = 1; y < tilemap[0].length; y++) {
                LevelTile tile = tileList.get(getTileAt(x, y));
                LevelTile tileAbove = tileList.get(getTileAt(x, y - 1));
                if (tile.isDecorable() && !tileAbove.isSolid()) {
                    decorationsToSpawn++;
                    availableSpots.add(new Point(x, y - 1));
                }
            }
        }
        decorationsToSpawn /= 4;
        RNG.seed(((GameLevel)this).id);
        for (int i = 0; i < decorationsToSpawn; i++) {
            Decoration decoration = RNG.choose(decorations);
            int width = decoration.getType().getWidth();
            int height = decoration.getType().getHeight();
            ArrayList<Point> spots = new ArrayList<>(availableSpots);
            ArrayList<Point> validSpots = new ArrayList<>();
            for (Point spot : spots) {
                boolean canSpawn = true;
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y > -height; y--) {
                        int X = spot.x + x;
                        int Y = spot.y + y;
                        if (X < 0 || Y < 0 || X >= tilemap.length || Y >= tilemap[0].length) {
                            canSpawn = false;
                            break;
                        }
                        if (tileList.get(getTileAt(X, Y)).isSolid()) {
                            canSpawn = false;
                            break;
                        }
                        if (!tileList.get(getTileAt(X, Y + 1)).isSolid() && y == 0) {
                            canSpawn = false;
                            break;
                        }
                    }
                    if (!canSpawn) break;
                }
                if (canSpawn) {
                    validSpots.add(spot);
                }
            }
            if (validSpots.size() == 0) continue;
            Point spot = RNG.choose(validSpots);
            this.decorations.add(new Pair<>(new Point(spot.x, spot.y - decoration.getType().getHeight() + 1), decoration));
            for (int x = 0; x < decoration.getType().getWidth(); x++) {
                availableSpots.remove(new Point(spot.x + x, spot.y));
            }
        }
    }
    public void draw(Renderer renderer, int unitWidth, int unitHeight, Entity focusEntity) {
        int width = Main.WIDTH;
        int height = Main.HEIGHT;
        int fullWidth = getLevelBoundaries().width * unitWidth;
        int fullHeight = getLevelBoundaries().height * unitHeight;
        renderer.setColor(bg.getColor());
        renderer.rect(0, 0, width, height);
        renderer.setColor(0xFFFFFFFF);
        double[] cameraTarget = focusEntity.getTextureOrigin(unitWidth, unitHeight);
        camera.setTarget(cameraTarget[0] + ((GameEntity)focusEntity).textureRegion.width / 2.0 / (double)unitWidth, -cameraTarget[1] - ((GameEntity)focusEntity).textureRegion.height / 2.0 / (double)unitHeight + Main.HEIGHT / (double)unitHeight);
        if (Controls.LOOK_UP.isPressed()) camera.targetY += Main.HEIGHT / 32.0 - 2;
        if (Controls.LOOK_LEFT.isPressed()) camera.targetX -= Main.WIDTH / 32.0 - 2;
        if (Controls.LOOK_DOWN.isPressed()) camera.targetY -= Main.HEIGHT / 32.0 - 2;
        if (Controls.LOOK_RIGHT.isPressed()) camera.targetX += Main.WIDTH / 32.0 - 2;
        camera.update();
        Game.cameraX = camera.x * unitWidth - width / 2.0;
        Game.cameraY = camera.y * unitHeight - height / 2.0;
        if (Game.cameraX < 0) Game.cameraX = 0;
        if (Game.cameraY > 0) Game.cameraY = 0;
        if (Game.cameraX > fullWidth - width) Game.cameraX = fullWidth - width;
        if (Game.cameraY < height - fullHeight) Game.cameraY = height - fullHeight;
        Game.cameraX += RNG.range(-camera.screenshakeX, camera.screenshakeX);
        Game.cameraY += RNG.range(-camera.screenshakeY, camera.screenshakeY);
        if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(Main.solidColorShader);
        Main.solidColorShader.setUniformf("color", 0, 0, 0, 1);
        Texture bgImage = bg.getImage();
        int bgRepeatX = (int)Math.ceil((double)width / bgImage.getWidth()) + 1;
        int bgX = (int)Math.abs(Game.cameraX % (2 / ((double)width / bgImage.getWidth()) * width));
        renderer.translate(-bgX / 2, 0);
        for (int x = 0; x < bgRepeatX; x++) {
            renderer.draw(bgImage, x * bgImage.getWidth(), 0);
        }
        if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(Main.solidColorShader);
        Main.solidColorShader.setUniformf("color", 0.5f, 0.5f, 0.5f, 1);
        renderer.resetTranslation();
        renderer.translate(-(int)Game.cameraX, (int)Game.cameraY);
        for (Pair<Point, Decoration> decoration : decorations) {
            renderer.draw(decoration.b.getTexture(), decoration.a.x * unitWidth, decoration.a.y * unitHeight);
        }
        if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(Main.solidColorShader);
        Main.solidColorShader.setUniformf("color", 1, 1, 1, 1);
        for (Entity entity : entities) {
            if (!entity.getProperties().drawInBG || ((GameEntity)entity).invisible || (((GameEntity)entity).entityType == EntityType.PLAYER && Game.invincibilityFrames % 2 == 1 && Game.powerupTimeout == 0) || entity.getProperties().texture == null) continue;
            double[] origin = entity.getTextureOrigin(unitWidth, unitHeight);
            if (entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height <= 0) {
                if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(null);
                renderer.setColor(0x000000AF);
                renderer.draw(entity.getProperties().texture, ((GameEntity)entity).textureRegion, (int)(origin[0] * unitWidth), 0, (int)(((GameEntity)entity).textureRegion.width * ((GameEntity)entity).provider.scaleX), (int)(((GameEntity)entity).textureRegion.height * ((GameEntity)entity).provider.scaleY), ((GameEntity)entity).provider.flipX, ((GameEntity)entity).provider.flipY);
                renderer.setColor(0xFFFFFFFF);
                if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(Main.solidColorShader);
            }
            else {
                if (((GameLevel)this).gimmick != GameLevel.Gimmick.FOG && ((GameEntity)entity).entityType == EntityType.PLAYER && Game.invincibilityTimeout > 0) renderer.setShader(Main.hueshiftShader);
                Main.hueshiftShader.setUniformf("hueshift", System.currentTimeMillis() % 1000 / 1000f);
                renderer.draw(entity.getProperties().texture, ((GameEntity)entity).textureRegion, (int)(origin[0] * unitWidth), (int)(origin[1] * unitHeight), (int)(((GameEntity)entity).textureRegion.width * ((GameEntity)entity).provider.scaleX), (int)(((GameEntity)entity).textureRegion.height * ((GameEntity)entity).provider.scaleY), ((GameEntity)entity).provider.flipX, ((GameEntity)entity).provider.flipY);
                if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(Main.solidColorShader);
                else renderer.setShader(null);
            }
        }
        for (int x = (int)Math.max(0, (Game.cameraX / (double)unitWidth) - 1); x < Math.min(tilemap.length, (Game.cameraX + width) / (double)unitWidth + 1); x++) {
            for (int y = (int)Math.max(0, (-Game.cameraY / (double)unitHeight) - 1); y < Math.min(tilemap[0].length, (-Game.cameraY + height) / (double)unitHeight + 1); y++) {
                LevelTile tile = tileList.get(getTileAt(x, y));
                if (!((GameLevel)this).loaded[x][y]) {
                    ((GameLevel)this).loaded[x][y] = true;
                    tile.init(this, x, y);
                }
                tile.update(this, x, y);
                if (tile instanceof GameTile) {
                    Rectangle rectangle = new Rectangle(0, 0, 16, 16);
                    int location = ((GameTile)tile).getCurrentTextureLocation();
                    rectangle.x = (location % 16) * 16;
                    rectangle.y = (location / 16) * 16;
                    renderer.draw(tile.getTexture(), rectangle, x * unitWidth, y * unitHeight, unitWidth, unitHeight);
                    if (((GameLevel)this).theme == 3 && tile.isSolid()) {
                        Point point = new Point(x, y);
                        Boolean exposedToAir = Game.snowCache.get(point);
                        if (exposedToAir == null) {
                            exposedToAir = true;
                            for (int i = 0; i < y; i++) {
                                if (tileList.get(getTileAt(x, i)).isSolid()) exposedToAir = false;
                            }
                            Game.snowCache.put(point, exposedToAir);
                        }
                        if (exposedToAir) renderer.draw(TextureLoader.get("images/themes/3/snow.png"), x * unitWidth, y * unitHeight, unitWidth, unitHeight);
                    }
                }
            }
        }
        for (Entity entity : entities) {
            if (entity.getProperties().drawInBG || ((GameEntity)entity).invisible || (((GameEntity)entity).entityType == EntityType.PLAYER && Game.invincibilityFrames % 2 == 1 && Game.powerupTimeout == 0) || entity.getProperties().texture == null) continue;
            double[] origin = entity.getTextureOrigin(unitWidth, unitHeight);
            if (entity.getPhysics().getHitbox().y + entity.getPhysics().getHitbox().height <= 0) {
                if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(null);
                renderer.setColor(0x000000AF);
                renderer.draw(entity.getProperties().texture, ((GameEntity)entity).textureRegion, (int)(origin[0] * unitWidth), 0, (int)(((GameEntity)entity).textureRegion.width * ((GameEntity)entity).provider.scaleX), (int)(((GameEntity)entity).textureRegion.height * ((GameEntity)entity).provider.scaleY), ((GameEntity)entity).provider.flipX, ((GameEntity)entity).provider.flipY);
                renderer.setColor(0xFFFFFFFF);
                if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(Main.solidColorShader);
            }
            else {
                if (((GameLevel)this).gimmick != GameLevel.Gimmick.FOG && ((GameEntity)entity).entityType == EntityType.PLAYER && Game.invincibilityTimeout > 0) renderer.setShader(Main.hueshiftShader);
                Main.hueshiftShader.setUniformf("hueshift", System.currentTimeMillis() % 1000 / 1000f);
                renderer.draw(entity.getProperties().texture, ((GameEntity)entity).textureRegion, (int)(origin[0] * unitWidth), (int)(origin[1] * unitHeight), (int)(((GameEntity)entity).textureRegion.width * ((GameEntity)entity).provider.scaleX), (int)(((GameEntity)entity).textureRegion.height * ((GameEntity)entity).provider.scaleY), ((GameEntity)entity).provider.flipX, ((GameEntity)entity).provider.flipY);
                if (((GameLevel)this).gimmick == GameLevel.Gimmick.FOG) renderer.setShader(Main.solidColorShader);
                else renderer.setShader(null);
            }
        }
        if (displayHitboxes) {
            renderer.setColor(0xFF00007F);
            for (Entity entity : entities) {
                renderer.rect(entity.getPhysics().getHitbox().x * 16 / 100, entity.getPhysics().getHitbox().y * 16 / 100, entity.getPhysics().getHitbox().width * 16 / 100, entity.getPhysics().getHitbox().height * 16 / 100);
            }
            renderer.setColor(0xFFFFFFFF);
        }
        Fluid fluid = ((GameLevel)this).fluid;
        if (fluid != null) {
            int offset = (int)(System.currentTimeMillis() % 800 / 100);
            int x = (int)Game.cameraX / 8 * 8;
            int y = fluid.movement.getFluidLevel();
            Texture overlay = TextureLoader.get("images/fluid/overlay.png");
            Texture mask = TextureLoader.get("images/fluid/mask.png");
            renderer.setColor(fluid.type.color);
            renderer.draw(mask, x - offset, y);
            renderer.rect(x - offset, y + 8, Main.WIDTH + 16, Main.HEIGHT);
            renderer.setColor(0xFFFFFFFF);
            renderer.draw(overlay, x - offset, y);
        }
        renderer.setShader(null);
    }
}
package com.smj.game;

import com.smj.game.entity.GameEntity;
import com.smj.jmario.entity.Entity;
import com.smj.jmario.level.Level;

import java.awt.Rectangle;

public class Location {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Level level;
    private Location(int x, int y, int width, int height, Level level) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level = level;
    }
    public static Location entity(Entity entity) {
        Rectangle hitbox = entity.getPhysics().getHitbox();
        return new Location((hitbox.x + hitbox.width / 2) * 16 / 100, (hitbox.y + hitbox.height / 2) * 16 / 100, hitbox.width * 16 / 100, hitbox.height * 16 / 100, ((GameEntity)entity).getLevel());
    }
    public static Location tile(int x, int y, Level level) {
        return new Location(x * 16 + 8, y * 16 + 8, 16, 16, level);
    }
    public static Location pixel(int x, int y, Level level) {
        return new Location(x, y, 1, 1, level);
    }
    public static Location custom(int x, int y, int width, int height, Level level) {
        return new Location(x, y, width, height, level);
    }
    public static Location none() {
        return new Location(-1000, -1000, 1, 1, null);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Level getLevel() {
        return level;
    }
    public Location move(int x, int y) {
        return new Location(this.x + x, this.y + y, width, height, level);
    }
}

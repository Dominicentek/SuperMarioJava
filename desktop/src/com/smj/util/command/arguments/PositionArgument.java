package com.smj.util.command.arguments;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.util.AccessCounter;
import com.smj.util.command.CommandException;
import com.smj.util.command.console.Console;

import java.awt.Point;
import java.awt.Rectangle;

public class PositionArgument extends CommandArgument<Point> {
    public static final int PIXEL = 16;
    public static final int ENTITY = 100;
    public static final int TILE = 1;
    public static final int ABSOLUTE = 0;
    public static final int RELATIVE = 1;
    public static final int CAMERA = 2;
    public static final int WRAPPED = 3;
    private final int scale;
    public PositionArgument(String name, int scale) {
        super(name);
        this.scale = scale;
    }
    public Point parse(String[] arguments, AccessCounter counter) {
        String x = arguments[counter.access()];
        String y = arguments[counter.access()];
        int xMod = ABSOLUTE;
        int yMod = ABSOLUTE;
        int xSubstr = 1;
        int ySubstr = 1;
        if (x.startsWith("@")) xMod = RELATIVE;
        else if (x.startsWith("&")) xMod = CAMERA;
        else if (x.startsWith("~")) xMod = WRAPPED;
        else xSubstr = 0;
        if (y.startsWith("@")) yMod = RELATIVE;
        else if (y.startsWith("&")) yMod = CAMERA;
        else if (y.startsWith("~")) yMod = WRAPPED;
        else ySubstr = 0;
        x = x.substring(xSubstr);
        y = y.substring(ySubstr);
        int X = (int)(Double.parseDouble(x) * scale);
        int Y = (int)(Double.parseDouble(y) * scale);
        Rectangle hitbox = Game.player.getPhysics().getHitbox();
        if (xMod == RELATIVE) X += (hitbox.x + hitbox.width / 2) / 100.0 * scale;
        if (yMod == RELATIVE) Y += (hitbox.y + hitbox.height / 2) / 100.0 * scale;
        if (xMod == CAMERA) X += Game.cameraX / 16.0 * scale;
        if (yMod == CAMERA) Y += Game.cameraY / 16.0 * scale;
        if (xMod == WRAPPED) X = Game.currentLevel.getLevelBoundaries().width * scale - X;
        if (yMod == WRAPPED) Y = Game.currentLevel.getLevelBoundaries().height * scale - Y;
        return new Point(X, Y);
    }
}

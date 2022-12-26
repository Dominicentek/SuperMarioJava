package com.smj.game.fluid;

public class FluidMovement {
    public final int highTide;
    public final int lowTide;
    public final int stayTime;
    public final int fallTime;
    public final int movementType;
    public static final int FALL = 0;
    public static final int RISE = 1;
    public static final int WAVES = 2;
    private int y;
    private int timeout;
    private int stage;
    public FluidMovement(int highTide, int lowTide, int stayTime, int fallTime, int movementType) {
        this.highTide = highTide;
        this.lowTide = lowTide;
        this.stayTime = stayTime;
        this.fallTime = fallTime;
        this.movementType = movementType;
        timeout = stayTime;
        stage = movementType == 0 ? 0 : 2;
        y = movementType == 0 ? highTide : lowTide;
    }
    public void update() {
        timeout--;
        if (timeout == 0) {
            stage++;
            if (stage == 4) stage = 0;
            timeout = stage % 2 == 0 ? stayTime : fallTime;
        }
        if (stage == 0) y = highTide;
        if (stage == 1) y = (int)((Math.sin(90 + ((fallTime - 1 - timeout) / (double)fallTime * 180)) / 2 + 0.5) * (highTide - lowTide)) + lowTide;
        if (stage == 2) y = lowTide;
        if (stage == 3) y = (int)((Math.sin(270 + ((timeout - 1) / (double)fallTime * 180)) / 2 + 0.5) * (highTide - lowTide)) + lowTide;
    }
    public int getFluidLevel() {
        return y;
    }
}

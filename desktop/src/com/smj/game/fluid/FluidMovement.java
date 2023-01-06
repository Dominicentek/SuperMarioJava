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
        stage = movementType == FALL ? 0 : 2;
        y = movementType == FALL ? highTide : lowTide;
    }
    public void update() {
        timeout--;
        timeout = Math.max(0, timeout);
        if (timeout == 0) {
            if (movementType == WAVES) {
                stage++;
                if (stage == 4) stage = 0;
                timeout = stage % 2 == 0 ? stayTime : fallTime;
            }
            if (movementType == FALL) {
                if (stage != 3) {
                    stage = 3;
                    timeout = fallTime;
                }
            }
            if (movementType == RISE) {
                if (stage != 1) {
                    stage = 1;
                    timeout = fallTime;
                }
            }
        }
        if (stage == 0) y = lowTide;
        if (stage == 1) y = (int)((Math.sin(Math.toRadians(270 + (timeout / (double)fallTime * 180))) / 2 + 0.5) * (lowTide - highTide)) + highTide;
        if (stage == 2) y = highTide;
        if (stage == 3) y = (int)((Math.sin(Math.toRadians(90 + (timeout / (double)fallTime * 180))) / 2 + 0.5) * (lowTide - highTide)) + highTide;
    }
    public int getFluidLevel() {
        return y;
    }
}

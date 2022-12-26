package com.smj.jmario.entity.physics;

public class PlayerMovement {
    private boolean walkLeft = false;
    private boolean walkRight = false;
    private boolean running = false;
    private boolean jumpButtonHeld = false;
    PlayerMovement() {}
    public PlayerMovement setWalkingLeft(boolean walkLeft) {
        this.walkLeft = walkLeft;
        return this;
    }
    public PlayerMovement setWalkingRight(boolean walkRight) {
        this.walkRight = walkRight;
        return this;
    }
    public PlayerMovement setRunning(boolean running) {
        this.running = running;
        return this;
    }
    public PlayerMovement setJumping(boolean jumping) {
        this.jumpButtonHeld = jumping;
        return this;
    }
    public boolean isWalkingLeft() {
        return walkLeft;
    }
    public boolean isWalkingRight() {
        return walkRight;
    }
    public boolean isRunning() {
        return running;
    }
    public boolean isJumping() {
        return jumpButtonHeld;
    }
}

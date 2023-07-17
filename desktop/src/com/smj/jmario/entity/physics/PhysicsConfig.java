package com.smj.jmario.entity.physics;

import java.awt.Dimension;

public class PhysicsConfig {
    public PhysicsConfig(PhysicsConfig config) {
        this.hitbox = config.hitbox;
        this.underwater = config.underwater;
        this.solidHitbox = config.solidHitbox;
        this.collisionEnabled = config.collisionEnabled;
        this.maxWalkingSpeed = config.maxWalkingSpeed;
        this.maxRunningSpeed = config.maxRunningSpeed;
        this.maxUnderwaterSpeed = config.maxUnderwaterSpeed;
        this.maxUnderwaterOnGroundSpeed = config.maxUnderwaterOnGroundSpeed;
        this.acceleration = config.acceleration;
        this.underwaterAcceleration = config.underwaterAcceleration;
        this.gravity = config.gravity;
        this.underwaterGravity = config.underwaterGravity;
        this.terminalVelocity = config.terminalVelocity;
        this.maxJumpHeight = config.maxJumpHeight;
        this.jumpingSpeed = config.jumpingSpeed;
        this.underwaterSwimSpeed = config.underwaterSwimSpeed;
    }
    public PhysicsConfig() {}
    public Dimension hitbox = new Dimension(100, 100);
    public boolean underwater = false;
    public boolean solidHitbox = false;
    public boolean collisionEnabled = true;
    public double maxWalkingSpeed = 0.5859375;
    public double maxRunningSpeed = 0.9765625;
    public double maxUnderwaterSpeed = 0.390625;
    public double maxUnderwaterOnGroundSpeed = 0.09765625;
    public double acceleration = 0.05859375;
    public double underwaterAcceleration = 0.05859375;
    public double gravity = 0.078125;
    public double underwaterGravity = 0.01953125;
    public double terminalVelocity = 3.2421875;
    public double maxJumpHeight = 300;
    public double jumpingSpeed = 1.171875;
    public double underwaterSwimSpeed = 0.5859375;
    public PhysicsConfig hitbox(Dimension hitbox) {
        this.hitbox = hitbox;
        return this;
    }
    public PhysicsConfig underwater(boolean underwater) {
        this.underwater = underwater;
        return this;
    }
    public PhysicsConfig solidHitbox(boolean solidHitbox) {
        this.solidHitbox = solidHitbox;
        return this;
    }
    public PhysicsConfig collisionEnabled(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
        return this;
    }
    public PhysicsConfig maxWalkingSpeed(double maxWalkingSpeed) {
        this.maxWalkingSpeed = maxWalkingSpeed;
        return this;
    }
    public PhysicsConfig maxRunningSpeed(double maxRunningSpeed) {
        this.maxRunningSpeed = maxRunningSpeed;
        return this;
    }
    public PhysicsConfig maxUnderwaterSpeed(double maxUnderwaterSpeed) {
        this.maxUnderwaterSpeed = maxUnderwaterSpeed;
        return this;
    }
    public PhysicsConfig maxUnderwaterOnGroundSpeed(double maxUnderwaterOnGroundSpeed) {
        this.maxUnderwaterOnGroundSpeed = maxUnderwaterOnGroundSpeed;
        return this;
    }
    public PhysicsConfig acceleration(double acceleration) {
        this.acceleration = acceleration;
        return this;
    }
    public PhysicsConfig underwaterAcceleration(double underwaterAcceleration) {
        this.underwaterAcceleration = underwaterAcceleration;
        return this;
    }
    public PhysicsConfig gravity(double gravity) {
        this.gravity = gravity;
        return this;
    }
    public PhysicsConfig underwaterGravity(double underwaterGravity) {
        this.underwaterGravity = underwaterGravity;
        return this;
    }
    public PhysicsConfig terminalVelocity(double terminalVelocity) {
        this.terminalVelocity = terminalVelocity;
        return this;
    }
    public PhysicsConfig maxJumpHeight(double maxJumpHeight) {
        this.maxJumpHeight = maxJumpHeight;
        return this;
    }
    public PhysicsConfig jumpingSpeed(double jumpingSpeed) {
        this.jumpingSpeed = jumpingSpeed;
        return this;
    }
    public PhysicsConfig underwaterSwimSpeed(double underwaterSwimSpeed) {
        this.underwaterSwimSpeed = underwaterSwimSpeed;
        return this;
    }
}

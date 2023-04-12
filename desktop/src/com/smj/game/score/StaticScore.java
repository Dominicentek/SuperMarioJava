package com.smj.game.score;

public class StaticScore extends Score {
    public static final StaticScore BRICK = new StaticScore().score(50);
    public static final StaticScore COIN = new StaticScore().score(200);
    public static final StaticScore POWERUP = new StaticScore().score(1000);
    public static final StaticScore TIMER = new StaticScore().score(5);
    public static final StaticScore SHELL_KICK = new StaticScore().score(400);
    public static final StaticScore CHECKPOINT = new StaticScore().score(2000);
    public static final StaticScore STAR_FINISH = new StaticScore().coins(10);
    public static final StaticScore FIREBALL = new StaticScore().score(100);
    private int score;
    private int lives;
    private int coins;
    public StaticScore score(int score) {
        this.score = score;
        return this;
    }
    public StaticScore lives(int lives) {
        this.lives = lives;
        return this;
    }
    public StaticScore coins(int coins) {
        this.coins = coins;
        return this;
    }
    public int awardScore() {
        return score;
    }
    public int awardLives() {
        return lives;
    }
    public int awardCoins() {
        return coins;
    }
    public StaticScore applyMultiplier(float multiplier) {
        return new StaticScore().score((int)(score * multiplier)).lives((int)(lives * multiplier)).coins((int)(coins * multiplier));
    }
}

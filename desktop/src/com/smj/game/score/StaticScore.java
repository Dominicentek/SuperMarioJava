package com.smj.game.score;

public class StaticScore extends Score {
    public static final StaticScore BRICK = new StaticScore(50);
    public static final StaticScore COIN = new StaticScore(200);
    public static final StaticScore POWERUP = new StaticScore(1000);
    public static final StaticScore TIMER = new StaticScore(5);
    public static final StaticScore SHELL_KICK = new StaticScore(400);
    public static final StaticScore CHECKPOINT = new StaticScore(2000);
    public static final StaticScore STAR_FINISH = new StaticScore(1000);
    public static final StaticScore FIREBALL = new StaticScore(100);
    private final int score;
    public StaticScore(int score) {
        this.score = score;
    }
    public int awardScore() {
        return score;
    }
    public int awardLives() {
        return 0;
    }
    public StaticScore applyMultiplier(float scoreMultiplier) {
        return new StaticScore((int)(score * scoreMultiplier));
    }
}

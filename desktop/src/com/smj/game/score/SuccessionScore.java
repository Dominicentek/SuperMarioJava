package com.smj.game.score;

public class SuccessionScore extends Score {
    private static final int[] REWARDS = {
        200,
        400,
        800,
        1000,
        2000,
        4000,
        8000
    };
    private int level;
    public void reset() {
        level = 0;
    }
    public void awarded() {
        if (level < REWARDS.length) level++;
    }
    public int awardScore() {
        return level == REWARDS.length ? 0 : REWARDS[level];
    }
    public int awardLives() {
        return level == REWARDS.length ? 1 : 0;
    }
}

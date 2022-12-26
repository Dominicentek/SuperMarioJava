package com.smj.game.score;

public class BowserScore extends Score {
    private static final int[] SCORES = {
        2000,
        4000,
        8000
    };
    public int step = 0;
    public int awardScore() {
        int score = SCORES[step];
        step++;
        return score;
    }
    public int awardLives() {
        return 0;
    }
}

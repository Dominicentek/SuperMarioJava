package com.smj.game.score;

public class CoinScore extends Score {
    private int coins;
    public CoinScore(int coins) {
        this.coins = coins;
    }
    public int awardScore() {
        return 0;
    }
    public int awardLives() {
        return 0;
    }
    public int awardCoins() {
        return coins;
    }
}

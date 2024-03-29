package com.smj.game.challenge;

public enum ChallengeType {
    TIME(0x7F7FFF),
    COIN(0xFFFF00),
    JUMP(0xFF3F3F),
    MISC(0x00FF00);
    public int color;
    ChallengeType(int color) {
        this.color = color;
    }
}

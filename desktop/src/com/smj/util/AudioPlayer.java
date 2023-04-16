package com.smj.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.Location;
import com.smj.game.entity.GameEntity;
import com.smj.jmario.entity.Entity;

public class AudioPlayer {
    public static final AudioPlayer BEEP = FileLoader.read("sounds/beep.wav").asSound().noTimeout();
    public static final AudioPlayer TIMEOUT = FileLoader.read("sounds/timeout.wav").asSound();
    public static final AudioPlayer DEATH = FileLoader.read("sounds/death.wav").asSound();
    public static final AudioPlayer GAME_OVER = FileLoader.read("sounds/gameover.wav").asSound();
    public static final AudioPlayer JUMP = FileLoader.read("sounds/jump.wav").asSound();
    public static final AudioPlayer POWERUP = FileLoader.read("sounds/powerup.wav").asSound();
    public static final AudioPlayer WARP = FileLoader.read("sounds/warp.wav").asSound();
    public static final AudioPlayer KICK = FileLoader.read("sounds/kick.wav").asSound();
    public static final AudioPlayer STOMP = FileLoader.read("sounds/stomp.wav").asSound();
    public static final AudioPlayer BUMP = FileLoader.read("sounds/bump.wav").asSound();
    public static final AudioPlayer LIFE = FileLoader.read("sounds/life.wav").asSound();
    public static final AudioPlayer FIREBALL = FileLoader.read("sounds/fireball.wav").asSound();
    public static final AudioPlayer COIN = FileLoader.read("sounds/coin.wav").asSound();
    public static final AudioPlayer KEY_COIN = FileLoader.read("sounds/keycoin.wav").asSound();
    public static final AudioPlayer ALL_KEY_COINS = FileLoader.read("sounds/allkeycoins.wav").asSound();
    public static final AudioPlayer QUESTION_BLOCK = FileLoader.read("sounds/questionblock.wav").asSound();
    public static final AudioPlayer BRICK = FileLoader.read("sounds/brick.wav").asSound();
    public static final AudioPlayer BIG_COIN = FileLoader.read("sounds/bigcoin.wav").asSound();
    public static final AudioPlayer SWITCH = FileLoader.read("sounds/switch.wav").asSound();
    public static final AudioPlayer CHECKPOINT = FileLoader.read("sounds/checkpoint.wav").asSound();
    public static final AudioPlayer FINISH = FileLoader.read("sounds/finish.wav").asSound();
    public static final AudioPlayer EXPLOSION = FileLoader.read("sounds/explosion.wav").asSound();
    public static final AudioPlayer BURNER = FileLoader.read("sounds/burner.wav").asSound();
    public static final AudioPlayer PAUSE = FileLoader.read("sounds/pause.wav").asSound();
    public static final AudioPlayer ICICLE_FALL = FileLoader.read("sounds/iciclefall.wav").asSound();
    public static final AudioPlayer ICICLE_BREAK = FileLoader.read("sounds/iciclebreak.wav").asSound();
    public static final SMJMusic[] MUSIC = {
        FileLoader.read("sounds/music/ground.smjaud").asMusic(),
        FileLoader.read("sounds/music/underground.smjaud").asMusic(),
        FileLoader.read("sounds/music/desert.smjaud").asMusic(),
        FileLoader.read("sounds/music/snow.smjaud").asMusic(),
        FileLoader.read("sounds/music/underwater.smjaud").asMusic(),
        FileLoader.read("sounds/music/forest.smjaud").asMusic(),
        FileLoader.read("sounds/music/sky.smjaud").asMusic(),
        FileLoader.read("sounds/music/lava.smjaud").asMusic(),
        FileLoader.read("sounds/music/castle.smjaud").asMusic(),
        FileLoader.read("sounds/music/bonus.smjaud").asMusic(),
        FileLoader.read("sounds/music/enemy.smjaud").asMusic(),
        FileLoader.read("sounds/music/superstar.smjaud").asMusic(),
        FileLoader.read("sounds/music/boss.smjaud").asMusic(),
        FileLoader.read("sounds/music/title.smjaud").asMusic(),
        FileLoader.read("sounds/music/ending.smjaud").asMusic()
    };
    private final Sound sound;
    private long time = System.currentTimeMillis();
    private boolean hasTimeout = true;
    public AudioPlayer(FileHandle handle) {
        sound = Gdx.audio.newSound(handle);
    }
    public AudioPlayer noTimeout() {
        hasTimeout = false;
        return this;
    }
    public void play() {
        sound.play(Main.options.soundVolume);
    }
    public void play(Location source) {
        if (System.currentTimeMillis() - time < 100 && hasTimeout) return;
        time = System.currentTimeMillis();
        Location player = Location.entity(Game.player);
        double distance = Math.sqrt(Math.pow(source.getX() - player.getX(), 2) + Math.pow(source.getY() - player.getY(), 2));
        double hearingDistance = 320;
        double volume = 1 - MathUtils.clamp(distance / hearingDistance, 0, 1);
        double pan = Main.options.stereoSound ? MathUtils.clamp((source.getX() - player.getX()) / hearingDistance, -1, 1) : 0;
        sound.play((float)volume * Main.options.soundVolume, 1f, (float)pan);
    }
}
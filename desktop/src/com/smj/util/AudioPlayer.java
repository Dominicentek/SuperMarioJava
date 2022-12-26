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
    public static final AudioPlayer BEEP = new AudioPlayer(Gdx.files.internal("assets/sounds/beep.wav"), false);
    public static final AudioPlayer TIMEOUT = new AudioPlayer(Gdx.files.internal("assets/sounds/timeout.wav"));
    public static final AudioPlayer DEATH = new AudioPlayer(Gdx.files.internal("assets/sounds/death.wav"));
    public static final AudioPlayer GAME_OVER = new AudioPlayer(Gdx.files.internal("assets/sounds/gameover.wav"));
    public static final AudioPlayer JUMP = new AudioPlayer(Gdx.files.internal("assets/sounds/jump.wav"));
    public static final AudioPlayer POWERUP = new AudioPlayer(Gdx.files.internal("assets/sounds/powerup.wav"));
    public static final AudioPlayer WARP = new AudioPlayer(Gdx.files.internal("assets/sounds/warp.wav"));
    public static final AudioPlayer KICK = new AudioPlayer(Gdx.files.internal("assets/sounds/kick.wav"));
    public static final AudioPlayer STOMP = new AudioPlayer(Gdx.files.internal("assets/sounds/stomp.wav"));
    public static final AudioPlayer BUMP = new AudioPlayer(Gdx.files.internal("assets/sounds/bump.wav"));
    public static final AudioPlayer LIFE = new AudioPlayer(Gdx.files.internal("assets/sounds/life.wav"));
    public static final AudioPlayer FIREBALL = new AudioPlayer(Gdx.files.internal("assets/sounds/fireball.wav"));
    public static final AudioPlayer COIN = new AudioPlayer(Gdx.files.internal("assets/sounds/coin.wav"));
    public static final AudioPlayer KEY_COIN = new AudioPlayer(Gdx.files.internal("assets/sounds/keycoin.wav"));
    public static final AudioPlayer ALL_KEY_COINS = new AudioPlayer(Gdx.files.internal("assets/sounds/allkeycoins.wav"));
    public static final AudioPlayer QUESTION_BLOCK = new AudioPlayer(Gdx.files.internal("assets/sounds/questionblock.wav"));
    public static final AudioPlayer BRICK = new AudioPlayer(Gdx.files.internal("assets/sounds/brick.wav"));
    public static final AudioPlayer BIG_COIN = new AudioPlayer(Gdx.files.internal("assets/sounds/bigcoin.wav"));
    public static final AudioPlayer SWITCH = new AudioPlayer(Gdx.files.internal("assets/sounds/switch.wav"));
    public static final AudioPlayer CHECKPOINT = new AudioPlayer(Gdx.files.internal("assets/sounds/checkpoint.wav"));
    public static final AudioPlayer FINISH = new AudioPlayer(Gdx.files.internal("assets/sounds/finish.wav"));
    public static final AudioPlayer EXPLOSION = new AudioPlayer(Gdx.files.internal("assets/sounds/explosion.wav"));
    public static final AudioPlayer BURNER = new AudioPlayer(Gdx.files.internal("assets/sounds/burner.wav"));
    public static final AudioPlayer PAUSE = new AudioPlayer(Gdx.files.internal("assets/sounds/pause.wav"));
    public static final SMJMusic[] MUSIC = {
        new SMJMusic(Gdx.files.internal("assets/sounds/music/ground.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/underground.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/desert.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/snow.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/underwater.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/forest.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/sky.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/lava.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/castle.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/bonus.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/enemy.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/superstar.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/boss.smjaud")),
        new SMJMusic(Gdx.files.internal("assets/sounds/music/title.smjaud"))
    };
    private final Sound sound;
    private long time = System.currentTimeMillis();
    private boolean hasTimeout;
    public AudioPlayer(FileHandle file) {
        this(file, true);
    }
    public AudioPlayer(FileHandle file, boolean hasTimeout) {
        sound = Gdx.audio.newSound(file);
        this.hasTimeout = hasTimeout;
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
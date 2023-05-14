package com.smj.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Ogg;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALLwjgl3Audio;
import com.badlogic.gdx.files.FileHandle;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.EXTEfx;

import java.util.ArrayList;

public class SMJMusic {
    private static ArrayList<SMJMusic> allMusic = new ArrayList<>();
    private static SMJMusic currentMusic;
    private static boolean paused;
    private static boolean introPaused;
    private static boolean loopPaused;
    private static boolean introFasterPaused;
    private static boolean loopFasterPaused;
    private Ogg.Music intro;
    private Ogg.Music loop;
    private Ogg.Music introFaster; // this is a terrible way of implementing the faster audio tracks, but libgdx is a bitch and doesnt provide tempo changing functions
    private Ogg.Music loopFaster;
    public static float volume = 1f;
    public final String name;
    public SMJMusic(byte[] data, String path) {
        allMusic.add(this);
        ByteArray array = new ByteArray(data);
        byte[] introData = array.readArray(array.readInt());
        byte[] loopData = array.readArray(array.readInt());
        boolean containsFasterData = array.readBoolean();
        byte[] introFasterData = introData;
        byte[] loopFasterData = loopData;
        if (containsFasterData) {
            introFasterData = array.readArray(array.readInt());
            loopFasterData = array.readArray(array.readInt());
        }
        intro = new Ogg.Music((OpenALLwjgl3Audio)Gdx.audio, new BinaryFileHandle(introData, path));
        loop = new Ogg.Music((OpenALLwjgl3Audio)Gdx.audio, new BinaryFileHandle(loopData, path));
        introFaster = new Ogg.Music((OpenALLwjgl3Audio)Gdx.audio, new BinaryFileHandle(introFasterData, path));
        loopFaster = new Ogg.Music((OpenALLwjgl3Audio)Gdx.audio, new BinaryFileHandle(loopFasterData, path));
        intro.setOnCompletionListener((music) -> {
            loop.play();
        });
        loop.setLooping(true);
        introFaster.setOnCompletionListener((music) -> {
            loopFaster.play();
        });
        loopFaster.setLooping(true);
        name = Gdx.files.internal(path).nameWithoutExtension();
    }
    public void play(boolean faster) {
        if (currentMusic != null) currentMusic.stop();
        currentMusic = this;
        intro.reset();
        loop.reset();
        introFaster.reset();
        loopFaster.reset();
        if (!faster) intro.play();
        else introFaster.play();
        intro.setVolume(0.5f * volume);
        loop.setVolume(0.5f * volume);
        introFaster.setVolume(0.5f * volume);
        loopFaster.setVolume(0.5f * volume);
    }
    public void play() {
        play(false);
    }
    public void stop() {
        if (intro.isPlaying()) intro.stop();
        if (loop.isPlaying()) loop.stop();
        if (introFaster.isPlaying()) introFaster.stop();
        if (loopFaster.isPlaying()) loopFaster.stop();
    }
    public static void stopMusic() {
        if (currentMusic == null) return;
        currentMusic.stop();
        currentMusic = null;
    }
    public static void pause() {
        if (paused) return;
        if (currentMusic == null) return;
        if (currentMusic.intro.isPlaying()) {
            currentMusic.intro.pause();
            introPaused = true;
        }
        else introPaused = false;
        if (currentMusic.loop.isPlaying()) {
            currentMusic.loop.pause();
            loopPaused = true;
        }
        else loopPaused = false;
        if (currentMusic.introFaster.isPlaying()) {
            currentMusic.introFaster.pause();
            introFasterPaused = true;
        }
        else introFasterPaused = false;
        if (currentMusic.loopFaster.isPlaying()) {
            currentMusic.loopFaster.pause();
            loopFasterPaused = true;
        }
        else loopFasterPaused = false;
        paused = true;
    }
    public static void resume() {
        if (!paused) return;
        if (introPaused) currentMusic.intro.play();
        if (loopPaused) currentMusic.loop.play();
        if (introFasterPaused) currentMusic.introFaster.play();
        if (loopFasterPaused) currentMusic.loopFaster.play();
        paused = false;
    }
    public static void setVolumeAll(float volume) {
        SMJMusic.volume = volume;
        for (SMJMusic music : AudioPlayer.MUSIC) {
            music.setVolume(volume);
        }
    }
    public static boolean isPlaying() {
        return currentMusic != null && !paused;
    }
    public void setVolume(float volume) {
        intro.setVolume(0.5f * volume);
        loop.setVolume(0.5f * volume);
        introFaster.setVolume(0.5f * volume);
        loopFaster.setVolume(0.5f * volume);
    }
}

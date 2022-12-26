package com.smj.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.Ogg;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALLwjgl3Audio;
import com.badlogic.gdx.files.FileHandle;
import org.lwjgl.openal.AL10;

import java.util.ArrayList;

public class SMJMusic {
    private static ArrayList<SMJMusic> allMusic = new ArrayList<>();
    private static SMJMusic currentMusic;
    private static boolean paused;
    private static boolean introPaused;
    private static boolean loopPaused;
    private Ogg.Music intro;
    private Ogg.Music loop;
    public static float volume = 1f;
    public SMJMusic(FileHandle file) {
        allMusic.add(this);
        ByteArray array = new ByteArray(file.readBytes());
        byte[] introData = array.readArray(array.readInt());
        byte[] loopData = array.readArray(array.readInt());
        intro = new Ogg.Music((OpenALLwjgl3Audio)Gdx.audio, new BinaryFileHandle(introData));
        loop = new Ogg.Music((OpenALLwjgl3Audio)Gdx.audio, new BinaryFileHandle(loopData));
        intro.setOnCompletionListener((music) -> {
            loop.play();
        });
        loop.setLooping(true);
    }
    public void play(float speed) {
        if (currentMusic != null) currentMusic.stop();
        currentMusic = this;
        intro.reset();
        loop.reset();
        intro.play();
        intro.setVolume(0.5f * volume);
        loop.setVolume(0.5f * volume);
    }
    public void play() {
        play(1f);
    }
    public void stop() {
        if (intro.isPlaying()) intro.stop();
        if (loop.isPlaying()) loop.stop();
    }
    public static void stopMusic() {
        if (currentMusic == null) return;
        currentMusic.stop();
        currentMusic = null;
    }
    public static void pause() {
        if (paused) return;
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
        paused = true;
    }
    public static void resume() {
        if (!paused) return;
        if (introPaused) currentMusic.intro.play();
        if (loopPaused) currentMusic.loop.play();
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
    }
}

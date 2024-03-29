package com.smj.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class FileLoader {
    private final String path;
    private FileLoader(String path) {
        this.path = path;
    }
    public static FileLoader read(String path) {
        return new FileLoader(path);
    }
    public byte[] asBytes() {
        FileHandle handle = Gdx.files.internal("assets/" + path);
        if (!handle.exists()) throw new RuntimeException("File not found: " + path);
        return handle.readBytes();
    }
    public String asString() {
        return new String(asBytes());
    }
    public Texture asTexture() {
        return new Texture(new BinaryFileHandle(asBytes(), path));
    }
    public SMJMusic asMusic() {
        return new SMJMusic(asBytes(), path);
    }
    public AudioPlayer asSound() {
        return new AudioPlayer(new BinaryFileHandle(asBytes(), path));
    }
    public boolean[] asBits() {
        byte[] bytes = asBytes();
        boolean[] bits = new boolean[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = ((bytes[i] & (1 << (7 - j))) >> (7 - j) & 1) == 1;
            }
        }
        return bits;
    }
}

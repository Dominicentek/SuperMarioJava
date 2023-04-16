package com.smj.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class FileLoader {
    private static final HashMap<String, byte[]> data = new HashMap<>();
    private final String path;
    private FileLoader(String path) {
        this.path = path;
    }
    public static void load() {
        byte[] compressed = Gdx.files.internal("assets.bin").readBytes();
        ByteArray array = new ByteArray(GZIP.uncompress(compressed));
        int fileCount = array.readInt();
        for (int i = 0; i < fileCount; i++) {
            String filename = array.readString(array.readUnsignedShort());
            int bytes = array.readInt();
            byte[] filedata = array.readArray(bytes);
            data.put(filename, filedata);
        }
    }
    public static FileLoader read(String path) {
        return new FileLoader(path);
    }
    public byte[] asBytes() {
        byte[] data = FileLoader.data.get(path);
        if (data == null) throw new RuntimeException("File not found: " + path);
        return data;
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
}

package com.smj.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.security.MessageDigest;
import java.util.HashMap;

public class TextureLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    public static Texture getPlainTexture(int width, int height, int color) {
        String encoded = "w=" + width + ";h=" + height + ";c=" + color ;
        Texture texture = textures.get(encoded);
        if (texture == null) {
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            pixmap.setColor(color);
            pixmap.fillRectangle(0, 0, width, height);
            texture = new Texture(pixmap);
            textures.put(encoded, texture);
        }
        return texture;
    }
    public static Texture get(String path) {
        try {
            Texture texture = textures.get(path);
            if (texture == null) {
                texture = FileLoader.read(path).asTexture();
                textures.put(path, texture);
            }
            return texture;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return FileLoader.read(path).asTexture();
    }
}

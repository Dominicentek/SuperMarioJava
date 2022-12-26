package com.smj.util.bjson;

import com.badlogic.gdx.Gdx;
import com.smj.util.ByteArray;
import com.smj.util.GZIP;

import java.io.File;

public class BJSONFile {
    public static void write(File file, ObjectElement element) {
        try {
            Gdx.files.absolute(file.getAbsolutePath()).writeBytes(GZIP.compress(BJSONInputOutput.write(element)), false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ObjectElement read(File file) {
        return read(Gdx.files.absolute(file.getAbsolutePath()).readBytes());
    }
    public static ObjectElement read(byte[] data) {
        try {
            return BJSONInputOutput.readObject(new ByteArray(GZIP.uncompress(data)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

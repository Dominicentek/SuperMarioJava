package com.smj.util;

import com.badlogic.gdx.files.FileHandle;
import com.smj.util.bjson.BJSONFile;
import com.smj.util.bjson.BJSONInputOutput;
import com.smj.util.bjson.ObjectElement;

public interface Readable {
    static <T extends Readable> T read(byte[] data, Class<T> type) {
        try {
            return read(BJSONFile.read(data), type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static <T extends Readable> T read(ObjectElement object, Class<T> type) {
        try {
            return type.getConstructor(ObjectElement.class).newInstance(object);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

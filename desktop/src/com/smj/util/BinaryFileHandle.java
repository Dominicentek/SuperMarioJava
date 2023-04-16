package com.smj.util;

import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class BinaryFileHandle extends FileHandle {
    public byte[] data;
    public BinaryFileHandle(byte[] data, String filename) {
        this.data = data;
        file = new File(filename);
    }
    public byte[] readBytes() {
        return data;
    }
    public InputStream read() {
        return new ByteArrayInputStream(data);
    }
}

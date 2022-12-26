package com.smj.util;

import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BinaryFileHandle extends FileHandle {
    public byte[] data;
    public BinaryFileHandle(byte[] data) {
        this.data = data;
    }
    public byte[] readBytes() {
        return data;
    }
    public InputStream read() {
        return new ByteArrayInputStream(data);
    }
}

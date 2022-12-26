package com.smj.util;

import com.badlogic.gdx.files.FileHandle;
import com.smj.util.bjson.BJSONFile;
import com.smj.util.bjson.BJSONInputOutput;
import com.smj.util.bjson.ObjectElement;

public interface Saveable extends Readable {
    ObjectElement save();
    static void save(Saveable saveable, FileHandle file) {
        BJSONFile.write(file.file(), saveable.save());
    }
}

package com.smj.util;

import com.badlogic.gdx.files.FileHandle;
import com.smj.util.bjson.BJSONFile;
import com.smj.util.bjson.BJSONInputOutput;
import com.smj.util.bjson.ObjectElement;

import java.util.Calendar;
import java.util.Date;

public interface Saveable extends Readable {
    ObjectElement save();
    static void save(Saveable saveable, FileHandle file) {
        BJSONFile.write(file.file(), saveable.save());
    }
    static String getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR) + "-" +
            String.format("%1$2s", "" + (calendar.get(Calendar.MONTH) + 1)).replaceAll(" ", "0") + "-" +
            String.format("%1$2s", "" + calendar.get(Calendar.DAY_OF_MONTH)).replaceAll(" ", "0") + "_" +
            String.format("%1$2s", "" + calendar.get(Calendar.HOUR_OF_DAY)).replaceAll(" ", "0") + "." +
            String.format("%1$2s", "" + calendar.get(Calendar.MINUTE)).replaceAll(" ", "0") + "." +
            String.format("%1$2s", "" + calendar.get(Calendar.SECOND)).replaceAll(" ", "0");
    }
    static String ensureNotExists(FileHandle handle) {
        int n = 0;
        String name = handle.nameWithoutExtension();
        String extension = (handle.extension().isEmpty() ? "" : "." + handle.extension());
        String currentName = handle.name();
        while (handle.sibling(currentName).exists()) {
            n++;
            currentName = name + "-" + n + extension;
        }
        return handle.sibling(currentName).path();
    }
}

package com.smj;

import com.smj.util.bjson.JSONParser;
import com.smj.util.bjson.ListElement;
import com.smj.util.bjson.ObjectElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SkycastleBGTilesGen {
    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("assets/assets/skycastle_bgtiles.json");
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        ObjectElement level = JSONParser.parse(new String(data));
        int width = (int)level.getDouble("width");
        int height = (int)level.getDouble("height");
        boolean[] bits = new boolean[width * height];
        ListElement list = level.getList("tileData");
        for (int x = 0; x < list.size(); x++) {
            ListElement row = list.getList(x);
            for (int y = 0; y < row.size(); y++) {
                bits[y * width + x] = row.getDouble(y) != 0;
            }
        }
        byte[] bytes = new byte[bits.length / 8];
        for (int i = 0; i < bytes.length; i++) {
            byte value = 0;
            for (int j = 0; j < 8; j++) {
                if (!bits[i * 8 + j]) continue;
                value |= 1 << (7 - j);
            }
            bytes[i] = value;
        }
        OutputStream out = new FileOutputStream("assets/assets/skycastle_bgtiles.dat");
        out.write(bytes);
        out.close();
    }
}

package com.smj;

import com.smj.util.GZIP;

import java.io.*;

public class GZIPCompressor {
    public static void main(String[] args) throws Exception {
        File[] files = new File("assets/assets/levels").listFiles();
        for (File file : files) {
            if (!file.getName().endsWith(".lvl")) continue;
            InputStream in = new FileInputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            try {
                GZIP.safeUncompress(data);
                System.out.println("Skipping " + file.getName() + " because it's already compressed");
            }
            catch (IOException e) {
                System.out.println("Compressing " + file.getName() + "...");
                OutputStream out = new FileOutputStream(file);
                out.write(GZIP.compress(data));
                out.close();
            }
        }
    }
}

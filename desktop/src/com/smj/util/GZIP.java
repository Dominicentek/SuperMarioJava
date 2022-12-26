package com.smj.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP {
    public static byte[] compress(byte[] data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream in = new GZIPOutputStream(baos);
            in.write(data);
            in.close();
            baos.close();
            return baos.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] uncompress(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            GZIPInputStream in = new GZIPInputStream(bais);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead);
            }
            in.close();
            baos.close();
            return baos.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] safeUncompress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        GZIPInputStream in = new GZIPInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = in.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        in.close();
        baos.close();
        return baos.toByteArray();
    }
}

package com.smj;

import com.smj.util.GZIP;

import java.io.*;
import java.util.Scanner;

public class GZIPUncompressor {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Which levels to uncompress? ");
        String[] levels = scanner.nextLine().split(" ");
        for (String level : levels) {
            File file = new File("assets/levels/level" + level + ".lvl");
            if (!file.exists()) {
                System.out.println("File " + file.getName() + " doesn't exist");
                continue;
            }
            System.out.print("Uncompressing " + file.getName() + "...");
            InputStream in = new FileInputStream(file);
            byte[] array = new byte[in.available()];
            in.read(array);
            in.close();
            byte[] uncompressed;
            try {
                uncompressed = GZIP.safeUncompress(array);
            }
            catch (Exception e) {
                System.out.println("\rFile " + file.getName() + " is already uncompressed");
                continue;
            }
            System.out.println();
            OutputStream out = new FileOutputStream(file);
            out.write(uncompressed);
            out.close();
        }
    }
}

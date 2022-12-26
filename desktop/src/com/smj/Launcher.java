package com.smj;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.smj.game.tile.Tiles;
import com.smj.util.GZIP;
import com.smj.util.bjson.BJSONFile;
import com.smj.util.bjson.BJSONInputOutput;
import com.smj.util.bjson.ListElement;
import com.smj.util.bjson.ObjectElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class Launcher {
    public static void main(String[] args) throws Exception {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setWindowedMode(Main.windowWidth, Main.windowHeight);
        config.setTitle("Super Mario Java");
        new Lwjgl3Application(new Main(), config);
    }
}

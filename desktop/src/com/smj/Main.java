package com.smj;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.smj.game.cutscene.Cutscene;
import com.smj.game.cutscene.Dialog;
import com.smj.game.options.Controls;
import com.smj.gui.menu.Menu;
import com.smj.util.command.Command;
import com.smj.game.Game;
import com.smj.gui.hud.HUDLayout;
import com.smj.game.options.Options;
import com.smj.util.mask.Mask;
import com.smj.util.*;
import com.smj.gui.font.Font;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Main extends ApplicationAdapter {
    public static Renderer renderer;
    public static Renderer viewer;
    public static FrameBuffer buffer;
    public static FrameBuffer maskBuffer;
    public static OrthographicCamera mainCamera;
    public static OrthographicCamera viewCamera;
    public static int windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width * 3 / 4;
    public static int windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height * 3 / 4;
    public static final int WIDTH = 384;
    public static final int HEIGHT = 256;
    public static boolean fullscreen = false;
    public static int prevWidth = 0;
    public static int prevHeight = 0;
    public static Options options;
    public static Transition transition = new Transition(0.5, () -> {});
    public static Queue<Runnable> actionQueue = new Queue<>();
    public static Mask mask;
    public static Menu menu;
    public static Cutscene currentCutscene = null;
    public void create() {
        renderer = new Renderer(HEIGHT);
        viewer = new Renderer(windowHeight);
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, WIDTH, HEIGHT, false);
        mask = new Mask();
        mainCamera = new OrthographicCamera(WIDTH, HEIGHT);
        mainCamera.position.set(WIDTH / 2f, HEIGHT / 2f, 0f);
        viewCamera = new OrthographicCamera(windowWidth, windowHeight);
        viewCamera.position.set(windowWidth / 2f, windowHeight / 2f, 0f);
        options = Options.load();
        SMJMusic.setVolumeAll(Main.options.musicVolume);
        Dialog.parse();
        Command.loadCommands();
        GameText.parse();
        Font.loadFromBinaryData(Gdx.files.internal("assets/font.fnt").readBytes());
        Game.loadThemes();
        Game.loadSavefile();
        transition.stage = 1;
        transition.start();
        HUDLayout.LIFE_COUNTER.attachment.set(Game.savefile.lives);
        Command.console = Game.console;
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyTyped(char character) {
                Game.keyTyped(character);
                return true;
            }
            public boolean keyDown(int keycode) {
                Game.keyPressed(keycode);
                return true;
            }
        });
    }
    public void render() {
        update();
        ScreenUtils.clear(0, 0, 0, 1);
        maskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Main.WIDTH, Main.HEIGHT, false);
        maskBuffer.begin();
        mask.setProjectionMatrix(mainCamera.combined);
        mask.setTransformMatrix(new Matrix4().translate(-WIDTH / 2f, -HEIGHT / 2f, 0));
        mask.render();
        maskBuffer.end();
        buffer.begin();
        renderer.begin();
        renderer.setProjectionMatrix(mainCamera.combined);
        renderer.setTransformMatrix(new Matrix4().translate(-WIDTH / 2f, -HEIGHT / 2f, 0));
        renderer.rect(0, 0, WIDTH, HEIGHT, 0x000000FF);
        if (currentCutscene == null) {
            Game.render(renderer);
            renderer.resetTranslation();
            if (menu != null) menu.render(renderer);
        }
        else currentCutscene.render(renderer);
        renderer.rect(0, 0, WIDTH, HEIGHT, transition.alpha);
        if (Controls.SCREENSHOT.isJustPressed()) Screenshot.take();
        renderer.end();
        buffer.end();
        viewer.begin();
        viewer.setProjectionMatrix(viewCamera.combined);
        Texture texture = buffer.getColorBufferTexture();
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        Rectangle bounds = fit(new Dimension(windowWidth, windowHeight), new Dimension(WIDTH, HEIGHT));
        viewer.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height, false, true);
        viewer.end();
        maskBuffer.dispose(); // i forgor :skull: to add this line of code so my pc kept
                              // crashing every once in a while and i reinstalled the whole
                              // fucking operating system because of that, im a fucking dumbass...

                              // this comment exists so i get reminded of my stupidity every
                              // time i look at this shit, im so pissed off rn
    }
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            fullscreen = !fullscreen;
            if (fullscreen) {
                prevWidth = windowWidth;
                prevHeight = windowHeight;
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            else Gdx.graphics.setWindowedMode(prevWidth, prevHeight);
        }
        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        viewCamera.viewportWidth = windowWidth;
        viewCamera.viewportHeight = windowHeight;
        viewCamera.position.set(windowWidth / 2f, windowHeight / 2f, 0f);
        viewCamera.update();
        viewer.height = windowHeight;
        if (currentCutscene == null) Game.update();
        else currentCutscene.update();
        Gdx.graphics.setTitle("Super Mario Java - FPS: " + Gdx.graphics.getFramesPerSecond());
        transition.update();
        while (!actionQueue.empty()) {
            actionQueue.pull().run();
        }
    }
    public static Rectangle fit(Dimension container, Dimension size) {
        double aspectRatioX = container.width / (double)size.width;
        double aspectRatioY = container.height / (double)size.height;
        double aspectRatio = Math.min(aspectRatioX, aspectRatioY);
        Rectangle rectangle = new Rectangle();
        rectangle.width = (int)(size.width * aspectRatio);
        rectangle.height = (int)(size.height * aspectRatio);
        rectangle.x = container.width / 2 - rectangle.width / 2;
        rectangle.y = container.height / 2 - rectangle.height / 2;
        return rectangle;
    }
    public static void setTransition(Transition transition) {
        transition.start();
        Main.transition = transition;
    }
    public static void renderMask() {
        renderer.draw(maskBuffer.getColorBufferTexture(), 0, 0);
    }
    public void dispose() {
        System.exit(0);
    }
    public static void startCutscene(String id) {
        setTransition(new Transition(0.5, () -> {
            currentCutscene = Cutscene.cutscenes.get(id).build();
        }));
    }
}

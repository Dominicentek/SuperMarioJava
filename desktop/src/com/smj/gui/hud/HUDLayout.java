package com.smj.gui.hud;

import com.smj.Main;
import com.smj.gui.menu.Menu;
import com.smj.gui.menu.Menus;
import com.smj.util.Renderer;
import com.smj.util.bjson.ObjectElement;

import java.awt.Point;
import java.util.HashMap;

public class HUDLayout {
    public static final int WIDTH = Main.WIDTH / 4;
    public static final int HEIGHT = Main.HEIGHT / 4;
    public static final HashMap<String, HUDElement> elements = new HashMap<>();
    public static final HUDIconElement<HUDAnimatedCounterElement> COIN_COUNTER = create(2, 2, "coin_counter", new HUDIconElement<>(HUDIconElement.COIN, (HUDAnimatedCounterElement)new HUDAnimatedCounterElement().color(0xFFFF00).digits(2)));
    public static final HUDIconElement<HUDAnimatedCounterElement> LIFE_COUNTER = create(11, 2, "life_counter", new HUDIconElement<>(HUDIconElement.MUSHROOM, (HUDAnimatedCounterElement)new HUDAnimatedCounterElement().color(0x00FF00).digits(2).limit()));
    public static final HUDIconElement<HUDCounterElement> STAR_TIMER = create(2, 5, "star_timer", new HUDIconElement<>(HUDIconElement.STAR, new HUDCounterElement().digits(1).step(Integer.MAX_VALUE)));
    public static final HUDIconElement<HUDKeyCounterElement> KEY_COIN_COUNTER = create(2, 8, "key_coin_counter", new HUDIconElement<>(HUDIconElement.KEY_COIN, (HUDKeyCounterElement)new HUDKeyCounterElement().color(0xFF7F7F).digits(2).limit()));
    public static final HUDIconElement<HUDTimerElement> TIMER = create(85, 2, "timer", new HUDIconElement<>(HUDIconElement.CLOCK, (HUDTimerElement)new HUDTimerElement().digits(3).step(0)));
    public static final HUDCounterElement SCORE = create(WIDTH - 29, 2, "score", new HUDCounterElement().digits(8).step(20).limit());
    public static final HUDTextElement DEATH_TEXT = create(0, HEIGHT / 2 - 1, "death_text", new HUDTextElement());
    public static final HUDTextElement WORLD_TEXT = create(76, 5, "world_text", new HUDTextElement());
    public static final HUDSpeedrunElement SPEEDRUN_TIMER = create(74, 8, "speedrun_timer", new HUDSpeedrunElement());
    public static void store(ObjectElement element) {
        for (String name : elements.keySet()) {
            ObjectElement pos = new ObjectElement();
            pos.setByte("x", (byte)elements.get(name).position.x);
            pos.setByte("y", (byte)elements.get(name).position.y);
            element.setObject(name, pos);
        }
    }
    public static void read(ObjectElement element) {
        for (String name : element.keys()) {
            HUDElement hud = elements.get(name);
            if (hud == null) continue;
            ObjectElement pos = element.getObject(name);
            hud.position = new Point(Byte.toUnsignedInt(pos.getByte("x")), Byte.toUnsignedInt(pos.getByte("y")));
        }
    }
    public static <T extends HUDElement> T create(int x, int y, String name, T instance) {
        instance.position = new Point(x, y);
        elements.put(name, instance);
        return instance;
    }
    public static void renderAll(Renderer renderer) {
        for (String name : elements.keySet()) {
            HUDElement element = elements.get(name);
            if (!element.visible && Menu.currentMenu != Menus.HUD_LAYOUT) continue;
            renderer.translate(element.position.x * 4, element.position.y * 4);
            element.render(renderer);
            renderer.translate(-element.position.x * 4, -element.position.y * 4);
        }
    }
    public static void updateAll() {
        for (String name : elements.keySet()) {
            HUDElement element = elements.get(name);
            element.update();
        }
    }
}

package com.smj.game.challenge.events;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.challenge.ChallengeEvent;
import com.smj.game.challenge.Medals;
import com.smj.gui.hud.HUDElement;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

import java.awt.*;

public class NoClockChallengeEvent implements ChallengeEvent {
    public int time;
    public int clocks;
    public int update(Medals medals) {
        if (time < Game.time) clocks++;
        time = Game.time;
        if (medals.medal(clocks) == 0) Game.die();
        return clocks;
    }
    public void init(Medals medals) {
        time = Game.time;
        clocks = 0;
    }
    public void render(Renderer renderer, Medals medals) {
        int width = 24 + 16 * medals.bronze;
        int medal = medals.medal(clocks);
        renderer.setColor(medal == 0 ? 0x3F3F3FFF : medal == 1 ? 0x7F7F00FF : medal == 2 ? 0xDFDFDFFF : 0xFFCF00FF);
        renderer.rect(Main.WIDTH / 2 - width / 2, 4, width, 24);
        renderer.setColor(0xFFFFFFFF);
        int displayClocks = medals.bronze - clocks;
        for (int i = 0; i <= displayClocks; i++) {
            renderer.draw(TextureLoader.get("images/themes/0/tileset.png"), new Rectangle(192, 80, 16, 16), Main.WIDTH / 2 - width / 2 + 4 + i * 16, 8);
        }
    }
    public boolean show(HUDElement element) {
        return false;
    }
    public String getString(Medals medals, Integer value, boolean highScore) {
       if (value == null) return "- clocks";
       else if (highScore) return value + " clocks";
       return "max " + value + " clocks";
    }
}

package com.smj.game.challenge.events;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.challenge.ChallengeEvent;
import com.smj.game.challenge.Medals;
import com.smj.gui.font.Font;
import com.smj.gui.hud.HUDElement;
import com.smj.gui.hud.HUDLayout;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

public class TimedCoinsChallengeEvent implements ChallengeEvent {
    public int coins = 0;
    public void init(Medals medals) {
        coins = 0;
    }
    public int update(Medals medals) {
        while (coins % 100 != Game.savefile.coins % 100) {
            coins++;
        }
        if (Game.time == 1 && Game.timeTimeout == 1) {
            if (medals.medal(coins) == 0) Game.die();
            else Game.finishLevel();
        }
        return coins;
    }
    public void render(Renderer renderer, Medals medals) {
        int width = 8 + 16 + 4 + Font.stringWidth(coins + "");
        int medal = medals.medal(coins);
        renderer.setColor(medal == 0 ? 0x3F3F3FFF : medal == 1 ? 0x7F7F00FF : medal == 2 ? 0xDFDFDFFF : 0xFFCF00FF);
        renderer.rect(Main.WIDTH / 2 - width / 2, 4, width, 24);
        renderer.setColor(0xFFFFFFFF);
        renderer.draw(TextureLoader.get("images/entities/coin.png"), Main.WIDTH / 2 - width / 2 + 4, 8);
        Font.render(renderer, Main.WIDTH / 2 - width / 2 + 4 + 16 + 4, 12, coins + "");
    }
    public boolean show(HUDElement element) {
        return element == HUDLayout.COIN_COUNTER;
    }
    public String getString(Medals medals, Integer value, boolean highScore) {
        if (value == null) return "- coins";
        return value + " coins";
    }
}

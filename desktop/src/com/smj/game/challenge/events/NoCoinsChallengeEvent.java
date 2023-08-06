package com.smj.game.challenge.events;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.challenge.ChallengeEvent;
import com.smj.game.challenge.Medals;
import com.smj.gui.hud.HUDElement;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

public class NoCoinsChallengeEvent implements ChallengeEvent {
    public int update(Medals medals) {
        if (medals.medal(Game.savefile.coins) == 0) Game.die();
        return Game.savefile.coins;
    }
    public void init(Medals medals) {}
    public void render(Renderer renderer, Medals medals) {
        int width = 8 + 16 * medals.bronze;
        int medal = medals.medal(Game.savefile.coins);
        renderer.setColor(medal == 0 ? 0x3F3F3FFF : medal == 1 ? 0x7F7F00FF : medal == 2 ? 0xDFDFDFFF : 0xFFCF00FF);
        renderer.rect(Main.WIDTH / 2 - width / 2, 4, width, 24);
        renderer.setColor(0xFFFFFFFF);
        int displayCoins = medals.bronze - Game.savefile.coins;
        for (int i = 0; i < displayCoins; i++) {
            renderer.draw(TextureLoader.get("images/entities/coin.png"), Main.WIDTH / 2 - width / 2 + 4 + i * 16, 8);
        }
    }
    public boolean show(HUDElement element) {
        return false;
    }
}

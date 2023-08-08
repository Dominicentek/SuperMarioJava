package com.smj.game.challenge.events;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.challenge.ChallengeEvent;
import com.smj.game.challenge.Medals;
import com.smj.gui.font.Font;
import com.smj.gui.hud.HUDElement;
import com.smj.util.Renderer;
import com.smj.util.TextureLoader;

import java.awt.*;

public class JumpChallengeEvent implements ChallengeEvent {
    public int jumps = 0;
    public boolean previouslyJumping = false;
    public void init(Medals medals) {
        jumps = 0;
    }
    public int update(Medals medals) {
        boolean jumping = Game.player.getPhysics().jumpingUpward;
        if (jumping && !previouslyJumping) jumps++;
        previouslyJumping = jumping;
        return jumps;
    }
    public void render(Renderer renderer, Medals medals) {
        int width = 8 + 16 + 4 + Font.stringWidth(jumps + "");
        int medal = medals.medal(jumps);
        renderer.setColor(medal == 0 ? 0x3F3F3FFF : medal == 1 ? 0x7F7F00FF : medal == 2 ? 0xDFDFDFFF : 0xFFCF00FF);
        renderer.rect(Main.WIDTH / 2 - width / 2, 4, width, 24);
        renderer.setColor(0xFFFFFFFF);
        renderer.draw(TextureLoader.get("images/entities/mario.png"), new Rectangle(80, 0, 16, 16), Main.WIDTH / 2 - width / 2 + 4, 8);
        Font.render(renderer, Main.WIDTH / 2 - width / 2 + 4 + 16 + 4, 12, jumps + "");
    }
    public boolean show(HUDElement element) {
        return false;
    }
    public String getString(Medals medals, Integer value, boolean highScore) {
        if (value == null) return "- jumps";
        if (highScore) return value + " jumps";
        return "max " + value + " jumps";
    }
}

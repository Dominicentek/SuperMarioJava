package com.smj.game.challenge.events;

import com.smj.Main;
import com.smj.game.challenge.ChallengeEvent;
import com.smj.game.challenge.Medals;
import com.smj.gui.font.Font;
import com.smj.gui.hud.HUDElement;
import com.smj.util.Renderer;

public class TimerChallengeEvent implements ChallengeEvent {
    public int time;
    public int timeout;
    public int update(Medals medals) {
        timeout--;
        if (timeout == 0) {
            timeout = 60;
            time++;
        }
        return time;
    }
    public void init(Medals medals) {
        time = 0;
        timeout = 60;
    }
    public void render(Renderer renderer, Medals medals) {
        String clock = ((time / 60) % 10) + ":" + String.format("%1$2s", time % 60).replaceAll(" ", "0") + "." + String.format("%1$2s", (timeout * 100 / 60) % 100).replaceAll(" ", "0");
        int width = Font.stringWidth(clock) + 8;
        int medal = medals.medal(time);
        renderer.setColor(medal == 0 ? 0x3F3F3FFF : medal == 1 ? 0x7F7F00FF : medal == 2 ? 0xDFDFDFFF : 0xFFCF00FF);
        renderer.rect(Main.WIDTH / 2 - width / 2, 4, width, 16);
        renderer.setColor(0xFFFFFFFF);
        Font.render(renderer, Main.WIDTH / 2 - width / 2 + 4, 8, clock);
    }
    public boolean show(HUDElement element) {
        return false;
    }
}

package com.smj.game.challenge;

import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.challenge.events.CoinsChallengeEvent;
import com.smj.gui.hud.HUDLayout;
import com.smj.gui.menu.Menu;
import com.smj.gui.menu.MenuButtonItem;
import com.smj.gui.menu.MenuItem;
import com.smj.gui.menu.Menus;
import com.smj.util.Saveable;
import com.smj.util.StringUtils;
import com.smj.util.Transition;
import com.smj.util.bjson.BJSONFile;
import com.smj.util.bjson.ObjectElement;

import java.io.File;
import java.util.LinkedHashMap;

public class Challenges {
    public static final File file = new File("challenges.dat");
    public static final LinkedHashMap<String, Challenge> challenges = new LinkedHashMap<>();
    public static final Challenge TEST = challenges.put("test", new Challenge()
        .medals(new Medals(1500, 2100, 3000).comparator(Medals.LESS_THAN))
        .difficulty(1)
        .level(0)
        .timer(50)
        .event(new CoinsChallengeEvent(50))
        .type(ChallengeType.COIN));
    public static void load() {
        if (file.exists()) read(BJSONFile.read(file));
        else save();
    }
    public static void read(ObjectElement element) {
        for (String key : element.keys()) {
            Challenge challenge = Challenges.challenges.get(key);
            if (challenge == null) continue;
            if (element.isNull(key)) challenge.highScore = null;
            else challenge.highScore = element.getInt(key);
        }
    }
    public static void save() {
        ObjectElement element = new ObjectElement();
        for (String key : Challenges.challenges.keySet()) {
            Integer highScore = Challenges.challenges.get(key).highScore;
            element.setInt(key, highScore);
        }
        BJSONFile.write(file, element);
    }
    public static MenuItem[] getMenuItems() {
        MenuItem[] items = new MenuItem[challenges.size()];
        int i = 0;
        for (String key : challenges.keySet()) {
            Challenge challenge = challenges.get(key);
            items[i] = new MenuChallengeItem((menu, index, item) -> {
                Game.currentChallenge = challenge;
                Game.currentChallengeName = item.label.substring(12);
                Game.currentChallengeIndex = index - 1;
                Menus.CHALLENGE_CONFIRM.selector = 0;
                Menus.CHALLENGE_CONFIRM.selectedIndex = 0;
                Menu.switchMenu(Menus.CHALLENGE_CONFIRM);
            }, challenge);
            i++;
        }
        return items;
    }
    public static class MenuChallengeItem extends MenuButtonItem {
        public boolean init = false;
        public Challenge challenge;
        public MenuChallengeItem(MenuButtonItemAction action, Challenge challenge) {
            super(action);
            this.challenge = challenge;
        }
        public void update(Menu menu) {
            if (!init) {
                init = true;
                label = "$cFFFF00" + String.format("%1$3s", StringUtils.repeat("*", challenge.difficulty)) + " $c" + String.format("%1$6s", Integer.toHexString(challenge.type.color)).replaceAll(" ", "0") + label;
            }
            int medal = challenge.highScore == null ? 0 : challenge.medals.medal(challenge.highScore);
            right = "$c" + (medal == 0 ? "7F7F7F" : medal == 1 ? "7F7F00" : medal == 2 ? "CFCFCF" : "FFFF00") + "O";
        }
    }
}

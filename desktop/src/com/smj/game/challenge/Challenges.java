package com.smj.game.challenge;

import com.badlogic.gdx.Gdx;
import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.challenge.events.*;
import com.smj.game.tile.Tiles;
import com.smj.gui.menu.Menu;
import com.smj.gui.menu.MenuButtonItem;
import com.smj.gui.menu.MenuItem;
import com.smj.gui.menu.Menus;
import com.smj.util.StringUtils;
import com.smj.util.bjson.BJSONFile;
import com.smj.util.bjson.ObjectElement;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class Challenges {
    public static final String congratulations = "Congratulations!!\n" +
        "\n" +
        "You got gold in all SMJ challenges!\n" +
        "Honestly, I didn't expect someone actually downloading the game, let alone 100%-ing it.\n" +
        "This means a lot to me. I spent a whole year working as a solo developer on this game.\n" +
        "Now yes, I technically could finish it in like 5 months if I procrastinated less but here we are.\n" +
        "I'm so happy that you enjoyed my first attempt at a platformer. I had a lot of fun making it!\n" +
        "Once again, thank you for increasing the download count by 1. It really makes me happy. :D\n" +
        "\n" +
        "  - Dominicentek\n" +
        "\n" +
        "btw if your looking at this in the source code fuck you this is meant as a reward for 100%-ing the game\n" +
        "(im obviously kidding xdd)";
    public static final File file = new File("challenges.dat");
    public static final LinkedHashMap<String, Challenge> challenges = new LinkedHashMap<>();
    public static final Challenge GRASS_SPEEDRUN = add("grass_speedrun", new Challenge().medals(new Medals(11 * 60, 20 * 60, 40 * 60).comparator(Medals.LESS_THAN)).difficulty(1).level(0).timer(40).event(new TimerChallengeEvent()).type(ChallengeType.TIME));
    public static final Challenge SANDSTORM_SPEEDRUN = add("sandstorm_speedrun", new Challenge().medals(new Medals(37 * 60, 45 * 60, 60 * 60).comparator(Medals.LESS_THAN)).difficulty(1).level(12).timer(60).event(new TimerChallengeEvent()).type(ChallengeType.TIME));
    public static final Challenge DARK_CAVE_SPEEDRUN = add("dark_cave_speedrun", new Challenge().medals(new Medals(48 * 60, 55 * 60, 70 * 60).comparator(Medals.LESS_THAN)).difficulty(2).level(6).timer(70).event(new TimerChallengeEvent()).type(ChallengeType.TIME));
    public static final Challenge DEDICATED_LEVEL_SPEEDRUN = add("dedicated_level_speedrun", new Challenge().medals(new Medals(11 * 60, 20 * 60, 40 * 60).comparator(Medals.LESS_THAN)).difficulty(2).level(100).timer(40).event(new TimerChallengeEvent()).type(ChallengeType.TIME));
    public static final Challenge SUPERSTAR_SPEEDRUN = add("superstar_speedrun", new Challenge().medals(new Medals(11 * 60, 20 * 60, 40 * 60).comparator(Medals.LESS_THAN)).difficulty(3).level(101).timer(40).event(new TimerChallengeEvent()).type(ChallengeType.TIME));
    public static final Challenge LOW_GRAVITY_SPEEDRUN = add("low_gravity_speedrun", new Challenge().medals(new Medals(53 * 60, 55 * 60 + 30, 58 * 60).comparator(Medals.LESS_THAN)).difficulty(3).level(32).timer(58).event(new TimerChallengeEvent()).type(ChallengeType.TIME));
    public static final Challenge COIN_50_SPEEDRUN = add("coin_50_speedrun", new Challenge().medals(new Medals(25 * 60, 35 * 60, 50 * 60).comparator(Medals.LESS_THAN)).difficulty(1).level(0).timer(50).disallowStarFinish().event(new CoinsChallengeEvent(50)).type(ChallengeType.COIN));
    public static final Challenge GRASS_COINLESS = add("grass_coinless", new Challenge().medals(new Medals(0, 2, 4).comparator(Medals.LESS_THAN)).difficulty(1).level(0).timer(120).disallowWarps().event(new NoCoinsChallengeEvent()).type(ChallengeType.COIN));
    public static final Challenge PIPES_COINLESS = add("pipes_coinless", new Challenge().medals(new Medals(0, 2, 4).comparator(Medals.LESS_THAN)).difficulty(2).level(1).timer(120).event(new NoCoinsChallengeEvent()).type(ChallengeType.COIN));
    public static final Challenge MANY_COINS = add("many_coins", new Challenge().medals(new Medals(0, 0, 0).comparator(Medals.GREATER_THAN)).difficulty(2).level(102).timer(30).event(new TimedCoinsChallengeEvent()).type(ChallengeType.COIN));
    public static final Challenge PYRAMID_COINS = add("pyramid_coins", new Challenge().medals(new Medals(55 * 60, 60 * 60, 65 * 60).comparator(Medals.LESS_THAN)).difficulty(3).level(10).timer(120).disallowStarFinish().event(new CoinsChallengeEvent(130)).type(ChallengeType.COIN));
    public static final Challenge PYRAMID_COINLESS = add("pyramid_coinless", new Challenge().medals(new Medals(0, 2, 4).comparator(Medals.LESS_THAN)).difficulty(3).level(10).timer(120).event(new NoCoinsChallengeEvent()).type(ChallengeType.COIN));
    public static final Challenge PYRAMID_JUMPS = add("pyramid_jumps", new Challenge().medals(new Medals(15, 16, 17).comparator(Medals.LESS_THAN)).difficulty(1).level(103).timer(120).event(new JumpChallengeEvent()).type(ChallengeType.JUMP));
    public static final Challenge SNOW_JUMPS = add("snow_jumps", new Challenge().medals(new Medals(15, 16, 17).comparator(Medals.LESS_THAN)).difficulty(2).level(104).timer(120).event(new JumpChallengeEvent()).type(ChallengeType.JUMP));
    public static final Challenge GRASS_JUMPS = add("grass_jumps", new Challenge().medals(new Medals(15, 16, 17).comparator(Medals.LESS_THAN)).difficulty(3).level(1).timer(120).event(new JumpChallengeEvent()).type(ChallengeType.JUMP));
    public static final Challenge CAVE_PACIFIST = add("cave_pacifist", new Challenge().difficulty(1).level(6).timer(120).event(new PacifistChallengeEvent()).type(ChallengeType.MISC));
    public static final Challenge GRASS_LIVES = add("grass_lives", new Challenge().medals(new Medals(4, 3, 2).comparator(Medals.GREATER_THAN)).difficulty(1).level(0).timer(120).event(new LifeChallengeEvent()).type(ChallengeType.MISC));
    public static final Challenge WATER_TIME = add("water_time", new Challenge().medals(new Medals(1, 2, 3).comparator(Medals.LESS_THAN)).difficulty(1).level(22).timer(30).event(new NoClockChallengeEvent()).type(ChallengeType.MISC));
    public static final Challenge GRASS_SCORE = add("grass_score", new Challenge().difficulty(2).level(1).timer(120).event(new NoScoreChallengeEvent()).type(ChallengeType.MISC));
    public static final Challenge CAVE_DARK = add("cave_dark", new Challenge().difficulty(2).level(6).timer(120).event(new ExtraDarkChallengeEvent()).type(ChallengeType.MISC));
    public static final Challenge GOOMBA_STOMP = add("goomba_stomp", new Challenge().medals(new Medals(20, 25, 30)).difficulty(2).level(105).timer(120).event(new EnemyKillChallengeEvent(10)).type(ChallengeType.MISC));
    public static final Challenge WATER_COINS = add("water_coins", new Challenge().difficulty(3).level(21).timer(120).event(new TimelessCoinsChallengeEvent(150)).type(ChallengeType.MISC));
    public static final Challenge METEORITE_ALT = add("meteorite_alt", new Challenge().difficulty(3).level(37).timer(120).replace(172, 13, Tiles.AIR).event(new DummyChallengeEvent()).type(ChallengeType.MISC));
    public static final Challenge CONSOLE_CHEAT = add("console_cheat", new Challenge().medals(new Medals(1 * 60, 2 * 60, 3 * 60).comparator(Medals.LESS_THAN)).difficulty(3).level(0).timer(3).event(new TimerChallengeEvent()).type(ChallengeType.MISC));
    public static Challenge add(String id, Challenge challenge) {
        challenges.put(id, challenge);
        return challenge;
    }
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
            if (highScore == null) element.setNull(key);
            else element.setInt(key, highScore);
        }
        BJSONFile.write(file, element);
    }
    public static MenuItem[] getMenuItems() {
        MenuItem[] items = new MenuItem[challenges.size() - 1];
        int i = 0;
        for (String key : challenges.keySet()) {
            Challenge challenge = challenges.get(key);
            if (challenge == CONSOLE_CHEAT) continue;
            items[i] = constructMenuItem(challenge);
            i++;
        }
        if (countGold() >= challenges.size() - 1) Main.actionQueue.push(Challenges::appendFinalChallenge);
        return items;
    }
    public static MenuItem constructMenuItem(Challenge challenge) {
        return new MenuChallengeItem((menu, index, item) -> {
            Game.currentChallenge = challenge;
            Game.currentChallengeName = "$cFFFF00" + StringUtils.repeat("*", challenge.difficulty) + " " + item.label.substring(12);
            Game.currentChallengeIndex = index - 1;
            Menus.CHALLENGE_CONFIRM.selector = 0;
            Menus.CHALLENGE_CONFIRM.selectedIndex = 0;
            Menu.switchMenu(Menus.CHALLENGE_CONFIRM);
        }, challenge);
    }
    public static int countGold() {
        int golds = 0;
        for (String key : challenges.keySet()) {
            Challenge challenge = challenges.get(key);
            if (challenge.highScore != null && challenge.medals.medal(challenge.highScore) == 3) golds++;
        }
        return golds;
    }
    public static void appendFinalChallenge() {
        if (Menus.CHALLENGES.items.length != challenges.size()) return;
        Menus.CHALLENGES.items = Stream.concat(Arrays.stream(Menus.CHALLENGES.items), Arrays.stream(new MenuItem[]{ constructMenuItem(CONSOLE_CHEAT) })).toArray(MenuItem[]::new);
        Menus.CHALLENGES.text("challenges");
    }
    public static void finish() {
        int golds = countGold();
        if (golds == challenges.size() - 1) appendFinalChallenge();
        if (golds == challenges.size()) {
            Gdx.files.local("congratulations.txt").writeString(congratulations, false);
        }
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

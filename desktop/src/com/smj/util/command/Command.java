package com.smj.util.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.smj.Main;
import com.smj.game.cutscene.Cutscene;
import com.smj.game.tile.GameTile;
import com.smj.util.*;
import com.smj.util.Readable;
import com.smj.util.command.arguments.*;
import com.smj.game.Game;
import com.smj.game.entity.EntityType;
import com.smj.game.score.StaticScore;
import com.smj.game.tile.Tiles;
import com.smj.util.command.console.Console;
import com.smj.util.command.console.StdoutConsole;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

public class Command {
    public static CommandLiteral commands = new CommandLiteral();
    public static Console console = new StdoutConsole();
    public static ArrayList<String> commandHistory = new ArrayList<>(Collections.singletonList(""));
    public static int historyIndex = 0;
    public static void run(String command) {
        try {
            historyIndex = 0;
            command = command.trim().replaceAll("/\\s+/g", " ").toLowerCase();
            if (command.isEmpty()) return;
            if (commandHistory.size() <= 1 || !commandHistory.get(1).equals(command)) commandHistory.add(1, command);
            String[] args = command.split(" ");
            run(new CommandContext(), new ArrayList<>(Collections.singletonList(commands)), args, 0);
        }
        catch (Exception e) {
            console.err("An unknown error occured");
            e.printStackTrace();
        }
    }
    public static void run(CommandContext context, ArrayList<CommandNode> nodes, String[] args, int i) {
        for (CommandNode node : nodes) {
            if (i >= args.length) {
                if (node instanceof CommandExecution) {
                    CommandExecution execution = (CommandExecution)node;
                    execution.run(context);
                }
                else {
                    console.err("Insufficient arguments");
                }
                return;
            }
            if (node instanceof CommandLiteral) {
                CommandLiteral literal = (CommandLiteral)node;
                if (!literal.paths.containsKey(args[i])) {
                    console.err("Literal not found: " + args[i]);
                    return;
                }
                run(context, literal.paths.get(args[i]), args, i + 1);
                return;
            }
            if (node instanceof CommandArgument) {
                try {
                    CommandArgument<?> argument = (CommandArgument<?>)node;
                    AccessCounter counter = new AccessCounter(i);
                    context.values.put(argument.name, argument.parse(args, counter));
                    i = counter.access();
                }
                catch (CommandException e) {
                    console.err(e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
    public static void loadCommands() {
        commands.addPath("life", new CommandBuilder().addNode(
            new CommandLiteral().addPath("set", new CommandBuilder().addNode(
                new IntegerArgument("amount")
            ).addNode(
                (CommandExecution)context -> {
                    Game.setLives(context.get("amount"));
                    console.log("Set " + context.get("amount") + " lives to the life counter");
                }
            ).get()).addPath("add", new CommandBuilder().addNode(
                new IntegerArgument("amount")
            ).addNode(
                (CommandExecution)context -> {
                    Game.setLives(Game.savefile.lives + context.<Integer>get("amount"));
                    console.log("Added " + context.get("amount") + " lives to the life counter");
                }
            ).get())
        ).get());
        commands.addPath("coin", new CommandBuilder().addNode(
            new CommandLiteral().addPath("set", new CommandBuilder().addNode(
                new IntegerArgument("amount")
            ).addNode(
                (CommandExecution)context -> {
                    Game.setCoins(context.get("amount"));
                    console.log("Set " + context.get("amount") + " coins to the coin counter");
                }
            ).get()).addPath("add", new CommandBuilder().addNode(
                new IntegerArgument("amount")
            ).addNode(
                (CommandExecution)context -> {
                    Game.setCoins(Game.savefile.coins + context.<Integer>get("amount"));
                    console.log("Added " + context.get("amount") + " coins to the coin counter");
                }
            ).get())
        ).get());
        commands.addPath("time", new CommandBuilder().addNode(
            new CommandLiteral().addPath("set", new CommandBuilder().addNode(
                new IntegerArgument("amount")
            ).addNode(
                (CommandExecution)context -> {
                    Game.time = context.get("amount");
                    console.log("Set " + context.get("amount") + " seconds to the timer");
                }
            ).get()).addPath("add", new CommandBuilder().addNode(
                new IntegerArgument("amount")
            ).addNode(
                (CommandExecution)context -> {
                    Game.time += context.<Integer>get("amount");
                    console.log("Added " + context.get("amount") + " seconds to the timer");
                }
            ).get())
        ).get());
        commands.addPath("score", new CommandBuilder().addNode(
            new IntegerArgument("amount")
        ).addNode(
            (CommandExecution)context -> {
                Game.awardScore(new StaticScore().score(context.get("amount")));
                console.log("Awarded " + context.get("amount") + " score");
            }
        ).get());
        commands.addPath("powerup", new CommandBuilder().addNode(
            new ListArgument("state", "small", "big", "fire", "ice", "jump", "speed")
        ).addNode(
            (CommandExecution)context -> {
                if (context.<Integer>get("state") == -1 || context.<Integer>get("state") >= 6) {
                    console.err("Invalid state");
                    return;
                }
                Game.setPowerup(context.get("state"));
                console.log("Set powerup with ID " + context.get("state"));
            }
        ).get());
        commands.addPath("superstar", new CommandBuilder().addNode(
            (CommandExecution)context -> {
                Game.getSuperstar(10);
                console.log("Granted invincibility for 10 seconds");
            }
        ).addNode(
            new IntegerArgument("seconds")
        ).addNode(
            (CommandExecution)context -> {
                int seconds = context.<Integer>get("seconds");
                Game.getSuperstar(seconds);
                console.log("Granted invincibility for " + seconds + " seconds");
            }
        ).get()).aliasTo("star", "superstar");
        commands.addPath("entity", new CommandBuilder().addNode(
            new ListArgument("type", ArrayStringifier.modify(value -> {
                return value.name().toLowerCase();
            }, EntityType.values()))
        ).addNode(
            new PositionArgument("pos", PositionArgument.ENTITY)
        ).addNode(
            (CommandExecution)context -> {
                if (context.<Integer>get("type") < 0 || context.<Integer>get("type") >= EntityType.values().length) {
                    console.err("Invalid entity");
                    return;
                }
                Point position = context.get("pos");
                EntityType.values()[context.<Integer>get("type")].spawn(Game.currentLevel, position.x, position.y);
                console.log("Spawned entity " + EntityType.values()[context.<Integer>get("type")].name().toLowerCase());
            }
        ).get());
        commands.addPath("tile", new CommandBuilder().addNode(
            new ListArgument("tile", ArrayStringifier.modify(value -> {
                return value.getName().toLowerCase();
            }, Tiles.class.getFields()))
        ).addNode(
            new PositionArgument("pos", PositionArgument.TILE)
        ).addNode(
            (CommandExecution)context -> {
                try {
                    if (context.<Integer>get("tile") < 0 || context.<Integer>get("tile") >= Tiles.class.getFields().length) {
                        console.err("Invalid tile");
                        return;
                    }
                    Point position = context.get("pos");
                    Game.currentLevel.setTileAt(Tiles.class.getFields()[(int)context.get("tile")].getInt(null), position.x, position.y);
                    console.log("Set tile " + Tiles.class.getFields()[(int)context.get("tile")].getName().toLowerCase() + " to pos (" + position.x + ", " + position.y + ")");
                }
                catch (Exception e) {
                    console.err("An unknown error occured");
                    e.printStackTrace();
                }
            }
        ).get());
        commands.addPath("position", new CommandBuilder().addNode(
            new PositionArgument("pos", PositionArgument.ENTITY)
        ).addNode(
            (CommandExecution)context -> {
                Point position = context.get("pos");
                Rectangle hitbox = Game.player.getPhysics().getHitbox();
                hitbox.x = position.x - hitbox.width / 2;
                hitbox.y = position.y - hitbox.height;
                console.log("Teleported to pos (" + position.x + ", " + position.y + ")");
            }
        ).get()).aliasTo("teleport", "position").aliasTo("tp", "position");
        commands.addPath("level", new CommandBuilder().addNode(
            new IntegerArgument("id")
        ).addNode(
            (CommandExecution)context -> {
                try {
                    Game.loadLevel(context.get("id"), true);
                }
                catch (Exception e) {
                    console.err("Failed to load level");
                    return;
                }
                Game.savefile.levelsCompleted = context.get("id");
                console.log("Loaded level with ID " + context.get("id"));
            }
        ).get());
        commands.addPath("die", new CommandBuilder().addNode(
            (CommandExecution)context -> {
                Game.die();
                console.log("Mario committed suicide :(");
            }
        ).get());
        commands.addPath("save", new CommandBuilder().addNode(
            (CommandExecution)context -> {
                Saveable.save(Game.savefile, Gdx.files.local("save.sav"));
                console.log("Manually saved the game");
            }
        ).get());
        commands.addPath("clear", new CommandBuilder().addNode(
            (CommandExecution)context -> {
                console.clr();
                console.log("Cleared the console");
            }
        ).get());
        commands.addPath("finish", new CommandBuilder().addNode(
            (CommandExecution)context -> {
                Game.finishLevel();
                console.log("Level finished");
            }
        ).get());
        String[] cutsceneIDList = Cutscene.cutscenes.keySet().toArray(new String[0]);
        commands.addPath("cutscene", new CommandBuilder().addNode(
            new ListArgument("id", cutsceneIDList)
        ).addNode(
            (CommandExecution)context -> {
                int id = context.get("id");
                if (id < 0 || id >= Cutscene.cutscenes.size()) {
                    console.err("Invalid cutscene");
                    return;
                }
                console.log("Starting cutscene");
                Main.startCutscene(cutsceneIDList[id]);
            }
        ).get());
        commands.addPath("bump", new CommandBuilder().addNode(
            new PositionArgument("pos", PositionArgument.TILE)
        ).addNode(
            (CommandExecution)context -> {
                Point pos = context.get("pos");
                GameTile.bump(Game.currentLevel, pos.x, pos.y);
                console.log("Bumped a tile at pos " + pos.x + " " + pos.y);
            }
        ).get());
        commands.addPath("music", new CommandBuilder().addNode(
            new ListArgument("music", ArrayStringifier.modify(value -> {
                return value.name;
            }, AudioPlayer.MUSIC))
        ).addNode(
            (CommandExecution)context -> {
                int id = context.get("music");
                if (id < 0 || id >= AudioPlayer.MUSIC.length) {
                    console.err("Invalid music name or ID");
                    return;
                }
                AudioPlayer.MUSIC[id].play();
                console.log("Playing " + AudioPlayer.MUSIC[id].name);
            }
        ).addNode(
            new BooleanArgument("faster")
        ).addNode(
            (CommandExecution)context -> {
                int id = context.get("music");
                if (id < 0 || id >= AudioPlayer.MUSIC.length) {
                    console.err("Invalid music name or ID");
                    return;
                }
                boolean faster = context.get("faster");
                AudioPlayer.MUSIC[id].play(faster);
                console.log("Playing " + (faster ? "a fast version of " : "") + AudioPlayer.MUSIC[id].name);
            }
        ).get());
        commands.addPath("screenshake", new CommandBuilder().addNode(
            new DoubleArgument("x")
        ).addNode(
            new DoubleArgument("y")
        ).addNode(
            (CommandExecution)context -> {
                Game.currentLevel.camera.screenshake(context.get("x"), context.get("y"), 60);
            }
        ).addNode(
            new IntegerArgument("duration")
        ).addNode(
            (CommandExecution)context -> {
                Game.currentLevel.camera.screenshake(context.get("x"), context.get("y"), context.get("duration"));
            }
        ).get());
    }
}

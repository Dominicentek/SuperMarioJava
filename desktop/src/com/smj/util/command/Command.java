package com.smj.util.command;

import com.badlogic.gdx.Gdx;
import com.smj.util.command.arguments.CommandArgument;
import com.smj.util.command.arguments.IntegerArgument;
import com.smj.util.command.arguments.ListArgument;
import com.smj.game.Game;
import com.smj.game.entity.EntityType;
import com.smj.game.score.StaticScore;
import com.smj.game.tile.Tiles;
import com.smj.util.AccessCounter;
import com.smj.util.ArrayStringifier;
import com.smj.util.Saveable;
import com.smj.util.command.console.Console;
import com.smj.util.command.console.StdoutConsole;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Command {
    public static CommandLiteral commands = new CommandLiteral();
    public static Console console = new StdoutConsole();
    public static ArrayList<String> previousCommands = new ArrayList<>(Collections.singletonList(""));
    public static void run(String command) {
        try {
            command = command.trim().replaceAll("/\\s+/g", " ");
            if (command.isEmpty()) return;
            previousCommands.add(0, command);
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
                CommandArgument<?> argument = (CommandArgument<?>)node;
                AccessCounter counter = new AccessCounter(i);
                context.values.put(argument.name, argument.parse(args, counter));
                i = counter.access();
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
                Game.awardScore(new StaticScore(context.get("amount")));
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
        ).get());
        commands.addPath("entity", new CommandBuilder().addNode(
            new ListArgument("type", ArrayStringifier.modify(value -> {
                return value.name().toLowerCase();
            }, EntityType.values()))
        ).addNode(
            new IntegerArgument("x")
        ).addNode(
            new IntegerArgument("y")
        ).addNode(
            (CommandExecution)context -> {
                if (context.<Integer>get("type") == -1 || context.<Integer>get("type") >= EntityType.values().length) {
                    console.err("Invalid entity");
                    return;
                }
                EntityType.values()[context.<Integer>get("type")].spawn(Game.currentLevel, context.get("x"), context.get("y"));
                console.log("Spawned entity " + EntityType.values()[context.<Integer>get("type")].name().toLowerCase());
            }
        ).get());
        commands.addPath("tile", new CommandBuilder().addNode(
            new ListArgument("tile", ArrayStringifier.modify(value -> {
                return value.getName().toLowerCase();
            }, Tiles.class.getFields()))
        ).addNode(
            new IntegerArgument("x")
        ).addNode(
            new IntegerArgument("y")
        ).addNode(
            (CommandExecution)context -> {
                try {
                    if (context.<Integer>get("state") == -1 || context.<Integer>get("state") >= Tiles.class.getFields().length) {
                        console.err("Invalid state");
                        return;
                    }
                    Game.currentLevel.setTileAt(Tiles.class.getFields()[(int)context.get("tile")].getInt(null), context.get("x"), context.get("y"));
                    console.log("Set tile " + Tiles.class.getFields()[(int)context.get("tile")].getName().toLowerCase() + " to pos (" + context.get("x") + ", " + context.get("y") + ")");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        ).get());
        commands.addPath("position", new CommandBuilder().addNode(
            new IntegerArgument("x")
        ).addNode(
            new IntegerArgument("y")
        ).addNode(
            (CommandExecution)context -> {
                Rectangle hitbox = Game.player.getPhysics().getHitbox();
                hitbox.x = context.<Integer>get("x");
                hitbox.y = context.<Integer>get("y");
                console.log("Teleported to pos (" + context.get("x") + ", " + context.get("y") + ")");
            }
        ).addNode(
            new CommandLiteral().addPath("absolute", new CommandBuilder().addNode(
                (CommandExecution)context -> {
                    Rectangle hitbox = Game.player.getPhysics().getHitbox();
                    hitbox.x = context.<Integer>get("x");
                    hitbox.y = context.<Integer>get("y");
                    console.log("Teleported to pos (" + context.get("x") + ", " + context.get("y") + ")");
                }
            ).get()).addPath("relative", new CommandBuilder().addNode(
                (CommandExecution)context -> {
                    Rectangle hitbox = Game.player.getPhysics().getHitbox();
                    hitbox.x += context.<Integer>get("x");
                    hitbox.y += context.<Integer>get("y");
                    console.log("Moved by pos (" + context.get("x") + ", " + context.get("y") + ")");
                }
            ).get())
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
    }
}

package com.smj.gui.menu;

import com.badlogic.gdx.Gdx;
import com.smj.Main;
import com.smj.game.Game;
import com.smj.game.SaveFile;
import com.smj.game.options.Controls;
import com.smj.gui.hud.HUDLayout;
import com.smj.util.AudioPlayer;
import com.smj.util.SMJMusic;
import com.smj.util.Saveable;
import com.smj.util.Transition;

import java.util.Arrays;
import java.util.stream.Stream;

public class Menus {
    public static final Menu HUD_LAYOUT = new Menu(
        new MenuButtonItem((menu, index, item) -> {
            Menu.goBack();
        }),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.COIN_COUNTER.position.x = item.value;
        }, 0, HUDLayout.COIN_COUNTER.position.x, 89),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.COIN_COUNTER.position.y = item.value;
        }, 0, HUDLayout.COIN_COUNTER.position.y, 62),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.LIFE_COUNTER.position.x = item.value;
        }, 0, HUDLayout.LIFE_COUNTER.position.x, 89),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.LIFE_COUNTER.position.y = item.value;
        }, 0, HUDLayout.LIFE_COUNTER.position.y, 62),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.STAR_TIMER.position.x = item.value;
        }, 0, HUDLayout.STAR_TIMER.position.x, 90),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.STAR_TIMER.position.y = item.value;
        }, 0, HUDLayout.STAR_TIMER.position.y, 62),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.KEY_COIN_COUNTER.position.x = item.value;
        }, 0, HUDLayout.KEY_COIN_COUNTER.position.x, 83),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.KEY_COIN_COUNTER.position.y = item.value;
        }, 0, HUDLayout.KEY_COIN_COUNTER.position.y, 62),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.SCORE.position.x = item.value;
        }, 0, HUDLayout.SCORE.position.x, 80),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.SCORE.position.y = item.value;
        }, 0, HUDLayout.SCORE.position.y, 62),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.TIMER.position.x = item.value;
        }, 0, HUDLayout.TIMER.position.x, 87),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.TIMER.position.y = item.value;
        }, 0, HUDLayout.TIMER.position.y, 62),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.WORLD_TEXT.position.x = item.value;
        }, 0, HUDLayout.WORLD_TEXT.position.x, 78),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.WORLD_TEXT.position.y = item.value;
        }, 0, HUDLayout.WORLD_TEXT.position.y, 62),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.SPEEDRUN_TIMER.position.x = item.value;
        }, 0, HUDLayout.SPEEDRUN_TIMER.position.x, 76),
        new MenuValueItem((menu, index, item) -> {
            HUDLayout.SPEEDRUN_TIMER.position.y = item.value;
        }, 0, HUDLayout.SPEEDRUN_TIMER.position.y, 62)
    ).text("hud_layout");
    public static final Menu CONTROLS = new Menu(
        Stream.concat(Arrays.stream(new MenuItem[] {
            new MenuButtonItem((menu, index, item) -> {
                Menu.goBack();
            })
        }), Arrays.stream(Controls.getMenuItems())).toArray(MenuItem[]::new)
    ).text("controls");
    public static final Menu OPTIONS = new Menu(
        new MenuButtonItem((menu, index, item) -> {
            Saveable.save(Main.options, Gdx.files.local("options.dat"));
            Menu.goBack();
        }),
        new MenuButtonItem((menu, index, item) -> {
            Menu.loadMenu(HUD_LAYOUT);
        }),
        new MenuButtonItem((menu, index, item) -> {
            Menu.loadMenu(CONTROLS);
        }),
        new MenuToggleItem((menu, index, item) -> {
            Main.options.stereoSound = item.value != 0;
        }, Main.options.stereoSound),
        new MenuValueItem((menu, index, item) -> {
            Main.options.soundVolume = item.value / 100f;
            AudioPlayer.COIN.play();
        }, 0, (int)(Main.options.soundVolume * 100), 100),
        new MenuMusicItem((menu, index, item) -> {
            Main.options.musicVolume = item.value / 100f;
            SMJMusic.setVolumeAll(Main.options.musicVolume);
        }, 0, (int)(Main.options.musicVolume * 100), 100),
        new MenuToggleItem((menu, index, item) -> {
            Main.options.saveClipScreenshot = item.value != 0;
        }, Main.options.saveClipScreenshot),
        new MenuToggleItem((menu, index, item) -> {
            Main.options.saveFileScreenshot = item.value != 0;
        }, Main.options.saveFileScreenshot),
        new MenuToggleItem((menu, index, item) -> {
            Main.options.speedrunTimer = item.value != 0;
        }, Main.options.speedrunTimer)
    ).text("options");
    public static final Menu JUKEBOX = new Menu(
        Stream.concat(Arrays.stream(new MenuItem[]{
                new MenuButtonItem((menu, index, item) -> {
                    Menu.goBack();
                }),
                new MenuButtonItem((menu, index, item) -> {
                    if (SMJMusic.jukebox == null) return;
                    SMJMusic.jukebox = null;
                    SMJMusic.shouldPlay.play(SMJMusic.shouldPlayFast);
                })
            }),
            Arrays.stream(SMJMusic.getJukeboxMenuItems())
        ).toArray(MenuItem[]::new)
    ).text("jukebox");
    public static final Menu MAIN = new Menu(
        new MenuButtonItem((menu, index, item) -> {
            Main.setTransition(new Transition(0.5, () -> {
                Game.pauseMenuOpen = false;
                Game.title = false;
                Menu.loadMenu(null);
                SMJMusic.stopMusic();
                Game.levelIntro(Game.savefile.levelsCompleted);
            }));
        }),
        new MenuButtonItem((menu, index, item) -> {
            Main.startCutscene("beginning", () -> {
                Game.pauseMenuOpen = false;
                Game.title = false;
                Menu.loadMenu(null);
                Game.savefile = new SaveFile();
                Saveable.save(Game.savefile, Gdx.files.local("save.sav"));
                SMJMusic.stopMusic();
                Game.levelIntro(Game.savefile.levelsCompleted);
            });
        }),
        new MenuButtonItem((menu, index, item) -> {
            Menu.loadMenu(OPTIONS);
        }),
        new MenuButtonItem((menu, index, item) -> {
            Menu.loadMenu(JUKEBOX);
        }),
        new MenuButtonItem((menu, index, item) -> {
            System.exit(0);
        })
    ).text("main").offset(0, 24);
    public static final Menu PAUSE = new Menu(
        new MenuButtonItem((menu, index, item) -> {
            AudioPlayer.PAUSE.play();
            Game.pauseMenuOpen = false;
            Game.paused = false;
            SMJMusic.resume();
            Menu.goBack();
        }),
        new MenuButtonItem((menu, index, item) -> {
            Menu.loadMenu(OPTIONS);
        }),
        new MenuButtonItem((menu, index, item) -> {
            Saveable.save(Game.savefile, Gdx.files.local("save.sav"));
        }),
        new MenuButtonItem((menu, index, item) -> {
            Main.setTransition(new Transition(0.5, () -> {
                Game.title = true;
                Game.loadLevel(Game.savefile.levelsCompleted, true);
                Menu.loadMenu(null);
                Menu.loadMenu(MAIN);
            }));
        }),
        new MenuButtonItem((menu, index, item) -> {
            Menu.loadMenu(JUKEBOX);
        })
    ).text("pause");
}

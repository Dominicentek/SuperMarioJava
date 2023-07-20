# Super Mario Java

**WARNING**<br>
If you want to look into the source code, beware!<br>
A heart attack is guaranteed. (because I write bad code)

A Super Mario Bros. styled Mario fan game.

**Super Mario Java is a free, open-source,**<br>
**non-profit Mario fan-game that uses graphics**<br>
**and audio that are subject to copyright by**<br>
**Nintendo Company Ltd. This game is not published**<br>
**nor endrosed by them. If you want to play much**<br>
**better Mario games than this one, go support**<br>
**Nintendo and buy their consoles and games.**<br>

The game is built on top of heavily modified JMario engine, an unreleased,
broken and confusing Mario engine coded in Java programmed by the same creator.

## How to Build
### Windows
Just run `gradlew dist` command in the project directory<br>
The jar should be located at `desktop/build/libs/desktop-1.0.jar`
### Linux or macOS
Same as Windows, just include `./` at the beginning.<br>
Like this: `./gradlew dist`

## Controls
* W - Up
* S - Left
* A - Down
* D - Right
* Up - Camera Up
* Left - Camera Left
* Down - Camera Down
* Right - Camera Down
* Space - Jump/OK
* Left Shift - Run
* Escape - Pause
* F1 - Hide HUD
* F2 - Screenshot
* F3 - Open Console

## Level Editor

You can find the level editor here: https://dominicentek.github.io/SuperMarioJava, or you can find it offline in the `<project_root>/assets/assets/levelbuilder/lvledit.html`

The game supports .lvl files which are both uncompressed and compressed using the GZIP algorithm.
It is best practice to compress them as the game will report a non-fatal error if the level is uncompressed.

When running any built-in tool, remember to put the JAR file

### How to compress the .lvl file

1. Place the .lvl file inside `<project_root>/assets/assets/levels/` directory with the name `levelX.lvl` with `X` being a positive number.
2. Run the Super Mario Java JAR file with `java -cp SuperMarioJava.jar com.smj.tools.GZIPCompress`
3. The level should be compressed and good to go.
4. You can uncompress levels by running `java -cp SuperMarioJava.jar com.smj.tools.GZIPUncompress` instead.

### Editing sky castle background tiles

1. Create a level .json file. Place ground where you want the background tile to be, air where not.
2. Run the tool `SkycastleBGTilesGen` using `java -cp SuperMarioJava.jar com.smj.tools.SkycastleBGTilesGen`.
3. Recompile

## Replacing Music

The .smjmus file is very basic. The file is a container for 2 ogg files.

First 4 bytes are the length of the intro ogg file, in big-endian, followed by the intro data. Leave 0 for no intro. After that, there are another 4 bytes. Those are the length of the loop, followed by the loop ogg file. The file ends after that.

## Editing Font

1. Run the command `java -cp SuperMarioJava.jar com.smj.tools.FontEditor`
2. Open the font file
3. Edit it
4. Export it to its original location
5. Recompile

## Console Commands
| Name          | Aliases         | Syntax                                     | Description                                        |
|---------------|-----------------|--------------------------------------------|----------------------------------------------------|
| `life`        |                 | `<set,add> <amount>`                       | Manages the amount of lives                        |
| `coin`        |                 | `<set,add> <amount>`                       | Manages the amount of coins                        |
| `time`        |                 | `<set,add> <amount>`                       | Manages the time remaining                         |
| `score`       |                 | `<set,add> <amount>`                       | Manages the score                                  |
| `powerup`     |                 | `<state>`                                  | Sets the powerup state for the player              |
| `superstar`   | `star`          | `<seconds>`                                | Adds an amount of seconds to player's star powerup |
| `entity`      |                 | `<type> <position>`                        | Spawns an entity                                   |
| `tile`        |                 | `<type> <position>`                        | Sets a tile to a position                          |
| `position`    | `teleport` `tp` | `<position>`                               | Teleport player to position                        |
| `die`         |                 |                                            | Makes Mario die                                    |
| `finish`      |                 |                                            | Finishes the level                                 |
| `save`        |                 |                                            | Saves the game                                     |
| `clear`       |                 |                                            | Clears the console                                 |
| `cutscene`    |                 | `<cutscene>`                               | Plays a cutscene                                   |
| `bump`        |                 | `<position>`                               | Bumps a tile at a certain position                 |
| `music`       |                 | `<id> [faster: true,false]`                | Plays music                                        |
| `screenshake` |                 | `<x>` `<y>` `[duration]`                   | Stars a screenshake                                |

The position argument is a set of 2 numeric values.
Those value are the X and Y coordinates of the position.
The values can have a decimal point, and are on the scale of a tile (16 pixels).
You can use the `~` symbol right behind a value like this: `~0.5`.
This will make the coordinate relative to right (or down), and go left (or up)
instead of the other way around.
Use the `@` symbol to make the coordinates relative to the player,
or the `&` symbol to make it relative to the middle of the camera.

### Entity IDs

Here is the list of entities in the game:
* `player`
* `stomped_goomba`
* `goomba`
* `green_koopa_shell`
* `red_koopa_shell`
* `buzzy_beetle_shell`
* `green_koopa`
* `red_koopa`
* `spiny`
* `buzzy_beetle`
* `piranha_plant`
* `dead_bullet_bill`
* `bullet_bill`
* `cheep_cheep`
* `blooper`
* `podoboo`
* `mushroom`
* `fire_flower`
* `ice_flower`
* `jump_shoes`
* `speed_shoes`
* `poisonous_mushroom`
* `life_mushroom`
* `superstar`
* `fireball`
* `iceball`
* `ice_cube`
* `coin`
* `brick`
* `flame_up`
* `flame_left`
* `flame_down`
* `flame_right`
* `bowser`
* `bowser_fire`
* `brick_block`
* `firebar_fireball`
* `pushable_stone`
* `icicle`
* `up_pipe_stream`
* `left_pipe_stream`
* `down_pipe_stream`
* `right_pipe_stream`
* `crate`
* `bump_tile`
* `warper`
* `moving_block`
* `meteorite`
* `meteorite_fragment`
* `final_fight_switch`
* `final_fight_switch_collision`
* `big_bowser`
* `big_bowser_fire`

### Tile IDs

Here is the list of tiles in the game:
* `air`
* `ground`
* `barrier`
* `undecorable_ground`
* `stone_block`
* `ice`
* `spike`
* `empty_block`
* `firebar`
* `big_stone_top_left`
* `big_stone_top_center`
* `big_stone_top_right`
* `big_stone_center_left`
* `big_stone_center_center`
* `big_stone_center_right`
* `big_stone_bottom_left`
* `big_stone_bottom_center`
* `big_stone_bottom_right`
* `coin`
* `key_coin`
* `frozen_coin`
* `key`
* `question_block_coin`
* `question_block_mushroom`
* `question_block_fire_flower`
* `question_block_ice_flower`
* `question_block_jump_shoes`
* `question_block_speed_shoes`
* `question_block_1up`
* `question_block_poison_mushroom`
* `question_block_superstar`
* `brick_empty`
* `brick_coin`
* `brick_mushroom`
* `brick_fire_flower`
* `brick_ice_flower`
* `brick_jump_shoes`
* `brick_speed_shoes`
* `brick_1up`
* `brick_poison_mushroom`
* `brick_superstar`
* `fragile_brick`
* `invisible_question_block_coin`
* `invisible_question_block_mushroom`
* `invisible_question_block_fire_flower`
* `invisible_question_block_ice_flower`
* `invisible_question_block_jump_shoes`
* `invisible_question_block_speed_shoes`
* `invisible_question_block_1up`
* `invisible_question_block_poison_mushroom`
* `invisible_question_block_superstar`
* `pipe_vertical_top_left`
* `pipe_vertical_top_right`
* `pipe_vertical_bottom_left`
* `pipe_vertical_bottom_right`
* `warpable_pipe`
* `pipe_horizontal_top_left`
* `pipe_horizontal_top_right`
* `pipe_horizontal_bottom_left`
* `pipe_horizontal_bottom_right`
* `on_off_switch_red`
* `on_off_switch_blue`
* `on_off_block_red_off`
* `on_off_block_blue_off`
* `on_off_block_red_on`
* `on_off_block_blue_on`
* `burner_up`
* `burner_left`
* `burner_down`
* `burner_right`
* `big_coin_top_left_10`
* `big_coin_top_right_10`
* `big_coin_bottom_left_10`
* `big_coin_bottom_right_10`
* `big_coin_top_left_30`
* `big_coin_top_right_30`
* `big_coin_bottom_left_30`
* `big_coin_bottom_right_30`
* `big_coin_top_left_50`
* `big_coin_top_right_50`
* `big_coin_bottom_left_50`
* `big_coin_bottom_right_50`
* `checkpoint_top_left`
* `checkpoint_top_right`
* `checkpoint_bottom_left`
* `checkpoint_bottom_right`
* `collected_checkpoint_top_left`
* `collected_checkpoint_top_right`
* `collected_checkpoint_bottom_left`
* `collected_checkpoint_bottom_right`
* `clock_10s`
* `clock_30s`
* `clock_50s`
* `clock_100s`
* `enemy_goomba`
* `enemy_green_koopa`
* `enemy_red_koopa`
* `enemy_spiny`
* `enemy_buzzy_beetle`
* `enemy_piranha_plant`
* `enemy_bullet_bill`
* `enemy_cheep_cheep`
* `enemy_blooper`
* `enemy_podoboo`
* `icicle`
* `enemy_lakitu`
* `enemy_hammer_bro`
* `enemy_green_parakoopa`
* `enemy_red_parakoopa`
* `crate`
* `star`
* `locked_star`
* `bullet_bill_launcher_top`
* `bullet_bill_launcher_middle`
* `bullet_bill_launcher_bottom`
* `exclamation_point_circle_ball_thing_idk_how_to_call_this_but_it_is_very_important_for_the_bowser_boss_fight_ig`
* `pushable_stone`
* `upwards_stream`
* `left_stream`
* `downwards_stream`
* `right_stream`
* `warper`
* `moving_block`

### Music IDs

Here is the list of music in the game:
* `ground`
* `underground`
* `desert`
* `snow`
* `underwater`
* `forest`
* `sky`
* `lava`
* `castle`
* `bonus`
* `enemy`
* `superstar`
* `boss`
* `title`
* `ending`
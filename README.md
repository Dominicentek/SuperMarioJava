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

## Console Commands
| Name        | Aliases         | Syntax               | Description                                        |
|-------------|-----------------|----------------------|----------------------------------------------------|
| `life`      |                 | `<set,add> <amount>` | Manages the amount of lives                        |
| `coin`      |                 | `<set,add> <amount>` | Manages the amount of coins                        |
| `time`      |                 | `<set,add> <amount>` | Manages the time remaining                         |
| `score`     |                 | `<set,add> <amount>` | Manages the score                                  |
| `powerup`   |                 | `<state>`            | Sets the powerup state for the player              |
| `superstar` |                 | `<seconds>`          | Adds an amount of seconds to player's star powerup |
| `entity`    |                 | `<type> <position>`  | Spawns an entity                                   |
| `tile`      |                 | `<type> <position>`  | Sets a tile to a position                          |
| `position`  | `teleport` `tp` | `<position>`         | Teleport player to position                        |
| `die`       |                 |                      | Makes Mario die                                    |
| `finish`    |                 |                      | Finishes the level                                 |
| `save`      |                 |                      | Saves the game                                     |
| `clear`     |                 |                      | Clears the console                                 |
| `cutscene`  |                 | `<cutscene>`         | Plays a cutscene                                   |

The position argument is a set of 2 numeric values.
Those value are the X and Y coordinates of the position.
The values can have a decimal point, and are on the scale of a tile (16 pixels).
You can use the `~` symbol right behind a value like this: `~0.5`.
This will make the coordinate relative to right (or down), and go left (or up)
instead of the other way around.
Use the `@` symbol to make the coordinates relative to the player,
or the `&` symbol to make it relative to the middle of the camera.
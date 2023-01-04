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
| Name        | Aliases         | Syntax                                       | Description                                        |
|-------------|-----------------|----------------------------------------------|----------------------------------------------------|
| `life`      |                 | `<set,add> <amount>`                         | Manages the amount of lives                        |
| `coin`      |                 | `<set,add> <amount>`                         | Manages the amount of coins                        |
| `time`      |                 | `<set,add> <amount>`                         | Manages the time remaining                         |
| `score`     |                 | `<set,add> <amount>`                         | Manages the score                                  |
| `powerup`   |                 | `<state>`                                    | Sets the powerup state for the player              |
| `superstar` |                 | `<seconds>`                                  | Adds an amount of seconds to player's star powerup |
| `entity`    |                 | `<type> <entity x> <entity y>`               | Spawns an entity                                   |
| `tile`      |                 | `<type> <tile x> <tile y>`                   | Sets a tile to a position                          |
| `position`  | `teleport` `tp` | `<entity x> <entity y> [absolute,relative] ` | Teleport player to position                        |
| `die`       |                 |                                              | Makes Mario die                                    |
| `finish`    |                 |                                              | Finishes the level                                 |
| `save`      |                 |                                              | Saves the game                                     |
| `clear`     |                 |                                              | Clears the console                                 |


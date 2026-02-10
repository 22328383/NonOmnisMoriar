# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

This is an Eclipse Java SE 1.8 project with no external dependencies (only java.awt, javax.swing, javax.imageio). There is no Maven/Gradle build system.

```bash
# Compile from project root
javac -d bin src/util/*.java src/*.java

# Run (must run from project root so res/ paths resolve correctly)
java -cp bin MainWindow
```

Resource paths (e.g. `"res/assets/hero.png"`, `"res/spacebackground.png"`) are relative to the working directory, not the classpath. The game will fail to load textures if not run from the project root.

There is no test suite. `util/UnitTests.java` only contains a frame-rate check that logs to stdout at runtime.

## Architecture

The game follows an **MVC pattern** coordinated by `MainWindow`:

- **MainWindow** — Entry point. Creates the JFrame (1000x1000), shows a start menu, then runs an infinite game loop at 100 FPS. Each tick calls `Model.gamelogic()` then `Viewer.updateview()`.
- **Model** — Game state and logic. Owns the player `GameObject`, processes input from `Controller`, handles movement and the fishing mechanic. The `gameLogic()` method (object interactions) is a stub.
- **Viewer** — Extends `JPanel`. Draws the background and player sprite with sprite-sheet animation (4 frames, 32px wide each, cycling every 40 ticks). Reloads images from disk every frame.
- **Controller** — Singleton `KeyListener`. Tracks static boolean flags for WASD + Space. Model polls these flags each frame; Space is manually reset after consumption.

### util package

- **GameObject** — Wraps a position (`Point3f`), dimensions, and a texture file path. Falls back to `res/blankSprite.png` if untextured.
- **Point3f** — 3D position with `ApplyVector()` that clamps coordinates to 0–900. Note: `ApplyVector` **negates Y and Z** (`y - vector.y`), so positive W vector = upward movement on screen.
- **Vector3f** — 3D vector with arithmetic, dot/cross product, normalization.

### Key constants

| Constant | Value |
|---|---|
| Window size | 1000x1000 |
| World boundary | 0–900 (enforced in `Point3f`) |
| Target FPS | 100 (10ms/frame) |
| Player size | 128x128 |
| Player start | (500, 500, 0) |
| Movement speed | 2 px/frame |
| Sprite animation | 4 frames, 32px each, 10 ticks per frame |

### Game mechanics

the main game loop is going to the water and clicking on the water to cast your rod.
then letting go of the left click once a fish is caught. then depending on your
rod and other factors we can add later (weather, bait type, items etc etc) we rng
a random fish/loot fished up and generate a weight associated with it. items and fish
have different rarity so it should feel like gambiling.
based on the fish you caught, the price of the fish is determined by the weight and rarity of the item.
with the money you can buy better gear to increase odds or asthetics. the game ends when you collect
enough money to "prestige" permanently making your odds better but reseting all progress.

### Source files have no package declaration

`MainWindow`, `Model`, `Viewer`, and `Controller` are in the default package (no package statement). Only the `util/` classes use `package util;`.

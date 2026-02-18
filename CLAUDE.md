# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Important: Tutor Mode

The user is learning game development. Do NOT write large chunks of code for them. Instead:
- Explain concepts and approaches
- Point out bugs and explain why they happen
- Give small illustrative snippets, not copy-paste solutions
- Only write boilerplate/tedious code when explicitly asked
- Let the user do the conceptual and architectural work themselves

## Build & Run

This is an Eclipse Java SE 1.8 project with no external dependencies (only java.awt, javax.swing, javax.imageio). There is no Maven/Gradle build system.

```bash
# Compile from project root
javac -d bin src/util/*.java src/*.java

# Run (must run from project root so res/ paths resolve correctly)
java -cp bin MainWindow
```

Resource paths are relative to the working directory, not the classpath. The game will fail to load textures if not run from the project root.

There is no test suite. `util/UnitTests.java` only contains a frame-rate check that logs to stdout at runtime.

## Game: Non Omnis Moriar (NOM)

A tile-based roguelike dungeon crawler. **Due: March 3rd.** The goal is to get a high score by exploring procedurally generated dungeon levels, looting, fighting enemies, and deciding when to "cash out" or risk going deeper.

### Core Design (from idea_2.txt)

- **Scoring system**: All actions give score. Score multiplier increases with dungeon depth. If you die, score resets to 0 UNLESS you reach the same level again (loot score gets refunded). Only way to bank score is to leave the dungeon voluntarily between levels. "Double or nothing" gambling feel.
- **Inventory**: Loot varies by rarity and score value. Consumables (potions etc). 4 equipment slots: helmet, armour, weapon, charm. Gear scales with dungeon level.
- **Combat**: Turn-based bump combat (move into enemy tile to attack). Damage based on gear stats. Enemies move one tile toward player per turn.
- **Level generation**: Each level is a graph of rooms connected by doors. Rooms are individual screens (no scrolling). Procedurally generated with random layouts.

### What's Done

- Tile-based grid rendering (20x20, 50px tiles)
- Player movement (one tile per keypress, WASD)
- Wall collision (checks tile type before moving)
- Tile enum (NOTHING, VOID, FLOOR, WALL, DOOR)
- TextureCache (HashMap-based, loads images once)
- GameConstants (centralized config)
- Room class with grid generation (parameterized rectangular rooms)
- Level class (holds rooms, tracks current room)
- Model wired to use Level -> Room -> Tile[][] chain
- Door class (start position, end room, end position)
- DCSS tileset integrated for dungeon art
- Door placement on random wall positions with overlap prevention
- Room transitions via doors (walk into door tile to swap rooms)
- Multi-room levels with graph connections (chain + random extra)
- Random room sizes (borderX/borderY parameters)
- Enemy system: abstract Enemy base class with Orc and Rat subclasses
- 5-tier rarity spawn system with level-scaled weights (common→legendary)
- Linear stat scaling per dungeon level
- Tier table for sustainable enemy-per-tier management
- Safe spawn positioning (avoids doors, player start, other mobs)

### What's Next (in priority order)

1. **Draw enemies** — expose mobs to Viewer, render on screen
2. **Bump combat** — attack enemies by moving into them
3. **Enemy AI** — enemies move toward player each turn
4. **Items and inventory** — loot drops, equipment slots
5. **Scoring system** — the double-or-nothing mechanic
6. **Level progression** — stairs down, new level generation, depth multiplier
7. **Polish** — start screen, death screen, score display, balancing

## Architecture

MVC pattern coordinated by `MainWindow`:

- **MainWindow** — Entry point. JFrame (1000x1000), start menu, game loop at 60 FPS. Calls `Model.gamelogic()` then `Viewer.updateview()`.
- **Model** — Game state and logic. Owns the player `GameObject`, a `LinkedList<Level>` (the dungeon), and a reference to the current `Room`. Processes input from `Controller`, handles movement with tile collision.
- **Viewer** — Extends `JPanel`. Draws the current room's tile grid and the player sprite using `TextureCache`.
- **Controller** — Singleton `KeyListener`. Tracks boolean flags for WASD + Space. Model resets flags after consuming them (one move per keypress).
- **TextureCache** — HashMap<String, Image> that loads each image from disk once and caches it.
- **GameConstants** — All magic numbers: window size, grid size, tile size, FPS, texture paths.

### Game World Structure

```
Model
  -> LinkedList<Level> dungeon    (all levels in the dungeon)
  -> Room room                    (current room reference)

Level
  -> LinkedList<Room> allRooms    (all rooms in this level)
  -> Room currentRoom             (which room player is in)
  -> int levelNumber

Room
  -> Tile[][] grid                (the 20x20 tile map)
  -> LinkedList<Door> doors       (connections to other rooms)
  -> LinkedList<Enemy> mobs       (enemies in this room)
  -> generateRoom()               (fills the grid)
  -> spawnMobs()                  (tier-based enemy spawning)
  -> rollTier(level)              (weighted rarity roll)
  -> createEnemy(tier,x,y,level)  (tier table lookup + buildEnemy)

Door
  -> startX, startY               (position in this room)
  -> endX, endY                   (where player appears in target room)
  -> endRoom                      (which room this leads to)

Enemy (abstract)
  -> x, y, health, damage, gold, accuracy, critChance, name, texture
  -> Subclasses: Orc, Rat (stats scale linearly with level)
```

### util package

- **GameObject** — Wraps a position (`Point3f`), dimensions, and a texture file path.
- **Point3f** — 3D position with `ApplyVector()` that clamps coordinates to 0–950. Note: `ApplyVector` **negates Y and Z** (`y - vector.y`), so positive W vector = upward movement on screen.
- **Vector3f** — 3D vector with arithmetic, dot/cross product, normalization.

### Key Constants (in GameConstants.java)

| Constant | Value |
|---|---|
| Window size | 1000x1000 |
| Grid size | 20x20 tiles |
| Tile size | 50px |
| World boundary | 0–950 (in Point3f) |
| Target FPS | 60 |
| Player start | tile (10, 10) |

### Source files have no package declaration

All game classes are in the default package. Only `util/` classes use `package util;`. Classes in `util` cannot import from the default package (Java limitation).

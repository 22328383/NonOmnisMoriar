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
# Compile from project root (need -source/-target for Java 8 compat)
javac -source 1.8 -target 1.8 -d bin src/core/*.java src/util/*.java src/mobs/*.java src/props/*.java src/items/*.java src/*.java

# Run (must run from project root so res/ paths resolve correctly)
java -cp bin MainWindow
```

Resource paths are relative to the working directory, not the classpath. The game will fail to load textures if not run from the project root.

There is no test suite. `util/UnitTests.java` only contains a frame-rate check that logs to stdout at runtime.

## Game: Non Omnis Moriar (NOM)

A tile-based roguelike dungeon crawler. **Due: March 3rd.** The goal is to get a high score by exploring procedurally generated dungeon levels, looting, fighting enemies, and deciding when to "cash out" or risk going deeper.

### Core Design (from idea_2.txt)

- **Scoring system**: Gold earned from kills, multiplied by dungeon depth. If you die, gold drops where you died as a GoldPile — you can reclaim it by returning to that spot. Only way to bank gold safely is to walk into an Exit and leave the dungeon voluntarily. "Double or nothing" gambling feel.
- **Inventory**: 4 equipment slots: helmet, armour, weapon, charm. Gear is procedurally generated with random names, base stats, suffix modifiers, and optional prefix modifiers. All item stats scale with dungeon level. Consumables (potions etc) planned.
- **Combat**: Turn-based bump combat (move into enemy tile to attack). Player damage rolls between damageLow and damageHigh. Enemies have accuracy, crit chance, and speed-gated movement. Adjacent enemies always attack (skip speed roll).
- **Level generation**: Each level is a graph of rooms connected by doors. Rooms are individual screens (no scrolling). Procedurally generated with random layouts.

### What's Done

- Tile-based grid rendering (20x20, 25px tiles, 500x500 game area)
- Player movement (one tile per keypress, WASD + Space for idle turn)
- Wall collision (checks tile type before moving)
- Tile enum in core/ (NOTHING, VOID, FLOOR, WALL, STAIRS, DOOR)
- TextureCache (HashMap-based, loads images once)
- GameConstants in core/ (centralized config, default player stats, texture paths)
- Room class with grid generation (parameterized rectangular rooms)
- Level class (holds rooms, tracks current room)
- Model refactored: handleMove()/checkTile() pattern replaces 4 duplicated direction handlers
- Door class (start position, end room, end position)
- DCSS tileset integrated for dungeon art
- Door placement on random wall positions with overlap prevention
- Room transitions via doors (walk into door tile to swap rooms)
- Multi-room levels with graph connections (chain + random extra)
- Random room sizes (borderX/borderY parameters)
- Occupant interface in core/ — universal tile occupancy system (O(1) lookup via Occupant[][] grid on Room)
- Enemy system in mobs/ package: abstract Enemy base class implements Occupant, 10 subclasses
- 5-tier rarity spawn system with level-scaled weights (common->legendary)
- Linear stat scaling per dungeon level
- Enemy AI: vision-based chasing with speed roll (adjacent enemies always attack), random wandering when idle
- Enemy combat: accuracy roll (miss), crit chance roll (double damage), normal hit
- Bump combat with bounce-back effect
- Player class with Stats-based stat system (base stats from GameConstants defaults)
- Stats class in core/ (public fields, add() method for summing gear bonuses)
- Gold system: earn on kill (multiplied by depth), drop as GoldPile occupant on death, reclaim by walking over
- Death tracking (deathRoom, deathX, deathY) to clear old gold piles on re-death
- Cash-out via Exit occupant (walk into exit to bank gold, start fresh dungeon)
- Text log system (8-line scrolling log, 200px black bar at bottom)
- HUD: gold (left) and HP (right) displayed above log
- Click-to-inspect system (click tiles/mobs/doors/occupants for info)
- Level progression via stairs (STAIRS tile in last room, checks if level exists before creating new)
- Stairs and exits placed AFTER door connections to avoid spawn conflicts
- GameState enum (PLAY, INV) with Tab key toggle
- InventoryScreen class (separate file, draws centered panel with 4 equipment slot boxes)
- Custom font support (res/assets/fonts/rct2.otf)
- Start menu temporarily disabled (skips straight to gameplay)

### Item Generation System (In Progress)

Procedurally generated items with three-layer naming and stat system:

1. **Base item** — determined by Slot (Sword/Armor/Helmet/Charm). Provides core base stats. Stats scale with dungeon level (deeper = stronger items).
2. **Suffix (Noun)** — "of Greed", "of Rage", etc. Always present. Themed stat modification with randomised values within a range. Example: "of Greed" = goldModifier boost + maxHP penalty.
3. **Prefix (Adjective)** — "Unholy", "Blessed", "Rusty", etc. Optional (50/50 or depth-based). Additional themed stat tweak.

Final item: `[Prefix] <Base> of <Suffix>` → e.g. "Unholy Sword of Greed"
Final stats: base stats + suffix modifier + prefix modifier (if present)
All scaled by dungeon level to encourage exploring deeper.

Potions and general loot are exempt from this system.

### What's Next (in priority order)

1. **Item system** — base item templates, suffix/prefix modifier tables, LootTable generator, level scaling
2. **Equipment on Player** — HashMap<Slot, Item>, getEffectiveStats() sums base + gear
3. **Containers** — Chest occupant that holds items, walk into or click to loot
4. **Inventory interaction** — cursor navigation, equip/unequip, use consumables
5. **Scoring system** — high score tracking, score display
6. **Polish** — start screen, death screen, balancing, sound effects

## Architecture

MVC pattern coordinated by `MainWindow`:

- **MainWindow** — Entry point. JFrame (500x700), game loop at 60 FPS. Calls `Model.gamelogic()` then `Viewer.updateview()`. Start menu temporarily disabled.
- **Model** — Game state and logic. Owns a `Player`, `LinkedList<Level>` (dungeon), current `Room` reference, `GameState` (PLAY/INV). Handles movement, bump combat, enemy AI, click inspection, door transitions, level progression, death/respawn, cash-out.
- **Viewer** — Extends `JPanel`. Switches rendering on GameState. PLAY: draws room tiles, occupants from Occupant[][] grid, player sprite, HUD + log. INV: delegates to InventoryScreen.draw().
- **InventoryScreen** — Draws inventory UI (dark gray background, black panel, 4 equipment slot boxes). Handles input when in INV state.
- **Controller** — Singleton `KeyListener` + `MouseListener`. Tracks boolean flags for WASD + Space + Tab + mouse clicks. Tab uses KeyEvent.VK_TAB (keyCode, not keyChar). Canvas has setFocusTraversalKeysEnabled(false) so Swing doesn't eat Tab.
- **Player** — Owns sprite, Stats baseStats, hitPoints, gold, tile position. Getters read from getEffectiveStats() (currently returns baseStats, will sum equipment later). reset() rebuilds from GameConstants defaults.
- **Stats** — Simple class with public int fields (maxHP, damageLow, damageHigh, accuracy, critChance, critModifier, dodgeChance, goldModifier). Has add() to sum two Stats together. Used for player base stats and item bonuses.
- **TextureCache** — HashMap<String, Image> that loads each image from disk once and caches it.
- **GameConstants** — All magic numbers: window size, grid size, tile size, FPS, texture paths, default player stats.

### Package Structure

```
src/
  core/         GameConstants, Tile, Slot, Occupant, Stats
  util/         GameObject, Point3f, Vector3f, UnitTests
  mobs/         Enemy (abstract) + 10 subclasses
  props/        GoldPile, Exit (Occupant implementations)
  items/        Item (abstract, implements Occupant)
  (default)     MainWindow, Model, Viewer, Controller, Player, Room, Level, Door, TextureCache, InventoryScreen
```

Named packages (core, mobs, props, items) cannot import from the default package (Java limitation). Workarounds: pass data as parameters (e.g. mob textures as String), or put shared types in core/.

### Game World Structure

```
Model
  -> GameState state              (PLAY or INV)
  -> Player player                (stats, position, gold)
  -> LinkedList<Level> dungeon    (all levels)
  -> Room room                    (current room reference)
  -> int currLevel                (current depth)
  -> deathRoom/deathX/deathY      (tracks last death for gold pile cleanup)

Level
  -> LinkedList<Room> allRooms    (all rooms in this level)
  -> Room currentRoom             (which room player is in)
  -> int levelNumber

Room
  -> Tile[][] grid                (20x20 tile map)
  -> Occupant[][] occupants       (occupancy grid for O(1) lookup)
  -> LinkedList<Door> doors       (connections to other rooms)
  -> LinkedList<Enemy> mobs       (enemies in this room)
  -> set/get/clearOccupant()      (occupancy helpers)
  -> makeStairs(), makeExit()     (placed after door connections)

Player
  -> Stats baseStats              (from GameConstants defaults)
  -> getEffectiveStats()          (TODO: base + equipment bonuses)
  -> hitPoints, gold, tileX/Y

Stats
  -> public int maxHP, damageLow, damageHigh, accuracy, critChance, critModifier, dodgeChance, goldModifier
  -> add(Stats other) -> Stats    (sums all fields)

Item (abstract, implements Occupant)
  -> name, texture, slot, value, stats (Stats bonuses)
```

### Key Constants (in GameConstants.java)

| Constant | Value |
|---|---|
| Window size | 500x700 (500 game + 200 log) |
| Grid size | 20x20 tiles |
| Tile size | 25px |
| Target FPS | 60 |
| Player start | tile (10, 10) |
| Default HP | 25 |
| Default damage | 3-5 |
| Default accuracy | 100 |
| Default crit | 5% chance, 2x modifier |

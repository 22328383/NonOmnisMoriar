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

**Core Systems**
- Tile-based grid rendering (20x20, 25px tiles, 500x500 game area)
- Player movement (one tile per keypress, WASD + Space for idle turn)
- Wall collision (checks tile type before moving)
- Tile enum in core/ (NOTHING, VOID, FLOOR, WALL, STAIRS, DOOR)
- TextureCache (HashMap-based, loads images once)
- GameConstants in core/ (centralized config, default player stats, texture paths, SFX paths)
- Room class with grid generation (parameterized rectangular rooms)
- Level class (holds rooms, tracks current room)
- Model refactored: handleMove()/checkTile() pattern replaces 4 duplicated direction handlers
- DCSS tileset integrated for dungeon art
- Custom font support (res/assets/fonts/rct2.otf)
- Start menu temporarily disabled (skips straight to gameplay)

**Level Generation & Navigation**
- Door class (start position, end room, end position, `used` flag for tracking traversal)
- Door placement on random wall positions with overlap prevention
- Room transitions via doors (walk into door tile to swap rooms)
- Multi-room levels with graph connections (chain + random extra)
- Random room sizes (borderX/borderY parameters)
- Level progression via stairs (STAIRS tile in last room, checks if level exists before creating new)
- Stairs and exits placed AFTER door connections to avoid spawn conflicts
- Click used doors to see destination ("This door leads to room X-Y")

**Occupant & Prop System**
- Occupant interface in core/ — universal tile occupancy system (O(1) lookup via Occupant[][] grid on Room)
- Bounce-back on all prop interactions (Shop, Chest, Exit) — player doesn't overlap props
- GoldPile occupant (dropped on death, reclaim by walking over)
- Exit occupant (walk into to bank gold, start fresh dungeon)
- Chest occupant in props/ (bump to open inventory; generates random mix of gear, loot, potions)
- Shop occupant in props/ (one per level, bump to sell all loot for gold)

**Enemy System**
- Enemy system in mobs/ package: abstract Enemy base class implements Occupant, 10 subclasses
- 5-tier rarity spawn system with level-scaled weights (common->legendary)
- Linear stat scaling per dungeon level
- Enemy AI: vision-based chasing with speed roll (adjacent enemies always attack), random wandering when idle
- Enemy-specific sound arrays (rodent, slime, orc, giant, beast, undead) passed via constructor

**Combat (fully stat-checked)**
- Bump combat with bounce-back effect
- Player attack: accuracy roll → crit roll (using critModifier) → normal hit. goldModifier applied on kills
- Enemy attack: accuracy roll → player dodge check (dodgeChance) → crit roll (2x) → normal hit
- Miss/dodge/crit/hit all produce distinct log messages

**Item System (Gear)**
- Gear class in items/ (extends Item) with procedural generation
- Three-layer naming: `[Prefix] <Base> of <Suffix>` → e.g. "Unholy Sword of Greed"
- Base item determined by Slot (Sword/Armor/Helmet/Charm) with core stats
- Suffix (always present): themed stat modification ("of Greed", "of Rage", etc.)
- Prefix (optional): additional themed stat tweak ("Unholy", "Blessed", "Rusty", etc.)
- All stats scale with dungeon level

**Loot System**
- Loot class in items/ (extends Item) — 15 base treasure items (rings, idols, gems, etc.)
- 4 quality tiers: Cracked (1x, 60%), Flawed (2x, 15%), Fine (3x, 15%), Perfect (4x, 10%)
- Found in chests, sold at shops for gold
- Player has lootBag (LinkedList<Loot>), sellLoot() sums values

**Potion System**
- Potion class in items/ (extends Item) — 4 types: Health, Might, Greed, Unknown
- Health Potion: instant heal 30% maxHP, consumed on use
- Might Potion: +3 damageLow/High for 10 turns
- Greed Potion: +2 goldModifier for 10 turns
- Unknown Potion: random +/-1 to all stats (except HP) for 10 turns
- Duration ticks down per player turn via tickPotions()
- isActive flag prevents stacking; inactive potions kept in inventory until used
- getEffectiveStats() sums base + equipment + active potion boosts

**Player & Equipment**
- Player class with Stats-based stat system (base stats from GameConstants defaults)
- Stats class in core/ (public fields, add() method returning new Stats)
- 4 equipment slots: HashMap<Slot, Item> (HELMET, ARMOR, WEAPON, CHARM)
- getEffectiveStats() sums baseStats + all equipped gear stats + active potion boosts
- Gold system: earn on kill (multiplied by depth and goldModifier), drop as GoldPile on death
- Death tracking (deathRoom, deathX, deathY) to clear old gold piles on re-death

**UI & HUD**
- GameState enum (PLAY, INV) with Tab key toggle
- Text log system (8-line scrolling log, 200px black bar at bottom)
- HUD: gold (left) and HP (right) displayed above log
- Click-to-inspect system (click tiles/mobs/doors/occupants for thematic info)
- InventoryScreen: chest panel (gear stats, loot value, potion effects) + player panel (equipment, stats, loot value, potions)
- Click to loot from chests, click to equip/swap gear, click to use potions

**Sound Effects**
- Viewer.playSound(String) using javax.sound.sampled.Clip
- Sound for: sword hits, misses, mob-specific sounds (per subclass), gold pickup, equip, door, UI, death, cash-out
- Enemy sounds: each subclass passes String[] sounds to base, getSound() picks random

### What's Next (in priority order)

1. **Scoring system** — high score tracking, score display on HUD or death screen
2. **Polish** — start screen, death screen, balancing
3. **Visual polish** — animations, particle effects, screen transitions

## Architecture

MVC pattern coordinated by `MainWindow`:

- **MainWindow** — Entry point. JFrame (500x700), game loop at 60 FPS. Calls `Model.gamelogic()` then `Viewer.updateview()`. Start menu temporarily disabled.
- **Model** — Game state and logic. Owns a `Player`, `LinkedList<Level>` (dungeon), current `Room` reference, `GameState` (PLAY/INV). Handles movement, bump combat (full stat checks), enemy AI, click inspection, door transitions, level progression, death/respawn, cash-out, shop selling, chest opening, potion ticking.
- **Viewer** — Extends `JPanel`. Switches rendering on GameState. PLAY: draws room tiles, occupants from Occupant[][] grid, player sprite, HUD + log. INV: delegates to InventoryScreen.draw(). Has static playSound(String) for SFX.
- **InventoryScreen** — Draws inventory UI: chest panel (items with stats/value/effects) and player panel (equipment slots, stat summary, loot value, potions). Handles click-to-loot, click-to-equip, click-to-use-potion.
- **Controller** — Singleton `KeyListener` + `MouseListener`. Tracks boolean flags for WASD + Space + Tab + mouse clicks. Tab uses KeyEvent.VK_TAB (keyCode, not keyChar). Canvas has setFocusTraversalKeysEnabled(false) so Swing doesn't eat Tab.
- **Player** — Owns sprite, Stats baseStats, HashMap<Slot,Item> equipped, LinkedList<Loot> lootBag, LinkedList<Potion> potions, hitPoints, gold. getEffectiveStats() sums base + gear + active potions. reset() clears everything.
- **Stats** — Simple class with public int fields (maxHP, damageLow, damageHigh, accuracy, critChance, critModifier, dodgeChance, goldModifier). Has add() returning new Stats. Used for player base stats, item bonuses, potion boosts.
- **TextureCache** — HashMap<String, Image> that loads each image from disk once and caches it.
- **GameConstants** — All magic numbers: window size, grid size, tile size, FPS, texture paths, SFX paths, default player stats. Centralized Random via getRand().

### Package Structure

```
src/
  core/         GameConstants, Tile, Slot, Occupant, Stats
  util/         GameObject, Point3f, Vector3f, UnitTests
  mobs/         Enemy (abstract) + 10 subclasses (Rat, Slime, Orc, Goblin, Troll, Knight, Wizard, Beast, Drake, Demon)
  props/        GoldPile, Exit, Chest, Shop (Occupant implementations)
  items/        Item (abstract), Gear, Loot, Potion
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

Door
  -> x, y, endDoor, endRoom       (position + linked destination)
  -> boolean used                  (tracks if player has traversed this door)

Player
  -> Stats baseStats              (from GameConstants defaults)
  -> HashMap<Slot, Item> equipped  (4 gear slots)
  -> LinkedList<Loot> lootBag     (sellable treasures)
  -> LinkedList<Potion> potions   (consumables)
  -> getEffectiveStats()          (base + gear + active potions)
  -> hitPoints, gold, tileX/Y

Stats
  -> public int maxHP, damageLow, damageHigh, accuracy, critChance, critModifier, dodgeChance, goldModifier
  -> add(Stats other) -> Stats    (sums all fields, returns new Stats)

Item (abstract, implements Occupant)
  -> name, texture, slot, value, stats (Stats bonuses)
  -> Gear extends Item            (procedural prefix/suffix generation)
  -> Loot extends Item            (quality tiers: Cracked/Flawed/Fine/Perfect)
  -> Potion extends Item          (duration, isActive, boost Stats, tick())
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

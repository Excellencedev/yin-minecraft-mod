# How to Test the Mod — Step by Step

A complete walkthrough from a fresh install to verifying every feature.

---

## Before You Launch (one-time setup)

1. **Get Fabric Loader installed** for **Minecraft 1.21.1**:
   - Download the **Fabric Installer** from https://fabricmc.net/use/installer/
   - Run it, select **Minecraft Version 1.21.1**, click **Install**
2. **Put both jars in your `mods` folder**:
   - `adventuremod-1.0.0.jar` (the mod — from your artifact folder, the smaller jar)
   - `fabric-api-0.116.12+1.21.1.jar` (from https://modrinth.com/mod/fabric-api)
   - Folder location: `%appdata%\.minecraft\mods` (press `Win+R`, type that, hit Enter)
3. **Launch Minecraft** with the Fabric 1.21.1 profile.

If you see **"Adventure & Survival Expansion"** in the Mods list on the title screen, the mod loaded. Go to **Singleplayer → Create New World → Creative → Create**.

---

## Test Plan (in order)

### 🏃 Movement
- **Double Jump:** Jump, then press **Space** again in mid-air → cloud particles, phantom flap sound. You should rise a bit.
- **Wall Jump:** Run at a wall, jump into it, press **Space** while mid-air and touching the wall → knocked backward off the wall.
- **Dodge Dash:** Press **Left Alt** while moving → you fly forward, faint poof particles. Has a 1.5s cooldown.

### ⚔️ Combat
- **Fist Combos:** Switch to Creative, give yourself nothing (empty hand), punch a pig 3 times in < 1.5s. Punch 1 = 4 dmg, punch 2 = small knockback, punch 3 = 6 dmg + sweep particles.
- **Parry:** Stand in front of a zombie, hold **right-click** to raise shield, let it hit you within 0.4s of raising → anvil sound, spark particles, zombie gets Slowness X + Weakness X for 2s and is knocked back.
- **Class Multipliers:** `/class warrior` (open chat), then punch → +20% damage.

### 🌱 Class + Skills
- `/class hunter` → set your class. Open inventory, type `/class` to see current class.
- Break 10 wheat fully grown (the crop, not the seeds) → you'll see "[Skill] Farming leveled up" messages in chat.

### 💧 Thirst
- Hold a **water bucket** and right-click → "[Thirst] fully quenched!" message.
- Sprint for ~10 minutes → you'll see thirst deplete, then take starvation damage at 0.

### 🐗 Custom Animals
- `/summon adventuremod:wild_boar` → neutral pig-like mob. Hit it to anger it. It charges.
- `/summon adventuremod:deer` → goat-shaped mob. Sprint near it, it flees. Sneak + hold wheat → it approaches. Wait 5 minutes → it drops a deer antler.

### 💂 Guard Villager
- `/summon adventuremod:guard_villager` → sword+shield villager. Right-click with **emerald block** (1/3 chance) → tamed. It follows you and fights zombies.

### 🧑‍🌾 Hunter Profession
- Place the **Hunter's Table** (in creative inventory → "Adventure & Survival Expansion" tab) near an unemployed villager → he becomes a Hunter. Right-click him for trades.

### 🌿 Berry Bush
- Find a **Wild Berry Bush** (look in the world or `/setblock` one) → wait for berries, right-click → drops wild berries + small thirst boost.

### 🔪 Butchering Table
- Craft a **Butchering Table** (in creative inventory or via recipe) → place, right-click while holding raw beef/pork/mutton → converts to raw boar meat + bones.

### 🗡️ Custom Weapons
- **Boar Tusk Dagger** (poison II for 3s on hit) — craft from boar tusk + stick
- **Antler Greatsword** (sweep attack hits nearby enemies) — craft from deer antler + stick
- **Hunter Bow** (450 durability, full-draw = crit) — craft from deer antler + stick

### 🐺 Mount
- `/summon adventuremod:rideable_boar` → right-click with **saddle** → right-click empty hand to mount → ride it.

### 🦊 Pets + Mythics + Bosses
- `/summon adventuremod:forest_fox` → tame with sweet berries
- `/summon adventuremod:stag_spirit` → hostile, glows, teleports
- `/summon adventuremod:forest_guardian` → 200 HP boss, drops Antler Greatsword + emerald blocks. Damage it below 50% HP → phase 2 (more damage, faster, ground-slam AoE every 5s)

### 🏞️ World Generation
- Create a new world, run around for a few minutes. You should see:
  - Wild boars and deer spawning in the overworld (they spawn more in forests)
  - A **Hunter Outpost** (small cobblestone + spruce structure) at some point — rare (1 in 120 chunks)
  - A **Whispering Woods** biome if you wander far enough

### 🏆 Advancements
- Open the advancements screen (book icon) → you should see an "Adventure & Survival" tab. Several will unlock as you do things.

---

## Quick smoke test (5 minutes, minimum)

If you just want to confirm the mod works:

1. Launch → no crash, mod in list ✓
2. Creative world → `/class warrior` → punch a pig, see extra damage
3. `/summon adventuremod:forest_guardian` → kill it → drops loot
4. Press Left Alt → you dash
5. Press Space twice mid-air → you double-jump

If all 5 work, the mod is good. If any of them crash or don't work, share the crash log or what's missing and I'll debug.

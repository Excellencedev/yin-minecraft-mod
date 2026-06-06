# What Next: How to Build, Install, and Test the Mod

The mod is now **code-complete**: every module listed in `description.txt` is implemented. Build with CI, install, and verify.

---

## 📦 Step 1: Build the Mod

### Option A: GitHub Actions CI (Recommended)
1. Push your code to a GitHub repository on the `main` branch.
2. Navigate to the **Actions** tab — a workflow named "Minecraft Mod CI" will run.
3. When complete (2-3 min), click the workflow run, scroll to **Artifacts**, and download `adventure-mod-jar`.
4. Extract the zip to get `adventuremod-1.0.0.jar`.

### Option B: Local Build
```bash
./gradlew build
```
The compiled JAR will be at `build/libs/adventuremod-1.0.0.jar`.

---

## 🎮 Step 2: Install and Run

### Requirements
- **Java 21** (download from [Adoptium](https://adoptium.net/))
- **Minecraft 1.21.1** with **Fabric Loader** and **Fabric API** (version 0.116.12+1.21.1)

### Using Prism Launcher (Free)
1. Download [Prism Launcher](https://prismlauncher.org/download/).
2. Add an offline account: **Profiles → Manage Accounts → Add Offline**.
3. Create an instance: **Add Instance → 1.21.1**.
4. Select the instance → **Edit → Version tab → Install Fabric**.
5. Go to **Mods tab → Add** → select `adventuremod-1.0.0.jar`.
6. Also add the [Fabric API](https://modrinth.com/mod/fabric-api/version/0.116.12+1.21.1) jar.

### Using Legacy Launcher (Free)
1. Download [Legacy Launcher](https://llaun.ch/).
2. Select **Fabric 1.21.1** from the version list and install/play.
3. Open `%appdata%\.minecraft\mods` and add both `adventuremod-1.0.0.jar` and `Fabric API.jar`.

---

## 🧪 Step 3: Feature Verification Checklist

### 🏃‍♂️ Movement System (Manifest Movement)
- **Double Jump**: Jump once, then press Spacebar mid-air (phantom flap sound, cloud particles).
- **Wall Jump**: Face a wall mid-air and press Spacebar (leap backward, knock sound).
- **Dodge Dash**: Press **Left Alt** while moving (forward dash, 0.5s invulnerability, 1.5s cooldown).

### ⚔️ Combat System (Manifest Combat)
- **Fist Combos**: Unarmed hits deal 4 base damage. Punch 2 knocks back, Punch 3 sweeps with particles and 6 damage.
- **Parry Shield**: Raise shield within 0.4s of being hit — anvil clang, spark particles, attacker stunned (Slowness X + Weakness X for 2s) and knocked back.
- **Class Multipliers**: Warrior +20% damage +10% speed; Hunter +20% damage +10% XP; Scout +10% damage +30% speed (handled automatically by mixin).

### 🐗 Custom Animals (Manifest Animals)
- **Wild Boar** (`/summon adventuremod:wild_boar` or spawn egg): Neutral, charges when provoked, eats/destroys mature carrots/potatoes/wheat to heal.
- **Deer** (`/summon adventuremod:deer` or spawn egg): Flees from sprinting players, approachable while sneaking/holding wheat/apples, sheds antlers every 5 minutes.

### 💂‍♂️ Guard Villagers (Manifest Villagers)
- **Guard Villager** (`/summon adventuremod:guard_villager` or spawn egg): Fights hostiles with sword and shield.
- **Hiring**: Right-click with Emerald Block or Golden Apple (1/3 chance). Hired guards follow and defend you.
- **Healing**: Feed bread or cooked meat.

### 🧑‍🌾 Hunter Profession
- **Hunter's Table** block: Place in a village; jobless villagers become Hunters.
- **Trades**: 4 levels — raw meat for emeralds, tusks/antlers, custom weapons (Boar Tusk Dagger, Antler Greatsword, Hunter Bow), spectral arrows.

### 🛡️ Custom Equipment (Manifest Weapons + Armors)
- **Boar Hide Armor**: Craft from boar tusks (helmet, chestplate, leggings, boots).
- **Deer Hide Armor**: Craft from deer antlers (helmet, chestplate, leggings, boots).
- **Boar Tusk Dagger**: Applies Poison II for 3 seconds on hit.
- **Antler Greatsword**: Sweep attack hitting all nearby enemies for 3 damage + knockback.
- **Hunter Bow**: Durable bow (450 uses) with critical shot at full draw.

### 🌿 World Generation (Manifest Biomes + Structures)
- **Whispering Woods**: Custom biome (auto-registered via data pack) — boars and deer spawn here in higher density.
- **Hunter Outpost**: Cobblestone structure with Spruce pillars/roof, spawns in overworld. Contains a Hunter's Table, loot chest, and a Guard Villager.

### 🌱 Farming & Ranching (Manifest Farming + Ranching)
- **Wild Berry Bush**: Harvest for wild berries; grows in stages like sweet berry bushes.
- **Ranching**: Right-click cow/goat/sheep/mooshroom with a bucket for milk.

### 🔪 Hunting & Butchering (Manifest Hunting)
- **Butchering Table**: Convert raw vanilla meat (pork, beef, chicken, mutton, rabbit) into raw boar meat + bones. Awards hunting XP.

### 📈 Skills, RPG & Survival (Manifest Skills + RPG + Survival)
- **Skills**: Hunting, Combat, and Farming skills that level up with XP. Levels shown via action bar chat.
- **Player Classes**: Use `/class <hunter|warrior|scout|none>` to set your class. Multipliers apply automatically.
- **Class Tome** (`adventuremod:class_tome`): A reminder item showing your current class.
- **Thirst System**: Sprinting depletes thirst; drinking water buckets, potions, or milk restores it. At 0 thirst you take starvation damage.

### 🐾 Pets, Mythics & Bosses (Manifest Pets + Mythics + Bosses)
- **Forest Fox** (spawn egg): Tameable with sweet berries/glow berries, follows and fights hostiles.
- **Stag Spirit** (spawn egg): Mythical hostile creature that teleports, glows, and applies Glowing effect.
- **Forest Guardian** (spawn egg): Boss with 200 HP, boss bar, two phases. Phase 2 boosts damage/speed and adds a 6-block ground slam AoE. Drops Antler Greatsword and emerald blocks.

### ⚙️ Manifest Vanilla / Remodels
- **Vanilla Tweaks**: Mixin'd double-jump, wall-jump, dash, parry, fist combo, sprint-thirst.
- **Remodels & Animations**: Custom block/item/entity models and textures (hunter's table, butchering table, wild berry bush, all custom items/entities).

---

## 📝 Notes
- **Spawn eggs** are available in creative mode for all custom entities.
- All items have crafting recipes; raw meat can be smelted/campfire-cooked.
- The mod is **code-complete** with all 21 modules from `description.txt` implemented. If CI fails, ensure your toolchain is Java 21 and the Fabric Loader version is 0.19.2 or later.

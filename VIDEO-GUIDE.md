# 🎬 Video Showcase Guide — Adventure & Survival Expansion Mod

A scene-by-scene playbook for recording a video that proves the mod works.
Follow this top-to-bottom and you'll capture every feature on camera.

> **Lens:** Do each scene in **one take where possible** — show the action, then the result.
> Cut between scenes in editing. Aim for ~6–10 minutes total.

---

## 📋 Pre-Flight Checklist (before hitting Record)

1. **Install** Minecraft 1.21.1 + Fabric Loader + Fabric API 0.116.12+1.21.1.
2. Drop `adventuremod-1.0.0.jar` into `%appdata%\.minecraft\mods`.
3. Launch → confirm **"Adventure & Survival Expansion"** appears in the Mods list.
4. Create a **Creative** world for quick access to items + spawn eggs.
   - Keep a second **Survival** world handy for the thirst/sprint demonstration.
5. Recommended keybinds (vanilla defaults work): **Space** = jump, **Left Alt** = dash.
6. Video settings: turn **Particles to All**, render distance 12+, so effects show clearly.

---

## 🎬 Scene 1 — Intro & Mod Loaded (15 sec)

- Open the **Mods** list from the title screen.
- Highlight the **Adventure & Survival Expansion** entry, show the version 1.0.0.
- Say one sentence: "This mod adds movement, combat, animals, villagers, world-gen, and an RPG progression layer."

**✅ Pass:** the entry is present and blue (loaded, no errors).

---

## 🎬 Scene 2 — Movement Trio (45 sec)

**Double Jump**
1. Stand on flat ground, jump with **Space**, then press **Space again mid-air**.
2. Camera catches the **phantom flap sound** + **cloud particles** at your feet, and the extra hop.

**Wall Jump**
1. Build a 3-block-tall wall (or use a tree trunk).
2. Run at it, jump, hold **W** into the wall, press **Space** mid-air while touching it.
3. You should **leap backward off the wall** with a knock sound + cloud puff.

**Dodge Dash**
1. On open ground, press **Left Alt** while holding **W**.
2. You **dash forward**, poof particles trail behind, 1.5s cooldown before you can dash again.

**✅ Pass:** all three work; particles and sounds play.

---

## 🎬 Scene 3 — Combat: Fists, Parry, Classes (60 sec)

**Fist Combos**
1. Empty your hotbar (no weapon), spawn a pig: `/summon pig`.
2. Punch the pig **3 times quickly** (within ~1.5s).
   - Punch 1: normal hit.
   - Punch 2: extra knockback.
   - Punch 3: **sweep particles** + 6 damage (pig likely dies here).

**Parry**
1. Equip a **shield** in your off-hand, spawn a zombie: `/summon zombie`.
2. Let the zombie wind up, then **right-click** to raise the shield within ~0.4s of being hit.
3. **Anvil clang** + **spark particles**; the zombie gets **Slowness X + Weakness X** for 2s and is knocked back.

**Class Multipliers**
1. Type `/class warrior` in chat.
2. Punch a mob → bonus damage on top of the base hit (Warrior = +20%).
3. Type `/class scout` → notice you move faster.
4. Type `/class none` to reset.

**✅ Pass:** combo escalation visible, parry clang audible, class changes take effect.

---

## 🎬 Scene 4 — Custom Animals (45 sec)

**Wild Boar**
1. `/summon adventuremod:wild_boar` (or use the spawn egg from the creative inventory).
2. Highlight it's **neutral** — walks around calmly.
3. Hit it → it **angers and charges** at you.

**Deer**
1. `/summon adventuremod:deer`.
2. **Sprint** toward it → it flees.
3. **Sneak** + hold **wheat** (or apple) → it relaxes and approaches.
4. (Optional, time-lapse) Wait ~5 minutes → it **sheds an antler** item on the ground.

**✅ Pass:** boar charges when provoked; deer flees from sprinters, approaches when sneaking.

---

## 🎬 Scene 5 — Guard Villager & Hunter Profession (60 sec)

**Guard Villager**
1. `/summon adventuremod:guard_villager` → spawns with **iron sword + shield**.
2. Hold an **Emerald Block** (or **Golden Apple**) and right-click the guard → 1/3 chance to hire.
   - Success → heart particles, guard follows you.
   - Fail → smoke particles, try again.
3. Spawn a zombie nearby → the guard **fights it**, sometimes raising its shield.
4. Feed the hired guard **bread** or **cooked beef** when low HP → it heals.

**Hunter Profession**
1. Place a **Hunter's Table** block near a jobless villager (or find a Hunter Outpost in the world).
2. The villager becomes a **Hunter** (green apron).
3. Right-click the Hunter → show the **4 trade tiers**:
   - Tier 1: raw meat ↔ emeralds, arrows.
   - Tier 2: tusks/antlers → emeralds; emeralds → leather armor.
   - Tier 3: emeralds → **Boar Tusk Dagger**, **Antler Greatsword**.
   - Tier 4: emeralds → **Hunter Bow**, spectral arrows.

**✅ Pass:** hiring works, guard fights, trades appear and progress with leveling.

---

## 🎬 Scene 6 — Hunting & Butchering + Farming + Ranching (45 sec)

**Butchering Table**
1. Place a **Butchering Table**, hold **raw porkchop** (or beef/mutton), right-click the table.
2. It converts to **raw boar meat** + a **bone**, with a sweep sound. You also gain Hunting XP (watch the action-bar message).

**Farming — Wild Berry Bush**
1. Place a **Wild Berry Bush** (`/setblock ~ ~ ~ adventuremod:wild_berry_bush`), bonemeal it to grow stages.
2. Right-click the mature bush → drops **wild berries** + small **thirst** boost when eaten.

**Ranching**
1. Find or spawn a **cow** (or goat/sheep/mooshroom).
2. Hold an **empty bucket**, right-click the animal → **milk bucket** with the cow-milking sound.

**✅ Pass:** butchering converts items, berries harvest and quench thirst, animals can be milked.

---

## 🎬 Scene 7 — Custom Weapons & Armor (45 sec)

**Weapons** (give yourself each via creative inventory or `/give @s adventuremod:<item>`)
1. **Boar Tusk Dagger** — hit a mob → applies **Poison II for 3s**, blood-red dust particles.
2. **Antler Greatsword** — hit a mob surrounded by 2-3 others → the **sweep** hits all nearby enemies for 3 damage + knockback + sweep particles.
3. **Hunter Bow** — fully draw and release → arrow flies as a **critical** (sparkle particles), 450 durability.

**Armor**
1. Equip the **Boar Hide** set (helmet/chestplate/leggings/boots) → show the armor UI bar.
2. Equip the **Deer Hide** set → same, different color tint.

**✅ Pass:** poison applies, greatsword sweeps multiple targets, bow crits, both armor sets equip.

---

## 🎬 Scene 8 — Mounts (20 sec)

1. `/summon adventuremod:rideable_boar`.
2. Right-click with a **saddle** → saddle sound, boar is saddled.
3. Right-click with empty hand → **mount** the boar; steer with WASD (it follows your look direction).

**✅ Pass:** boar accepts a saddle and is rideable, steering responds.

---

## 🎬 Scene 9 — Pets, Mythics & Boss (90 sec) — *the climax*

**Pet: Forest Fox**
1. `/summon adventuremod:forest_fox` → tame with **sweet berries** (1/3 chance per berry).
2. Once tamed → it **follows** you and **attacks hostiles** you hit.

**Mythic: Stag Spirit**
1. `/summon adventuremod:stag_spirit` → **hostile**, glowing aura, ambient end-rod particles.
2. When it agros you → it **teleports** toward you and applies the **Glowing** effect (outline visible through walls).

**Boss: Forest Guardian**
1. `/summon adventuremod:forest_guardian` → **200 HP**, a **green boss bar** appears at the top.
2. Damage it past **50% HP** → **Phase 2** triggers: more damage, faster movement, a **ground-slam AoE** every 5s (warden sonic boom + explosion particles, 6-block radius knockback + slowness).
3. Kill it → drops an **Antler Greatsword** + **3–5 Emerald Blocks** + 100 XP orbs.

**✅ Pass:** fox tames and defends, stag spirit teleports and glows you, boss has a bar + phase 2 + drops loot.

---

## 🎬 Scene 10 — Skills, Progression & Thirst (60 sec)

**Skills**
1. In creative, break a fully-grown wheat crop → "[Skill] Farming leveled up" message on the action bar.
2. Kill a wild boar/deer → **Hunting XP**; kill a zombie → **Combat XP**.

**Class persistence**
1. Type `/class hunter` → log out and back in → your class is **remembered**.

**Thirst (use the Survival world here)**
1. Hold a **water bucket**, right-click → "[Thirst] fully quenched!" message.
2. Hold a **potion** or **milk bucket** → smaller thirst restore messages.
3. Sprint for a while → thirst depletes; at **0 thirst** you take starvation damage.

**✅ Pass:** skill-up messages fire, class persists across logout, thirst drains and damages at zero.

---

## 🎬 Scene 11 — World Generation (45 sec)

1. Create a **fresh world** and fly around (creative flight).
2. Point out:
   - **Wild boars** and **deer** roaming naturally.
   - A **Hunter Outpost** — a small cobblestone + spruce structure with a Hunter's Table, lantern, loot chest (emeralds, arrows, tusks/antlers, sometimes a Boar Tusk Dagger), and a **Guard Villager** inside.
   - (If found) the **Whispering Woods** biome with elevated boar/deer density.

> Outposts are **rare (1 in 120 chunks)** — for the video, either fly far or `/locate` a structure if you've added one; otherwise just keep one pre-found outpost coords ready to teleport to.

**✅ Pass:** animals spawn naturally, an outpost generates with loot + guard.

---

## 🎬 Scene 12 — Wrap-Up (20 sec)

- Open the **Advancements** screen (the book icon) → show the **"Adventure & Survival"** tab populated.
- Quick montage: dash, double-jump, parry clang, boss bar, hire a guard, drop the greatsword.
- Sign off: "All 21 modules from the manifest are in-game and working."

**✅ Pass:** advancement tab visible; montage covers the highlight reel.

---

## 🎞️ Editing Tips

- **Cut on action** — start each clip just before the input, end just after the effect lands.
- Keep **audio** of the sound effects (phantom flap, anvil clang, warden boom) — they carry a lot of the "proof it works" weight.
- Add **lower-third titles** for each feature name so viewers can skim.
- For the bossfight, leave Phase 2's ground-slam **uncut** — it's the most cinematic moment.

---

## ⚡ 5-Minute Smoke Test (if you only need a quick proof)

1. Launch → mod in list, no crash.
2. Creative world → `/class warrior` → punch a pig, see extra damage.
3. Press **Left Alt** → you dash.
4. Press **Space** twice mid-air → you double-jump.
5. `/summon adventuremod:forest_guardian` → kill it → drops loot + boss bar shows.

If all five work, the mod is functional end-to-end.

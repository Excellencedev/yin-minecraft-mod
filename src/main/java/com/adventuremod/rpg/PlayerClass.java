package com.adventuremod.rpg;

public enum PlayerClass {
    HUNTER("hunter", 1.2f, 1.0f, 0.9f),
    WARRIOR("warrior", 1.0f, 1.2f, 1.1f),
    SCOUT("scout", 1.1f, 0.8f, 1.3f),
    NONE("none", 1.0f, 1.0f, 1.0f);

    private final String name;
    public final float huntingMultiplier;
    public final float combatMultiplier;
    public final float speedMultiplier;

    PlayerClass(String name, float hunting, float combat, float speed) {
        this.name = name;
        this.huntingMultiplier = hunting;
        this.combatMultiplier = combat;
        this.speedMultiplier = speed;
    }

    public String getName() { return name; }

    public static PlayerClass fromName(String name) {
        for (PlayerClass pc : values()) {
            if (pc.name.equals(name)) return pc;
        }
        return NONE;
    }
}

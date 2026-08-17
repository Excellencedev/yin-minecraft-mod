package com.adventuremod.rpg;

import com.adventuremod.AdventureMod;
import com.adventuremod.progression.PlayerProgressionHolder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Arrays;

public class ModRpg {
    private static final SuggestionProvider<ServerCommandSource> CLASS_SUGGESTIONS = (context, builder) ->
            CommandSource.suggestMatching(Arrays.stream(PlayerClass.values()).map(PlayerClass::getName), builder);

    public static void registerRpg() {
        AdventureMod.LOGGER.info("Registering RPG for " + AdventureMod.MOD_ID);
        CommandRegistrationCallback.EVENT.register(ModRpg::registerCommands);
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, net.minecraft.command.CommandRegistryAccess registryAccess, net.minecraft.server.command.CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(net.minecraft.server.command.CommandManager.literal("class")
                .then(net.minecraft.server.command.CommandManager.argument("name", StringArgumentType.string())
                        .suggests(CLASS_SUGGESTIONS)
                        .executes(ModRpg::setClass)
                )
                .executes(ModRpg::showClass)
        );

        dispatcher.register(net.minecraft.server.command.CommandManager.literal("adventuremod")
                .then(net.minecraft.server.command.CommandManager.literal("status")
                        .executes(ModRpg::showStatus)
                )
                .then(net.minecraft.server.command.CommandManager.literal("help")
                        .executes(ModRpg::showHelp)
                )
                .executes(ModRpg::showHelp)
        );
    }

    private static int showStatus(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getPlayer() instanceof PlayerProgressionHolder holder) {
            var progression = holder.adventuremod$getProgression();
            var skills = progression.skills;
            var thirst = progression.thirst;
            source.sendFeedback(() -> Text.literal("§6[AdventureMod] Class: §f" + progression.playerClass.getName()), false);
            source.sendFeedback(() -> Text.literal("§6[AdventureMod] Thirst: §b" + thirst.getThirstLevel() + "/20 §7(saturation " + String.format("%.1f", thirst.getThirstSaturation()) + ")"), false);
            source.sendFeedback(() -> Text.literal("§6[AdventureMod] Skills: §aHunting " + skills.getHuntingLevel() + "§7, §cCombat " + skills.getCombatLevel() + "§7, §eFarming " + skills.getFarmingLevel()), false);
            source.sendFeedback(() -> Text.literal("§6[AdventureMod] Movement: §fpress jump again in midair for double jump; press Left Alt for dash."), false);
            return 1;
        }
        return 0;
    }

    private static int showHelp(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        source.sendFeedback(() -> Text.literal("§6AdventureMod commands:"), false);
        source.sendFeedback(() -> Text.literal("§e/adventuremod status §7- show class, thirst, skills, and controls"), false);
        source.sendFeedback(() -> Text.literal("§e/class <hunter|warrior|scout|none> §7- choose your RPG class"), false);
        return 1;
    }

    private static int setClass(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        String name = StringArgumentType.getString(context, "name");
        PlayerClass pc = PlayerClass.fromName(name);
        if (pc == PlayerClass.NONE && !"none".equalsIgnoreCase(name)) {
            source.sendError(Text.literal("Unknown class: " + name + ". Valid: hunter, warrior, scout, none"));
            return 0;
        }
        if (source.getPlayer() instanceof PlayerProgressionHolder holder) {
            holder.adventuremod$getProgression().playerClass = pc;
            source.sendFeedback(() -> Text.literal("§6Set your class to " + pc.getName()), true);
            return 1;
        }
        return 0;
    }

    private static int showClass(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getPlayer() instanceof PlayerProgressionHolder holder) {
            PlayerClass pc = holder.adventuremod$getProgression().playerClass;
            source.sendFeedback(() -> Text.literal("§6Current class: " + pc.getName()), false);
            return 1;
        }
        return 0;
    }
}

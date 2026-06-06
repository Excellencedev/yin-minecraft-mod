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

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, net.minecraft.command.CommandRegistryAccess registryAccess) {
        dispatcher.register(net.minecraft.server.command.CommandManager.literal("class")
                .then(net.minecraft.server.command.CommandManager.argument("name", StringArgumentType.string())
                        .suggests(CLASS_SUGGESTIONS)
                        .executes(ModRpg::setClass)
                )
                .executes(ModRpg::showClass)
        );
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

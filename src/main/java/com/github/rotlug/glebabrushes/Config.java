package com.github.rotlug.glebabrushes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<Integer> MAX_SELECTION_SIZE = BUILDER.define("max_selection_size", 250);
    private static final ModConfigSpec.BooleanValue ONE_BLOCK_MODE = BUILDER.define("one_block_mode", false);

    public static int maxSelectionSize;
    public static boolean one_block_mode;

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        maxSelectionSize = MAX_SELECTION_SIZE.get();
        one_block_mode = ONE_BLOCK_MODE.get();
    }
}

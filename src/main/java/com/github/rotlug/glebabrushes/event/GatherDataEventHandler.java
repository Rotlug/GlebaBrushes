package com.github.rotlug.glebabrushes.event;

import com.github.rotlug.glebabrushes.Brushes;
import com.github.rotlug.glebabrushes.datagen.BLangProvider;
import com.github.rotlug.glebabrushes.datagen.BRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class GatherDataEventHandler {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> future = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        boolean client = event.includeClient();
        boolean server = event.includeServer();

        generator.addProvider(server, new BLangProvider(output, Brushes.MODID, "en_us"));
        generator.addProvider(server, new BRecipeProvider(output, future));
    }
}

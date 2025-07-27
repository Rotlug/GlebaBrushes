package com.github.rotlug.glebabrushes.event;

import com.github.rotlug.glebabrushes.BrushPayload;
import com.github.rotlug.glebabrushes.ServerPayloadHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class RegisterPayloadHandler {
    @SubscribeEvent // on the mod event bus
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                BrushPayload.TYPE,
                BrushPayload.STREAM_CODEC,
                ServerPayloadHandler::handleDataOnMain
        );
    }
}

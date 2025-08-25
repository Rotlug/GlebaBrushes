package com.github.rotlug.glebabrushes.event;

import com.github.rotlug.glebabrushes.Config;
import com.github.rotlug.glebabrushes.item.BaseBrushItem;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class EntityPlaceBlockHandler {
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (!Config.one_block_mode) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!(player.getOffhandItem().getItem() instanceof BaseBrushItem brush)) return;

        PacketDistributor.sendToServer(brush.getBrushPayload(event.getPos()));
    }
}

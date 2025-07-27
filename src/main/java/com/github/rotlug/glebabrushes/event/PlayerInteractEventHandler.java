package com.github.rotlug.glebabrushes.event;

import com.github.rotlug.glebabrushes.item.BaseBrushItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class PlayerInteractEventHandler {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack held = event.getItemStack();

        if (held.getItem() instanceof BaseBrushItem) {
            // Cancel the interaction so the chest won't open
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);

            // Simulate use-on logic
            UseOnContext context = new UseOnContext(player, event.getHand(), event.getHitVec());
            held.getItem().useOn(context);
        }
    }

}

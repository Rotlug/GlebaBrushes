package com.github.rotlug.glebabrushes.event;

import com.github.rotlug.glebabrushes.*;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.createmod.catnip.outliner.Outliner;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEventHandler {
    private static ItemStack lastHeldItem;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) {
            Brushes.state = null;
            return;
        }

        // If player switched an item, reset the brush state
        ItemStack currentItem = player.getMainHandItem();

        if (currentItem != lastHeldItem) {
            Brushes.state = null;
            lastHeldItem = currentItem;
            return;
        }

        if (Brushes.state == null || Brushes.state.level != player.level()) return;

        Level level = player.level();

        // Find blockpos
        HitResult hit = minecraft.hitResult;
        if (hit instanceof BlockHitResult blockHitResult && hit.getType() == HitResult.Type.BLOCK) {
            assert Brushes.state.startPos != null;

            Brushes.state.endPos = blockHitResult.getBlockPos();
            Outliner outliner = Outliner.getInstance();

            AABB bb = createAABB(Brushes.state.startPos, Brushes.state.endPos, blockHitResult.getDirection());
            if (getAABBSize(bb) > Config.maxSelectionSize) {
                Brushes.state.error = BrushStateError.SELECTION_TOO_BIG;
                player.displayClientMessage(Brushes.state.error.translatable, true);
            }
            else {
                Brushes.state.error = null; // TODO: do this better later
            }

            outliner.chaseAABB(Brushes.state.name, bb)
                    .colored(Brushes.state.error == null ? Brushes.state.color : Color.RED)
                    .lineWidth(1 / 16f)
                    .withFaceTexture(SpecialTextures.PAINT)
            ;
        }
    }

    public static AABB createAABB(BlockPos pos1, BlockPos pos2, Direction direction) {
        int minX = Math.min(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());

        int maxX = Math.max(pos1.getX(), pos2.getX()) + 1;
        int maxY = Math.max(pos1.getY(), pos2.getY()) + 1 ;
        int maxZ = Math.max(pos1.getZ(), pos2.getZ()) + 1;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static double getAABBSize(AABB bb) {
        return bb.getXsize() * bb.getYsize() * bb.getZsize();
    }
}

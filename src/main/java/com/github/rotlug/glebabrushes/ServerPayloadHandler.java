package com.github.rotlug.glebabrushes;

import com.github.rotlug.glebabrushes.compat.Supplementaries;
import com.github.rotlug.glebabrushes.item.BaseBrushItem;
import net.mehvahdjukaar.moonlight.api.set.BlocksColorAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ServerPayloadHandler {
    public static void handleDataOnMain(final BrushPayload data, final IPayloadContext context) {
        Player player = context.player();

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ServerLevel level = serverPlayer.serverLevel();
        Stream<BlockPos> blockPos = BlockPos.betweenClosedStream(data.start(), data.end());

        if (data.brushId().startsWith("paint")) {
             DyeColor color = DyeColor.byName(removeFirstSegment(data.brushId()), DyeColor.WHITE);
             paintBrush(serverPlayer, level, blockPos, color);
        }

        switch (data.brushId()) {
            case "wax" -> {waxBrush(serverPlayer, level, blockPos);}
            case "soap" -> {paintBrush(serverPlayer, level, blockPos, null);}
            default -> {}
        }
    }

    public static String removeFirstSegment(String input) {
        int underscoreIndex = input.indexOf('_');
        if (underscoreIndex == -1 || underscoreIndex == input.length() - 1) {
            return ""; // No underscore found or nothing after it
        }
        return input.substring(underscoreIndex + 1);
    }

    public static boolean isBed(BlockState state) {
        return state.hasProperty(BedBlock.PART);
    }

    public static Block getColor(Block block, DyeColor color) {
        Block newBlock = BlocksColorAPI.changeColor(block, color);
        if (newBlock != null) {
            return newBlock;
        }

        return block;
    }

    public static void spawnParticleForBlock(ServerLevel serverLevel, BlockPos blockPos) {
        serverLevel.sendParticles(ParticleTypes.WAX_ON, blockPos.getCenter().x, blockPos.getCenter().y, blockPos.getCenter().z, 5, 0.3, 0.3, 0.3, 1);
    }
    public static void replaceBlock(ServerLevel level, BlockPos pos, Block block) {
        CompoundTag entityData = null;

        BlockEntity oldEntity = level.getBlockEntity(pos);
        if (oldEntity != null) {
            entityData = oldEntity.saveWithFullMetadata(level.registryAccess());
        }

        if (!isBed(level.getBlockState(pos))) {
            level.setBlockAndUpdate(pos, block.withPropertiesOf(level.getBlockState(pos)));
        }

        // Restore entity data
        if (entityData != null) {
            BlockEntity newEntity = level.getBlockEntity(pos);
            if (newEntity != null) {
                newEntity.loadWithComponents(entityData, level.registryAccess());
            }
        }
    }

    /*
    Method for each brush type
     */
    public static void paintBrush(ServerPlayer player, ServerLevel level, Stream<BlockPos> blocks, @Nullable DyeColor color) {
        BrushItemStack brush = getBrush(player);
        if (brush == null) return;

        AtomicInteger damageAmount = new AtomicInteger(0);

        blocks.forEach(blockPos -> {;
            if (damageAmount.get() > brush.damageLeft()) return;

            BlockState oldState = level.getBlockState(blockPos);
            Block uncolored = oldState.getBlock();

            if (uncolored == Blocks.AIR) return;

            Block colored = getColor(uncolored, color);
            if (colored == uncolored) return;

            replaceBlock(level, blockPos, getColor(colored, color));

            spawnParticleForBlock(level, blockPos);
            damageAmount.addAndGet(1);
        });

        if (color == null && ModList.get().isLoaded("supplementaries")) {
            level.playSound(null, player.blockPosition(), Supplementaries.soapSound(), SoundSource.PLAYERS);
        }
        else {
            level.playSound(null, player.blockPosition(), SoundEvents.HONEY_BLOCK_SLIDE, SoundSource.PLAYERS);
        }

        brush.hurt(player, damageAmount.get());
    }

    public static void waxBrush(ServerPlayer player, ServerLevel level, Stream<BlockPos> blocks) {
        BrushItemStack brush = getBrush(player);
        if (brush == null) return;

        AtomicInteger damageAmount = new AtomicInteger(0);

        blocks.forEach(blockPos -> {
            if (damageAmount.get() > brush.damageLeft()) return;

            BlockState state = level.getBlockState(blockPos);
            ResourceLocation originalId = BuiltInRegistries.BLOCK.getKey(state.getBlock());

            ResourceLocation waxedLocation = ResourceLocation.fromNamespaceAndPath(originalId.getNamespace(), "waxed_" + originalId.getPath());

            Block waxedBlock = BuiltInRegistries.BLOCK.get(waxedLocation);

            if (waxedBlock == Blocks.AIR) return;

            replaceBlock(level, blockPos, waxedBlock);
            spawnParticleForBlock(level, blockPos);

            damageAmount.addAndGet(1);
        });

        level.playSound(null, player.blockPosition(), SoundEvents.HONEYCOMB_WAX_ON, SoundSource.PLAYERS);
        brush.hurt(player, damageAmount.get());
    }

    public static BrushItemStack getBrush(ServerPlayer player) {
        ItemStack brush = player.getMainHandItem();
        EquipmentSlot slot = EquipmentSlot.MAINHAND;

        if (!(brush.getItem() instanceof BaseBrushItem)) {
            brush = player.getOffhandItem();
            slot = EquipmentSlot.OFFHAND;
        }
        if (!(brush.getItem() instanceof BaseBrushItem)) return null;

        return new BrushItemStack(brush, slot);
    }
}

class BrushItemStack {
    ItemStack stack;
    EquipmentSlot slot;

    public BrushItemStack(ItemStack stack, EquipmentSlot slot) {
        this.slot = slot;
        this.stack = stack;
    }

    int damageLeft() {
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    void hurt(ServerPlayer player, int amount) {
        player.setItemSlot(slot, stack.hurtAndConvertOnBreak(amount, Items.BRUSH, player, slot));
    }
}

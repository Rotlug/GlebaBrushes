package com.github.rotlug.glebabrushes.item;

import com.github.rotlug.glebabrushes.BrushPayload;
import com.github.rotlug.glebabrushes.BrushState;
import com.github.rotlug.glebabrushes.Brushes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class BaseBrushItem extends Item {

    public BaseBrushItem(int durability) {
        super(new Properties().stacksTo(1).durability(durability));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (!level.isClientSide || player == null) return InteractionResult.SUCCESS;

        if (player.isCrouching()) {
            // Cancel the state
            Brushes.state = null;
            return InteractionResult.SUCCESS;
        }

        if (Brushes.state == null) {
            Brushes.state = new BrushState(level);
            Brushes.state.startPos = context.getClickedPos();
            onBrushStart(Brushes.state, context);
        }
        else {
            PacketDistributor.sendToServer(getBrushPayload());
            onBrushEnd(Brushes.state, context);
            Brushes.state = null;
        }

        return super.useOn(context);
    }

    public BrushPayload getBrushPayload() {
        assert Brushes.state != null;
        return new BrushPayload(Brushes.state.name, Brushes.state.startPos, Brushes.state.endPos);
    }

    public void onBrushStart(BrushState state, UseOnContext context) {
        state.level.playLocalSound(context.getPlayer(), brushStartSound(), SoundSource.PLAYERS, 1f, 1f);
    }

    public void onBrushEnd(BrushState state, UseOnContext context) {}

    public SoundEvent brushStartSound() {
        return SoundEvents.WOOL_STEP;
    }
}

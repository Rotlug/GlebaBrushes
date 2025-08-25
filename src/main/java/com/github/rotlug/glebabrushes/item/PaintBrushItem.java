package com.github.rotlug.glebabrushes.item;

import com.github.rotlug.glebabrushes.BrushState;
import net.createmod.catnip.theme.Color;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.UseOnContext;

import javax.annotation.Nullable;

public class PaintBrushItem extends BaseBrushItem {
    DyeColor dye;

    public PaintBrushItem(DyeColor dye) {
        super(250);
        this.dye = dye;
    }

    @Override
    public void onBrushStart(BrushState state, UseOnContext context) {
        state.color = new Color(dye.getTextureDiffuseColor());

        super.onBrushStart(state, context);
    }

    @Override
    public String getName() {
        return "paint_" + dye;
    }
}

package com.github.rotlug.glebabrushes.item;

import com.github.rotlug.glebabrushes.BrushState;
import net.createmod.catnip.theme.Color;
import net.minecraft.world.item.context.UseOnContext;

public class SoapBrushItem extends BaseBrushItem{
    public SoapBrushItem() {
        super(250);
    }

    @Override
    public void onBrushStart(BrushState state, UseOnContext context) {
        state.color = new Color(241, 161, 214); // Soap Color
        super.onBrushStart(state, context);
    }

    @Override
    public String getName() {
        return "soap";
    }
}
